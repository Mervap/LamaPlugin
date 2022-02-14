package org.jetbrains.lama.editor.completion

import org.jetbrains.lama.LamaMultiFileTest
import org.junit.Test

class LamaMultiFileCompletionTest : LamaMultiFileTest("completion") {

  @Test fun testVariable() = doTest("xxxx_c", "xxxx_b")
  @Test fun testFunction() = doTest("xxxx_c", "xxxx_b")
  @Test fun testInfix() = doTest("===%%", "===$$", strict = false)
  @Test fun testFromImportsPriority() = doTest("xxxx_c", "xxxx_b", "xxxx_a")

  private fun doTest(vararg variants: String, strict: Boolean = true) {
    doCompletionTest(*variants, strict = strict) { configureFiles() }
  }
}