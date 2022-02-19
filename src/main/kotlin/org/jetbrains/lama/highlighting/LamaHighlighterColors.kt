package org.jetbrains.lama.highlighting

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor

private typealias Colors = DefaultLanguageHighlighterColors

private val descriptors = ArrayList<AttributesDescriptor>()

val COMMENT = create("LAMA_LINE_COMMENT", Colors.LINE_COMMENT, "Line Comment")
val DOC_COMMENT = create("LAMA_DOCUMENT_COMMENT", Colors.DOC_COMMENT, "Document Comment")
val KEYWORD = create("LAMA_KEYWORD", Colors.KEYWORD, "Keyword")
val NUMBER = create("LAMA_NUMBER", Colors.NUMBER, "Number")
val STRING = create("LAMA_STRING", Colors.STRING, "String")

val PARENTHESES = create("LAMA_PARENTHESES", Colors.PARENTHESES, "Braces and Operators//Parentheses")
val BRACES = create("LAMA_BRACES", Colors.BRACES, "Braces and Operators//Braces")
val COMMA = create("LAMA_COMMA", Colors.COMMA, "Braces and Operators//Comma")
val SEMICOLON = create("LAMA_SEMICOLON", Colors.SEMICOLON, "Braces and Operators//Semicolon")

val LOCAL_VARIABLE = create("LAMA_LOCAL_VARIABLE", Colors.LOCAL_VARIABLE, "Variables//Local Variable")
val FUNCTION_CALL = create("LAMA_FUNCTION_CALL", Colors.FUNCTION_CALL, "Functions//Function call")
val FUNCTION_DECLARATION =
  create("LAMA_FUNCTION_DECLARATION", Colors.FUNCTION_DECLARATION, "Functions//Function declaration")

private fun create(externalName: String, fallback: TextAttributesKey, displayName: String): TextAttributesKey =
  TextAttributesKey.createTextAttributesKey(externalName, fallback)
    .also { descriptors.add(AttributesDescriptor(displayName, it)) }
