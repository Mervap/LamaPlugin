package org.jetbrains.lama.compiler

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessRunner
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessOutput
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import com.intellij.util.SystemProperties
import com.intellij.util.io.createDirectories
import com.intellij.util.io.delete
import com.intellij.util.io.exists
import org.jetbrains.lama.messages.LamaBundle
import org.jetbrains.lama.util.PathUtil.safePath
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.io.path.createTempDirectory

interface LamacLocation {
  val userHome: Path?
  fun fileExists(path: Path): Boolean
  fun stdlibSourcesRoot(project: Project): Path?

  companion object : LamacLocation {
    override val userHome: Path? get() = actualLocation.userHome
    override fun fileExists(path: Path): Boolean = actualLocation.fileExists(path)
    override fun stdlibSourcesRoot(project: Project): Path? = actualLocation.stdlibSourcesRoot(project)

    private val actualLocation: LamacLocation
      get() = when {
        SystemInfo.isMac && LamacManager.limaEnabled -> LimaLocation
        else -> HostLocation
      }
  }
}

/**
 * Lamac installed just on host machine
 */
private object HostLocation : LamacLocation {
  override val userHome: Path? = SystemProperties.getUserHome().safePath()
  override fun fileExists(path: Path): Boolean = path.exists()
  override fun stdlibSourcesRoot(project: Project): Path? = LamacManager.compilerHomePath?.stdlibRoot()
}

/**
 * Lamac installed on running [lima](https://github.com/lima-vm/lima) instance
 */
// TODO: change to SSH/RemoteFs instead of command line?
private object LimaLocation : LamacLocation {
  private val copyTaskStarted = AtomicBoolean(false)

  override val userHome: Path?
    get() = runSynchronouslyIfNeeded {
      val output = runOnLima("cd ~ && pwd")
      if (output.exitCode != 0) null
      else output.stdout.trim().safePath()
    }

  override fun fileExists(path: Path): Boolean = runSynchronouslyIfNeeded {
    val output = runOnLima("cd $path && pwd")
    output.exitCode == 0
  }

  override fun stdlibSourcesRoot(project: Project): Path? {
    val expected = Path.of(PathManager.getHomePath(), "lamacStdlib")
    if (expected.exists()) {
      return expected
    }
    expected.createDirectories()
    if (!copyTaskStarted.compareAndSet(false, true)) {
      return expected
    }

    ProgressManager.getInstance().run(
      object : Task.Backgroundable(project, LamaBundle.message("lamac.lima.copy.task.title"), false) {
        override fun run(indicator: ProgressIndicator) {
          val limaShare = LamacManager.compilerHomePath?.stdlibRoot() ?: return
          val tmpDir = createTempDirectory(Path.of("/", "tmp", "lima"), "stdlib")
          try {
            val output = runOnLima("cp $limaShare/*.lama $tmpDir")
            if (output.exitCode != 0) {
              error("""
                Can't fetch stdlib from lima
                Exit code: ${output.exitCode}
                Stdout: ${output.stdout}
                Stderr: ${output.stderr}
              """.trimIndent())
            }
            tmpDir.toFile().copyRecursively(expected.toFile(), true)
          }
          finally {
            tmpDir.delete(true)
          }
        }
      }
    )
    return expected
  }

  private fun runOnLima(command: String): ProcessOutput {
    val handler = OSProcessHandler(GeneralCommandLine("lima", "eval", command))
    val processRunner = CapturingProcessRunner(handler)
    return processRunner.runProcess(5000)
  }

  private fun <T> runSynchronouslyIfNeeded(action: () -> T): T {
    val application = ApplicationManager.getApplication()
    if (!application.isDispatchThread && !application.isReadAccessAllowed) {
      return action()
    }

    var result: T? = null
    ProgressManager.getInstance().runProcessWithProgressSynchronously(
      { result = action() },
      LamaBundle.message("lamac.lima.runnable.title"),
      false,
      null
    )
    return result!!
  }
}

private fun Path.stdlibRoot(): Path = resolve("share").resolve("Lama")
