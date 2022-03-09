package org.jetbrains.lama.refactoring.rename

import com.intellij.psi.PsiElement
import com.intellij.refactoring.rename.RenamePsiElementProcessor
import org.jetbrains.lama.psi.LamaLanguage

class RenameLamaPsiElementProcessor : RenamePsiElementProcessor() {
  override fun canProcessElement(element: PsiElement): Boolean = element.language == LamaLanguage
}