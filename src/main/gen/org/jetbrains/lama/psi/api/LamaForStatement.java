// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LamaForStatement extends LamaExpression {

  @NotNull
  List<LamaScope> getScopeList();

  @Nullable
  LamaScope getBeforeAll();

  @Nullable
  LamaExpressionSeries getBeforeEach();

  @NotNull
  List<LamaExpression> getAfterEach();

  @Nullable
  LamaScope getBody();

}
