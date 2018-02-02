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

public class LamaCaseBranchImpl extends LamaElementImpl implements LamaCaseBranch {

  public LamaCaseBranchImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitCaseBranch(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LamaVisitor) accept((LamaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public LamaPattern getPattern() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, LamaPattern.class));
  }

  @Override
  @Nullable
  public LamaScope getScope() {
    return PsiTreeUtil.getChildOfType(this, LamaScope.class);
  }

}
