package org.jetbrains.lama.psi.references

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.search.DelegatingGlobalSearchScope
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.PsiModificationTracker
import com.intellij.psi.util.PsiUtilBase
import org.jetbrains.lama.psi.LamaPsiUtil.importedUnits
import org.jetbrains.lama.psi.stubs.indices.LamaUnitsIndex

object LamaSearchScope {

  fun getGlobalScope(element: PsiElement): GlobalSearchScope {
    return GlobalSearchScope.allScope(element.project)
  }

  fun getScope(element: PsiElement): GlobalSearchScope {
    val project = element.project
    val containingFile = element.containingFile
    return CachedValuesManager.getManager(project).getCachedValue(containingFile) {
      CachedValueProvider.Result.create(
        createGlobalSearchScope(containingFile, project),
        PsiModificationTracker.MODIFICATION_COUNT
      )
    }
  }

  private fun createGlobalSearchScope(
    containingFile: PsiFile,
    project: Project,
  ): DelegatingGlobalSearchScope {
    val imports = containingFile.importedUnits.flatMap {
      LamaUnitsIndex.findUnitsByName(it, GlobalSearchScope.allScope(project))
    }
    return object : DelegatingGlobalSearchScope(GlobalSearchScope.allScope(project)) {
      override fun contains(file: VirtualFile): Boolean {
        if (file == PsiUtilBase.getVirtualFile(containingFile)) {
          return true
        }
        return file in imports
      }
    }
  }
}