@file:Suppress("UNUSED_PARAMETER")

package org.jetbrains.lama.psi.impl

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.lama.psi.LamaElementFactory
import org.jetbrains.lama.psi.api.*

internal object LamaPsiImplUtil {

  @JvmStatic
  fun getName(identifierExpression: LamaIdentifierExpression): String = identifierExpression.text

  @JvmStatic
  fun setName(identifier: LamaIdentifierExpression, name: String): PsiElement {
    return identifier.replace(LamaElementFactory.createLamaPsiElementFromText(identifier.project, name))
  }

  @JvmStatic
  fun getName(charLiteral: LamaCharLiteral): String = charLiteral.text.trim('\'')

  @JvmStatic
  fun setName(charLiteral: LamaCharLiteral, name: String): PsiElement {
    val oldText = charLiteral.text
    val newText = oldText.replaceRange(1, oldText.length - 1, name)
    val replacement = LamaElementFactory.createLamaPsiElementFromText(charLiteral.project, newText)
    return charLiteral.replace(replacement)
  }

  @JvmStatic
  fun getName(stringLiteral: LamaStringLiteral): String = stringLiteral.text.trim('\'')

  @JvmStatic
  fun setName(stringLiteral: LamaStringLiteral, name: String): PsiElement {
    val oldText = stringLiteral.text
    val newText = oldText.replaceRange(1, oldText.length - 1, name)
    val replacement = LamaElementFactory.createLamaPsiElementFromText(stringLiteral.project, newText)
    return stringLiteral.replace(replacement)
  }

  @JvmStatic
  fun getReference(expression: LamaExpression): PsiReference? = null

  @JvmStatic
  fun getReference(operator: LamaOperator): PsiReference? = null

  @JvmStatic
  fun getName(operator: LamaOperator): String = operator.text

  @JvmStatic
  fun setName(operator: LamaInfixOperator, name: String): PsiElement {
    val replacement = LamaElementFactory.createLamaPsiElementFromText(operator.project, "1 $name 1").children[1]
    return operator.replace(replacement)
  }

  @JvmStatic
  fun getLeftExpr(expr: LamaOperatorExpression): LamaExpression? {
    val expressions = PsiTreeUtil.getChildrenOfType(expr, LamaExpression::class.java)
    return if (expressions == null || expressions.isEmpty()) null else expressions[0]
  }

  @JvmStatic
  fun getRightExpr(expr: LamaOperatorExpression): LamaExpression? {
    val expressions = PsiTreeUtil.getChildrenOfType(expr, LamaExpression::class.java)
    return if (expressions == null || expressions.size != 2) null else expressions[1]
  }

  @JvmStatic
  fun getOperator(expr: LamaOperatorExpression): LamaOperator? {
    return PsiTreeUtil.getChildOfType(expr, LamaOperator::class.java)
  }
}
