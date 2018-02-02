// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.lama.psi.stubs.LamaFunctionDefinitionStub;
import com.intellij.psi.PsiNamedElement;

public interface LamaFunctionDefinition extends LamaDefinition, PsiNameIdentifierOwner, StubBasedPsiElement<LamaFunctionDefinitionStub> {

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
  PsiNamedElement getNameIdentifier();

  boolean isPublic();

  @NotNull
  String getParameters();

}
