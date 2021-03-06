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

public class LamaSOrAtPatternImpl extends LamaPatternImpl implements LamaSOrAtPattern {

  public LamaSOrAtPatternImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitSOrAtPattern(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LamaVisitor) accept((LamaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public LamaIdentifierExpression getIdentifierExpression() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, LamaIdentifierExpression.class));
  }

  @Override
  @NotNull
  public String getName() {
    return LamaPsiImplUtil.getName(this);
  }

  @Override
  @NotNull
  public PsiElement setName(@NotNull String name) {
    return LamaPsiImplUtil.setName(this, name);
  }

  @Override
  @NotNull
  public LamaIdentifierExpression getNameIdentifier() {
    return LamaPsiImplUtil.getNameIdentifier(this);
  }

}
