// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.lama.psi.stubs.LamaFunctionDefinitionStub;

public interface LamaFunctionDefinition extends LamaControlFlowHolder, LamaDefinition, PsiNameIdentifierOwner, StubBasedPsiElement<LamaFunctionDefinitionStub> {

  @Nullable
  LamaFunctionBody getFunctionBody();

  @NotNull
  LamaIdentifierExpression getIdentifierExpression();

  @Nullable
  LamaParameterList getParameterList();

  @NotNull
  String getName();

  @NotNull
  PsiElement setName(@NotNull String name);

  @Nullable
  LamaIdentifierExpression getNameIdentifier();

  @NotNull
  String getParameters();

  boolean isPublic();

  boolean isTopLevel();

}
