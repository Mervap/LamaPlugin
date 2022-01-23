package org.jetbrains.lama

import com.intellij.testFramework.fixtures.BasePlatformTestCase

abstract class LamaBaseTest : BasePlatformTestCase() {
  override fun getTestDataPath(): String = "src/test/testData"
}