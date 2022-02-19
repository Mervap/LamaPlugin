package org.jetbrains.lama.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.AdditionalLibraryRootsProvider
import com.intellij.openapi.roots.SyntheticLibrary
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.lama.compiler.LamacLocation

class LamaStdlibRootProvider : AdditionalLibraryRootsProvider() {
  override fun getAdditionalProjectLibraries(project: Project): Collection<SyntheticLibrary> {
    val virtualRoot = stdlibVirtualFile(project, false) ?: return emptyList()
    return SyntheticLibrary.newImmutableLibrary(listOf(virtualRoot)).toList()
  }

  override fun getRootsToWatch(project: Project): Collection<VirtualFile> {
    val virtualRoot = stdlibVirtualFile(project, true) ?: return emptyList()
    return virtualRoot.toList()
  }

  private fun stdlibVirtualFile(project: Project, refreshIfNeeded: Boolean): VirtualFile? {
    val source = LamacLocation.stdlibSourcesRoot(project) ?: return null
    return VfsUtil.findFile(source, refreshIfNeeded)
  }

  private fun <T> T.toList(): List<T> = listOf(this)
}