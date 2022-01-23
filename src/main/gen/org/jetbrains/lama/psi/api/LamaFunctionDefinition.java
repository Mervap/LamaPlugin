// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LamaFunctionDefinition extends LamaPsiElement {

  @Nullable
  LamaFunctionBody getFunctionBody();

  @NotNull
  LamaIdentifierExpression getIdentifierExpression();

  @Nullable
  LamaParameterList getParameterList();

}
