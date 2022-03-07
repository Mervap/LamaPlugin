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

class WrongArgumentCountInspection : LamaInspection() {
  override fun getDisplayName() = LamaBundle.message("inspection.wrong.argument.count.name")

  override fun buildVisitor(
    holder: ProblemsHolder,
    isOnTheFly: Boolean,
    session: LocalInspectionToolSession,
  ): PsiElementVisitor = object : LamaVisitor() {

    override fun visitSOrCallExpression(o: LamaSOrCallExpression) {
      val arguments = o.argumentList?.expressionSeriesList?.size ?: return
      val resolve = o.expression.reference?.multiResolve(false)?.singleOrNull()?.element?.parent ?: return
      if (resolve !is LamaFunctionDefinition) return

      val patternList = resolve.parameterList?.patternList
      val parametersCount = patternList?.size ?: return
      if (parametersCount == arguments) return
      if (patternList.lastOrNull()?.text == "_" && parametersCount - 1 <= arguments) return

      holder.registerProblem(
        o.argumentList!!,
        LamaBundle.message("inspection.wrong.argument.count.description", resolve.name, parametersCount, arguments),
      )
    }
  }
}

