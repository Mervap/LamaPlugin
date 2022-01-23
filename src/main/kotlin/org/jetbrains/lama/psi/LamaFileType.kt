package org.jetbrains.lama.psi

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.lama.icons.LamaIcons
import javax.swing.Icon

object LamaLanguage : Language("Lama")

object LamaFileType : LanguageFileType(LamaLanguage) {
  override fun getName(): String = "Lama"
  override fun getDescription(): String = "Lama source file"
  override fun getDefaultExtension(): String = "lama"
  override fun getIcon(): Icon = LamaIcons.FileIcon
}