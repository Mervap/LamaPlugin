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

public class LamaArrayExpressionImpl extends LamaExpressionImpl implements LamaArrayExpression {

  public LamaArrayExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitArrayExpression(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LamaVisitor) accept((LamaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<LamaExpressionSeries> getExpressionSeriesList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LamaExpressionSeries.class);
  }

}
