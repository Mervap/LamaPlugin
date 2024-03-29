@file:OptIn(ExperimentalContracts::class)

package org.jetbrains.lama.psi.controlFlow

import com.intellij.codeInsight.controlflow.Instruction
import com.intellij.diagnostic.AttachmentFactory
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.progress.ProgressManager
import com.intellij.psi.PsiElement
import com.intellij.util.containers.Stack
import com.jetbrains.rd.util.first
import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentMapOf
import org.jetbrains.lama.psi.LamaPsiUtil.controlFlowContainer
import org.jetbrains.lama.psi.LamaPsiUtil.isDefinitionIdentifier
import org.jetbrains.lama.psi.LamaPsiUtil.isDefinitionOperator
import org.jetbrains.lama.psi.LamaPsiUtil.isNotDefinitionOperator
import org.jetbrains.lama.psi.LamaPsiUtil.isNotIdentifierReference
import org.jetbrains.lama.psi.api.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun LamaControlFlowHolder.analyzeLocals(): Map<LamaControlFlowHolder, LocalAnalysisResult> {
  val result = mutableMapOf<LamaControlFlowHolder, LocalAnalysisResult>()
  Analyzer(this, result).process(LocalSymbolInfos())
  return result
}

sealed class SymbolInfo<T : LamaPsiElement>(val name: String, val definition: T) {
  val reads = mutableListOf<T>()
  val writes = mutableListOf(definition)
}

class IdentifierSymbolInfo(name: String, definition: LamaIdentifierExpression) :
  SymbolInfo<LamaIdentifierExpression>(name, definition)

class OperatorSymbolInfo(name: String, definition: LamaOperator) : SymbolInfo<LamaOperator>(name, definition)

class LocalSymbolInfos(private val symbols: PersistentMap<String, SymbolInfo<*>> = persistentMapOf()) {
  val symbolInfos: ImmutableCollection<SymbolInfo<*>>
    get() = symbols.values

  fun withSymbol(name: String, symbolInfo: SymbolInfo<*>): LocalSymbolInfos {
    val newSymbols = symbols.put(name, symbolInfo)
    return LocalSymbolInfos(newSymbols)
  }

  @Suppress("UNCHECKED_CAST")
  operator fun <T : LamaPsiElement> get(name: String): SymbolInfo<T>? {
    return symbols[name] as SymbolInfo<T>?
  }
}

data class LocalAnalysisResult(
  val localSymbolsInfos: Map<Instruction, LocalSymbolInfos>,
  val summaryInfo: LocalSymbolInfos,
  val closure: Set<SymbolInfo<*>>,
)

private class Analyzer(
  private val controlFlowHolder: LamaControlFlowHolder,
  private val controlFlowResults: MutableMap<LamaControlFlowHolder, LocalAnalysisResult>,
) {
  private val controlFlow = controlFlowHolder.controlFlow
  private val closure = mutableSetOf<SymbolInfo<*>>()
  private val result = mutableMapOf<Instruction, LocalSymbolInfos>()
  private val definitionsToAnalyze = mutableListOf<LamaControlFlowHolder>()
  private val destructuringStack = Stack<LocalSymbolInfos>()

  fun process(initialInfo: LocalSymbolInfos): LocalAnalysisResult {
    ProgressManager.checkCanceled()
    val analyzeDefinitionMarker = controlFlowHolder.children.firstOrNull { it is LamaExpression }
    val instructions = controlFlow.instructions
    result[instructions.first()] = initialInfo

    var summaryInfo = initialInfo
    for (instruction in instructions.drop(1)) {
      ProgressManager.checkCanceled()
      if (instruction.element == DESTRUCT_ELEMENT) {
        destructuringStack.push(summaryInfo)
        result[instruction] = summaryInfo
        continue
      }

      val info = joinPredInfos(instruction)
      if (instruction.element == analyzeDefinitionMarker) {
        analyzeInnerControlFlowHolder(info)
      }
      summaryInfo = info.updateWith(instruction)
      result[instruction] = summaryInfo
    }
    if (definitionsToAnalyze.isNotEmpty()) {
      analyzeInnerControlFlowHolder(summaryInfo)
    }

    val analysisResult = LocalAnalysisResult(result, summaryInfo, closure.toSet())
    controlFlowResults[controlFlowHolder] = analysisResult
    return analysisResult
  }

  private fun analyzeInnerControlFlowHolder(initInfo: LocalSymbolInfos) {
    definitionsToAnalyze.forEach { initInfo.analyzeInnerControlFlowHolder(it) }
    definitionsToAnalyze.clear()
  }

  private fun LocalSymbolInfos.updateWith(instruction: Instruction): LocalSymbolInfos {
    when (val element = instruction.element) {
      is LamaVariableDefinition -> {
        element.nameIdentifier?.let { return addWrite(it) }
      }
      is LamaFunctionDefinition -> {
        val res = element.nameIdentifier?.let { addWrite(it) } ?: this
        if (element != controlFlowHolder) {
          definitionsToAnalyze.add(element)
//          res.analyzeInnerControlFlowHolder(element)
        }
        return res
      }
      is LamaInfixOperatorDefinition -> {
        val res = addWrite(element.nameOperator)
        if (element != controlFlowHolder) {
          definitionsToAnalyze.add(element)
//          res.analyzeInnerControlFlowHolder(element)
        }
        return res
      }
      is LamaAssignmentExpression -> {
        val assignee = element.assignee
        if (assignee is LamaIdentifierExpression) {
          return addWrite(assignee)
        }
      }
      is LamaSOrAtPattern -> {
        return addWrite(element.identifierExpression)
      }
      is LamaIdentifierExpression -> {
        val parent = element.parent
        if (parent !is LamaSOrAtPattern && element.isNotIdentifierReference()) {
          addRead(element)
        }
      }
      is LamaControlFlowHolder -> {
        val parent = element.parent
        if (
          parent is LamaForStatement && parent.beforeAll == element ||
          parent is LamaDoStatement && parent.body == element
        ) {
          val analysisResult = analyzeInnerControlFlowHolder(element)
          return analysisResult.summaryInfo
        }
        else if (element != controlFlowHolder) {
          analyzeInnerControlFlowHolder(element)
        }
      }
      is LamaInfixOperator -> {
        if (element.isNotDefinitionOperator()) {
          addRead(element)
        }
      }
    }
    return this
  }

  private fun LocalSymbolInfos.analyzeInnerControlFlowHolder(element: LamaControlFlowHolder): LocalAnalysisResult {
    val analysisResult = Analyzer(element, controlFlowResults).process(this)
    for (variableDescriptor in analysisResult.closure) {
      if (variableDescriptor.definition.controlFlowContainer != controlFlowHolder) {
        closure.add(variableDescriptor)
      }
    }
    return analysisResult
  }

  private fun LocalSymbolInfos.addWrite(element: LamaIdentifierExpression): LocalSymbolInfos {
    val name = element.name
    if (element.isDefinitionIdentifier()) {
      return withSymbol(name, IdentifierSymbolInfo(name, element))
    }

    val oldSymbolInfo = get<LamaIdentifierExpression>(name) ?: return withSymbol(name, IdentifierSymbolInfo(name, element))
    oldSymbolInfo.writes.add(element)
    return this
  }

  private fun LocalSymbolInfos.addWrite(element: LamaOperator): LocalSymbolInfos {
    val name = element.name
    if (element.isDefinitionOperator()) {
      return withSymbol(name, OperatorSymbolInfo(name, element))
    }

    val oldSymbolInfo = get<LamaOperator>(name) ?: return withSymbol(name, OperatorSymbolInfo(name, element))
    oldSymbolInfo.writes.add(element)
    return this
  }

  private fun LocalSymbolInfos.addRead(element: LamaIdentifierExpression) = addRead(element, element.name)
  private fun LocalSymbolInfos.addRead(element: LamaInfixOperator) = addRead(element, element.name)

  private fun <T : LamaPsiElement> LocalSymbolInfos.addRead(element: T, name: String) {
    val variableDescription = get<T>(name) ?: return
    if (variableDescription.definition.controlFlowContainer != controlFlowHolder) {
      closure.add(variableDescription)
    }
    variableDescription.reads.add(element)
  }

  private fun joinPredInfos(instruction: Instruction): LocalSymbolInfos {
    val element = instruction.element
    if (isDestructuringElement(element)) {
      if (destructuringStack.isEmpty()) {
        val attachment = element.containingFile?.virtualFile?.let { AttachmentFactory.createAttachment(it) }
        val message = "No found destructuring marker for $instruction"
        if (attachment == null) {
          logger.error(message)
        }
        else {
          logger.error(message, attachment)
        }
        return result.first().value
      }
      return destructuringStack.pop()
    }
    return joinPredInfos(
      instruction.allPred()
        .filter { pred ->
          controlFlow.isReachable(pred) && pred.num() < instruction.num()
        }
        .distinct()
        .sortedBy { it.num() }
        .map { result[it]!! }
    )
  }

  @OptIn(ExperimentalContracts::class)
  private fun isDestructuringElement(element: PsiElement?): Boolean {
    contract {
      returns(true) implies (element != null)
    }
    return when (element) {
      is LamaCaseBranch -> {
        //   [a, b] -> c
        // | [b, e] -> <caret> -- a and b invisible
        true
      }
      is LamaForStatement -> {
        // for var i = 0, i < 10, i := 1 do
        //   -- here i is visible
        // od
        // -- but here not
        true
      }
      is LamaDoStatement -> {
        // do
        //   var n = read();
        // while n != 0 -- here n is visible
        // od;
        // -- but here not
        true
      }
      is LamaSyntaxSeq -> {
        // syntax (
        //   t = token["s"]  { -- t is visible here } |
        //   tt = token["z"] { -- but not here }
        // )
        true
      }
      else -> false
    }
  }

  private fun joinPredInfos(infos: List<LocalSymbolInfos>): LocalSymbolInfos {
    if (infos.size == 1) {
      return infos.single()
    }
    val merged = persistentMapOf<String, SymbolInfo<*>>().mutate {
      for (info in infos) {
        for (symbolInfo in info.symbolInfos) {
          it[symbolInfo.name] = symbolInfo
        }
      }
    }
    return LocalSymbolInfos(merged)
  }

  companion object {
    private val logger = logger<LamaControlFlowHolder>()
  }
}
