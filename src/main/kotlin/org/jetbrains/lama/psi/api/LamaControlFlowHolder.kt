package org.jetbrains.lama.psi.api

import com.intellij.codeInsight.controlflow.Instruction
import org.jetbrains.lama.psi.controlFlow.LamaControlFlow
import org.jetbrains.lama.psi.controlFlow.LocalAnalysisResult
import org.jetbrains.lama.psi.controlFlow.LocalSymbolInfos

interface LamaControlFlowHolder : LamaPsiElement {
  val controlFlow: LamaControlFlow
  val localAnalysisResult: LocalAnalysisResult

  fun getLocalSymbolInfo(element: LamaPsiElement): LocalSymbolInfos? {
    val instruction = controlFlow.getInstructionByElement(element) ?: return null
    return getLocalSymbolInfo(instruction)
  }

  fun getLocalSymbolInfo(instruction: Instruction): LocalSymbolInfos? {
    return localAnalysisResult.localSymbolsInfos[instruction]
  }
}
