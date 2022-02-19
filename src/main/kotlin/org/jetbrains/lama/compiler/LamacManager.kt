package org.jetbrains.lama.compiler

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.util.SystemInfo
import org.jetbrains.lama.util.PathUtil.safePath
import org.jetbrains.lama.util.ProjectRootUtil.invalidateProjectRoots
import java.nio.file.Path

object LamacManager {
  private val logger = logger<LamacManager>()

  var limaEnabled: Boolean
    get() {
      if (!SystemInfo.isMac) return false
      return PropertiesComponent.getInstance().getBoolean("lama.lima.enabled", false)
    }
    set(value) {
      if (value && !SystemInfo.isMac) {
        logger.warn("Trying to use lima on '${SystemInfo.OS_NAME}'")
      }
      else {
        invalidateProjectRoots()
        PropertiesComponent.getInstance().setValue("lama.lima.enabled", value)
      }
    }

  var compilerHome: String
    get() {
      val saved = PropertiesComponent.getInstance().getValue("lama.lamac.home")
      return saved ?: basePathIfExists()?.also { compilerHome = it } ?: ""
    }
    set(value) {
      if (!isCompilerHomeOk(value)) {
        logger.warn("Trying to set bad compiler home: '$value'")
      }
      else {
        invalidateProjectRoots()
        PropertiesComponent.getInstance().setValue("lama.lamac.home", value)
      }
    }

  var compilerHomePath: Path?
    get() {
      return compilerHome.safePath()
    }
    set(value) {
      compilerHome = value?.toString() ?: ""
    }

  fun isCompilerHomeOk(home: String): Boolean {
    val compilerPath = home.safePath()?.resolve("bin")?.resolve("lamac") ?: return false
    return LamacLocation.fileExists(compilerPath)
  }

  private fun basePathIfExists(): String? {
    val relativePath = Path.of(".opam", "lama")
    val basePath = LamacLocation.userHome?.resolve(relativePath)
    return basePath?.takeIf { LamacLocation.fileExists(it) }?.toString()
  }
}