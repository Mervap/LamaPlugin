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
import org.jetbrains.lama.psi.stubs.LamaVariableDefinitionStub;
import com.intellij.psi.stubs.IStubElementType;

public class LamaVariableDefinitionImpl extends LamaVariableDefinitionBase implements LamaVariableDefinition {

  public LamaVariableDefinitionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public LamaVariableDefinitionImpl(@NotNull LamaVariableDefinitionStub stub, @NotNull IStubElementType<?, ?> type) {
    super(stub, type);
  }

  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitVariableDefinition(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LamaVisitor) accept((LamaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<LamaExpression> getExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LamaExpression.class);
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
  @Nullable
  public LamaIdentifierExpression getNameIdentifier() {
    return LamaPsiImplUtil.getNameIdentifier(this);
  }

  @Override
  @Nullable
  public String getDefaultValue() {
    return LamaPsiImplUtil.getDefaultValue(this);
  }

  @Override
  public boolean isPublic() {
    return LamaPsiImplUtil.isPublic(this);
  }

  @Override
  public boolean isTopLevel() {
    return LamaPsiImplUtil.isTopLevel(this);
  }

  @Override
  @Nullable
  public LamaExpression getDefaultValueExpression() {
    List<LamaExpression> p1 = getExpressionList();
    return p1.size() < 2 ? null : p1.get(1);
  }

}
