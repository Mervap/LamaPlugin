package org.jetbrains.lama

import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import java.io.File
import java.nio.file.Path

abstract class LamaMultiFileTest(private val group: String) : LamaBaseTest() {
  override fun getTestDataPath(): String = super.getTestDataPath() + "/multiFile/$group/" + getTestName(true)

  protected fun configureFiles(editorFileName: String = "main"): List<PsiFile> {
    val filePaths = collectFilePaths(editorFileName)
    val virtualFiles = filePaths.map {
      val relativePath = Path.of(testDataPath).relativize(Path.of(it)).toString()
      myFixture.copyFileToProject(relativePath)
    }
    myFixture.configureFromExistingVirtualFile(virtualFiles[0])
    return virtualFiles.map { PsiManager.getInstance(project).findFile(it)!! }
  }

  private fun collectFilePaths(editorFileName: String): List<String> {
    val files = File(testDataPath)
      .walkTopDown()
      .filter { it.isFile }
      .map { it.path }
      .sortedByDescending { it.contains(editorFileName) }
      .toList()
    if (files.isEmpty()) error("No files at $testDataPath")
    return files
  }
}