// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.lama.psi.stubs.LamaVariableDefinitionStub;
import com.intellij.psi.PsiNamedElement;

public interface LamaVariableDefinition extends LamaDefinition, PsiNameIdentifierOwner, StubBasedPsiElement<LamaVariableDefinitionStub> {

  @NotNull
  List<LamaExpression> getExpressionList();

  @NotNull
  String getName();

  @NotNull
  PsiElement setName(@NotNull String name);

  @Nullable
  PsiNamedElement getNameIdentifier();

  @Nullable
  String getDefaultValue();

  @Nullable
  LamaExpression getDefaultValueExpression();

}
