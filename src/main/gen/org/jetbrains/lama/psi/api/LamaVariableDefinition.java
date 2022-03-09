// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.lama.psi.stubs.LamaVariableDefinitionStub;

public interface LamaVariableDefinition extends LamaDefinition, StubBasedPsiElement<LamaVariableDefinitionStub> {

  @NotNull
  List<LamaExpression> getExpressionList();

  @NotNull
  String getName();

  @NotNull
  PsiElement setName(@NotNull String name);

  @Nullable
  LamaIdentifierExpression getNameIdentifier();

  @Nullable
  String getDefaultValue();

  boolean isPublic();

  boolean isTopLevel();

  @Nullable
  LamaExpression getDefaultValueExpression();

}
