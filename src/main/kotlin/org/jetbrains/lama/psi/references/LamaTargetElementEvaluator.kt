package org.jetbrains.lama.psi.references

import com.intellij.codeInsight.TargetElementEvaluatorEx2
import com.intellij.psi.PsiElement
import org.jetbrains.lama.psi.LamaPsiUtil.isDefinitionIdentifier
import org.jetbrains.lama.psi.LamaPsiUtil.isDefinitionOperator
import org.jetbrains.lama.psi.api.LamaIdentifierExpression
import org.jetbrains.lama.psi.api.LamaOperator

class LamaTargetElementEvaluator : TargetElementEvaluatorEx2() {
  override fun isAcceptableNamedParent(parent: PsiElement): Boolean {
    return when (parent) {
      is LamaIdentifierExpression -> parent.isDefinitionIdentifier()
      is LamaOperator -> parent.isDefinitionOperator()
      else -> false
    }
  }
}