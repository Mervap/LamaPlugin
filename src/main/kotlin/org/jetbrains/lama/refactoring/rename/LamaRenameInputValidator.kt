package org.jetbrains.lama.refactoring.rename

import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.patterns.StandardPatterns
import com.intellij.psi.PsiElement
import com.intellij.refactoring.rename.RenameInputValidator
import com.intellij.util.ProcessingContext
import org.jetbrains.lama.psi.api.LamaDefinition
import org.jetbrains.lama.psi.api.LamaIdentifierExpression
import org.jetbrains.lama.psi.api.LamaOperator
import org.jetbrains.lama.refactoring.LamaNamesValidator

class LamaRenameInputValidator : RenameInputValidator {
  override fun getPattern(): ElementPattern<PsiElement> {
    return StandardPatterns.or(
      PlatformPatterns.psiElement(LamaIdentifierExpression::class.java),
      PlatformPatterns.psiElement(LamaOperator::class.java),
      PlatformPatterns.psiElement(LamaDefinition::class.java),
    )
  }

  override fun isInputValid(newName: String, element: PsiElement, context: ProcessingContext): Boolean {
    return when (element) {
      is LamaIdentifierExpression -> LamaNamesValidator.isIdentifier(element.name)
      is LamaOperator -> LamaNamesValidator.isOperator(element.name)
      else -> true
    }
  }
}