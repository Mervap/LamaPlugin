package org.jetbrains.lama.editor.completion

import org.jetbrains.lama.LamaBaseTest
import org.junit.Test

class LamaIdentifierCompletionTest : LamaBaseTest() {

  override fun setUp() {
    super.setUp()
    addStdlib()
  }

  @Test
  fun testVariable() = doTest("""
    var xxxx_a = 3, xxxx_b = 5;
    xxxx<caret>
  """.trimIndent(), "xxxx_a", "xxxx_b")

  @Test
  fun testFunction() = doTest("""
    fun xxxx_a(a, b) {}
    fun xxxx_b(a, b) {}
    
    xxxx<caret>
  """.trimIndent(), "xxxx_a", "xxxx_b")

  @Test
  fun testInfixOperator() = doTest("""
    infix ===%% at == () {}
    infix ===$$ at == () {}
    
    1 ===<caret>
  """.trimIndent(), "===\$\$", "===%%", strict = false)


  @Test
  fun testCase() {
    val text = """
      var xxxx_a = 3, xxxx_aa = 4;
      
      xxxx<caret1>;
      
      case xxxx_b of
        [xxxx_c, xxxx_d] -> xxxx<caret2>
        | [xxxx_e, xxxx_f] -> xxxx<caret3>
      esac;
      
      xxxx<caret4>
    """.trimIndent()

    doTest(text.preserveSingleCaret(1), "xxxx_a", "xxxx_aa")
    doTest(text.preserveSingleCaret(2), "xxxx_a", "xxxx_aa", "xxxx_c", "xxxx_d")
    doTest(text.preserveSingleCaret(3), "xxxx_a", "xxxx_aa", "xxxx_e", "xxxx_f")
    doTest(text.preserveSingleCaret(4), "xxxx_a", "xxxx_aa")
  }

  @Test
  fun testFor() {
    val text = """
      var xxxx_a = 3, xxxx_aa = 4;
      
      xxxx<caret1>;
      
      for var xxxx_c; xxxx<caret2>, xxxx<caret3>, xxxx<caret4> do
        var xxxx_d = xxxx<caret5>;
        xxxx<caret6>
      od;
      
      xxxx<caret7>
    """.trimIndent()

    doTest(text.preserveSingleCaret(1), "xxxx_a", "xxxx_aa")
    doTest(text.preserveSingleCaret(2), "xxxx_a", "xxxx_aa", "xxxx_c")
    doTest(text.preserveSingleCaret(3), "xxxx_a", "xxxx_aa", "xxxx_c")
    doTest(text.preserveSingleCaret(4), "xxxx_a", "xxxx_aa", "xxxx_c")
    doTest(text.preserveSingleCaret(5), "xxxx_a", "xxxx_aa", "xxxx_c")
    doTest(text.preserveSingleCaret(6), "xxxx_a", "xxxx_aa", "xxxx_c", "xxxx_d")
    doTest(text.preserveSingleCaret(7), "xxxx_a", "xxxx_aa")
  }

  @Test
  fun testParameters() {
    val text = """
      var xxxx_a = 3;
      
      fun xxxx_b(xxxx_c, [xxxx_d]) {
        xxxx<caret1>
      }
      
      xxxx<caret2>;
    """.trimIndent()

    doTest(text.preserveSingleCaret(1), "xxxx_a", "xxxx_b", "xxxx_c", "xxxx_d")
    doTest(text.preserveSingleCaret(2), "xxxx_a", "xxxx_b")
  }


  @Test
  fun testNestedDeclarations() {
    val text = """
      var xxxx_a = 3;
      fun xxxx_b() {}
      
      fun xxxx_c(xxxx_d) {
        var xxxx_e = 3; 
        fun xxxx_f(xxxx_g) {
          var xxxx_l = 4;
          xxxx<caret1>;
        }
        
        xxxx<caret2>;
      }
      
      xxxx<caret3>;
    """.trimIndent()

    doTest(text.preserveSingleCaret(1), "xxxx_a", "xxxx_b", "xxxx_c", "xxxx_d", "xxxx_e", "xxxx_f", "xxxx_g", "xxxx_l")
    doTest(text.preserveSingleCaret(2), "xxxx_a", "xxxx_b", "xxxx_c", "xxxx_d", "xxxx_e", "xxxx_f")
    doTest(text.preserveSingleCaret(3), "xxxx_a", "xxxx_b", "xxxx_c")
  }

  @Test
  fun testFunctionExpression() {
    val text = """
      var xxxx_a = 3;
      var xxxx_b = fun (xxxx_c, [xxxx_d]) {
        var xxxx_e = 42;
        xxxx<caret1>
      };
      
      xxxx<caret2>
    """.trimIndent()

    doTest(text.preserveSingleCaret(1), "xxxx_a", "xxxx_c", "xxxx_d", "xxxx_e")
    doTest(text.preserveSingleCaret(2), "xxxx_a", "xxxx_b")
  }

  @Test
  fun testKeywords() {
    doTest("var fav; var a = fa<caret>", "false", strict = false)
    doTest("var trv; var a = tr<caret>", "true", strict = false)
    doTest("v<caret>", "var", strict = false)
    doTest("var pubeda; pub<caret>", "public", strict = false)
    doTest("var casa; cas<caret>", "case", strict = false)
    doTest("var esa; es<caret>", "esac", strict = false)
  }

  @Test
  fun testImportList() {
    doTest("import <caret>", "Array", "Data", "Ref", strict = false)
    doWrongVariantsTest("import va<caret>", "var")
    doWrongVariantsTest("var a = 5; imp", "import")
  }

  @Test
  fun testApplyFunction() {
    doApplyCompletionTest("""
      var xxxx_v = 2;
      fun xxxx_a() {}
      
      xxxx<caret>
    """.trimIndent(), """
      var xxxx_v = 2;
      fun xxxx_a() {}
      
      xxxx_a()<caret>
    """.trimIndent(), "xxxx_a")

    doApplyCompletionTest("""
      var xxxx_v = 2;
      fun xxxx_a(a, b) {}
      
      xxxx<caret>
    """.trimIndent(), """
      var xxxx_v = 2;
      fun xxxx_a(a, b) {}
      
      xxxx_a(<caret>)
    """.trimIndent(), "xxxx_a")
  }

  @Test
  fun testFromStdlib() = doTest(
    "arra<caret>",
    "arrayList", "foldlArray", "foldrArray", "initArray", "iterArray", "iteriArray", "listArray", "mapArray",
    strict = false
  )

  @Test
  fun testImportedStdlibHigher() {
    doTest("import Buffer; co<caret>", "concatBuffer", "compareOf", "getCol", strict = false)
    doTest("import Collection; co<caret>", "compareOf", "concatBuffer", "getCol", strict = false)
  }

  @Test
  fun testStdUnit() {
    doTest("print<caret>", "printf", "fprintf", "sprintf")
  }

  @Test
  fun testLocalLaterDeclaration() {
    doTest("""
      fun compile_a() {
        fun compile_c() {
          com<caret>
        }
        fun compile_b() {
          fun compile_d() {}
        }
      }
    """.trimIndent(), "compile_a", "compile_b", "compile_c", "compare", "compareOf", "flatCompare")
  }

  @Test
  fun testNumeric() {
    doTest("42 + 42<caret>")
  }

  @Test
  fun testCharString() {
    doTest("\"42\"<caret>")
    doTest("'42'<caret>")
  }

  @Test
  fun testDot() {
    doTest("a.<caret>", "string", strict = false)
  }

  @Test
  fun testOneArgPriority() {
    doTest("""
      fun xxxx_aaaa(a, b) {}
      fun xxxx_bbbb(c@[a, b, c, d]) {}
      fun xxxx_cccc(a) {}
      fun xxxx_dddd([a, b], c) {}
      
      xxxx_<caret>
    """.trimIndent(), "xxxx_aaaa", "xxxx_bbbb", "xxxx_cccc", "xxxx_dddd")


    doTest("""
      fun xxxx_aaaa(a, b) {}
      fun xxxx_bbbb(c@[a, b, c, d]) {}
      fun xxxx_cccc(a) {}
      fun xxxx_dddd([a, b], c) {}
      
      something.xxxx_<caret>
    """.trimIndent(), "xxxx_bbbb", "xxxx_cccc", "xxxx_aaaa", "xxxx_dddd")
  }

  private fun doWrongVariantsTest(text: String, vararg variants: String) {
    doWrongCompletionVariantsTest(*variants) { myFixture.configureByText("lama.lama", text) }
  }

  private fun doTest(text: String, vararg variants: String, strict: Boolean = true) {
    doCompletionTest(*variants, strict = strict) { myFixture.configureByText("lama.lama", text) }
  }

  private fun doApplyCompletionTest(text: String, expected: String, lookupString: String) {
    doApplyCompletionTest(expected, lookupString) { myFixture.configureByText("lama.lama", text) }
  }

  private fun String.preserveSingleCaret(ind: Int): String {
    return replace("<caret$ind>", "<caret>").replace("<caret\\d>".toRegex(), "")
  }
}
