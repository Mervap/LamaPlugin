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
import org.jetbrains.lama.psi.stubs.LamaInfixAssociativity;
import org.jetbrains.lama.psi.stubs.LamaInfixOperatorDefinitionStub;
import com.intellij.psi.stubs.IStubElementType;

public class LamaInfixOperatorDefinitionImpl extends LamaInfixOperatorDefinitionBase implements LamaInfixOperatorDefinition {

  public LamaInfixOperatorDefinitionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public LamaInfixOperatorDefinitionImpl(@NotNull LamaInfixOperatorDefinitionStub stub, @NotNull IStubElementType<?, ?> type) {
    super(stub, type);
  }

  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitInfixOperatorDefinition(this);
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
  public List<LamaOperator> getOperatorList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LamaOperator.class);
  }

  @Override
  @Nullable
  public LamaParameterList getParameterList() {
    return PsiTreeUtil.getChildOfType(this, LamaParameterList.class);
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
  public LamaInfixAssociativity getAssociativity() {
    return LamaPsiImplUtil.getAssociativity(this);
  }

  @Override
  @NotNull
  public String getParameters() {
    return LamaPsiImplUtil.getParameters(this);
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
  @NotNull
  public LamaOperator getNameOperator() {
    List<LamaOperator> p1 = getOperatorList();
    return p1.get(0);
  }

  @Override
  @Nullable
  public LamaOperator getLevelOperator() {
    List<LamaOperator> p1 = getOperatorList();
    return p1.size() < 2 ? null : p1.get(1);
  }

}
