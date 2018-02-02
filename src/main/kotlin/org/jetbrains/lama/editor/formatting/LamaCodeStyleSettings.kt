@file:Suppress("PropertyName")

package org.jetbrains.lama.editor.formatting

import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CustomCodeStyleSettings
import org.jetbrains.lama.psi.LamaLanguage

class LamaCodeStyleSettings(container: CodeStyleSettings) : CustomCodeStyleSettings(LamaLanguage.id, container) {

  @JvmField var SPACE_AROUND_CONJUNCTION_OPERATORS: Boolean = true
  @JvmField var SPACE_AROUND_DISJUNCTION_OPERATORS: Boolean = true
  @JvmField var SPACE_AROUND_INFIX_OPERATOR: Boolean = true
  @JvmField var SPACE_AROUND_LIST_CONS_OPERATOR: Boolean = false
  @JvmField var SPACE_AROUND_DOT_OPERATOR: Boolean = false
  @JvmField var USE_INDENT_BEFORE_BRANCHES: Boolean = true
  @JvmField var ALIGN_MULTILINE_FOR: Boolean = true
}

fun CodeStyleSettings.lamaSettings(): LamaCodeStyleSettings {
  return getCustomSettings(LamaCodeStyleSettings::class.java)
}
