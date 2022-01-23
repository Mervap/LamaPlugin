package org.jetbrains.lama.psi.stubs

import com.intellij.psi.stubs.PsiFileStubImpl
import com.intellij.psi.tree.IStubFileElementType
import org.jetbrains.lama.parser.LamaParserDefinition
import org.jetbrains.lama.psi.api.LamaFile

class LamaSkeletonFileStub : PsiFileStubImpl<LamaFile>(null) {
  override fun getType(): IStubFileElementType<*> = LamaParserDefinition.FILE
}