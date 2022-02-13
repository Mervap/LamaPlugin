package org.jetbrains.lama.psi.controlFlow

import com.intellij.codeInsight.controlflow.ControlFlow
import com.intellij.codeInsight.controlflow.ControlFlowProvider
import com.intellij.codeInsight.controlflow.Instruction
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.lama.psi.api.LamaControlFlowHolder

class LamaControlFlowProvider: ControlFlowProvider {
  override fun getAdditionalInfo(instruction: Instruction): String? = null

  override fun getControlFlow(element: PsiElement): ControlFlow? {
    return PsiTreeUtil.getParentOfType(element, LamaControlFlowHolder::class.java, false)?.controlFlow
  }
}