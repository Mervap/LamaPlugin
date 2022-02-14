package org.jetbrains.lama.psi.elementTypes

import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.intellij.util.io.StringRef
import org.jetbrains.lama.psi.api.LamaVariableDefinition
import org.jetbrains.lama.psi.impl.LamaVariableDefinitionImpl
import org.jetbrains.lama.psi.stubs.LamaStubElementType
import org.jetbrains.lama.psi.stubs.LamaVariableDefinitionStub
import org.jetbrains.lama.psi.stubs.LamaVariableDefinitionStubImpl
import org.jetbrains.lama.psi.stubs.indices.LamaIdentifierCompletionIndex
import org.jetbrains.lama.psi.stubs.indices.LamaIdentifierNameIndex

class LamaVariableDefinitionElementType(debugName: String) :
  LamaStubElementType<LamaVariableDefinitionStub, LamaVariableDefinition>(debugName) {

  override fun createPsi(stub: LamaVariableDefinitionStub): LamaVariableDefinition {
    return LamaVariableDefinitionImpl(stub, this)
  }

  override fun createStub(psi: LamaVariableDefinition, parentStub: StubElement<*>?): LamaVariableDefinitionStub {
    return LamaVariableDefinitionStubImpl(psi.name, psi.defaultValue, psi.isPublic, psi.isTopLevel, parentStub, this)
  }

  override fun serialize(stub: LamaVariableDefinitionStub, dataStream: StubOutputStream) {
    dataStream.writeName(stub.name)
    dataStream.writeName(stub.defaultValue)
    dataStream.writeBoolean(stub.isPublic)
    dataStream.writeBoolean(stub.isTopLevel)
  }

  override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): LamaVariableDefinitionStub {
    val name = StringRef.toString(dataStream.readName())
    val defaultValue = StringRef.toString(dataStream.readName())
    val isPublic = dataStream.readBoolean()
    val isTopLevel = dataStream.readBoolean()
    return LamaVariableDefinitionStubImpl(name, defaultValue, isPublic, isTopLevel, parentStub, this)
  }

  override fun indexStub(stub: LamaVariableDefinitionStub, sink: IndexSink) {
    val name = stub.name
    if (name != null && stub.isTopLevel) {
      LamaIdentifierCompletionIndex.sink(sink)
      LamaIdentifierNameIndex.sink(name, sink)
    }
  }
}