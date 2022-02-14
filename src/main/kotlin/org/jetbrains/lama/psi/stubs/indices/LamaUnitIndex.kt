package org.jetbrains.lama.psi.stubs.indices

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.lama.psi.LamaFileType

object LamaUnitsIndex {
  fun findAllUnitNames(project: Project): List<String> {
    return FilenameIndex.getAllFilesByExt(project, LamaFileType.defaultExtension).map { it.nameWithoutExtension }
  }

  fun findUnitsByName(name: String, searchScope: GlobalSearchScope): Collection<VirtualFile> {
    return FilenameIndex.getVirtualFilesByName("$name.lama", searchScope)
  }
}
