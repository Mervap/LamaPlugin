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

public class LamaForStatementImpl extends LamaExpressionImpl implements LamaForStatement {

  public LamaForStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitForStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LamaVisitor) accept((LamaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<LamaScope> getScopeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LamaScope.class);
  }

  @Override
  @Nullable
  public LamaScope getBeforeAll() {
    List<LamaScope> p1 = getScopeList();
    return p1.size() < 1 ? null : p1.get(0);
  }

  @Override
  @Nullable
  public LamaExpressionSeries getBeforeEach() {
    return PsiTreeUtil.getChildOfType(this, LamaExpressionSeries.class);
  }

  @Override
  @NotNull
  public List<LamaExpression> getAfterEach() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LamaExpression.class);
  }

  @Override
  @Nullable
  public LamaScope getBody() {
    List<LamaScope> p1 = getScopeList();
    return p1.size() < 2 ? null : p1.get(1);
  }

}
