package org.jetbrains.lama.psi.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.NamedStub
import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.tree.TokenSet
import org.jetbrains.lama.parser.LamaElementTypes
import org.jetbrains.lama.psi.api.LamaInfixOperatorDefinition

enum class LamaInfixAssociativity {
  INFIXL,
  INFIXR,
  INFIX
}

val LAMA_INFIX_ASSOCIATIVITY_TYPES = TokenSet.create(
  LamaElementTypes.LAMA_INFIXL, LamaElementTypes.LAMA_INFIXR, LamaElementTypes.LAMA_INFIX
)

interface LamaInfixOperatorDefinitionStub : NamedStub<LamaInfixOperatorDefinition> {
  val parameters: String
  val associativity: LamaInfixAssociativity
}

class LamaInfixOperatorDefinitionStubImpl(
  private val name: String,
  override val parameters: String,
  override val associativity: LamaInfixAssociativity,
  parent: StubElement<*>?,
  stubElementType: IStubElementType<*, *>
) : StubBase<LamaInfixOperatorDefinition>(parent, stubElementType), LamaInfixOperatorDefinitionStub {

  override fun toString(): String {
    return "LamaInfixOperatorDefinitionStub($name)"
  }

  override fun getName(): String = name
}