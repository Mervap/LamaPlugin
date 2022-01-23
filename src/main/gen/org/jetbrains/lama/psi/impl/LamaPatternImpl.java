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

public class LamaPatternImpl extends LamaElementImpl implements LamaPattern {

  public LamaPatternImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitPattern(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LamaVisitor) accept((LamaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<LamaBooleanLiteral> getBooleanLiteralList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LamaBooleanLiteral.class);
  }

  @Override
  @NotNull
  public List<LamaCharLiteral> getCharLiteralList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LamaCharLiteral.class);
  }

  @Override
  @NotNull
  public List<LamaNumericLiteral> getNumericLiteralList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LamaNumericLiteral.class);
  }

  @Override
  @NotNull
  public List<LamaPattern> getPatternList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LamaPattern.class);
  }

  @Override
  @NotNull
  public List<LamaStringLiteral> getStringLiteralList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LamaStringLiteral.class);
  }

}
