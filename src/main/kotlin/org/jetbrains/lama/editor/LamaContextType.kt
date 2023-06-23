package org.jetbrains.lama.editor

import com.intellij.codeInsight.template.FileTypeBasedContextType
import com.intellij.codeInsight.template.TemplateActionContext
import org.jetbrains.lama.psi.LamaFileType
import org.jetbrains.lama.psi.LamaLanguage

class LamaContextType : FileTypeBasedContextType("Lama", "&Lama", LamaFileType) {
  override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
    return templateActionContext.file.language.isKindOf(LamaLanguage)
  }
}