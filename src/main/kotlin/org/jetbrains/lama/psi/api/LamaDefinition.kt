package org.jetbrains.lama.psi.api

import com.intellij.psi.PsiNameIdentifierOwner

interface LamaDefinition : LamaPsiElement, PsiNameIdentifierOwner {
  val isPublic: Boolean
  val isTopLevel: Boolean
}
