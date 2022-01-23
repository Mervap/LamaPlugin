package org.jetbrains.lama.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement

open class LamaElementImpl(astNode: ASTNode) : LamaBaseElementImpl<StubElement<*>>(astNode)