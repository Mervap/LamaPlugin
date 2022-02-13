// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LamaAssignmentExpression extends LamaExpression {

  @NotNull
  LamaAssignmentOperator getAssignmentOperator();

  @NotNull
  List<LamaExpression> getExpressionList();

  @Nullable
  LamaAssignmentOperator getOperator();

  @Nullable
  LamaExpression getAssignment();

  @NotNull
  LamaExpression getAssignee();

}
