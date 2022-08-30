package org.jetbrains.lama.execution

import com.intellij.execution.filters.ConsoleFilterProvider
import com.intellij.execution.filters.Filter
import com.intellij.execution.filters.OpenFileHyperlinkInfo
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import org.jetbrains.lama.psi.stubs.indices.LamaUnitsIndex

class LamaConsoleFilterProvider : ConsoleFilterProvider {
  override fun getDefaultFilters(project: Project): Array<Filter> {
    return arrayOf(LamaExceptionFilter(project))
  }
}

class LamaExceptionFilter(private val project: Project) : Filter {

  override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
    val matcher = EXCEPTION_PATTERN.matcher(StringUtil.newBombedCharSequence(line, 100))
    try {
      if (!matcher.find()) {
        return null
      }
    }
    catch (e: ProcessCanceledException) {
      return null
    }

    val (fileName, lineNumber, column) = matcher.group("ref").split(":")
    val unitName = fileName.dropLast(".lama".length)
    val file = LamaUnitsIndex.findUnitsByName(unitName, project).singleOrNull() ?: return null
    val info = OpenFileHyperlinkInfo(project, file, lineNumber.toInt() - 1, column.toInt())

    val st = entireLength - line.length + matcher.start("ref")
    val end = entireLength - line.length + matcher.end("ref")
    return Filter.Result(st, end, info)
  }


  companion object {
    private val EXCEPTION_PATTERN = "\\*\\*\\* FAILURE: .* (?<ref>[a-zA-z0-9_]+\\.lama:\\d+:\\d+)".toPattern()
  }
}