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

public class LamaVariableDefinitionSeriesImpl extends LamaElementImpl implements LamaVariableDefinitionSeries {

  public LamaVariableDefinitionSeriesImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LamaVisitor visitor) {
    visitor.visitVariableDefinitionSeries(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LamaVisitor) accept((LamaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<LamaVariableDefinition> getVariableDefinitionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LamaVariableDefinition.class);
  }

}
