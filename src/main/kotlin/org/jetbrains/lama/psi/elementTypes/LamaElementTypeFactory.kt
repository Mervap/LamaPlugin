package org.jetbrains.lama.psi.elementTypes

import com.intellij.psi.tree.IElementType

object LamaElementTypeFactory {
  @JvmStatic
  fun getElementTypeByName(name: String): IElementType {
    if (name == "LAMA_VARIABLE_DEFINITION") {
      return LamaVariableDefinitionElementType(name)
    }
    if (name == "LAMA_FUNCTION_DEFINITION") {
      return LamaFunctionDefinitionElementType(name)
    }
    if (name == "LAMA_INFIX_OPERATOR_DEFINITION") {
      return LamaInfixOperatorDefinitionElementType(name)
    }
    throw IllegalArgumentException("Unknown element type: $name")
  }
}