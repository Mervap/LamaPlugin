package org.jetbrains.lama.psi.controlFlow

import com.intellij.codeInsight.controlflow.ControlFlow
import com.intellij.codeInsight.controlflow.ControlFlowBuilder
import com.intellij.codeInsight.controlflow.Instruction
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import org.jetbrains.lama.psi.LamaRecursiveVisitor
import org.jetbrains.lama.psi.api.*

internal fun buildControlFlow(controlFlowHolder: LamaControlFlowHolder): LamaControlFlow {
  val builder = LamaControlFlowBuilder()
  return LamaControlFlow(builder.build(controlFlowHolder).instructions)
}

private class LamaControlFlowBuilder : LamaRecursiveVisitor() {
  private val builder = ControlFlowBuilder()

  fun build(root: LamaPsiElement): ControlFlow {
    return builder.build(this, root)
  }

  override fun visitPsiElement(element: LamaPsiElement) {
    super.visitElement(element)
    startNode(element)
  }

  override fun visitControlFlowHolder(holder: LamaControlFlowHolder) {
    startNode(holder)
  }

  override fun visitArgumentList(argumentList: LamaArgumentList) {
    argumentList.expressionSeriesList.forEach { it.accept(this) }
    startNode(argumentList)
  }

  override fun visitArrayExpression(array: LamaArrayExpression) {
    array.expressionSeriesList.forEach { it.accept(this) }
    startNode(array)
  }

  override fun visitArrayPattern(pattern: LamaArrayPattern) {
    pattern.patternList.forEach { it.accept(this) }
    startNode(pattern)
  }

  override fun visitAssignmentExpression(op: LamaAssignmentExpression) {
    op.operator?.accept(this)
    op.assignment?.accept(this)
    op.assignee.accept(this)
    startNode(op)
  }

  override fun visitCaseStatement(case: LamaCaseStatement) {
    case.expressionSeries?.accept(this)
    val branches = mutableListOf<Instruction>()
    for (branch in case.caseBranchList) {
      val pattern = branch.pattern.accept()
      branch.scope?.accept()
      val branchIns = startNode(branch)
      builder.addEdge(pattern, branchIns)
      branches.add(branchIns)
    }
    val end = startNode(case)
    for (branch in branches) {
      builder.addEdge(branch, end)
    }
  }

  override fun visitDoStatement(o: LamaDoStatement) {
    val beforeBody = builder.prevInstruction
    o.body.accept()
    val condition = o.condition.accept()
    startNode(o)
    builder.addEdge(condition, beforeBody)
  }

  override fun visitExpressionSeries(series: LamaExpressionSeries) {
    series.expressionList.forEach { it.accept(this) }
    startNode(series)
  }

  override fun visitForStatement(forSt: LamaForStatement) {
    val beforeAll = forSt.beforeAll.accept()
    val beforeEach = forSt.beforeEach.accept()
    val end = startNode(forSt)
    builder.prevInstruction = beforeEach
    forSt.body.accept()
    val afterLoop = forSt.afterEach.map { it.accept() }.lastOrNull() ?: builder.prevInstruction
    builder.addEdge(afterLoop, beforeAll)
    builder.prevInstruction = end
  }

  override fun visitFunctionBody(body: LamaFunctionBody) {
    body.scope?.accept(this)
    startNode(body)
  }

  override fun visitIfStatement(o: LamaIfStatement) {
    val branchs = mutableListOf<Instruction>()
    for (branch in o.ifBranchList) {
      val condition = branch.condition?.accept()
      branch.scope?.accept()
      val branchIns = startNode(branch)
      builder.addEdge(condition, branchIns)
      branchs.add(branchIns)
    }
    val end = startNode(o)
    for (scope in branchs) {
      builder.addEdge(scope, end)
    }
  }

  override fun visitListExpression(list: LamaListExpression) {
    list.expressionSeriesList.forEach { it.accept(this) }
    startNode(list)
  }

  override fun visitListPattern(list: LamaListPattern) {
    list.patternList.forEach { it.accept(this) }
    startNode(list)
  }

  override fun visitOperatorExpression(op: LamaOperatorExpression) {
    op.operator?.accept(this)
    op.leftExpr?.accept(this)
    op.rightExpr?.accept(this)
    startNode(op)
  }

  override fun visitParameterList(paramList: LamaParameterList) {
    paramList.patternList.forEach { it.accept(this) }
    startNode(paramList)
  }

  override fun visitParenthesizedExpression(par: LamaParenthesizedExpression) {
    par.scope?.accept(this)
    startNode(par)
  }

  override fun visitSOrAtPattern(pattern: LamaSOrAtPattern) {
    pattern.identifierExpression.accept(this)
    pattern.patternList.forEach { it.accept(this) }
    startNode(pattern)
  }

  override fun visitSubscriptionExpression(sub: LamaSubscriptionExpression) {
    sub.expressionSeries?.accept(this)
    sub.expression.accept(this)
    startNode(sub)
  }

  override fun visitVariableDefinition(def: LamaVariableDefinition) {
    def.defaultValueExpression?.accept(this)
    def.nameIdentifier?.accept(this)
    startNode(def)
  }

  override fun visitVariableDefinitionSeries(defSeries: LamaVariableDefinitionSeries) {
    defSeries.variableDefinitionList.forEach { it.accept(this) }
    startNode(defSeries)
  }

  override fun visitSOrCallExpression(sOrCall: LamaSOrCallExpression) {
    sOrCall.expression.accept(this)
    sOrCall.argumentList?.accept(this)
    startNode(sOrCall)
  }

  override fun visitWhileStatement(whileSt: LamaWhileStatement) {
    val beforeCondition = builder.prevInstruction
    val afterCondition = whileSt.condition.map { it.accept() }.lastOrNull() ?: beforeCondition
    val end = startNode(whileSt)
    builder.prevInstruction = afterCondition
    val body = whileSt.body.accept()
    builder.addEdge(body, beforeCondition)
    builder.prevInstruction = end
  }

  override fun visitSyntaxBinding(binding: LamaSyntaxBinding) {
    binding.syntaxPrimaryExpression.accept()
    binding.pattern.accept()
    startNode(binding)
  }

  override fun visitSyntaxExpression(syntax: LamaSyntaxExpression) {
    val branches = mutableListOf<Instruction>()
    for (branch in syntax.syntaxSeqList) {
      branch.syntaxBindingList.forEach { it.accept() }
      branch.syntaxSeqBody?.accept()
      branches.add(startNode(branch))
    }
    val end = startNode(syntax)
    for (branch in branches) {
      builder.addEdge(branch, end)
    }
  }

  private fun startNode(o: LamaPsiElement): Instruction {
    return builder.startNode(o)
  }

  private fun LamaPsiElement?.accept(): Instruction {
    this?.accept(this@LamaControlFlowBuilder)
    return builder.prevInstruction
  }
}