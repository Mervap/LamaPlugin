package org.jetbrains.lama.editor

import com.intellij.codeInsight.generation.actions.CommentByBlockCommentAction
import com.intellij.codeInsight.generation.actions.CommentByLineCommentAction
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.testFramework.TestActionEvent
import com.intellij.testFramework.TestDataProvider
import org.jetbrains.lama.LamaBaseTest
import org.junit.Test

class LamaCommenterTest : LamaBaseTest() {

  @Test
  fun testEolComment() = doTest(
    "this is line comment",
    "-- this is line comment",
    CommentByLineCommentAction()
  )

  @Test
  fun testBlockComment() = doTest(
    "This <selection>is block comment</selection>",
    "This <selection>(*is block comment*)</selection>",
    CommentByBlockCommentAction()
  )

  private fun doTest(before: String, after: String, action: AnAction) {
    myFixture.configureByText("lama.lama", before)

    action.actionPerformed(TestActionEvent(TestDataProvider(project)))
    myFixture.checkResult(after)

    action.actionPerformed(TestActionEvent(TestDataProvider(project)))
    myFixture.checkResult(before)
  }
}