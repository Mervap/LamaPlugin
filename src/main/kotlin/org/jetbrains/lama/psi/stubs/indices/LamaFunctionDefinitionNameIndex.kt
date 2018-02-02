package org.jetbrains.lama.psi.stubs.indices

import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import org.jetbrains.lama.psi.api.LamaFunctionDefinition
import org.jetbrains.lama.psi.api.LamaVariableDefinition

class LamaFunctionDefinitionNameIndex : StringStubIndexExtension<LamaFunctionDefinition>() {
  override fun getKey(): StubIndexKey<String, LamaFunctionDefinition> = KEY

  companion object : LamaCompletionIndex<LamaFunctionDefinition>(
    "lama.definition.function",
    LamaFunctionDefinition::class
  )
}