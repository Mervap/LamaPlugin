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
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.lama.psi.stubs.LamaFunctionDefinitionStub;
import com.intellij.psi.stubs.IStubElementType;

public class LamaFunctionDefinitionImpl extends LamaFunctionDefinitionBase implements LamaFunctionDefinition {

  public LamaFunctionDefinitionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public LamaFunctionDefinitionImpl(@NotNull LamaFunctionDefinitionStub stub, @NotNull IStubElementType<?, ?> type) {
    super(stub, type);
  }

  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitFunctionDefinition(this);
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
  public LamaIdentifierExpression getIdentifierExpression() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, LamaIdentifierExpression.class));
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
  @Nullable
  public PsiNamedElement getNameIdentifier() {
    return LamaPsiImplUtil.getNameIdentifier(this);
  }

  @Override
  public boolean isPublic() {
    return LamaPsiImplUtil.isPublic(this);
  }

  @Override
  @NotNull
  public String getParameters() {
    return LamaPsiImplUtil.getParameters(this);
  }

  @Override
  public boolean getIsTopLevel() {
    return LamaPsiImplUtil.getIsTopLevel(this);
  }

}
