package org.jetbrains.lama.psi.api

import com.intellij.psi.PsiFile

interface LamaFile : PsiFile, LamaPsiElement {
  fun importStatements(): List<LamaImportStatement>
  fun scope(): LamaScope
}
