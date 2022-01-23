// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LamaOperatorExpression extends LamaExpression {

  @Nullable
  LamaOperator getOperator();

  @Nullable
  LamaExpression getLeftExpr();

  @Nullable
  LamaExpression getRightExpr();

}
