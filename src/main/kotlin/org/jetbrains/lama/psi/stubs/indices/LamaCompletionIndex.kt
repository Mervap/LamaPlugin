package org.jetbrains.lama.psi.stubs.indices

import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.stubs.StubIndexKey
import com.intellij.util.Processor
import org.jetbrains.lama.psi.api.LamaIdentifierOwnerDefinition
import org.jetbrains.lama.psi.api.LamaInfixOperatorDefinition
import org.jetbrains.lama.psi.api.LamaPsiElement
import kotlin.reflect.KClass

class LamaIdentifierCompletionIndex : StringStubIndexExtension<LamaIdentifierOwnerDefinition>() {
  override fun getKey(): StubIndexKey<String, LamaIdentifierOwnerDefinition> = KEY

  companion object : LamaCompletionIndex<LamaIdentifierOwnerDefinition>(
    "lama.completion.definition.identifier",
    LamaIdentifierOwnerDefinition::class
  )
}

class LamaOperatorCompletionIndex : StringStubIndexExtension<LamaInfixOperatorDefinition>() {
  override fun getKey(): StubIndexKey<String, LamaInfixOperatorDefinition> = KEY

  companion object : LamaCompletionIndex<LamaInfixOperatorDefinition>(
    "lama.completion.definition.operator",
    LamaInfixOperatorDefinition::class
  )
}

abstract class LamaCompletionIndex<T : LamaPsiElement>(name: String, selfKClass: KClass<T>) {
  private val selfClass = selfKClass.java

  @Suppress("PropertyName")
  val KEY = StubIndexKey.createIndexKey<String, T>(name)

  fun process(
    project: Project,
    scope: GlobalSearchScope,
    processor: Processor<in T>,
  ): Boolean {
    return StubIndex.getInstance().processElements(
      KEY, "", project, scope, selfClass, processor
    )
  }

  fun sink(sink: IndexSink) {
    sink.occurrence(KEY, "")
  }
}