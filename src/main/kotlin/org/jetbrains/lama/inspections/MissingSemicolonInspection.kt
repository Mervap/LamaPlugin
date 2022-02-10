package org.jetbrains.lama.inspections

import com.intellij.codeInspection.*
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import org.jetbrains.annotations.Nls
import org.jetbrains.lama.messages.LamaBundle
import org.jetbrains.lama.parser.LamaElementTypes
import org.jetbrains.lama.psi.api.*
import org.jetbrains.lama.psi.elementTypes.LamaElementFactory

class MissingSemicolonInspection : LamaInspection() {
  override fun getDisplayName() = LamaBundle.message("inspection.missing.semicolon.name")

  override fun buildVisitor(
    holder: ProblemsHolder,
    isOnTheFly: Boolean,
    session: LocalInspectionToolSession,
  ): PsiElementVisitor = object : LamaVisitor() {
    override fun visitImportStatement(o: LamaImportStatement) {
      o.checkLastChild(LamaBundle.message("inspection.missing.semicolon.import.description"))
    }

    override fun visitVariableDefinitionSeries(o: LamaVariableDefinitionSeries) {
      o.checkLastChild(LamaBundle.message("inspection.missing.semicolon.variable.description"))
    }

    override fun visitExpression(o: LamaExpression) {
      val nextSibling = PsiTreeUtil.skipWhitespacesForward(o)
      if (nextSibling is LamaExpression) {
        holder.reportMissingSemi(
          o,
          LamaBundle.message("inspection.missing.semicolon.expression.description"),
          MissingBetweenExpressionFix
        )
      }
    }

    private fun LamaPsiElement.checkLastChild(@Nls message: String) {
      if (lastChild.elementType != LamaElementTypes.LAMA_SEMI) {
        holder.reportMissingSemi(this, message, MissingLastChildFix)
      }
    }
  }

  private fun ProblemsHolder.reportMissingSemi(
    expression: LamaPsiElement,
    @Nls message: String,
    quickFix: MissingSemicolonQuickFix
  ) {
    val problemDescriptor = manager.createProblemDescriptor(
      expression,
      message,
      arrayOf(quickFix),
      ProblemHighlightType.GENERIC_ERROR,
      isOnTheFly,
      true
    )
    registerProblem(problemDescriptor)
  }

  companion object {

    private val MissingBetweenExpressionFix = MissingSemicolonQuickFix(false)
    private val MissingLastChildFix = MissingSemicolonQuickFix(true)

    private class MissingSemicolonQuickFix(private val isPartOfPsi: Boolean): LocalQuickFix {
      override fun getFamilyName() = LamaBundle.message("inspection.missing.semicolon.fix.name")

      override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val expression = descriptor.psiElement
        val semi = createSemicolon(expression)
        if (isPartOfPsi) {
          expression.add(semi)
        }
        else {
          expression.parent.addAfter(semi, expression)
        }
      }
    }

    private fun createSemicolon(expression: PsiElement): PsiElement {
      return LamaElementFactory.buildLamaFileFromText(expression.project, "42;").children[1]
    }
  }
}

