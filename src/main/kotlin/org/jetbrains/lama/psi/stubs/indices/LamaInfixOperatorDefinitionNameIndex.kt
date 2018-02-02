package org.jetbrains.lama.psi.stubs.indices

import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import org.jetbrains.lama.psi.api.LamaFunctionDefinition
import org.jetbrains.lama.psi.api.LamaInfixOperatorDefinition
import org.jetbrains.lama.psi.api.LamaVariableDefinition

class LamaInfixOperatorDefinitionNameIndex : StringStubIndexExtension<LamaInfixOperatorDefinition>() {
  override fun getKey(): StubIndexKey<String, LamaInfixOperatorDefinition> = KEY

  companion object : LamaCompletionIndex<LamaInfixOperatorDefinition>(
    "lama.definition.infix.operator",
    LamaInfixOperatorDefinition::class
  )
}