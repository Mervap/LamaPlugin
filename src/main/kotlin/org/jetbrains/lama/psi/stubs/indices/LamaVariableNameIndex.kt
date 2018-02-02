package org.jetbrains.lama.psi.stubs.indices

import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import org.jetbrains.lama.psi.api.LamaVariableDefinition

class LamaVariableNameIndex : StringStubIndexExtension<LamaVariableDefinition>() {
  override fun getKey(): StubIndexKey<String, LamaVariableDefinition> = KEY

  companion object : LamaCompletionIndex<LamaVariableDefinition>(
    "lama.definition.variable",
    LamaVariableDefinition::class
  )
}