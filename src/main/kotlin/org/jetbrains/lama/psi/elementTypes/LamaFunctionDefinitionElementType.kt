package org.jetbrains.lama.psi.elementTypes

import com.intellij.psi.stubs.*
import com.intellij.util.io.StringRef
import org.jetbrains.lama.psi.api.LamaFunctionDefinition
import org.jetbrains.lama.psi.impl.LamaFunctionDefinitionImpl
import org.jetbrains.lama.psi.stubs.*
import org.jetbrains.lama.psi.stubs.indices.LamaFunctionDefinitionNameIndex

class LamaFunctionDefinitionElementType(debugName: String) :
  LamaStubElementType<LamaFunctionDefinitionStub, LamaFunctionDefinition>(debugName) {

  override fun createPsi(stub: LamaFunctionDefinitionStub): LamaFunctionDefinition {
    return LamaFunctionDefinitionImpl(stub, this)
  }

  override fun createStub(psi: LamaFunctionDefinition, parentStub: StubElement<*>?): LamaFunctionDefinitionStub {
    return LamaFunctionDefinitionStubImpl(psi.name, psi.parameters, psi.isPublic, parentStub, this)
  }

  override fun serialize(stub: LamaFunctionDefinitionStub, dataStream: StubOutputStream) {
    dataStream.writeName(stub.name)
    dataStream.writeName(stub.parameters)
    dataStream.writeBoolean(stub.isPublic)
  }

  override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): LamaFunctionDefinitionStub {
    val name = StringRef.toString(dataStream.readName())
    val parameters = StringRef.toString(dataStream.readName())
    val isPublic = dataStream.readBoolean()
    return LamaFunctionDefinitionStubImpl(name, parameters, isPublic, parentStub, this)
  }

  override fun indexStub(stub: LamaFunctionDefinitionStub, sink: IndexSink) {
    val name = stub.name
    if (name != null && stub.parentStub is PsiFileStub<*>) {
      LamaFunctionDefinitionNameIndex.sink(sink)
    }
  }
}