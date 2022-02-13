package org.jetbrains.lama.psi.elementTypes

import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.intellij.util.io.StringRef
import org.jetbrains.lama.psi.api.LamaFunctionDefinition
import org.jetbrains.lama.psi.impl.LamaFunctionDefinitionImpl
import org.jetbrains.lama.psi.stubs.LamaFunctionDefinitionStub
import org.jetbrains.lama.psi.stubs.LamaFunctionDefinitionStubImpl
import org.jetbrains.lama.psi.stubs.LamaStubElementType
import org.jetbrains.lama.psi.stubs.indices.LamaFunctionDefinitionNameIndex

class LamaFunctionDefinitionElementType(debugName: String) :
  LamaStubElementType<LamaFunctionDefinitionStub, LamaFunctionDefinition>(debugName) {

  override fun createPsi(stub: LamaFunctionDefinitionStub): LamaFunctionDefinition {
    return LamaFunctionDefinitionImpl(stub, this)
  }

  override fun createStub(psi: LamaFunctionDefinition, parentStub: StubElement<*>?): LamaFunctionDefinitionStub {
    return LamaFunctionDefinitionStubImpl(psi.name, psi.parameters, psi.isPublic, psi.isTopLevel, parentStub, this)
  }

  override fun serialize(stub: LamaFunctionDefinitionStub, dataStream: StubOutputStream) {
    dataStream.writeName(stub.name)
    dataStream.writeName(stub.parameters)
    dataStream.writeBoolean(stub.isPublic)
    dataStream.writeBoolean(stub.isTopLevel)
  }

  override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): LamaFunctionDefinitionStub {
    val name = StringRef.toString(dataStream.readName())
    val parameters = StringRef.toString(dataStream.readName())
    val isPublic = dataStream.readBoolean()
    val isTopLevel = dataStream.readBoolean()
    return LamaFunctionDefinitionStubImpl(name, parameters, isPublic, isTopLevel, parentStub, this)
  }

  override fun indexStub(stub: LamaFunctionDefinitionStub, sink: IndexSink) {
    val name = stub.name
    if (name != null && stub.isTopLevel) {
      LamaFunctionDefinitionNameIndex.sink(sink)
    }
  }
}