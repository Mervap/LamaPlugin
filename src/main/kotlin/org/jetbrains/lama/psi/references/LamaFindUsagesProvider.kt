package org.jetbrains.lama.psi.references

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.TokenSet
import org.jetbrains.lama.lexer.LamaLexer
import org.jetbrains.lama.messages.LamaBundle
import org.jetbrains.lama.parser.LamaElementTypes.*
import org.jetbrains.lama.parser.LamaParserDefinition
import org.jetbrains.lama.psi.LamaPsiUtil.isPatternIdentifier
import org.jetbrains.lama.psi.api.*

class LamaFindUsagesProvider : FindUsagesProvider {
  override fun getWordsScanner(): WordsScanner = LamaWorldScanner()

  override fun canFindUsagesFor(psiElement: PsiElement): Boolean = psiElement is PsiNamedElement

  override fun getHelpId(psiElement: PsiElement): String? = null

  override fun getType(element: PsiElement): String = when (element) {
    is LamaVariableDefinition -> LamaBundle.message("find.usages.variable")
    is LamaFunctionDefinition -> LamaBundle.message("find.usages.function")
    is LamaInfixOperatorDefinition -> LamaBundle.message("find.usages.operator")
    is LamaIdentifierExpression -> {
      val parent = element.parent
      when {
        parent is LamaVariableDefinition -> LamaBundle.message("find.usages.variable")
        parent is LamaFunctionDefinition -> LamaBundle.message("find.usages.function")
        element.isPatternIdentifier() -> LamaBundle.message("find.usages.parameter")
        else -> LamaBundle.message("find.usages.variable")
      }
    }
    is LamaOperator -> LamaBundle.message("find.usages.operator")
    else -> LamaBundle.message("find.usages.variable")
  }

  override fun getDescriptiveName(element: PsiElement): String {
    if (element is PsiNameIdentifierOwner) {
      return element.identifyingElement?.text ?: element.name ?: ""
    }
    return element.text
  }

  override fun getNodeText(element: PsiElement, useFullName: Boolean): String = element.text
}

private class LamaWorldScanner : DefaultWordsScanner(
  LamaLexer(),
  TokenSet.create(LAMA_UINDENT, LAMA_LINDENT),
  LamaParserDefinition().commentTokens,
  TokenSet.create(LAMA_STRING, LAMA_CHAR),
  TokenSet.EMPTY,
  OPERATOR_TOKEN_SET,
) {

  override fun getVersion(): Int = super.getVersion() + 1

  companion object {
    private val OPERATOR_TOKEN_SET = TokenSet.create(
      LAMA_INFIX_OP, LAMA_LIST_CONS, LAMA_OR, LAMA_AND, LAMA_EQEQ, LAMA_NEQ, LAMA_LEQ, LAMA_LE, LAMA_GEQ, LAMA_GE,
      LAMA_MINUS, LAMA_PLUS, LAMA_MUL, LAMA_DIV, LAMA_MOD
    )
  }
}
