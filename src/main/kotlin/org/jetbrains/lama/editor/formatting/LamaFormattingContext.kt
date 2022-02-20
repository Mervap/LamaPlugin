package org.jetbrains.lama.editor.formatting

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.tree.TokenSet
import com.intellij.util.containers.FactoryMap
import org.jetbrains.lama.parser.LamaElementTypes.*
import org.jetbrains.lama.parser.LamaParserDefinition
import org.jetbrains.lama.psi.LamaFileType
import org.jetbrains.lama.psi.LamaLanguage
import org.jetbrains.lama.psi.api.*

private val NON_INDENT_PARTS = TokenSet.create(
  LAMA_RPAR,
  LAMA_RBRACE,
  LAMA_RBRACKET,
  LAMA_LPAR,
  LAMA_LBRACE,
  LAMA_LBRACKET,
)

private val NON_INDENT_RELATIVE_PARTS = TokenSet.create(
  LAMA_DO,
  LAMA_WHILE,
  LAMA_OF,
  LAMA_FI,
  LAMA_OD,
  LAMA_ESAC,
)

private val CONTINUATION_PARTS = TokenSet.create(
  LAMA_COMMA,
)

private val PATTERN_LIST_PARENTS = TokenSet.create(
  LAMA_PARAMETER_LIST,
  LAMA_S_OR_AT_PATTERN,
  LAMA_ARRAY_PATTERN,
  LAMA_LIST_PATTERN,
)

private val EXPRESSION_SERIES_LIST_PARENTS = TokenSet.create(
  LAMA_LIST_EXPRESSION,
  LAMA_ARRAY_EXPRESSION,
  LAMA_ARRAY_EXPRESSION,
  LAMA_SYNTAX_PRIMARY_ARRAY,
)

private val SPACE_AFTER = TokenSet.create(
  LAMA_SEMI,
  LAMA_IMPORT,
  LAMA_FUN,
  LAMA_VAR,
  LAMA_IF,
  LAMA_FOR,
  LAMA_WHILE,
  LAMA_DO,
  LAMA_THEN,
  LAMA_ELSE,
  LAMA_ELIF,
  LAMA_CASE,
  LAMA_CASE_OR
)

class LamaFormattingContext(private val settings: CodeStyleSettings) {
  private val spacingBuilder = createSpacingBuilder(settings)

  private val childIndentAlignments: MutableMap<ASTNode, Alignment> = FactoryMap.create { Alignment.createAlignment() }

  fun computeAlignment(node: ASTNode): Alignment? {
    val parent = node.treeParent ?: return null
    val nodePsi by lazy { node.psi }
    val parentElementType = parent.elementType
    val common = settings.getCommonSettings(LamaLanguage)

    return when {
      common.ALIGN_MULTILINE_PARAMETERS && parentElementType in PATTERN_LIST_PARENTS && nodePsi is LamaPattern -> childIndentAlignments[parent]
      common.ALIGN_MULTILINE_PARAMETERS_IN_CALLS && parentElementType in EXPRESSION_SERIES_LIST_PARENTS && node.firstChildNode != null -> childIndentAlignments[parent]
      else -> null
    }
  }

  fun computeSpacing(parent: Block, child1: Block?, child2: Block): Spacing? {
    return spacingBuilder.getSpacing(parent, child1, child2)
  }

  fun isIncomplete(node: ASTNode): Boolean {
    val psi = node.psi
    return when {
      psi is LamaCaseBranch && psi.scope == null -> true
      psi is LamaIfBranch && psi.scope == null -> true
      psi is LamaVariableDefinitionSeries -> node.nextNonWsNode?.elementType != LAMA_SEMI
      psi is LamaExpression -> node.nextNonWsNode?.elementType != LAMA_SEMI
      else -> false
    }
  }

  private val ASTNode.nextNonWsNode: ASTNode?
    get() {
      var next = treeNext
      while (next?.elementType == TokenType.WHITE_SPACE) {
        next = next.treeNext
      }
      return next
    }

  fun computeNewChildIndent(node: ASTNode, isLastChild: Boolean = false): Indent {
    val psi = node.psi
    val parent = psi.parent
    return when {
      psi is LamaFile || parent is LamaFile -> Indent.getNoneIndent()
      psi is LamaScope || psi is LamaFunctionBody -> Indent.getNormalIndent()
      psi is LamaForStatement || psi is LamaDoStatement || psi is LamaWhileStatement -> Indent.getNormalIndent()
      psi is LamaIfStatement || psi is LamaCaseStatement-> Indent.getNormalIndent()
      psi is LamaCaseBranch || psi is LamaIfBranch -> Indent.getNormalIndent()
      psi is LamaArgumentList -> Indent.getContinuationIndent()
      psi is LamaParameterList -> Indent.getContinuationIndent()
      psi is LamaPattern -> getContinuationIndentIfNoError(psi, isLastChild)
      psi is LamaArrayExpression -> getContinuationIndentIfNoError(psi, isLastChild)
      psi is LamaListExpression -> getContinuationIndentIfNoError(psi, isLastChild)
      psi is LamaParenthesizedExpression -> getContinuationIndentIfNoError(psi, isLastChild)
      psi is LamaExpression -> getContinuationIndentIfNoError(psi, isLastChild)
      else -> Indent.getNoneIndent()
    }
  }

  fun computeWrap(node: ASTNode): Wrap {
    return Wrap.createWrap(WrapType.NONE, true)
  }

  fun computeBlockIndent(node: ASTNode): Indent? {
    val psi = node.psi
    val parent = psi.parent

    val indentOptions = settings.getIndentOptions(LamaFileType)
    return when {
      parent is LamaFile -> Indent.getNoneIndent()
      node.elementType in NON_INDENT_PARTS -> Indent.getNoneIndent()
      node.elementType in NON_INDENT_RELATIVE_PARTS -> getNoneIndent(indentOptions.USE_RELATIVE_INDENTS)
      node.elementType in CONTINUATION_PARTS -> Indent.getContinuationIndent()
      psi is LamaIfBranch || psi is LamaCaseBranch -> {
        val lamaSettings = settings.lamaSettings()
        if (lamaSettings.USE_INDENT_BEFORE_BRANCHES) {
          Indent.getNormalIndent(indentOptions.USE_RELATIVE_INDENTS)
        }
        else {
          getNoneIndent(indentOptions.USE_RELATIVE_INDENTS)
        }
      }
      parent is LamaForStatement && (psi is LamaExpression || parent.beforeAll == psi) -> Indent.getContinuationIndent()
      psi is LamaScope -> Indent.getNormalIndent()
      psi is LamaVariableDefinition || parent is LamaVariableDefinition -> Indent.getContinuationIndent()
      parent is LamaParameterList -> Indent.getContinuationWithoutFirstIndent()
      parent is LamaArrayPattern || parent is LamaListPattern -> Indent.getContinuationWithoutFirstIndent()
      parent is LamaListExpression || parent is LamaArgumentList -> Indent.getContinuationWithoutFirstIndent()
      parent is LamaArrayExpression || parent is LamaSyntaxPrimaryArray -> Indent.getContinuationWithoutFirstIndent()
      parent is LamaExpression && parent.firstChild != psi -> Indent.getContinuationWithoutFirstIndent()
      else -> Indent.getNoneIndent()
    }
  }
}

private fun createSpacingBuilder(settings: CodeStyleSettings): SpacingBuilder {
  val common = settings.getCommonSettings(LamaLanguage)
  val custom = settings.getCustomSettings(LamaCodeStyleSettings::class.java)

  return SpacingBuilder(settings, LamaLanguage)
    // Comments
    .before(LamaParserDefinition().commentTokens)
    .spacing(1, Int.MAX_VALUE, 0, true, common.KEEP_BLANK_LINES_IN_CODE)

    // Binary operators
    .around(LAMA_EQ).spaceIf(common.SPACE_AROUND_ASSIGNMENT_OPERATORS)
    .around(LAMA_ASSIGNMENT_OPERATOR).spaceIf(common.SPACE_AROUND_ASSIGNMENT_OPERATORS)
    .around(LAMA_COMPARE_OPERATOR).spaceIf(common.SPACE_AROUND_RELATIONAL_OPERATORS)
    .around(LAMA_PLUS_MINUS_OPERATOR).spaceIf(common.SPACE_AROUND_ADDITIVE_OPERATORS)
    .around(LAMA_MUL_DIV_MOD_OPERATOR).spaceIf(common.SPACE_AROUND_MULTIPLICATIVE_OPERATORS)

    .around(LAMA_AND_OPERATOR).spaceIf(custom.SPACE_AROUND_CONJUNCTION_OPERATORS)
    .around(LAMA_OR_OPERATOR).spaceIf(custom.SPACE_AROUND_DISJUNCTION_OPERATORS)
    .around(LAMA_INFIX_OPERATOR).spaceIf(custom.SPACE_AROUND_INFIX_OPERATOR)
    .around(LAMA_LIST_CONS_OPERATOR).spaceIf(custom.SPACE_AROUND_LIST_CONS_OPERATOR)
    .around(LAMA_DOT_OPERATOR).spaceIf(custom.SPACE_AROUND_DOT_OPERATOR)

    // Parentheses group
    .betweenInside(LAMA_LPAR, LAMA_RPAR, LAMA_ARGUMENT_LIST).spaceIf(common.SPACE_WITHIN_EMPTY_METHOD_CALL_PARENTHESES)
    .afterInside(LAMA_LPAR, LAMA_ARGUMENT_LIST).spaceIf(common.SPACE_WITHIN_METHOD_CALL_PARENTHESES)
    .beforeInside(LAMA_RPAR, LAMA_ARGUMENT_LIST).spaceIf(common.SPACE_WITHIN_METHOD_CALL_PARENTHESES)

    .betweenInside(LAMA_LPAR, LAMA_RPAR, LAMA_S_OR_AT_PATTERN)
    .spaceIf(common.SPACE_WITHIN_EMPTY_METHOD_CALL_PARENTHESES)
    .afterInside(LAMA_LPAR, LAMA_S_OR_AT_PATTERN).spaceIf(common.SPACE_WITHIN_METHOD_CALL_PARENTHESES)
    .beforeInside(LAMA_RPAR, LAMA_S_OR_AT_PATTERN).spaceIf(common.SPACE_WITHIN_METHOD_CALL_PARENTHESES)

    .betweenInside(LAMA_LPAR, LAMA_RPAR, LAMA_SYNTAX_EXPRESSION)
    .spaceIf(common.SPACE_WITHIN_EMPTY_METHOD_CALL_PARENTHESES)
    .afterInside(LAMA_LPAR, LAMA_SYNTAX_EXPRESSION).spaceIf(common.SPACE_WITHIN_METHOD_CALL_PARENTHESES)
    .beforeInside(LAMA_RPAR, LAMA_SYNTAX_EXPRESSION).spaceIf(common.SPACE_WITHIN_METHOD_CALL_PARENTHESES)

    .betweenInside(LAMA_LPAR, LAMA_RPAR, LAMA_PARAMETER_LIST).spaceIf(common.SPACE_WITHIN_EMPTY_METHOD_PARENTHESES)
    .afterInside(LAMA_LPAR, LAMA_PARAMETER_LIST).spaceIf(common.SPACE_WITHIN_METHOD_PARENTHESES)
    .beforeInside(LAMA_RPAR, LAMA_PARAMETER_LIST).spaceIf(common.SPACE_WITHIN_METHOD_PARENTHESES)

    .beforeInside(LAMA_ARGUMENT_LIST, LAMA_S_OR_CALL_EXPRESSION).spaceIf(common.SPACE_BEFORE_METHOD_CALL_PARENTHESES)
    .beforeInside(LAMA_PARAMETER_LIST, TokenSet.create(LAMA_FUNCTION_DEFINITION, LAMA_INFIX_OPERATOR_DEFINITION))
    .spaceIf(common.SPACE_BEFORE_METHOD_PARENTHESES)

    .afterInside(LAMA_LPAR, LAMA_PARENTHESIZED_EXPRESSION).spaceIf(common.SPACE_WITHIN_PARENTHESES)
    .beforeInside(LAMA_RPAR, LAMA_PARENTHESIZED_EXPRESSION).spaceIf(common.SPACE_WITHIN_PARENTHESES)

    .betweenInside(LAMA_LPAR, LAMA_RPAR, LAMA_SYNTAX_PRIMARY_PARENTHESIZED).spaceIf(common.SPACE_WITHIN_PARENTHESES)
    .afterInside(LAMA_LPAR, LAMA_SYNTAX_PRIMARY_PARENTHESIZED).spaceIf(common.SPACE_WITHIN_PARENTHESES)

    // Brackets group
    .after(LAMA_LBRACKET).spaceIf(common.SPACE_WITHIN_BRACKETS)
    .before(LAMA_RBRACKET).spaceIf(common.SPACE_WITHIN_BRACKETS)

    .after(LAMA_LBRACE).spaceIf(common.SPACE_WITHIN_BRACES)
    .before(LAMA_RBRACE).spaceIf(common.SPACE_WITHIN_BRACES)

    // Comma
    .before(LAMA_COMMA).spaceIf(common.SPACE_BEFORE_COMMA)
    .after(LAMA_COMMA).spaceIf(common.SPACE_AFTER_COMMA)

    // Other
    .around(LAMA_ARROW).spaces(1)
    .after(SPACE_AFTER).spaces(1)
}

private fun getNoneIndent(relativeToDirectParent: Boolean): Indent {
  return Indent.getIndent(Indent.Type.NONE, relativeToDirectParent, false)
}

private fun getContinuationIndentIfNoError(psi: PsiElement, isLastChild: Boolean): Indent {
  return if (!isLastChild || psi.lastChild is PsiErrorElement) {
    Indent.getContinuationIndent()
  }
  else {
    Indent.getNoneIndent()
  }
}


