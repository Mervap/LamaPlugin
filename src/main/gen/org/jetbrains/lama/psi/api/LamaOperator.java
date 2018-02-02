// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import org.jetbrains.lama.psi.references.LamaReferenceBase;

public interface LamaOperator extends LamaPsiElement {

  @NotNull
  String getName();

  @Nullable
  LamaReferenceBase<?> getReference();

}
