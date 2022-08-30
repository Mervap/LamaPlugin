package org.jetbrains.lama.refactoring

import com.intellij.lang.refactoring.NamesValidator
import com.intellij.openapi.project.Project

object LamaNamesValidator : NamesValidator {
  override fun isKeyword(name: String, project: Project?) = isKeyword(name)
  override fun isIdentifier(name: String, project: Project?) = isIdentifier(name)

  @Suppress("MemberVisibilityCanBePrivate")
  fun isKeyword(name: String) = name in RESERVED_WORDS

  fun isIdentifier(name: String) = !isKeyword(name) && name matches IDENTIFIER_REGEX

  fun isOperator(name: String) = name matches OPERATOR_REGEX

  private val RESERVED_WORDS = setOf(
    "after", "array", "at", "before", "box", "case", "do", "elif", "else", "esac", "eta", "false", "fi", "for",
    "fun", "if", "import", "infix", "infixl", "infixr", "lazy", "od", "of", "public", "sexp", "skip", "str",
    "syntax", "then", "true", "val", "var", "while",
  )
  private val IDENTIFIER_REGEX = "[a-zA-Z][a-zA-Z0-9_]*".toRegex()
  private val OPERATOR_REGEX = "[+*/%$#@!|&^?<>:=\\\\-]+".toRegex()
}