// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.jetbrains.lama.parser.LamaElementTypes.*;
import static org.jetbrains.lama.parser.LamaParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class LamaParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return root(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(LAMA_ARRAY_PATTERN, LAMA_LIST_PATTERN, LAMA_PATTERN, LAMA_SHARP_PATTERN,
      LAMA_S_OR_AT_PATTERN, LAMA_WILDCARD_PATTERN),
    create_token_set_(LAMA_AND_OPERATOR, LAMA_ASSIGNMENT_OPERATOR, LAMA_COMPARE_OPERATOR, LAMA_DOT_OPERATOR,
      LAMA_INFIX_OPERATOR, LAMA_LIST_CONS_OPERATOR, LAMA_MUL_DIV_MOD_OPERATOR, LAMA_OR_OPERATOR,
      LAMA_PLUS_MINUS_OPERATOR),
    create_token_set_(LAMA_ARRAY_EXPRESSION, LAMA_BOOLEAN_LITERAL, LAMA_CALL_EXPRESSION, LAMA_CASE_STATEMENT,
      LAMA_CHAR_LITERAL, LAMA_DO_STATEMENT, LAMA_ETA_EXPRESSION, LAMA_EXPRESSION,
      LAMA_FOR_STATEMENT, LAMA_FUNCTION_EXPRESSION, LAMA_IDENTIFIER_EXPRESSION, LAMA_IF_STATEMENT,
      LAMA_IMPORT_STATEMENT, LAMA_INFIX_EXPRESSION, LAMA_LAZY_EXPRESSION, LAMA_LIST_EXPRESSION,
      LAMA_NUMERIC_LITERAL, LAMA_OPERATOR_EXPRESSION, LAMA_PARENTHESIZED_EXPRESSION, LAMA_SKIP_EXPRESSION,
      LAMA_STRING_LITERAL, LAMA_SUBSCRIPTION_EXPRESSION, LAMA_SYNTAX_EXPRESSION, LAMA_WHILE_STATEMENT),
  };

  /* ********************************************************** */
  // "&&"
  public static boolean and_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "and_operator")) return false;
    if (!nextTokenIs(b, LAMA_AND)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LAMA_AND);
    exit_section_(b, m, LAMA_AND_OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // "|" case_branch_impl
  public static boolean another_case_branch(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "another_case_branch")) return false;
    if (!nextTokenIs(b, LAMA_CASE_OR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_CASE_BRANCH, null);
    r = consumeToken(b, LAMA_CASE_OR);
    p = r; // pin = 1
    r = r && case_branch_impl(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // predefined_operator | infix_operator
  static boolean any_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "any_operator")) return false;
    boolean r;
    r = predefined_operator(b, l + 1);
    if (!r) r = infix_operator(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // "(" [ expression_series ] ")"
  public static boolean argument_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argument_list")) return false;
    if (!nextTokenIs(b, LAMA_LPAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_ARGUMENT_LIST, null);
    r = consumeToken(b, LAMA_LPAR);
    p = r; // pin = 1
    r = r && report_error_(b, argument_list_1(b, l + 1));
    r = p && consumeToken(b, LAMA_RPAR) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ expression_series ]
  private static boolean argument_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argument_list_1")) return false;
    expression_series(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "[" [ pattern_list ] "]"
  public static boolean array_pattern(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_pattern")) return false;
    if (!nextTokenIs(b, LAMA_LBRACKET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_ARRAY_PATTERN, null);
    r = consumeToken(b, LAMA_LBRACKET);
    p = r; // pin = 1
    r = r && report_error_(b, array_pattern_1(b, l + 1));
    r = p && consumeToken(b, LAMA_RBRACKET) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ pattern_list ]
  private static boolean array_pattern_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_pattern_1")) return false;
    pattern_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ":="
  public static boolean assignment_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignment_operator")) return false;
    if (!nextTokenIs(b, LAMA_ASSIGN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LAMA_ASSIGN);
    exit_section_(b, m, LAMA_ASSIGNMENT_OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // "@" pattern
  static boolean at_pattern_suffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "at_pattern_suffix")) return false;
    if (!nextTokenIs(b, LAMA_AT_SIGN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_AT_SIGN);
    p = r; // pin = 1
    r = r && pattern(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // case_branch_impl
  public static boolean case_branch(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_branch")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMA_CASE_BRANCH, "<case branch>");
    r = case_branch_impl(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // pattern "->" case_branch_scope
  static boolean case_branch_impl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_branch_impl")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = pattern(b, l + 1);
    r = r && consumeToken(b, LAMA_ARROW);
    p = r; // pin = 2
    r = r && case_branch_scope(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // !("|" | esac)
  static boolean case_branch_recover_rule(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_branch_recover_rule")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !case_branch_recover_rule_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // "|" | esac
  private static boolean case_branch_recover_rule_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_branch_recover_rule_0")) return false;
    boolean r;
    r = consumeToken(b, LAMA_CASE_OR);
    if (!r) r = consumeToken(b, LAMA_ESAC);
    return r;
  }

  /* ********************************************************** */
  // scope
  static boolean case_branch_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_branch_scope")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = scope(b, l + 1);
    exit_section_(b, l, m, r, false, LamaParser::case_branch_recover_rule);
    return r;
  }

  /* ********************************************************** */
  // "==" | "!=" | "<=" | "<" | ">=" | ">"
  public static boolean compare_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compare_operator")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMA_COMPARE_OPERATOR, "<compare operator>");
    r = consumeToken(b, LAMA_EQEQ);
    if (!r) r = consumeToken(b, LAMA_NEQ);
    if (!r) r = consumeToken(b, LAMA_LEQ);
    if (!r) r = consumeToken(b, LAMA_LE);
    if (!r) r = consumeToken(b, LAMA_GEQ);
    if (!r) r = consumeToken(b, LAMA_GE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // variable_definition_series | function_definition | infix_operator_definition
  static boolean definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition")) return false;
    boolean r;
    r = variable_definition_series(b, l + 1);
    if (!r) r = function_definition(b, l + 1);
    if (!r) r = infix_operator_definition(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // scope
  static boolean do_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "do_scope")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = scope(b, l + 1);
    exit_section_(b, l, m, r, false, LamaParser::do_scope_recover_rule);
    return r;
  }

  /* ********************************************************** */
  // !od
  static boolean do_scope_recover_rule(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "do_scope_recover_rule")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, LAMA_OD);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // strict_scope
  static boolean do_while_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "do_while_scope")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = strict_scope(b, l + 1);
    exit_section_(b, l, m, r, false, LamaParser::do_while_scope_recover_rule);
    return r;
  }

  /* ********************************************************** */
  // !while
  static boolean do_while_scope_recover_rule(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "do_while_scope_recover_rule")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, LAMA_WHILE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "."
  public static boolean dot_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dot_operator")) return false;
    if (!nextTokenIs(b, LAMA_DOT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LAMA_DOT);
    exit_section_(b, m, LAMA_DOT_OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // elif expression_batch then then_scope
  public static boolean elif_branch(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elif_branch")) return false;
    if (!nextTokenIs(b, LAMA_ELIF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_IF_BRANCH, null);
    r = consumeToken(b, LAMA_ELIF);
    p = r; // pin = 1
    r = r && report_error_(b, expression_batch(b, l + 1));
    r = p && report_error_(b, consumeToken(b, LAMA_THEN)) && r;
    r = p && then_scope(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // else else_scope
  public static boolean else_branch(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "else_branch")) return false;
    if (!nextTokenIs(b, LAMA_ELSE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_IF_BRANCH, null);
    r = consumeToken(b, LAMA_ELSE);
    p = r; // pin = 1
    r = r && else_scope(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // !fi
  static boolean else_expression_scope_recover_rule(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "else_expression_scope_recover_rule")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, LAMA_FI);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // scope
  static boolean else_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "else_scope")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = scope(b, l + 1);
    exit_section_(b, l, m, r, false, LamaParser::else_expression_scope_recover_rule);
    return r;
  }

  /* ********************************************************** */
  // expression       (";"?  expression)*
  static boolean expression_batch(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_batch")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1, -1);
    r = r && expression_batch_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (";"?  expression)*
  private static boolean expression_batch_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_batch_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!expression_batch_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "expression_batch_1", c)) break;
    }
    return true;
  }

  // ";"?  expression
  private static boolean expression_batch_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_batch_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression_batch_1_0_0(b, l + 1);
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ";"?
  private static boolean expression_batch_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_batch_1_0_0")) return false;
    consumeToken(b, LAMA_SEMI);
    return true;
  }

  /* ********************************************************** */
  // expression_batch ("," expression_batch)*
  public static boolean expression_series(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_series")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMA_EXPRESSION_SERIES, "<expression series>");
    r = expression_batch(b, l + 1);
    r = r && expression_series_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ("," expression_batch)*
  private static boolean expression_series_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_series_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!expression_series_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "expression_series_1", c)) break;
    }
    return true;
  }

  // "," expression_batch
  private static boolean expression_series_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_series_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_COMMA);
    p = r; // pin = ","
    r = r && expression_batch(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // scope
  static boolean for_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_scope")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = scope(b, l + 1);
    exit_section_(b, l, m, r, false, LamaParser::for_scope_recover_rule);
    return r;
  }

  /* ********************************************************** */
  // !("," | do)
  static boolean for_scope_recover_rule(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_scope_recover_rule")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !for_scope_recover_rule_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // "," | do
  private static boolean for_scope_recover_rule_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_scope_recover_rule_0")) return false;
    boolean r;
    r = consumeToken(b, LAMA_COMMA);
    if (!r) r = consumeToken(b, LAMA_DO);
    return r;
  }

  /* ********************************************************** */
  // "{" function_body_scope "}"
  public static boolean function_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_body")) return false;
    if (!nextTokenIs(b, LAMA_LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_FUNCTION_BODY, null);
    r = consumeToken(b, LAMA_LBRACE);
    p = r; // pin = 1
    r = r && report_error_(b, function_body_scope(b, l + 1));
    r = p && consumeToken(b, LAMA_RBRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // scope
  static boolean function_body_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_body_scope")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = scope(b, l + 1);
    exit_section_(b, l, m, r, false, LamaParser::function_body_scope_recover_rule);
    return r;
  }

  /* ********************************************************** */
  // !"}"
  static boolean function_body_scope_recover_rule(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_body_scope_recover_rule")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, LAMA_RBRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // [ public ] fun identifier_expression parameter_list function_body
  public static boolean function_definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_definition")) return false;
    if (!nextTokenIs(b, "<function definition>", LAMA_FUN, LAMA_PUBLIC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_FUNCTION_DEFINITION, "<function definition>");
    r = function_definition_0(b, l + 1);
    r = r && consumeToken(b, LAMA_FUN);
    r = r && identifier_expression(b, l + 1);
    p = r; // pin = 3
    r = r && report_error_(b, parameter_list(b, l + 1));
    r = p && function_body(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ public ]
  private static boolean function_definition_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_definition_0")) return false;
    consumeToken(b, LAMA_PUBLIC);
    return true;
  }

  /* ********************************************************** */
  // recoverable_import_statement ";"?
  public static boolean import_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_statement")) return false;
    if (!nextTokenIs(b, LAMA_IMPORT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_IMPORT_STATEMENT, null);
    r = recoverable_import_statement(b, l + 1);
    p = r; // pin = 1
    r = r && import_statement_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ";"?
  private static boolean import_statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_statement_1")) return false;
    consumeToken(b, LAMA_SEMI);
    return true;
  }

  /* ********************************************************** */
  // [ public ] (infix | infixl | infixr) any_operator [ at | before | after ] any_operator
  static boolean infix_head(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "infix_head")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = infix_head_0(b, l + 1);
    r = r && infix_head_1(b, l + 1);
    r = r && any_operator(b, l + 1);
    r = r && infix_head_3(b, l + 1);
    r = r && any_operator(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ public ]
  private static boolean infix_head_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "infix_head_0")) return false;
    consumeToken(b, LAMA_PUBLIC);
    return true;
  }

  // infix | infixl | infixr
  private static boolean infix_head_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "infix_head_1")) return false;
    boolean r;
    r = consumeToken(b, LAMA_INFIX);
    if (!r) r = consumeToken(b, LAMA_INFIXL);
    if (!r) r = consumeToken(b, LAMA_INFIXR);
    return r;
  }

  // [ at | before | after ]
  private static boolean infix_head_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "infix_head_3")) return false;
    infix_head_3_0(b, l + 1);
    return true;
  }

  // at | before | after
  private static boolean infix_head_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "infix_head_3_0")) return false;
    boolean r;
    r = consumeToken(b, LAMA_AT);
    if (!r) r = consumeToken(b, LAMA_BEFORE);
    if (!r) r = consumeToken(b, LAMA_AFTER);
    return r;
  }

  /* ********************************************************** */
  // INFIX_OP | "@" | "#" | "->" | ("|" !(pattern "->")) | "=" | "?"
  public static boolean infix_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "infix_operator")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMA_INFIX_OPERATOR, "<infix operator>");
    r = consumeToken(b, LAMA_INFIX_OP);
    if (!r) r = consumeToken(b, LAMA_AT_SIGN);
    if (!r) r = consumeToken(b, LAMA_SHARP);
    if (!r) r = consumeToken(b, LAMA_ARROW);
    if (!r) r = infix_operator_4(b, l + 1);
    if (!r) r = consumeToken(b, LAMA_EQ);
    if (!r) r = consumeToken(b, LAMA_QUEST);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // "|" !(pattern "->")
  private static boolean infix_operator_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "infix_operator_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LAMA_CASE_OR);
    r = r && infix_operator_4_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !(pattern "->")
  private static boolean infix_operator_4_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "infix_operator_4_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !infix_operator_4_1_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // pattern "->"
  private static boolean infix_operator_4_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "infix_operator_4_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = pattern(b, l + 1);
    r = r && consumeToken(b, LAMA_ARROW);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // infix_head parameter_list function_body
  public static boolean infix_operator_definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "infix_operator_definition")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_INFIX_OPERATOR_DEFINITION, "<infix operator definition>");
    r = infix_head(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, parameter_list(b, l + 1));
    r = p && function_body(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ":"
  public static boolean list_cons_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_cons_operator")) return false;
    if (!nextTokenIs(b, LAMA_LIST_CONS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LAMA_LIST_CONS);
    exit_section_(b, m, LAMA_LIST_CONS_OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // "{" [ pattern_list ] "}"
  public static boolean list_pattern(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_pattern")) return false;
    if (!nextTokenIs(b, LAMA_LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_LIST_PATTERN, null);
    r = consumeToken(b, LAMA_LBRACE);
    p = r; // pin = 1
    r = r && report_error_(b, list_pattern_1(b, l + 1));
    r = p && consumeToken(b, LAMA_RBRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ pattern_list ]
  private static boolean list_pattern_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_pattern_1")) return false;
    pattern_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "*" | "/" | "%"
  public static boolean mul_div_mod_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_div_mod_operator")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMA_MUL_DIV_MOD_OPERATOR, "<mul div mod operator>");
    r = consumeToken(b, LAMA_MUL);
    if (!r) r = consumeToken(b, LAMA_DIV);
    if (!r) r = consumeToken(b, LAMA_MOD);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "!!"
  public static boolean or_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "or_operator")) return false;
    if (!nextTokenIs(b, LAMA_OR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LAMA_OR);
    exit_section_(b, m, LAMA_OR_OPERATOR, r);
    return r;
  }

  /* ********************************************************** */
  // "(" [ pattern_list ] ")"
  public static boolean parameter_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_list")) return false;
    if (!nextTokenIs(b, LAMA_LPAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_PARAMETER_LIST, null);
    r = consumeToken(b, LAMA_LPAR);
    p = r; // pin = 1
    r = r && report_error_(b, parameter_list_1(b, l + 1));
    r = p && consumeToken(b, LAMA_RPAR) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ pattern_list ]
  private static boolean parameter_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_list_1")) return false;
    pattern_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // scope
  static boolean parenthesized_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_scope")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = scope(b, l + 1);
    exit_section_(b, l, m, r, false, LamaParser::parenthesized_scope_recover_rule);
    return r;
  }

  /* ********************************************************** */
  // !")"
  static boolean parenthesized_scope_recover_rule(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_scope_recover_rule")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, LAMA_RPAR);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // simple_pattern (":" simple_pattern)*
  public static boolean pattern(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pattern")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, LAMA_PATTERN, "<pattern>");
    r = simple_pattern(b, l + 1);
    r = r && pattern_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (":" simple_pattern)*
  private static boolean pattern_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pattern_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!pattern_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "pattern_1", c)) break;
    }
    return true;
  }

  // ":" simple_pattern
  private static boolean pattern_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pattern_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_LIST_CONS);
    p = r; // pin = ":"
    r = r && simple_pattern(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // pattern ("," pattern)*
  static boolean pattern_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pattern_list")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = pattern(b, l + 1);
    r = r && pattern_list_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ("," pattern)*
  private static boolean pattern_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pattern_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!pattern_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "pattern_list_1", c)) break;
    }
    return true;
  }

  // "," pattern
  private static boolean pattern_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pattern_list_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_COMMA);
    p = r; // pin = ","
    r = r && pattern(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // "(" pattern ")"
  static boolean pattern_parenthesized(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pattern_parenthesized")) return false;
    if (!nextTokenIs(b, LAMA_LPAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_LPAR);
    p = r; // pin = 1
    r = r && report_error_(b, pattern(b, l + 1));
    r = p && consumeToken(b, LAMA_RPAR) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // "+" | "-"
  public static boolean plus_minus_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "plus_minus_operator")) return false;
    if (!nextTokenIs(b, "<plus minus operator>", LAMA_MINUS, LAMA_PLUS)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMA_PLUS_MINUS_OPERATOR, "<plus minus operator>");
    r = consumeToken(b, LAMA_PLUS);
    if (!r) r = consumeToken(b, LAMA_MINUS);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // assignment_operator | list_cons_operator  | or_operator | and_operator |
  //                                 compare_operator    | plus_minus_operator | mul_div_mod_operator
  static boolean predefined_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "predefined_operator")) return false;
    boolean r;
    r = assignment_operator(b, l + 1);
    if (!r) r = list_cons_operator(b, l + 1);
    if (!r) r = or_operator(b, l + 1);
    if (!r) r = and_operator(b, l + 1);
    if (!r) r = compare_operator(b, l + 1);
    if (!r) r = plus_minus_operator(b, l + 1);
    if (!r) r = mul_div_mod_operator(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // import identifier_expression
  static boolean recoverable_import_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "recoverable_import_statement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_IMPORT);
    p = r; // pin = 1
    r = r && identifier_expression(b, l + 1);
    exit_section_(b, l, m, r, p, LamaParser::semi_recover_rule);
    return r || p;
  }

  /* ********************************************************** */
  // import_statement* scope
  static boolean root(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = root_0(b, l + 1);
    r = r && scope(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // import_statement*
  private static boolean root_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!import_statement(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "root_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // identifier_expression [ s_pattern_suffix | at_pattern_suffix ]
  public static boolean s_or_at_pattern(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_or_at_pattern")) return false;
    if (!nextTokenIs(b, "<s or at pattern>", LAMA_LINDENT, LAMA_UINDENT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMA_S_OR_AT_PATTERN, "<s or at pattern>");
    r = identifier_expression(b, l + 1);
    r = r && s_or_at_pattern_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [ s_pattern_suffix | at_pattern_suffix ]
  private static boolean s_or_at_pattern_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_or_at_pattern_1")) return false;
    s_or_at_pattern_1_0(b, l + 1);
    return true;
  }

  // s_pattern_suffix | at_pattern_suffix
  private static boolean s_or_at_pattern_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_or_at_pattern_1_0")) return false;
    boolean r;
    r = s_pattern_suffix(b, l + 1);
    if (!r) r = at_pattern_suffix(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // "(" pattern_list ")"
  static boolean s_pattern_suffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_pattern_suffix")) return false;
    if (!nextTokenIs(b, LAMA_LPAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_LPAR);
    p = r; // pin = 1
    r = r && report_error_(b, pattern_list(b, l + 1));
    r = p && consumeToken(b, LAMA_RPAR) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // definition* [ expression_batch ]
  public static boolean scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scope")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMA_SCOPE, "<scope>");
    r = scope_0(b, l + 1);
    r = r && scope_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // definition*
  private static boolean scope_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scope_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!definition(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "scope_0", c)) break;
    }
    return true;
  }

  // [ expression_batch ]
  private static boolean scope_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scope_1")) return false;
    expression_batch(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // !(";" | <<isEOL>>)
  static boolean semi_recover_rule(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "semi_recover_rule")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !semi_recover_rule_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ";" | <<isEOL>>
  private static boolean semi_recover_rule_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "semi_recover_rule_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LAMA_SEMI);
    if (!r) r = isEOL(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "#" (box | val | str | array | sexp | fun)
  public static boolean sharp_pattern(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sharp_pattern")) return false;
    if (!nextTokenIs(b, LAMA_SHARP)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LAMA_SHARP);
    r = r && sharp_pattern_1(b, l + 1);
    exit_section_(b, m, LAMA_SHARP_PATTERN, r);
    return r;
  }

  // box | val | str | array | sexp | fun
  private static boolean sharp_pattern_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sharp_pattern_1")) return false;
    boolean r;
    r = consumeToken(b, LAMA_BOX);
    if (!r) r = consumeToken(b, LAMA_VAL);
    if (!r) r = consumeToken(b, LAMA_STR);
    if (!r) r = consumeToken(b, LAMA_ARRAY);
    if (!r) r = consumeToken(b, LAMA_SEXP);
    if (!r) r = consumeToken(b, LAMA_FUN);
    return r;
  }

  /* ********************************************************** */
  // wildcard_pattern | s_or_at_pattern | array_pattern | list_pattern |
  //                            numeric_literal | string_literal | char_literal |
  //                            boolean_literal | sharp_pattern | pattern_parenthesized
  static boolean simple_pattern(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_pattern")) return false;
    boolean r;
    r = wildcard_pattern(b, l + 1);
    if (!r) r = s_or_at_pattern(b, l + 1);
    if (!r) r = array_pattern(b, l + 1);
    if (!r) r = list_pattern(b, l + 1);
    if (!r) r = numeric_literal(b, l + 1);
    if (!r) r = string_literal(b, l + 1);
    if (!r) r = char_literal(b, l + 1);
    if (!r) r = boolean_literal(b, l + 1);
    if (!r) r = sharp_pattern(b, l + 1);
    if (!r) r = pattern_parenthesized(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // expression       (";"   expression)*
  static boolean strict_expression_batch(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "strict_expression_batch")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1, -1);
    r = r && strict_expression_batch_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (";"   expression)*
  private static boolean strict_expression_batch_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "strict_expression_batch_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!strict_expression_batch_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "strict_expression_batch_1", c)) break;
    }
    return true;
  }

  // ";"   expression
  private static boolean strict_expression_batch_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "strict_expression_batch_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_SEMI);
    p = r; // pin = ";"
    r = r && expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // definition* [ strict_expression_batch ]
  public static boolean strict_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "strict_scope")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMA_SCOPE, "<strict scope>");
    r = strict_scope_0(b, l + 1);
    r = r && strict_scope_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // definition*
  private static boolean strict_scope_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "strict_scope_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!definition(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "strict_scope_0", c)) break;
    }
    return true;
  }

  // [ strict_expression_batch ]
  private static boolean strict_scope_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "strict_scope_1")) return false;
    strict_expression_batch(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // [ "-" ] [ pattern "=" ] syntax_primary ("*" | "+" | "?")*
  public static boolean syntax_binding(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_binding")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_SYNTAX_BINDING, "<syntax binding>");
    r = syntax_binding_0(b, l + 1);
    r = r && syntax_binding_1(b, l + 1);
    r = r && syntax_primary(b, l + 1);
    p = r; // pin = 3
    r = r && syntax_binding_3(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ "-" ]
  private static boolean syntax_binding_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_binding_0")) return false;
    consumeToken(b, LAMA_MINUS);
    return true;
  }

  // [ pattern "=" ]
  private static boolean syntax_binding_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_binding_1")) return false;
    syntax_binding_1_0(b, l + 1);
    return true;
  }

  // pattern "="
  private static boolean syntax_binding_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_binding_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = pattern(b, l + 1);
    r = r && consumeToken(b, LAMA_EQ);
    exit_section_(b, m, null, r);
    return r;
  }

  // ("*" | "+" | "?")*
  private static boolean syntax_binding_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_binding_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!syntax_binding_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "syntax_binding_3", c)) break;
    }
    return true;
  }

  // "*" | "+" | "?"
  private static boolean syntax_binding_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_binding_3_0")) return false;
    boolean r;
    r = consumeToken(b, LAMA_MUL);
    if (!r) r = consumeToken(b, LAMA_PLUS);
    if (!r) r = consumeToken(b, LAMA_QUEST);
    return r;
  }

  /* ********************************************************** */
  // (identifier_expression syntax_primary_array*) | syntax_primary_parenthesized | syntax_primary_expression_from
  static boolean syntax_primary(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_primary")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = syntax_primary_0(b, l + 1);
    if (!r) r = syntax_primary_parenthesized(b, l + 1);
    if (!r) r = syntax_primary_expression_from(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // identifier_expression syntax_primary_array*
  private static boolean syntax_primary_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_primary_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier_expression(b, l + 1);
    r = r && syntax_primary_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // syntax_primary_array*
  private static boolean syntax_primary_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_primary_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!syntax_primary_array(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "syntax_primary_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // "[" [ expression_series ] "]"
  public static boolean syntax_primary_array(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_primary_array")) return false;
    if (!nextTokenIs(b, LAMA_LBRACKET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_SYNTAX_PRIMARY_ARRAY, null);
    r = consumeToken(b, LAMA_LBRACKET);
    p = r; // pin = 1
    r = r && report_error_(b, syntax_primary_array_1(b, l + 1));
    r = p && consumeToken(b, LAMA_RBRACKET) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ expression_series ]
  private static boolean syntax_primary_array_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_primary_array_1")) return false;
    expression_series(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "$(" expression_batch ")"
  public static boolean syntax_primary_expression_from(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_primary_expression_from")) return false;
    if (!nextTokenIs(b, LAMA_DOLLAR_LPAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_SYNTAX_PRIMARY_EXPRESSION_FROM, null);
    r = consumeToken(b, LAMA_DOLLAR_LPAR);
    p = r; // pin = 1
    r = r && report_error_(b, expression_batch(b, l + 1));
    r = p && consumeToken(b, LAMA_RPAR) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // "(" syntax_seqs ")"
  public static boolean syntax_primary_parenthesized(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_primary_parenthesized")) return false;
    if (!nextTokenIs(b, LAMA_LPAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_SYNTAX_PRIMARY_PARENTHESIZED, null);
    r = consumeToken(b, LAMA_LPAR);
    p = r; // pin = 1
    r = r && report_error_(b, syntax_seqs(b, l + 1));
    r = p && consumeToken(b, LAMA_RPAR) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // syntax_binding+ [ syntax_seq_body ]
  public static boolean syntax_seq(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_seq")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_SYNTAX_SEQ, "<syntax seq>");
    r = syntax_seq_0(b, l + 1);
    p = r; // pin = 1
    r = r && syntax_seq_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // syntax_binding+
  private static boolean syntax_seq_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_seq_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = syntax_binding(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!syntax_binding(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "syntax_seq_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // [ syntax_seq_body ]
  private static boolean syntax_seq_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_seq_1")) return false;
    syntax_seq_body(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "{" expression "}"
  static boolean syntax_seq_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_seq_body")) return false;
    if (!nextTokenIs(b, LAMA_LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_LBRACE);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && consumeToken(b, LAMA_RBRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // syntax_seq ("|" syntax_seq)*
  static boolean syntax_seqs(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_seqs")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = syntax_seq(b, l + 1);
    p = r; // pin = 1
    r = r && syntax_seqs_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ("|" syntax_seq)*
  private static boolean syntax_seqs_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_seqs_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!syntax_seqs_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "syntax_seqs_1", c)) break;
    }
    return true;
  }

  // "|" syntax_seq
  private static boolean syntax_seqs_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_seqs_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_CASE_OR);
    p = r; // pin = 1
    r = r && syntax_seq(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // then then_scope
  public static boolean then_branch(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "then_branch")) return false;
    if (!nextTokenIs(b, LAMA_THEN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_IF_BRANCH, null);
    r = consumeToken(b, LAMA_THEN);
    p = r; // pin = 1
    r = r && then_scope(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // !(fi | else | elif)
  static boolean then_expression_scope_recover_rule(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "then_expression_scope_recover_rule")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !then_expression_scope_recover_rule_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // fi | else | elif
  private static boolean then_expression_scope_recover_rule_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "then_expression_scope_recover_rule_0")) return false;
    boolean r;
    r = consumeToken(b, LAMA_FI);
    if (!r) r = consumeToken(b, LAMA_ELSE);
    if (!r) r = consumeToken(b, LAMA_ELIF);
    return r;
  }

  /* ********************************************************** */
  // scope
  static boolean then_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "then_scope")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = scope(b, l + 1);
    exit_section_(b, l, m, r, false, LamaParser::then_expression_scope_recover_rule);
    return r;
  }

  /* ********************************************************** */
  // identifier_expression [ variable_definition_default_value ]
  public static boolean variable_definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition")) return false;
    if (!nextTokenIs(b, "<variable definition>", LAMA_LINDENT, LAMA_UINDENT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_VARIABLE_DEFINITION, "<variable definition>");
    r = identifier_expression(b, l + 1);
    p = r; // pin = 1
    r = r && variable_definition_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ variable_definition_default_value ]
  private static boolean variable_definition_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_1")) return false;
    variable_definition_default_value(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "=" expression
  static boolean variable_definition_default_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_default_value")) return false;
    if (!nextTokenIs(b, LAMA_EQ)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_EQ);
    p = r; // pin = 1
    r = r && expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // public variable_definition_sequence
  static boolean variable_definition_public_series(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_public_series")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_PUBLIC);
    r = r && variable_definition_sequence(b, l + 1);
    exit_section_(b, l, m, r, false, LamaParser::semi_recover_rule);
    return r;
  }

  /* ********************************************************** */
  // variable_definition ("," variable_definition)*
  static boolean variable_definition_sequence(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_sequence")) return false;
    if (!nextTokenIs(b, "", LAMA_LINDENT, LAMA_UINDENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = variable_definition(b, l + 1);
    r = r && variable_definition_sequence_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ("," variable_definition)*
  private static boolean variable_definition_sequence_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_sequence_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!variable_definition_sequence_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "variable_definition_sequence_1", c)) break;
    }
    return true;
  }

  // "," variable_definition
  private static boolean variable_definition_sequence_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_sequence_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_COMMA);
    p = r; // pin = ","
    r = r && variable_definition(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // (variable_definition_var_series | variable_definition_public_series) ";"?
  public static boolean variable_definition_series(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_series")) return false;
    if (!nextTokenIs(b, "<variable definition series>", LAMA_PUBLIC, LAMA_VAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_VARIABLE_DEFINITION_SERIES, "<variable definition series>");
    r = variable_definition_series_0(b, l + 1);
    p = r; // pin = 1
    r = r && variable_definition_series_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // variable_definition_var_series | variable_definition_public_series
  private static boolean variable_definition_series_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_series_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = variable_definition_var_series(b, l + 1);
    if (!r) r = variable_definition_public_series(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ";"?
  private static boolean variable_definition_series_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_series_1")) return false;
    consumeToken(b, LAMA_SEMI);
    return true;
  }

  /* ********************************************************** */
  // var variable_definition_sequence
  static boolean variable_definition_var_series(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_var_series")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LAMA_VAR);
    p = r; // pin = 1
    r = r && variable_definition_sequence(b, l + 1);
    exit_section_(b, l, m, r, p, LamaParser::semi_recover_rule);
    return r || p;
  }

  /* ********************************************************** */
  // "_"
  public static boolean wildcard_pattern(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "wildcard_pattern")) return false;
    if (!nextTokenIs(b, LAMA_BOTTOM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LAMA_BOTTOM);
    exit_section_(b, m, LAMA_WILDCARD_PATTERN, r);
    return r;
  }

  /* ********************************************************** */
  // Expression root: expression
  // Operator priority table:
  // 0: ATOM(if_statement)
  // 1: ATOM(while_statement)
  // 2: ATOM(for_statement)
  // 3: ATOM(do_statement)
  // 4: ATOM(case_statement)
  // 5: ATOM(function_expression)
  // 6: BINARY(assignment_expression)
  // 7: BINARY(dot_expression)
  // 8: ATOM(eta_expression)
  // 9: ATOM(lazy_expression)
  // 10: ATOM(syntax_expression)
  // 11: BINARY(infix_operator_expression)
  // 12: ATOM(skip_expression)
  // 13: BINARY(or_expression)
  // 14: BINARY(and_expression)
  // 15: BINARY(compare_expression)
  // 16: BINARY(plus_minus_expression)
  // 17: BINARY(mul_div_mod_expression)
  // 18: ATOM(parenthesized_expression)
  // 19: ATOM(array_expression)
  // 20: ATOM(list_expression)
  // 21: POSTFIX(subscription_expression)
  // 22: BINARY(list_cons_expression)
  // 23: ATOM(infix_expression)
  // 24: POSTFIX(s_expression)
  // 25: POSTFIX(call_expression)
  // 26: ATOM(numeric_literal) ATOM(string_literal) ATOM(char_literal) ATOM(boolean_literal)
  //    ATOM(identifier_expression)
  public static boolean expression(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expression")) return false;
    addVariant(b, "<expression>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<expression>");
    r = if_statement(b, l + 1);
    if (!r) r = while_statement(b, l + 1);
    if (!r) r = for_statement(b, l + 1);
    if (!r) r = do_statement(b, l + 1);
    if (!r) r = case_statement(b, l + 1);
    if (!r) r = function_expression(b, l + 1);
    if (!r) r = eta_expression(b, l + 1);
    if (!r) r = lazy_expression(b, l + 1);
    if (!r) r = syntax_expression(b, l + 1);
    if (!r) r = skip_expression(b, l + 1);
    if (!r) r = parenthesized_expression(b, l + 1);
    if (!r) r = array_expression(b, l + 1);
    if (!r) r = list_expression(b, l + 1);
    if (!r) r = infix_expression(b, l + 1);
    if (!r) r = numeric_literal(b, l + 1);
    if (!r) r = string_literal(b, l + 1);
    if (!r) r = char_literal(b, l + 1);
    if (!r) r = boolean_literal(b, l + 1);
    if (!r) r = identifier_expression(b, l + 1);
    p = r;
    r = r && expression_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean expression_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expression_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 6 && assignment_operator(b, l + 1)) {
        r = expression(b, l, 5);
        exit_section_(b, l, m, LAMA_OPERATOR_EXPRESSION, r, true, null);
      }
      else if (g < 7 && dot_operator(b, l + 1)) {
        r = expression(b, l, 7);
        exit_section_(b, l, m, LAMA_OPERATOR_EXPRESSION, r, true, null);
      }
      else if (g < 11 && infix_operator(b, l + 1)) {
        r = expression(b, l, 11);
        exit_section_(b, l, m, LAMA_OPERATOR_EXPRESSION, r, true, null);
      }
      else if (g < 13 && or_operator(b, l + 1)) {
        r = expression(b, l, 13);
        exit_section_(b, l, m, LAMA_OPERATOR_EXPRESSION, r, true, null);
      }
      else if (g < 14 && and_operator(b, l + 1)) {
        r = expression(b, l, 14);
        exit_section_(b, l, m, LAMA_OPERATOR_EXPRESSION, r, true, null);
      }
      else if (g < 15 && compare_operator(b, l + 1)) {
        r = expression(b, l, 15);
        exit_section_(b, l, m, LAMA_OPERATOR_EXPRESSION, r, true, null);
      }
      else if (g < 16 && plus_minus_operator(b, l + 1)) {
        r = expression(b, l, 16);
        exit_section_(b, l, m, LAMA_OPERATOR_EXPRESSION, r, true, null);
      }
      else if (g < 17 && mul_div_mod_operator(b, l + 1)) {
        r = expression(b, l, 17);
        exit_section_(b, l, m, LAMA_OPERATOR_EXPRESSION, r, true, null);
      }
      else if (g < 21 && subscription_expression_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, LAMA_SUBSCRIPTION_EXPRESSION, r, true, null);
      }
      else if (g < 22 && list_cons_operator(b, l + 1)) {
        r = expression(b, l, 21);
        exit_section_(b, l, m, LAMA_OPERATOR_EXPRESSION, r, true, null);
      }
      else if (g < 24 && leftMarkerIs(b, LAMA_IDENTIFIER_EXPRESSION) && s_expression_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, LAMA_CALL_EXPRESSION, r, true, null);
      }
      else if (g < 25 && argument_list(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, LAMA_CALL_EXPRESSION, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // if expression_batch then_branch elif_branch* [ else_branch ] fi
  public static boolean if_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_statement")) return false;
    if (!nextTokenIsSmart(b, LAMA_IF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_IF_STATEMENT, null);
    r = consumeTokenSmart(b, LAMA_IF);
    p = r; // pin = 1
    r = r && report_error_(b, expression_batch(b, l + 1));
    r = p && report_error_(b, then_branch(b, l + 1)) && r;
    r = p && report_error_(b, if_statement_3(b, l + 1)) && r;
    r = p && report_error_(b, if_statement_4(b, l + 1)) && r;
    r = p && consumeToken(b, LAMA_FI) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // elif_branch*
  private static boolean if_statement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_statement_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!elif_branch(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "if_statement_3", c)) break;
    }
    return true;
  }

  // [ else_branch ]
  private static boolean if_statement_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_statement_4")) return false;
    else_branch(b, l + 1);
    return true;
  }

  // while strict_expression_batch do do_scope od
  public static boolean while_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_statement")) return false;
    if (!nextTokenIsSmart(b, LAMA_WHILE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_WHILE_STATEMENT, null);
    r = consumeTokenSmart(b, LAMA_WHILE);
    p = r; // pin = 1
    r = r && report_error_(b, strict_expression_batch(b, l + 1));
    r = p && report_error_(b, consumeToken(b, LAMA_DO)) && r;
    r = p && report_error_(b, do_scope(b, l + 1)) && r;
    r = p && consumeToken(b, LAMA_OD) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // for for_scope "," expression_batch "," strict_expression_batch do do_scope od
  public static boolean for_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_statement")) return false;
    if (!nextTokenIsSmart(b, LAMA_FOR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_FOR_STATEMENT, null);
    r = consumeTokenSmart(b, LAMA_FOR);
    p = r; // pin = 1
    r = r && report_error_(b, for_scope(b, l + 1));
    r = p && report_error_(b, consumeToken(b, LAMA_COMMA)) && r;
    r = p && report_error_(b, expression_batch(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, LAMA_COMMA)) && r;
    r = p && report_error_(b, strict_expression_batch(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, LAMA_DO)) && r;
    r = p && report_error_(b, do_scope(b, l + 1)) && r;
    r = p && consumeToken(b, LAMA_OD) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // do do_while_scope while expression_batch od
  public static boolean do_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "do_statement")) return false;
    if (!nextTokenIsSmart(b, LAMA_DO)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_DO_STATEMENT, null);
    r = consumeTokenSmart(b, LAMA_DO);
    p = r; // pin = 1
    r = r && report_error_(b, do_while_scope(b, l + 1));
    r = p && report_error_(b, consumeToken(b, LAMA_WHILE)) && r;
    r = p && report_error_(b, expression_batch(b, l + 1)) && r;
    r = p && consumeToken(b, LAMA_OD) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // case expression_batch of case_branch another_case_branch* esac
  public static boolean case_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_statement")) return false;
    if (!nextTokenIsSmart(b, LAMA_CASE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_CASE_STATEMENT, null);
    r = consumeTokenSmart(b, LAMA_CASE);
    p = r; // pin = 1
    r = r && report_error_(b, expression_batch(b, l + 1));
    r = p && report_error_(b, consumeToken(b, LAMA_OF)) && r;
    r = p && report_error_(b, case_branch(b, l + 1)) && r;
    r = p && report_error_(b, case_statement_4(b, l + 1)) && r;
    r = p && consumeToken(b, LAMA_ESAC) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // another_case_branch*
  private static boolean case_statement_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_statement_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!another_case_branch(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "case_statement_4", c)) break;
    }
    return true;
  }

  // fun parameter_list function_body
  public static boolean function_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_expression")) return false;
    if (!nextTokenIsSmart(b, LAMA_FUN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_FUNCTION_EXPRESSION, null);
    r = consumeTokenSmart(b, LAMA_FUN);
    r = r && parameter_list(b, l + 1);
    p = r; // pin = 2
    r = r && function_body(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // eta   expression
  public static boolean eta_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "eta_expression")) return false;
    if (!nextTokenIsSmart(b, LAMA_ETA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_ETA_EXPRESSION, null);
    r = consumeTokenSmart(b, LAMA_ETA);
    p = r; // pin = 1
    r = r && expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // lazy  expression
  public static boolean lazy_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lazy_expression")) return false;
    if (!nextTokenIsSmart(b, LAMA_LAZY)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_LAZY_EXPRESSION, null);
    r = consumeTokenSmart(b, LAMA_LAZY);
    p = r; // pin = 1
    r = r && expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // syntax "(" syntax_seqs ")"
  public static boolean syntax_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "syntax_expression")) return false;
    if (!nextTokenIsSmart(b, LAMA_SYNTAX)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_SYNTAX_EXPRESSION, null);
    r = consumeTokensSmart(b, 1, LAMA_SYNTAX, LAMA_LPAR);
    p = r; // pin = 1
    r = r && report_error_(b, syntax_seqs(b, l + 1));
    r = p && consumeToken(b, LAMA_RPAR) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // skip
  public static boolean skip_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "skip_expression")) return false;
    if (!nextTokenIsSmart(b, LAMA_SKIP)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LAMA_SKIP);
    exit_section_(b, m, LAMA_SKIP_EXPRESSION, r);
    return r;
  }

  // "(" parenthesized_scope ")"
  public static boolean parenthesized_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_expression")) return false;
    if (!nextTokenIsSmart(b, LAMA_LPAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_PARENTHESIZED_EXPRESSION, null);
    r = consumeTokenSmart(b, LAMA_LPAR);
    p = r; // pin = 1
    r = r && report_error_(b, parenthesized_scope(b, l + 1));
    r = p && consumeToken(b, LAMA_RPAR) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // "[" [ expression_series ] "]"
  public static boolean array_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_expression")) return false;
    if (!nextTokenIsSmart(b, LAMA_LBRACKET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_ARRAY_EXPRESSION, null);
    r = consumeTokenSmart(b, LAMA_LBRACKET);
    p = r; // pin = 1
    r = r && report_error_(b, array_expression_1(b, l + 1));
    r = p && consumeToken(b, LAMA_RBRACKET) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ expression_series ]
  private static boolean array_expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_expression_1")) return false;
    expression_series(b, l + 1);
    return true;
  }

  // "{" [ expression_series ] "}"
  public static boolean list_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_expression")) return false;
    if (!nextTokenIsSmart(b, LAMA_LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_LIST_EXPRESSION, null);
    r = consumeTokenSmart(b, LAMA_LBRACE);
    p = r; // pin = 1
    r = r && report_error_(b, list_expression_1(b, l + 1));
    r = p && consumeToken(b, LAMA_RBRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ expression_series ]
  private static boolean list_expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_expression_1")) return false;
    expression_series(b, l + 1);
    return true;
  }

  // "[" [ expression_batch ] "]"
  private static boolean subscription_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subscription_expression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LAMA_LBRACKET);
    r = r && subscription_expression_0_1(b, l + 1);
    r = r && consumeToken(b, LAMA_RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ expression_batch ]
  private static boolean subscription_expression_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subscription_expression_0_1")) return false;
    expression_batch(b, l + 1);
    return true;
  }

  // infix any_operator
  public static boolean infix_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "infix_expression")) return false;
    if (!nextTokenIsSmart(b, LAMA_INFIX)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMA_INFIX_EXPRESSION, null);
    r = consumeTokenSmart(b, LAMA_INFIX);
    p = r; // pin = 1
    r = r && any_operator(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ argument_list ]
  private static boolean s_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_expression_0")) return false;
    argument_list(b, l + 1);
    return true;
  }

  // NUMBER | "-" NUMBER
  public static boolean numeric_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "numeric_literal")) return false;
    if (!nextTokenIsSmart(b, LAMA_MINUS, LAMA_NUMBER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMA_NUMERIC_LITERAL, "<numeric literal>");
    r = consumeTokenSmart(b, LAMA_NUMBER);
    if (!r) r = parseTokensSmart(b, 0, LAMA_MINUS, LAMA_NUMBER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // STRING
  public static boolean string_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_literal")) return false;
    if (!nextTokenIsSmart(b, LAMA_STRING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LAMA_STRING);
    exit_section_(b, m, LAMA_STRING_LITERAL, r);
    return r;
  }

  // CHAR
  public static boolean char_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "char_literal")) return false;
    if (!nextTokenIsSmart(b, LAMA_CHAR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LAMA_CHAR);
    exit_section_(b, m, LAMA_CHAR_LITERAL, r);
    return r;
  }

  // true | false
  public static boolean boolean_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "boolean_literal")) return false;
    if (!nextTokenIsSmart(b, LAMA_FALSE, LAMA_TRUE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMA_BOOLEAN_LITERAL, "<boolean literal>");
    r = consumeTokenSmart(b, LAMA_TRUE);
    if (!r) r = consumeTokenSmart(b, LAMA_FALSE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // UINDENT | LINDENT
  public static boolean identifier_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier_expression")) return false;
    if (!nextTokenIsSmart(b, LAMA_LINDENT, LAMA_UINDENT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMA_IDENTIFIER_EXPRESSION, "<identifier expression>");
    r = consumeTokenSmart(b, LAMA_UINDENT);
    if (!r) r = consumeTokenSmart(b, LAMA_LINDENT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}
