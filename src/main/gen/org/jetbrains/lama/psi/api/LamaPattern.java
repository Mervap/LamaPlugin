// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LamaPattern extends LamaPsiElement {

  @NotNull
  List<LamaBooleanLiteral> getBooleanLiteralList();

  @NotNull
  List<LamaCharLiteral> getCharLiteralList();

  @NotNull
  List<LamaNumericLiteral> getNumericLiteralList();

  @NotNull
  List<LamaPattern> getPatternList();

  @NotNull
  List<LamaStringLiteral> getStringLiteralList();

}
