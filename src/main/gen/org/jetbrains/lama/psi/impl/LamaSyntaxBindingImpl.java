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

public class LamaSyntaxBindingImpl extends LamaElementImpl implements LamaSyntaxBinding {

  public LamaSyntaxBindingImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitSyntaxBinding(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LamaVisitor) accept((LamaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LamaPattern getPattern() {
    return PsiTreeUtil.getChildOfType(this, LamaPattern.class);
  }

  @Override
  @NotNull
  public LamaSyntaxPrimaryExpression getSyntaxPrimaryExpression() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, LamaSyntaxPrimaryExpression.class));
  }

}
