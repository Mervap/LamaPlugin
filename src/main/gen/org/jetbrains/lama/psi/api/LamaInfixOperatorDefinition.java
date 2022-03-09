// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.lama.psi.stubs.LamaInfixOperatorDefinitionStub;
import org.jetbrains.lama.psi.stubs.LamaInfixAssociativity;

public interface LamaInfixOperatorDefinition extends LamaControlFlowHolder, LamaDefinition, StubBasedPsiElement<LamaInfixOperatorDefinitionStub> {

  @Nullable
  LamaFunctionBody getFunctionBody();

  @NotNull
  List<LamaOperator> getOperatorList();

  @Nullable
  LamaParameterList getParameterList();

  @NotNull
  String getName();

  @NotNull
  PsiElement setName(@NotNull String name);

  @NotNull
  LamaOperator getNameIdentifier();

  @NotNull
  LamaInfixAssociativity getAssociativity();

  @NotNull
  String getParameters();

  boolean isPublic();

  boolean isTopLevel();

  @NotNull
  LamaOperator getNameOperator();

  @Nullable
  LamaOperator getLevelOperator();

}
