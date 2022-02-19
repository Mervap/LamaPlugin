package org.jetbrains.lama.psi.references

import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiManager
import com.intellij.psi.ResolveResult
import org.jetbrains.lama.psi.api.LamaIdentifierExpression
import org.jetbrains.lama.psi.stubs.indices.LamaUnitsIndex

class LamaUnitReference(element: LamaIdentifierExpression) : LamaReferenceBase<LamaIdentifierExpression>(element) {
  override fun resolveImpl(incompleteCode: Boolean): Array<ResolveResult> {
    val name = element.name
    val project = element.project
    return LamaUnitsIndex.findUnitsByName(name, project).mapNotNull {
      val psiFile = PsiManager.getInstance(project).findFile(it) ?: return@mapNotNull null
      PsiElementResolveResult(psiFile)
    }.toTypedArray()
  }
}