package org.jetbrains.lama.psi.api

import com.intellij.psi.PsiFile

interface LamaFile : PsiFile, LamaPsiElement {
  val importStatements: List<LamaImportStatement>
  val scope: LamaScope
}
