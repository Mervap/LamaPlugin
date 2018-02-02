// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LamaScope extends LamaPsiElement {

  @NotNull
  List<LamaExpression> getExpressionList();

  @NotNull
  List<LamaFunctionDefinition> getFunctionDefinitionList();

  @NotNull
  List<LamaInfixOperatorDefinition> getInfixOperatorDefinitionList();

  @NotNull
  List<LamaVariableDefinitionSeries> getVariableDefinitionSeriesList();

}
