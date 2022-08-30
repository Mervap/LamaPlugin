package org.jetbrains.lama.util

import org.jetbrains.lama.psi.LamaFileType
import java.nio.file.Path
import kotlin.io.path.notExists

object LamaStdUnitUtil {
  fun Class<*>.addStdUnitStubToDirectory(stdlibRoot: Path) {
    if (stdlibRoot.notExists()) return

    val stdUnitPath = stdlibRoot.resolve(UNIT_NAME_WITH_EXT)
    if (stdUnitPath.notExists()) {
      val stdUnitText = getResource("/stubs/$UNIT_NAME_WITH_EXT")?.readText()
      if (stdUnitText != null) {
        stdUnitPath.toFile().writeText(stdUnitText)
      }
    }
  }

  const val UNIT_NAME = "Std"
  val UNIT_NAME_WITH_EXT = "$UNIT_NAME.${LamaFileType.defaultExtension}"
}