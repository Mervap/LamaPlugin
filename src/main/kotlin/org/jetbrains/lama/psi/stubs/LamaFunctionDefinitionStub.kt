package org.jetbrains.lama.psi.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement
import org.jetbrains.lama.psi.api.LamaFunctionDefinition


interface LamaFunctionDefinitionStub : LamaDefinitionStub<LamaFunctionDefinition> {
  val parameters: String
  val isPublic: Boolean
}

class LamaFunctionDefinitionStubImpl(
  private val name: String,
  override val parameters: String,
  override val isPublic: Boolean,
  override val isTopLevel: Boolean,
  parent: StubElement<*>?,
  stubElementType: IStubElementType<*, *>
) : StubBase<LamaFunctionDefinition>(parent, stubElementType), LamaFunctionDefinitionStub {

  override fun toString(): String {
    return "LamaFunctionDefinitionStub($name)"
  }

  override fun getName(): String = name
}