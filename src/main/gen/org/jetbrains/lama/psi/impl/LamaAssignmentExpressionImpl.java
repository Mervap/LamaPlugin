// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.jetbrains.lama.parser.LamaElementTypes.*;
import org.jetbrains.lama.psi.api.*;

public class LamaAssignmentExpressionImpl extends LamaExpressionImpl implements LamaAssignmentExpression {

  public LamaAssignmentExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitAssignmentExpression(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LamaVisitor) accept((LamaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public LamaAssignmentOperator getAssignmentOperator() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, LamaAssignmentOperator.class));
  }

  @Override
  @NotNull
  public List<LamaExpression> getExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LamaExpression.class);
  }

  @Override
  @Nullable
  public LamaAssignmentOperator getOperator() {
    return LamaPsiImplUtil.getOperator(this);
  }

  @Override
  @Nullable
  public LamaExpression getAssignment() {
    return LamaPsiImplUtil.getAssignment(this);
  }

  @Override
  @NotNull
  public LamaExpression getAssignee() {
    return LamaPsiImplUtil.getAssignee(this);
  }

}
