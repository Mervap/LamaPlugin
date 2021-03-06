package org.jetbrains.lama.psi.elementTypes

import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.intellij.util.io.StringRef
import org.jetbrains.lama.psi.api.LamaInfixOperatorDefinition
import org.jetbrains.lama.psi.impl.LamaInfixOperatorDefinitionImpl
import org.jetbrains.lama.psi.stubs.LamaInfixAssociativity
import org.jetbrains.lama.psi.stubs.LamaInfixOperatorDefinitionStub
import org.jetbrains.lama.psi.stubs.LamaInfixOperatorDefinitionStubImpl
import org.jetbrains.lama.psi.stubs.LamaStubElementType
import org.jetbrains.lama.psi.stubs.indices.LamaOperatorCompletionIndex
import org.jetbrains.lama.psi.stubs.indices.LamaOperatorNameIndex

class LamaInfixOperatorDefinitionElementType(debugName: String) :
  LamaStubElementType<LamaInfixOperatorDefinitionStub, LamaInfixOperatorDefinition>(debugName) {

  override fun createPsi(stub: LamaInfixOperatorDefinitionStub): LamaInfixOperatorDefinition {
    return LamaInfixOperatorDefinitionImpl(stub, this)
  }

  override fun createStub(
    psi: LamaInfixOperatorDefinition,
    parentStub: StubElement<*>?,
  ): LamaInfixOperatorDefinitionStub {
    return LamaInfixOperatorDefinitionStubImpl(psi.name, psi.parameters, psi.associativity,  psi.isPublic, psi.isTopLevel, parentStub, this)
  }

  override fun serialize(stub: LamaInfixOperatorDefinitionStub, dataStream: StubOutputStream) {
    dataStream.writeName(stub.name)
    dataStream.writeName(stub.parameters)
    dataStream.writeInt(stub.associativity.ordinal)
    dataStream.writeBoolean(stub.isPublic)
    dataStream.writeBoolean(stub.isTopLevel)
  }

  override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): LamaInfixOperatorDefinitionStub {
    val name = StringRef.toString(dataStream.readName())
    val parameters = StringRef.toString(dataStream.readName())
    val associativity = LamaInfixAssociativity.values()[dataStream.readInt()]
    val isPublic = dataStream.readBoolean()
    val isTopLevel = dataStream.readBoolean()
    return LamaInfixOperatorDefinitionStubImpl(name, parameters, associativity, isPublic, isTopLevel, parentStub, this)
  }

  override fun indexStub(stub: LamaInfixOperatorDefinitionStub, sink: IndexSink) {
    val name = stub.name
    if (name != null && stub.isTopLevel) {
      LamaOperatorCompletionIndex.sink(sink)
      LamaOperatorNameIndex.sink(name, sink)
    }
  }
}