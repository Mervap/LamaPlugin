package org.jetbrains.lama.inspections

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.lama.messages.LamaBundle
import org.jetbrains.lama.psi.LamaPsiUtil.isDotCall
import org.jetbrains.lama.psi.api.*

class WrongArgumentCountInspection : LamaInspection() {
  override fun getDisplayName() = LamaBundle.message("inspection.wrong.argument.count.name")

  override fun buildVisitor(
    holder: ProblemsHolder,
    isOnTheFly: Boolean,
    session: LocalInspectionToolSession,
  ): PsiElementVisitor = object : LamaVisitor() {

    override fun visitSOrCallExpression(o: LamaSOrCallExpression) {
      val args = o.argumentList?.expressionSeriesList ?: return
      val argumentsCnt = args.size + if (o.isDotCall()) 1 else 0
      val resolve = o.expression.reference?.multiResolve()?.singleOrNull() ?: return
      if (resolve !is LamaFunctionDefinition) return

      val patternList = resolve.parameterList?.patternList
      val parametersCount = patternList?.size ?: return
      if (parametersCount == argumentsCnt) return
      if (patternList.lastOrNull()?.text == "_" && parametersCount - 1 <= argumentsCnt) return

      holder.registerProblem(
        o.argumentList!!,
        LamaBundle.message("inspection.wrong.argument.count.description", resolve.name, parametersCount, argumentsCnt),
        ProblemHighlightType.WEAK_WARNING
      )
    }
  }
}

