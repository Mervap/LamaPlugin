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
import org.jetbrains.lama.psi.references.LamaReferenceBase;

public class LamaOperatorImpl extends LamaElementImpl implements LamaOperator {

  public LamaOperatorImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitOperator(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LamaVisitor) accept((LamaVisitor)visitor);
    else super.accept(visitor);
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
  public LamaReferenceBase<?> getReference() {
    return LamaPsiImplUtil.getReference(this);
  }

}
