package org.jetbrains.lama.parser

import com.intellij.testFramework.ParsingTestCase

abstract class LamaBaseParserTest(dataPath: String) : ParsingTestCase(dataPath, "lama", LamaParserDefinition()) {
  override fun getTestDataPath(): String = "src/test/testData"
  override fun skipSpaces(): Boolean = true
  override fun includeRanges(): Boolean = true
}