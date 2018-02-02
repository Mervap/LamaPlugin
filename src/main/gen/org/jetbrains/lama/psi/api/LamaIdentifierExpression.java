// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.lama.psi.references.LamaReferenceBase;

public interface LamaIdentifierExpression extends LamaExpression, PsiNamedElement {

  @Nullable
  LamaReferenceBase<?> getReference();

  @NotNull
  String getName();

  @NotNull
  PsiElement setName(@NotNull String name);

}
