package org.jetbrains.lama.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import static org.jetbrains.lama.parser.LamaElementTypes.*;
import static org.jetbrains.lama.parser.LamaParserDefinition.*;

%%

%class _LamaLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType

WHITE_SPACE_CHAR = [\ \n\r\t\f]
END_OF_LINE_COMMENT = "--" [^\r\n\f]*
BLOCK_COMMENT = "(*" ~"*)"

DIGIT = [0-9]
LETTER = [:letter:]
LLETTER = [a-z]
ULETTER = [A-Z]

IDENT_CONTINUE = {LETTER}|{DIGIT}|"_"
LIDENT = {LLETTER} {IDENT_CONTINUE}*
UIDENT = {ULETTER} {IDENT_CONTINUE}*

STRING = "\"" ([^\"] | "\"\"")* "\""
CHAR = "'" ([^\']|"''")* "'"

INFIX_OP_PART = [+*/%$#@!|&\^?<>:=\\-]
NOT_INFIX_OP_PART = [^+*/%$#@!|&\^?<>:=\\-]

SHARP_PATTER = "#" {WHITE_SPACE_CHAR}* ("box" | "val" | "str" | "array" | "sexp" | "fun")

%%

<YYINITIAL> {

  {WHITE_SPACE_CHAR}+         { return TokenType.WHITE_SPACE; }
  {END_OF_LINE_COMMENT}       { return END_OF_LINE_COMMENT; }
  {BLOCK_COMMENT}             { return BLOCK_COMMENT; }

  ";"                         { return LAMA_SEMI; }
  "."                         { return LAMA_DOT; }
  ","                         { return LAMA_COMMA; }
  "$("                        { return LAMA_DOLLAR_LPAR; }
  "("                         { return LAMA_LPAR; }
  ")"                         { return LAMA_RPAR; }
  ")"                         { return LAMA_RPAR; }
  "{"                         { return LAMA_LBRACE; }
  "}"                         { return LAMA_RBRACE; }
  "["                         { return LAMA_LBRACKET; }
  "]"                         { return LAMA_RBRACKET; }
  "_"                         { return LAMA_BOTTOM; }

  "?"  / {NOT_INFIX_OP_PART}  { return LAMA_QUEST; }
  "|"  / {NOT_INFIX_OP_PART}  { return LAMA_CASE_OR; }
  "@"  / {SHARP_PATTER}       { return LAMA_AT_SIGN; }
  "@"  / {NOT_INFIX_OP_PART}  { return LAMA_AT_SIGN; }
  "#"  / {NOT_INFIX_OP_PART}  { return LAMA_SHARP; }
  "->" / {NOT_INFIX_OP_PART}  { return LAMA_ARROW; }
  "="  / {NOT_INFIX_OP_PART}  { return LAMA_EQ; }
  ":=" / {NOT_INFIX_OP_PART}  { return LAMA_ASSIGN; }
  ":"  / {NOT_INFIX_OP_PART}  { return LAMA_LIST_CONS; }
  "!!" / {NOT_INFIX_OP_PART}  { return LAMA_OR; }
  "&&" / {NOT_INFIX_OP_PART}  { return LAMA_AND; }
  "==" / {NOT_INFIX_OP_PART}  { return LAMA_EQEQ; }
  "!=" / {NOT_INFIX_OP_PART}  { return LAMA_NEQ; }
  "<=" / {NOT_INFIX_OP_PART}  { return LAMA_LEQ; }
  "<"  / {NOT_INFIX_OP_PART}  { return LAMA_LE; }
  ">=" / {NOT_INFIX_OP_PART}  { return LAMA_GEQ; }
  ">"  / {NOT_INFIX_OP_PART}  { return LAMA_GE; }
  "-"  / {NOT_INFIX_OP_PART}  { return LAMA_MINUS; }
  "+"  / {NOT_INFIX_OP_PART}  { return LAMA_PLUS; }
  "*"  / {NOT_INFIX_OP_PART}  { return LAMA_MUL; }
  "/"  / {NOT_INFIX_OP_PART}  { return LAMA_DIV; }
  "%"  / {NOT_INFIX_OP_PART}  { return LAMA_MOD; }

  {INFIX_OP_PART}+            { return LAMA_INFIX_OP; }

  "after"                     { return LAMA_AFTER; }
  "array"                     { return LAMA_ARRAY; }
  "at"                        { return LAMA_AT; }
  "before"                    { return LAMA_BEFORE; }
  "box"                       { return LAMA_BOX; }
  "case"                      { return LAMA_CASE; }
  "do"                        { return LAMA_DO; }
  "elif"                      { return LAMA_ELIF; }
  "else"                      { return LAMA_ELSE; }
  "esac"                      { return LAMA_ESAC; }
  "eta"                       { return LAMA_ETA; }
  "false"                     { return LAMA_FALSE; }
  "fi"                        { return LAMA_FI; }
  "for"                       { return LAMA_FOR; }
  "fun"                       { return LAMA_FUN; }
  "if"                        { return LAMA_IF; }
  "import"                    { return LAMA_IMPORT; }
  "infix"                     { return LAMA_INFIX; }
  "infixl"                    { return LAMA_INFIXL; }
  "infixr"                    { return LAMA_INFIXR; }
  "lazy"                      { return LAMA_LAZY; }
  "od"                        { return LAMA_OD; }
  "of"                        { return LAMA_OF; }
  "public"                    { return LAMA_PUBLIC; }
  "sexp"                      { return LAMA_SEXP; }
  "skip"                      { return LAMA_SKIP; }
  "str"                       { return LAMA_STR; }
  "syntax"                    { return LAMA_SYNTAX; }
  "then"                      { return LAMA_THEN; }
  "true"                      { return LAMA_TRUE; }
  "val"                       { return LAMA_VAL; }
  "var"                       { return LAMA_VAR; }
  "while"                     { return LAMA_WHILE; }

  {UIDENT}                    { return LAMA_UINDENT; }
  {LIDENT}                    { return LAMA_LINDENT; }

  {DIGIT}+                    { return LAMA_NUMBER; }
  {STRING}                    { return LAMA_STRING; }
  {CHAR}                      { return LAMA_CHAR; }

  .                           { return BAD_CHARACTER; }
}