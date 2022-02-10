package org.jetbrains.lama.psi.stubs

import com.intellij.psi.PsiNamedElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.NamedStub
import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement
import org.jetbrains.lama.psi.api.LamaVariableDefinition

interface LamaDefinitionStub<T : PsiNamedElement> : NamedStub<T> {
  val isTopLevel: Boolean
}
