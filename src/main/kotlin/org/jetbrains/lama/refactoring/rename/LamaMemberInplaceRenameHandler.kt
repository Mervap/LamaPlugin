package org.jetbrains.lama.refactoring.rename

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.refactoring.rename.inplace.InplaceRefactoring
import com.intellij.refactoring.rename.inplace.MemberInplaceRenameHandler
import org.jetbrains.lama.psi.LamaPsiUtil
import org.jetbrains.lama.psi.api.LamaIdentifierExpression
import org.jetbrains.lama.psi.api.LamaOperator
import org.jetbrains.lama.psi.api.LamaPsiElement

class LamaMemberInplaceRenameHandler : MemberInplaceRenameHandler() {
  override fun doRename(elementToRename: PsiElement, editor: Editor, dataContext: DataContext?): InplaceRefactoring? {
    return super.doRename(getElementToRename(elementToRename), editor, dataContext)
  }

  override fun isAvailable(element: PsiElement?, editor: Editor, file: PsiFile): Boolean {
    if (element !is LamaPsiElement || LamaPsiUtil.isLibraryElement(element)) return false
    return super.isAvailable(getElementToRename(element), editor, file)
  }

  private fun getElementToRename(element: PsiElement): PsiElement {
    return if (element is LamaIdentifierExpression || element is LamaOperator) {
      element.parent
    }
    else {
      element
    }
  }
}