package org.jetbrains.lama.parser

import com.intellij.lang.PsiBuilder
import com.intellij.lang.parser.GeneratedParserUtilBase

object LamaParserUtil : GeneratedParserUtilBase() {

  @Suppress("UNUSED_PARAMETER")
  @JvmStatic
  fun isEOL(builder: PsiBuilder, level: Int): Boolean {
    if (builder.tokenType == null) return true
    var offset = builder.currentOffset - 1
    val text = builder.originalText
    while (offset >= 0) {
      if (text[offset] == '\n') return true
      if (!text[offset].isWhitespace()) return false
      offset--
    }
    return false
  }
}