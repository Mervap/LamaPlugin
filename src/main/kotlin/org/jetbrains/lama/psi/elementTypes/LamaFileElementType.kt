package org.jetbrains.lama.psi.elementTypes

import com.intellij.psi.tree.IStubFileElementType
import org.jetbrains.lama.psi.LamaLanguage
import org.jetbrains.lama.psi.stubs.LamaSkeletonFileStub

class LamaFileElementType : IStubFileElementType<LamaSkeletonFileStub>(LamaLanguage) {
  override fun getStubVersion(): Int = 3
}