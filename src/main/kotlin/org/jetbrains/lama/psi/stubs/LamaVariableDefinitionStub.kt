package org.jetbrains.lama.psi.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement
import org.jetbrains.lama.psi.api.LamaVariableDefinition


interface LamaVariableDefinitionStub : LamaDefinitionStub<LamaVariableDefinition> {
  val defaultValue: String?
}

class LamaVariableDefinitionStubImpl(
  private val name: String,
  override val defaultValue: String?,
  override val isTopLevel: Boolean,
  parent: StubElement<*>?,
  stubElementType: IStubElementType<*, *>,
) : StubBase<LamaVariableDefinition>(parent, stubElementType), LamaVariableDefinitionStub {

  override fun toString(): String {
    return "LamaVariableDefinitionStub($name)"
  }

  override fun getName(): String = name
}