package org.jetbrains.lama.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveVisitor
import org.jetbrains.lama.psi.api.LamaVisitor

open class LamaRecursiveVisitor : LamaVisitor(), PsiRecursiveVisitor {
  override fun visitElement(element: PsiElement) {
    element.acceptChildren(this)
  }
}