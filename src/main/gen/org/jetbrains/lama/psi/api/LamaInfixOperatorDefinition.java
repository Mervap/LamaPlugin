// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LamaInfixOperatorDefinition extends LamaPsiElement {

  @Nullable
  LamaFunctionBody getFunctionBody();

  @NotNull
  List<LamaOperator> getOperatorList();

  @Nullable
  LamaParameterList getParameterList();

}
