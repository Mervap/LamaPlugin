package org.jetbrains.lama.controlFlow

import com.intellij.refactoring.suggested.startOffset
import org.jetbrains.lama.LamaBaseTest
import org.jetbrains.lama.psi.LamaRecursiveVisitor
import org.jetbrains.lama.psi.api.LamaControlFlowHolder
import org.jetbrains.lama.psi.api.LamaFile
import org.junit.Test
import java.nio.file.Path

class RControlFlowTest : LamaBaseTest() {

  override fun getTestDataPath(): String = Path.of(super.getTestDataPath(), "parser", "correct").toString()

  @Test fun testALotOfSubtractions() = doTest()
  @Test fun testAssign() = doTest()
  @Test fun testCase() = doTest()
  @Test fun testCaseAt() = doTest()
  @Test fun testCaseListMatch() = doTest()
  @Test fun testCaseMatch() = doTest()
  @Test fun testCaseSharp() = doTest()
  @Test fun testCompareOperators() = doTest()
  @Test fun testComplexArgumentList() = doTest()
  @Test fun testComplexExpression() = doTest()
  @Test fun testDoWhile() = doTest()
  @Test fun testEta() = doTest()
  @Test fun testFor() = doTest()
  @Test fun testForSkip() = doTest()
  @Test fun testFun() = doTest()
  @Test fun testFunFor() = doTest()
  @Test fun testFunIf() = doTest()
  @Test fun testHuge() = doTest()
  @Test fun testIf() = doTest()
  @Test fun testInfixCall() = doTest()
  @Test fun testInfixCallInScope() = doTest()
  @Test fun testInfixDefinition() = doTest()
  @Test fun testInfixr() = doTest()
  @Test fun testLazy() = doTest()
  @Test fun testManyOperators() = doTest()
  @Test fun testMatch() = doTest()
  @Test fun testNestedFun() = doTest()
  @Test fun testNestedIf() = doTest()
  @Test fun testNoSemiRequired() = doTest()
  @Test fun testOperatorsInCall() = doTest()
  @Test fun testParenthesisScope() = doTest()
  @Test fun testParenthesisScopeFun() = doTest()
  @Test fun testPrintSExpr() = doTest()
  @Test fun testRecursive() = doTest()
  @Test fun testSeveralExpressions() = doTest()
  @Test fun testSimpleFile() = doTest()
  @Test fun testSyntax() = doTest()
  @Test fun testWhile() = doTest()
  @Test fun testWhileIf() = doTest()
  @Test fun testWhileWhileIf() = doTest()

  private fun doTest() {
    val filename = getTestName(false)
    val holders = (myFixture.configureByFile("$filename.lama") as LamaFile).collectHolders()

    val res = buildString {
      for ((counter, holder) in holders.withIndex()) {
        val parent = holder.parent
        val text = parent.text.firstLine()
        appendLine("[${counter}] $parent ($text):")
        holder.controlFlow.instructions.forEach {
          append(it)
          append(" (")
          append(it.element?.text?.firstLine() ?: "<no_text>")
          appendLine(")")
        }
        appendLine()
      }
    }

    val outputPath = Path.of(testDataPath, filename).toString()
    assertSameLinesWithFile("$outputPath.cfg.txt", res)
  }

  private fun String.firstLine() = trim().takeWhile { it != '\n' }

  private fun LamaFile.collectHolders(): List<LamaControlFlowHolder> {
    val holders = mutableListOf<LamaControlFlowHolder>()
    accept(object : LamaRecursiveVisitor() {
      override fun visitControlFlowHolder(o: LamaControlFlowHolder) {
        super.visitControlFlowHolder(o)
        holders.add(o)
      }
    })
    return holders.sortedBy { it.startOffset }
  }
}

