package org.jetbrains.lama.psi.stubs

import com.intellij.psi.PsiNamedElement
import com.intellij.psi.stubs.NamedStub

interface LamaDefinitionStub<T : PsiNamedElement> : NamedStub<T> {
  val isTopLevel: Boolean
}
