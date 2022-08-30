package org.jetbrains.lama.util

import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.roots.ex.ProjectRootManagerEx
import com.intellij.openapi.util.EmptyRunnable

fun invalidateProjectRoots() = runWriteAction {
  ProjectManager.getInstance().openProjects.forEach { it.invalidateProjectRoots() }
}

fun Project.invalidateProjectRoots() {
  ProjectRootManagerEx.getInstanceEx(this).makeRootsChange(EmptyRunnable.INSTANCE, false, true)
}
