// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.lama.psi.LamaElementType;
import org.jetbrains.lama.psi.impl.*;

public interface LamaElementTypes {

  IElementType LAMA_AND_OPERATOR = new LamaElementType("LAMA_AND_OPERATOR");
  IElementType LAMA_ARGUMENT_LIST = new LamaElementType("LAMA_ARGUMENT_LIST");
  IElementType LAMA_ARRAY_EXPRESSION = new LamaElementType("LAMA_ARRAY_EXPRESSION");
  IElementType LAMA_ARRAY_PATTERN = new LamaElementType("LAMA_ARRAY_PATTERN");
  IElementType LAMA_ASSIGNMENT_OPERATOR = new LamaElementType("LAMA_ASSIGNMENT_OPERATOR");
  IElementType LAMA_BOOLEAN_LITERAL = new LamaElementType("LAMA_BOOLEAN_LITERAL");
  IElementType LAMA_CALL_EXPRESSION = new LamaElementType("LAMA_CALL_EXPRESSION");
  IElementType LAMA_CASE_BRANCH = new LamaElementType("LAMA_CASE_BRANCH");
  IElementType LAMA_CASE_STATEMENT = new LamaElementType("LAMA_CASE_STATEMENT");
  IElementType LAMA_CHAR_LITERAL = new LamaElementType("LAMA_CHAR_LITERAL");
  IElementType LAMA_COMPARE_OPERATOR = new LamaElementType("LAMA_COMPARE_OPERATOR");
  IElementType LAMA_DOT_OPERATOR = new LamaElementType("LAMA_DOT_OPERATOR");
  IElementType LAMA_DO_STATEMENT = new LamaElementType("LAMA_DO_STATEMENT");
  IElementType LAMA_ETA_EXPRESSION = new LamaElementType("LAMA_ETA_EXPRESSION");
  IElementType LAMA_EXPRESSION = new LamaElementType("LAMA_EXPRESSION");
  IElementType LAMA_FOR_STATEMENT = new LamaElementType("LAMA_FOR_STATEMENT");
  IElementType LAMA_FUNCTION_BODY = new LamaElementType("LAMA_FUNCTION_BODY");
  IElementType LAMA_FUNCTION_DEFINITION = new LamaElementType("LAMA_FUNCTION_DEFINITION");
  IElementType LAMA_FUNCTION_EXPRESSION = new LamaElementType("LAMA_FUNCTION_EXPRESSION");
  IElementType LAMA_IDENTIFIER_EXPRESSION = new LamaElementType("LAMA_IDENTIFIER_EXPRESSION");
  IElementType LAMA_IF_BRANCH = new LamaElementType("LAMA_IF_BRANCH");
  IElementType LAMA_IF_STATEMENT = new LamaElementType("LAMA_IF_STATEMENT");
  IElementType LAMA_IMPORT_STATEMENT = new LamaElementType("LAMA_IMPORT_STATEMENT");
  IElementType LAMA_INFIX_EXPRESSION = new LamaElementType("LAMA_INFIX_EXPRESSION");
  IElementType LAMA_INFIX_OPERATOR = new LamaElementType("LAMA_INFIX_OPERATOR");
  IElementType LAMA_INFIX_OPERATOR_DEFINITION = new LamaElementType("LAMA_INFIX_OPERATOR_DEFINITION");
  IElementType LAMA_LAZY_EXPRESSION = new LamaElementType("LAMA_LAZY_EXPRESSION");
  IElementType LAMA_LIST_CONS_OPERATOR = new LamaElementType("LAMA_LIST_CONS_OPERATOR");
  IElementType LAMA_LIST_EXPRESSION = new LamaElementType("LAMA_LIST_EXPRESSION");
  IElementType LAMA_LIST_PATTERN = new LamaElementType("LAMA_LIST_PATTERN");
  IElementType LAMA_MUL_DIV_MOD_OPERATOR = new LamaElementType("LAMA_MUL_DIV_MOD_OPERATOR");
  IElementType LAMA_NUMERIC_LITERAL = new LamaElementType("LAMA_NUMERIC_LITERAL");
  IElementType LAMA_OPERATOR_EXPRESSION = new LamaElementType("LAMA_OPERATOR_EXPRESSION");
  IElementType LAMA_OR_OPERATOR = new LamaElementType("LAMA_OR_OPERATOR");
  IElementType LAMA_PARAMETER_LIST = new LamaElementType("LAMA_PARAMETER_LIST");
  IElementType LAMA_PARENTHESIZED_EXPRESSION = new LamaElementType("LAMA_PARENTHESIZED_EXPRESSION");
  IElementType LAMA_PATTERN = new LamaElementType("LAMA_PATTERN");
  IElementType LAMA_PLUS_MINUS_OPERATOR = new LamaElementType("LAMA_PLUS_MINUS_OPERATOR");
  IElementType LAMA_SCOPE = new LamaElementType("LAMA_SCOPE");
  IElementType LAMA_SHARP_PATTERN = new LamaElementType("LAMA_SHARP_PATTERN");
  IElementType LAMA_SKIP_EXPRESSION = new LamaElementType("LAMA_SKIP_EXPRESSION");
  IElementType LAMA_STRING_LITERAL = new LamaElementType("LAMA_STRING_LITERAL");
  IElementType LAMA_SUBSCRIPTION_EXPRESSION = new LamaElementType("LAMA_SUBSCRIPTION_EXPRESSION");
  IElementType LAMA_SYNTAX_BINDING = new LamaElementType("LAMA_SYNTAX_BINDING");
  IElementType LAMA_SYNTAX_EXPRESSION = new LamaElementType("LAMA_SYNTAX_EXPRESSION");
  IElementType LAMA_SYNTAX_PRIMARY_ARRAY = new LamaElementType("LAMA_SYNTAX_PRIMARY_ARRAY");
  IElementType LAMA_SYNTAX_PRIMARY_EXPRESSION_FROM = new LamaElementType("LAMA_SYNTAX_PRIMARY_EXPRESSION_FROM");
  IElementType LAMA_SYNTAX_PRIMARY_PARENTHESIZED = new LamaElementType("LAMA_SYNTAX_PRIMARY_PARENTHESIZED");
  IElementType LAMA_SYNTAX_SEQ = new LamaElementType("LAMA_SYNTAX_SEQ");
  IElementType LAMA_S_OR_AT_PATTERN = new LamaElementType("LAMA_S_OR_AT_PATTERN");
  IElementType LAMA_VARIABLE_DEFINITION = new LamaElementType("LAMA_VARIABLE_DEFINITION");
  IElementType LAMA_VARIABLE_DEFINITION_EXPRESSION = new LamaElementType("LAMA_VARIABLE_DEFINITION_EXPRESSION");
  IElementType LAMA_WHILE_STATEMENT = new LamaElementType("LAMA_WHILE_STATEMENT");
  IElementType LAMA_WILDCARD_PATTERN = new LamaElementType("LAMA_WILDCARD_PATTERN");

  IElementType LAMA_AFTER = new LamaElementType("after");
  IElementType LAMA_AND = new LamaElementType("&&");
  IElementType LAMA_ARRAY = new LamaElementType("ARRAY");
  IElementType LAMA_ARROW = new LamaElementType("->");
  IElementType LAMA_ASSIGN = new LamaElementType(":=");
  IElementType LAMA_AT = new LamaElementType("at");
  IElementType LAMA_AT_SIGN = new LamaElementType("@");
  IElementType LAMA_BEFORE = new LamaElementType("before");
  IElementType LAMA_BOTTOM = new LamaElementType("_");
  IElementType LAMA_BOX = new LamaElementType("BOX");
  IElementType LAMA_CASE = new LamaElementType("CASE");
  IElementType LAMA_CASE_OR = new LamaElementType("|");
  IElementType LAMA_CHAR = new LamaElementType("CHAR");
  IElementType LAMA_COMMA = new LamaElementType(",");
  IElementType LAMA_DIV = new LamaElementType("/");
  IElementType LAMA_DO = new LamaElementType("DO");
  IElementType LAMA_DOLLAR_LPAR = new LamaElementType("$(");
  IElementType LAMA_DOT = new LamaElementType(".");
  IElementType LAMA_ELIF = new LamaElementType("elif");
  IElementType LAMA_ELSE = new LamaElementType("else");
  IElementType LAMA_EQ = new LamaElementType("=");
  IElementType LAMA_EQEQ = new LamaElementType("==");
  IElementType LAMA_ESAC = new LamaElementType("ESAC");
  IElementType LAMA_ETA = new LamaElementType("ETA");
  IElementType LAMA_FALSE = new LamaElementType("FALSE");
  IElementType LAMA_FI = new LamaElementType("FI");
  IElementType LAMA_FOR = new LamaElementType("FOR");
  IElementType LAMA_FUN = new LamaElementType("FUN");
  IElementType LAMA_GE = new LamaElementType(">");
  IElementType LAMA_GEQ = new LamaElementType(">=");
  IElementType LAMA_IF = new LamaElementType("IF");
  IElementType LAMA_IMPORT = new LamaElementType("IMPORT");
  IElementType LAMA_INFIX = new LamaElementType("INFIX");
  IElementType LAMA_INFIXL = new LamaElementType("infixl");
  IElementType LAMA_INFIXR = new LamaElementType("infixr");
  IElementType LAMA_INFIX_OP = new LamaElementType("INFIX_OP");
  IElementType LAMA_LAZY = new LamaElementType("LAZY");
  IElementType LAMA_LBRACE = new LamaElementType("{");
  IElementType LAMA_LBRACKET = new LamaElementType("[");
  IElementType LAMA_LE = new LamaElementType("<");
  IElementType LAMA_LEQ = new LamaElementType("<=");
  IElementType LAMA_LINDENT = new LamaElementType("LINDENT");
  IElementType LAMA_LIST_CONS = new LamaElementType(":");
  IElementType LAMA_LPAR = new LamaElementType("(");
  IElementType LAMA_MINUS = new LamaElementType("-");
  IElementType LAMA_MOD = new LamaElementType("%");
  IElementType LAMA_MUL = new LamaElementType("*");
  IElementType LAMA_NEQ = new LamaElementType("!=");
  IElementType LAMA_NUMBER = new LamaElementType("NUMBER");
  IElementType LAMA_OD = new LamaElementType("OD");
  IElementType LAMA_OF = new LamaElementType("OF");
  IElementType LAMA_OR = new LamaElementType("!!");
  IElementType LAMA_PLUS = new LamaElementType("+");
  IElementType LAMA_PUBLIC = new LamaElementType("public");
  IElementType LAMA_QUEST = new LamaElementType("?");
  IElementType LAMA_RBRACE = new LamaElementType("}");
  IElementType LAMA_RBRACKET = new LamaElementType("]");
  IElementType LAMA_RPAR = new LamaElementType(")");
  IElementType LAMA_SEMI = new LamaElementType(";");
  IElementType LAMA_SEXP = new LamaElementType("SEXP");
  IElementType LAMA_SHARP = new LamaElementType("#");
  IElementType LAMA_SKIP = new LamaElementType("SKIP");
  IElementType LAMA_STR = new LamaElementType("STR");
  IElementType LAMA_STRING = new LamaElementType("STRING");
  IElementType LAMA_SYNTAX = new LamaElementType("SYNTAX");
  IElementType LAMA_THEN = new LamaElementType("then");
  IElementType LAMA_TRUE = new LamaElementType("TRUE");
  IElementType LAMA_UINDENT = new LamaElementType("UINDENT");
  IElementType LAMA_VAL = new LamaElementType("VAL");
  IElementType LAMA_VAR = new LamaElementType("var");
  IElementType LAMA_WHILE = new LamaElementType("WHILE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == LAMA_AND_OPERATOR) {
        return new LamaAndOperatorImpl(node);
      }
      else if (type == LAMA_ARGUMENT_LIST) {
        return new LamaArgumentListImpl(node);
      }
      else if (type == LAMA_ARRAY_EXPRESSION) {
        return new LamaArrayExpressionImpl(node);
      }
      else if (type == LAMA_ARRAY_PATTERN) {
        return new LamaArrayPatternImpl(node);
      }
      else if (type == LAMA_ASSIGNMENT_OPERATOR) {
        return new LamaAssignmentOperatorImpl(node);
      }
      else if (type == LAMA_BOOLEAN_LITERAL) {
        return new LamaBooleanLiteralImpl(node);
      }
      else if (type == LAMA_CALL_EXPRESSION) {
        return new LamaCallExpressionImpl(node);
      }
      else if (type == LAMA_CASE_BRANCH) {
        return new LamaCaseBranchImpl(node);
      }
      else if (type == LAMA_CASE_STATEMENT) {
        return new LamaCaseStatementImpl(node);
      }
      else if (type == LAMA_CHAR_LITERAL) {
        return new LamaCharLiteralImpl(node);
      }
      else if (type == LAMA_COMPARE_OPERATOR) {
        return new LamaCompareOperatorImpl(node);
      }
      else if (type == LAMA_DOT_OPERATOR) {
        return new LamaDotOperatorImpl(node);
      }
      else if (type == LAMA_DO_STATEMENT) {
        return new LamaDoStatementImpl(node);
      }
      else if (type == LAMA_ETA_EXPRESSION) {
        return new LamaEtaExpressionImpl(node);
      }
      else if (type == LAMA_FOR_STATEMENT) {
        return new LamaForStatementImpl(node);
      }
      else if (type == LAMA_FUNCTION_BODY) {
        return new LamaFunctionBodyImpl(node);
      }
      else if (type == LAMA_FUNCTION_DEFINITION) {
        return new LamaFunctionDefinitionImpl(node);
      }
      else if (type == LAMA_FUNCTION_EXPRESSION) {
        return new LamaFunctionExpressionImpl(node);
      }
      else if (type == LAMA_IDENTIFIER_EXPRESSION) {
        return new LamaIdentifierExpressionImpl(node);
      }
      else if (type == LAMA_IF_BRANCH) {
        return new LamaIfBranchImpl(node);
      }
      else if (type == LAMA_IF_STATEMENT) {
        return new LamaIfStatementImpl(node);
      }
      else if (type == LAMA_IMPORT_STATEMENT) {
        return new LamaImportStatementImpl(node);
      }
      else if (type == LAMA_INFIX_EXPRESSION) {
        return new LamaInfixExpressionImpl(node);
      }
      else if (type == LAMA_INFIX_OPERATOR) {
        return new LamaInfixOperatorImpl(node);
      }
      else if (type == LAMA_INFIX_OPERATOR_DEFINITION) {
        return new LamaInfixOperatorDefinitionImpl(node);
      }
      else if (type == LAMA_LAZY_EXPRESSION) {
        return new LamaLazyExpressionImpl(node);
      }
      else if (type == LAMA_LIST_CONS_OPERATOR) {
        return new LamaListConsOperatorImpl(node);
      }
      else if (type == LAMA_LIST_EXPRESSION) {
        return new LamaListExpressionImpl(node);
      }
      else if (type == LAMA_LIST_PATTERN) {
        return new LamaListPatternImpl(node);
      }
      else if (type == LAMA_MUL_DIV_MOD_OPERATOR) {
        return new LamaMulDivModOperatorImpl(node);
      }
      else if (type == LAMA_NUMERIC_LITERAL) {
        return new LamaNumericLiteralImpl(node);
      }
      else if (type == LAMA_OPERATOR_EXPRESSION) {
        return new LamaOperatorExpressionImpl(node);
      }
      else if (type == LAMA_OR_OPERATOR) {
        return new LamaOrOperatorImpl(node);
      }
      else if (type == LAMA_PARAMETER_LIST) {
        return new LamaParameterListImpl(node);
      }
      else if (type == LAMA_PARENTHESIZED_EXPRESSION) {
        return new LamaParenthesizedExpressionImpl(node);
      }
      else if (type == LAMA_PATTERN) {
        return new LamaPatternImpl(node);
      }
      else if (type == LAMA_PLUS_MINUS_OPERATOR) {
        return new LamaPlusMinusOperatorImpl(node);
      }
      else if (type == LAMA_SCOPE) {
        return new LamaScopeImpl(node);
      }
      else if (type == LAMA_SHARP_PATTERN) {
        return new LamaSharpPatternImpl(node);
      }
      else if (type == LAMA_SKIP_EXPRESSION) {
        return new LamaSkipExpressionImpl(node);
      }
      else if (type == LAMA_STRING_LITERAL) {
        return new LamaStringLiteralImpl(node);
      }
      else if (type == LAMA_SUBSCRIPTION_EXPRESSION) {
        return new LamaSubscriptionExpressionImpl(node);
      }
      else if (type == LAMA_SYNTAX_BINDING) {
        return new LamaSyntaxBindingImpl(node);
      }
      else if (type == LAMA_SYNTAX_EXPRESSION) {
        return new LamaSyntaxExpressionImpl(node);
      }
      else if (type == LAMA_SYNTAX_PRIMARY_ARRAY) {
        return new LamaSyntaxPrimaryArrayImpl(node);
      }
      else if (type == LAMA_SYNTAX_PRIMARY_EXPRESSION_FROM) {
        return new LamaSyntaxPrimaryExpressionFromImpl(node);
      }
      else if (type == LAMA_SYNTAX_PRIMARY_PARENTHESIZED) {
        return new LamaSyntaxPrimaryParenthesizedImpl(node);
      }
      else if (type == LAMA_SYNTAX_SEQ) {
        return new LamaSyntaxSeqImpl(node);
      }
      else if (type == LAMA_S_OR_AT_PATTERN) {
        return new LamaSOrAtPatternImpl(node);
      }
      else if (type == LAMA_VARIABLE_DEFINITION) {
        return new LamaVariableDefinitionImpl(node);
      }
      else if (type == LAMA_VARIABLE_DEFINITION_EXPRESSION) {
        return new LamaVariableDefinitionExpressionImpl(node);
      }
      else if (type == LAMA_WHILE_STATEMENT) {
        return new LamaWhileStatementImpl(node);
      }
      else if (type == LAMA_WILDCARD_PATTERN) {
        return new LamaWildcardPatternImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
