package org.jetbrains.lama.resolve

import org.jetbrains.lama.LamaBaseTest
import org.jetbrains.lama.psi.LamaPsiUtil.isPatternIdentifier
import org.jetbrains.lama.psi.api.LamaIdentifierExpression
import org.jetbrains.lama.psi.api.LamaPattern
import org.jetbrains.lama.psi.api.LamaSOrAtPattern
import org.junit.Test

class LamaLocalResolveTest: LamaBaseTest() {

  @Test
  fun testLocalVar() {
    doTest("xxxx_a = 0", """
      fun local() {
        var xxxx_a = 0;
        xxxx<caret>_a + 10
      }
    """.trimIndent())
  }

  @Test
  fun testLocalVarOverridesGlobal() {
    doTest("xxxx_a = 1", """
      var xxxx_a = 0;
      fun local() {
        var xxxx_a = 1;
        xxxx<caret>_a + 10
      }
    """.trimIndent())
  }

  @Test
  fun testLocalFunction() {
    doTest("fun hello() {}", """
      fun local() {
        fun hello() {}
        hell<caret>o()
      }
    """.trimIndent())
  }

  @Test
  fun testParameter() {
    doTest("xxxx_a", """
      fun local([xxxx_a, xxxx_b]) {
        xxxx_<caret>a + xxxx_b
      }
    """.trimIndent(),
    isParameter = true)
  }

  @Test
  fun testLocalLaterDeclaration() {
    doTest("fun xxxx_b() {}", """
      fun xxxx_a() {
        fun xxxx_c() {
          xxxx_<caret>b()
        }
        fun xxxx_b() {}
        
        42 + 42
      }
    """.trimIndent())
  }

  @Test
  fun testLocalLaterDeclarationWithoutExpressions() {
    doTest("fun xxxx_b() {}", """
      fun xxxx_a() {
        fun xxxx_c() {
          xxxx_<caret>b()
        }
        fun xxxx_b() {}
      }
    """.trimIndent())
  }

  private fun doTest(targetText: String?, text: String, isParameter: Boolean = false) {
    myFixture.configureByText("lama.lama", text)
    val results = resolve()
    if (targetText == null) {
      assertEquals(0, results.size)
      return
    }

    assertEquals(1, results.size)
    val element = results[0].element!!
    assertTrue(element.isValid)
    assertEquals(targetText, element.text)
    assertEquals(isParameter, element is LamaSOrAtPattern)
  }
}