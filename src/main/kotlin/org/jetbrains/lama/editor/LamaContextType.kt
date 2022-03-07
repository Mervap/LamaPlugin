package org.jetbrains.lama.editor

import com.intellij.codeInsight.template.FileTypeBasedContextType
import com.intellij.psi.PsiFile
import org.jetbrains.lama.psi.LamaFileType
import org.jetbrains.lama.psi.LamaLanguage

class LamaContextType : FileTypeBasedContextType("Lama", "&Lama", LamaFileType) {
  override fun isInContext(file: PsiFile, offset: Int): Boolean {
    return file.language.isKindOf(LamaLanguage)
  }
}