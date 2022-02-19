package org.jetbrains.lama.psi

import com.intellij.psi.PsiFile
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.lama.parser.LamaElementTypes.*
import org.jetbrains.lama.psi.api.*

@Suppress("MemberVisibilityCanBePrivate")
object LamaPsiUtil {

  val PsiFile.importedUnits: List<String>
    get() {
      if (this !is LamaFile) return emptyList()
      return importStatements.mapNotNull { it.identifierExpression?.name }
    }

  val LamaPsiElement.controlFlowContainer: LamaControlFlowHolder?
    get() = PsiTreeUtil.getParentOfType(this, LamaControlFlowHolder::class.java)

  fun LamaIdentifierExpression.isImportIdentifier(): Boolean = parent is LamaImportStatement

  fun LamaIdentifierExpression.isNotIdentifierReference(): Boolean = !isIdentifierReference()

  fun LamaIdentifierExpression.isIdentifierReference(): Boolean = isAssignee() || isDefinitionIdentifier()

  fun LamaIdentifierExpression.isAssignee(): Boolean {
    val parent = parent
    return parent is LamaAssignmentExpression && parent.assignee == this
  }

  fun LamaIdentifierExpression.isDefinitionIdentifier(): Boolean {
    return isVariableDefinitionIdentifier() || isFunctionDefinitionIdentifier() || isParameterIdentifier()
  }

  fun LamaIdentifierExpression.isParameterIdentifier(): Boolean {
    val parent = parent
    return parent is LamaSOrAtPattern && PsiTreeUtil.getParentOfType(parent, LamaParameterList::class.java) != null
  }

  fun LamaIdentifierExpression.isVariableDefinitionIdentifier(): Boolean {
    val parent = parent
    return parent is LamaVariableDefinition && parent.nameIdentifier == this
  }

  fun LamaIdentifierExpression.isFunctionDefinitionIdentifier(): Boolean {
    val parent = parent
    return parent is LamaFunctionDefinition && parent.nameIdentifier == this
  }

  fun LamaOperator.isNotDefinitionOperator(): Boolean = !isDefinitionOperator()

  fun LamaOperator.isDefinitionOperator(): Boolean {
    val parent = parent
    return parent is LamaInfixOperatorDefinition && parent.nameOperator == this
  }

  val RESERVED_WORDS = TokenSet.create(
    LAMA_AFTER, LAMA_ARRAY, LAMA_AT, LAMA_BEFORE, LAMA_BOX, LAMA_CASE, LAMA_DO, LAMA_ELIF, LAMA_ELSE, LAMA_ESAC,
    LAMA_ETA, LAMA_FALSE, LAMA_FI, LAMA_FOR, LAMA_FUN, LAMA_IF, LAMA_IMPORT, LAMA_INFIX, LAMA_INFIXL, LAMA_INFIXR,
    LAMA_LAZY, LAMA_OD, LAMA_OF, LAMA_PUBLIC, LAMA_SEXP, LAMA_SKIP, LAMA_STR, LAMA_SYNTAX, LAMA_THEN, LAMA_TRUE,
    LAMA_VAL, LAMA_VAR, LAMA_WHILE
  )

}