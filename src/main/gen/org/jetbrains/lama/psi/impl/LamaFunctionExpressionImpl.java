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

public class LamaFunctionExpressionImpl extends LamaExpressionImpl implements LamaFunctionExpression {

  public LamaFunctionExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitFunctionExpression(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LamaVisitor) accept((LamaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LamaFunctionBody getFunctionBody() {
    return PsiTreeUtil.getChildOfType(this, LamaFunctionBody.class);
  }

  @Override
  @NotNull
  public LamaParameterList getParameterList() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, LamaParameterList.class));
  }

}
