package org.jetbrains.lama.psi.impl

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.lama.psi.LamaFileType
import org.jetbrains.lama.psi.api.LamaFile

open class LamaFileImpl(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, LamaFileType.language), LamaFile {
  override fun getFileType(): FileType = LamaFileType
  override fun toString(): String = "LamaFile:$name"
  override fun accept(visitor: PsiElementVisitor) {
    visitor.visitFile(this)
  }
}
