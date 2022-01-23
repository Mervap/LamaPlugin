package org.jetbrains.lama.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import org.jetbrains.lama.psi.api.LamaFile
import org.jetbrains.lama.psi.api.LamaPsiElement

object LamaElementFactory {
  fun createLamaPsiElementFromText(project: Project, text: String): LamaPsiElement {
    val file = buildLamaFileFromText(project, text)
    return file.firstChild as LamaPsiElement
  }

  fun buildLamaFileFromText(project: Project, text: String): LamaFile {
    return PsiFileFactory.getInstance(project).createFileFromText("file.lama", LamaLanguage, text) as LamaFile
  }
}
