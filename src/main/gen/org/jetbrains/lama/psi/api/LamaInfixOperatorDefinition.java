// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.lama.psi.stubs.LamaInfixOperatorDefinitionStub;
import org.jetbrains.lama.psi.stubs.LamaInfixAssociativity;

public interface LamaInfixOperatorDefinition extends LamaControlFlowHolder, LamaDefinition, PsiNamedElement, StubBasedPsiElement<LamaInfixOperatorDefinitionStub> {

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
  LamaInfixAssociativity getAssociativity();

  @NotNull
  String getParameters();

  boolean getIsTopLevel();

  @NotNull
  LamaOperator getNameOperator();

  @Nullable
  LamaOperator getLevelOperator();

}
