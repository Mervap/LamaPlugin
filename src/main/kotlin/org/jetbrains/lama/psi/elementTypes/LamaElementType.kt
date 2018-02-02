package org.jetbrains.lama.psi.elementTypes

import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls
import org.jetbrains.lama.psi.LamaLanguage

class LamaElementType(debugName: @NonNls String) : IElementType(debugName, LamaLanguage)