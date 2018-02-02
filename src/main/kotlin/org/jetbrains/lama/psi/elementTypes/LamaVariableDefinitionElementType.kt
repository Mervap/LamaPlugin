package org.jetbrains.lama.psi.elementTypes

import com.intellij.psi.stubs.*
import com.intellij.util.io.StringRef
import org.jetbrains.lama.psi.api.LamaVariableDefinition
import org.jetbrains.lama.psi.impl.LamaVariableDefinitionImpl
import org.jetbrains.lama.psi.stubs.LamaStubElementType
import org.jetbrains.lama.psi.stubs.LamaVariableDefinitionStub
import org.jetbrains.lama.psi.stubs.LamaVariableDefinitionStubImpl
import org.jetbrains.lama.psi.stubs.indices.LamaVariableNameIndex

class LamaVariableDefinitionElementType(debugName: String) :
  LamaStubElementType<LamaVariableDefinitionStub, LamaVariableDefinition>(debugName) {

  override fun createPsi(stub: LamaVariableDefinitionStub): LamaVariableDefinition {
    return LamaVariableDefinitionImpl(stub, this)
  }

  override fun createStub(psi: LamaVariableDefinition, parentStub: StubElement<*>?): LamaVariableDefinitionStub {
    return LamaVariableDefinitionStubImpl(psi.name, psi.defaultValue, parentStub, this)
  }

  override fun serialize(stub: LamaVariableDefinitionStub, dataStream: StubOutputStream) {
    dataStream.writeName(stub.name)
    dataStream.writeName(stub.defaultValue)
  }

  override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): LamaVariableDefinitionStub {
    val name = StringRef.toString(dataStream.readName())
    val defaultValue = StringRef.toString(dataStream.readName())
    return LamaVariableDefinitionStubImpl(name, defaultValue, parentStub, this)
  }

  override fun indexStub(stub: LamaVariableDefinitionStub, sink: IndexSink) {
    val name = stub.name
    if (name != null && stub.parentStub is PsiFileStub<*>) {
      LamaVariableNameIndex.sink(sink)
    }
  }
}