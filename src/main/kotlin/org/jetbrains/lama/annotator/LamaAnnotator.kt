package org.jetbrains.lama.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.lama.highlighting.DOC_COMMENT
import org.jetbrains.lama.highlighting.FUNCTION_CALL
import org.jetbrains.lama.highlighting.FUNCTION_DECLARATION
import org.jetbrains.lama.highlighting.LOCAL_VARIABLE
import org.jetbrains.lama.psi.api.*

class LamaAnnotator : Annotator {
  override fun annotate(psiElement: PsiElement, holder: AnnotationHolder) {
    val visitor = LamaAnnotatorVisitor(holder)
    psiElement.accept(visitor)
  }
}

class LamaAnnotatorVisitor(private val holder: AnnotationHolder) : LamaVisitor() {

  override fun visitComment(comment: PsiComment) {
    var next = PsiTreeUtil.skipWhitespacesAndCommentsForward(comment)
    if (next is LamaScope) {
      next = next.firstChild
    }
    if (next is LamaDefinition || next is LamaVariableDefinitionSeries) {
       highlight(comment, DOC_COMMENT)
    }
  }

  override fun visitSOrCallExpression(o: LamaSOrCallExpression) {
    if (o.expression.text.firstOrNull()?.isLowerCase() == true) {
      highlight(o.expression, FUNCTION_CALL)
    }
  }

  override fun visitVariableDefinition(o: LamaVariableDefinition) {
    highlight(o.expressionList.first(), LOCAL_VARIABLE)
  }

  override fun visitFunctionDefinition(o: LamaFunctionDefinition) {
    highlight(o.identifierExpression, FUNCTION_DECLARATION)
  }

  override fun visitInfixOperatorDefinition(o: LamaInfixOperatorDefinition) {
    highlight(o.operatorList.first(), FUNCTION_DECLARATION)
  }

  private fun highlight(element: PsiElement, colorKey: TextAttributesKey) {
    holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element).textAttributes(colorKey).create()
  }
}
