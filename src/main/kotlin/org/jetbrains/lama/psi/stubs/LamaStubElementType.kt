package org.jetbrains.lama.psi.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubElement
import org.jetbrains.annotations.NonNls
import org.jetbrains.lama.psi.LamaLanguage
import org.jetbrains.lama.psi.api.LamaPsiElement

abstract class LamaStubElementType<StubT : StubElement<*>, PsiT : LamaPsiElement>(debugName: @NonNls String) :
  IStubElementType<StubT, PsiT>(debugName, LamaLanguage) {
  override fun indexStub(stub: StubT, sink: IndexSink) {}
  override fun getExternalId(): String {
    return "lama." + super.toString()
  }
}