package org.jetbrains.lama.psi.elementTypes

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiWhiteSpace
import org.jetbrains.lama.psi.LamaLanguage
import org.jetbrains.lama.psi.api.*

object LamaElementFactory {
  fun createLamaIdentifierFromText(project: Project, text: String): LamaIdentifierExpression {
    val expression = createLamaExpressionFromText(project, text)
    return (expression as LamaSOrCallExpression).expression as LamaIdentifierExpression
  }

  fun createLamaPsiElementFromText(project: Project, text: String): LamaPsiElement {
    val file = buildLamaFileFromText(project, text)
    return file.firstChild as LamaPsiElement
  }

  fun createLamaExpressionFromText(project: Project, text: String): LamaPsiElement {
    val file = buildLamaFileFromText(project, text)
    return (file.firstChild as LamaScope).expressionSeries!!.expressionList[0]
  }

  fun createNl(project: Project): PsiWhiteSpace {
    val file = buildLamaFileFromText(project, "\n")
    return file.firstChild as PsiWhiteSpace
  }

  fun buildLamaFileFromText(project: Project, text: String): LamaFile {
    return PsiFileFactory.getInstance(project).createFileFromText("file.lama", LamaLanguage, text) as LamaFile
  }
}
