package org.jetbrains.lama.psi.api

import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiNamedElement

interface LamaDefinition : LamaPsiElement, PsiNamedElement {
  val isPublic: Boolean
  val isTopLevel: Boolean
}

interface LamaIdentifierOwnerDefinition : LamaDefinition, PsiNameIdentifierOwner
