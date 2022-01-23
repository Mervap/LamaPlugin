// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LamaSyntaxBinding extends LamaPsiElement {

  @Nullable
  LamaIdentifierExpression getIdentifierExpression();

  @Nullable
  LamaPattern getPattern();

  @NotNull
  List<LamaSyntaxPrimaryArray> getSyntaxPrimaryArrayList();

  @Nullable
  LamaSyntaxPrimaryExpressionFrom getSyntaxPrimaryExpressionFrom();

  @Nullable
  LamaSyntaxPrimaryParenthesized getSyntaxPrimaryParenthesized();

}
