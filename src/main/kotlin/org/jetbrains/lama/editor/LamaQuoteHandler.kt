package org.jetbrains.lama.editor

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler
import org.jetbrains.lama.parser.LamaElementTypes.*
import org.jetbrains.lama.parser.LamaParserDefinition.Companion.BAD_CHARACTER

class LamaQuoteHandler : SimpleTokenSetQuoteHandler(LAMA_STRING, LAMA_CHAR, BAD_CHARACTER)