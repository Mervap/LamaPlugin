package org.jetbrains.lama.psi.stubs.indices

import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.stubs.StubIndexKey
import org.jetbrains.lama.psi.api.LamaIdentifierOwnerDefinition
import org.jetbrains.lama.psi.api.LamaInfixOperatorDefinition
import org.jetbrains.lama.psi.api.LamaPsiElement
import kotlin.reflect.KClass

class LamaIdentifierNameIndex : StringStubIndexExtension<LamaIdentifierOwnerDefinition>() {
  override fun getKey(): StubIndexKey<String, LamaIdentifierOwnerDefinition> = KEY

  companion object : LamaNameIndex<LamaIdentifierOwnerDefinition>(
    "lama.name.definition.identifier",
    LamaIdentifierOwnerDefinition::class
  )
}

class LamaOperatorNameIndex : StringStubIndexExtension<LamaInfixOperatorDefinition>() {
  override fun getKey(): StubIndexKey<String, LamaInfixOperatorDefinition> = KEY

  companion object : LamaNameIndex<LamaInfixOperatorDefinition>(
    "lama.name.definition.operator",
    LamaInfixOperatorDefinition::class
  )
}

abstract class LamaNameIndex<T : LamaPsiElement>(name: String, selfKClass: KClass<T>) {
  private val selfClass = selfKClass.java

  @Suppress("PropertyName")
  val KEY = StubIndexKey.createIndexKey<String, T>(name)

  fun find(name: String, project: Project, scope: GlobalSearchScope?): Collection<T> {
    return StubIndex.getElements(KEY, name, project, scope, selfClass)
  }

  fun sink(name: String, sink: IndexSink) {
    sink.occurrence(KEY, name)
  }
}