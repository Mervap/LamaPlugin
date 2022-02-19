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
import org.jetbrains.lama.parser.LamaElementTypes
import org.jetbrains.lama.parser.LamaParserDefinition
import org.jetbrains.lama.psi.LamaPsiUtil.isParameterIdentifier
import org.jetbrains.lama.psi.api.*

class LamaFindUsagesProvider : FindUsagesProvider {
  override fun getWordsScanner(): WordsScanner {
    return DefaultWordsScanner(
      LamaLexer(),
      TokenSet.create(LamaElementTypes.LAMA_IDENTIFIER_EXPRESSION),
      LamaParserDefinition().commentTokens,
      TokenSet.create(LamaElementTypes.LAMA_STRING_LITERAL)
    )
  }

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
        element.isParameterIdentifier() -> LamaBundle.message("find.usages.parameter")
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
