package org.jetbrains.lama.inspections

import com.intellij.codeInspection.*
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import org.jetbrains.lama.messages.LamaBundle
import org.jetbrains.lama.parser.LamaElementTypes
import org.jetbrains.lama.psi.LamaPsiUtil.importedUnits
import org.jetbrains.lama.psi.LamaPsiUtil.isDefinitionIdentifier
import org.jetbrains.lama.psi.LamaPsiUtil.unitName
import org.jetbrains.lama.psi.api.LamaIdentifierExpression
import org.jetbrains.lama.psi.api.LamaPattern
import org.jetbrains.lama.psi.api.LamaVisitor
import org.jetbrains.lama.psi.elementTypes.LamaElementFactory
import org.jetbrains.lama.psi.references.LamaSearchScope
import org.jetbrains.lama.psi.stubs.indices.LamaIdentifierNameIndex
import org.jetbrains.lama.psi.stubs.indices.LamaNameIndex

class UnresolvedSymbolInspection : LamaInspection() {
  override fun getDisplayName() = LamaBundle.message("inspection.unresolved.name")

  override fun buildVisitor(
    holder: ProblemsHolder,
    isOnTheFly: Boolean,
    session: LocalInspectionToolSession,
  ): PsiElementVisitor = object : LamaVisitor() {
    override fun visitIdentifierExpression(o: LamaIdentifierExpression) {
      if (o.isDefinitionIdentifier()) return
      if (o.parent is LamaPattern) return
      if (o.elementType == LamaElementTypes.LAMA_UINDENT) return

      val refs = o.reference?.multiResolve(false) ?: return
      val importedUnits = o.containingFile.importedUnits
      val filtered = refs.filter {
        val file = it.element?.containingFile
        file == o.containingFile || file?.unitName in importedUnits
      }
      if (filtered.isNotEmpty()) return

      val name = o.name
      val units = LamaIdentifierNameIndex.find(name, o.project, LamaSearchScope.allScope(o)).mapNotNull {
        it.containingFile.unitName
      }.distinct()
      holder.registerProblem(
        o,
        LamaBundle.message("inspection.unresolved.description", name),
        ProblemHighlightType.ERROR,
        *units.map { ImportUnitFix(it) }.toTypedArray()
      )
    }
  }

  companion object {

    private class ImportUnitFix(private val unitName: String) : LocalQuickFix {
      override fun getFamilyName() = LamaBundle.message("inspection.unresolved.fix.family.name")
      override fun getName(): String = LamaBundle.message("inspection.unresolved.fix.name", unitName)

      override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val expression = descriptor.psiElement
        val file = expression.containingFile
        val firstChild = file.firstChild
        val firstNotComment =
          if (firstChild is PsiComment || firstChild is PsiWhiteSpace) {
            PsiTreeUtil.skipWhitespacesAndCommentsForward(file.firstChild) ?: expression.lastChild
          }
          else firstChild
        file.addBefore(LamaElementFactory.createLamaPsiElementFromText(project, "import $unitName;"), firstNotComment)
        file.addBefore(LamaElementFactory.createNl(project), firstNotComment)
      }
    }
  }
}

