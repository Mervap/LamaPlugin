// This is a generated file. Not intended for manual editing.
package org.jetbrains.lama.psi.api;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiNameIdentifierOwner;

public class LamaVisitor extends PsiElementVisitor {

  public void visitAndOperator(@NotNull LamaAndOperator o) {
    visitOperator(o);
  }

  public void visitArgumentList(@NotNull LamaArgumentList o) {
    visitPsiElement(o);
  }

  public void visitArrayExpression(@NotNull LamaArrayExpression o) {
    visitExpression(o);
  }

  public void visitArrayPattern(@NotNull LamaArrayPattern o) {
    visitPattern(o);
  }

  public void visitAssignmentExpression(@NotNull LamaAssignmentExpression o) {
    visitExpression(o);
  }

  public void visitAssignmentOperator(@NotNull LamaAssignmentOperator o) {
    visitOperator(o);
  }

  public void visitBooleanLiteral(@NotNull LamaBooleanLiteral o) {
    visitExpression(o);
  }

  public void visitCaseBranch(@NotNull LamaCaseBranch o) {
    visitPsiElement(o);
  }

  public void visitCaseStatement(@NotNull LamaCaseStatement o) {
    visitExpression(o);
  }

  public void visitCharLiteral(@NotNull LamaCharLiteral o) {
    visitExpression(o);
    // visitPsiNamedElement(o);
  }

  public void visitCompareOperator(@NotNull LamaCompareOperator o) {
    visitOperator(o);
  }

  public void visitDoStatement(@NotNull LamaDoStatement o) {
    visitExpression(o);
  }

  public void visitDotOperator(@NotNull LamaDotOperator o) {
    visitOperator(o);
  }

  public void visitEtaExpression(@NotNull LamaEtaExpression o) {
    visitExpression(o);
  }

  public void visitExpression(@NotNull LamaExpression o) {
    visitPsiElement(o);
  }

  public void visitExpressionSeries(@NotNull LamaExpressionSeries o) {
    visitPsiElement(o);
  }

  public void visitForStatement(@NotNull LamaForStatement o) {
    visitExpression(o);
  }

  public void visitFunctionBody(@NotNull LamaFunctionBody o) {
    visitPsiElement(o);
  }

  public void visitFunctionDefinition(@NotNull LamaFunctionDefinition o) {
    visitControlFlowHolder(o);
    // visitDefinition(o);
  }

  public void visitFunctionExpression(@NotNull LamaFunctionExpression o) {
    visitControlFlowHolder(o);
    // visitExpression(o);
  }

  public void visitIdentifierExpression(@NotNull LamaIdentifierExpression o) {
    visitExpression(o);
    // visitPsiNamedElement(o);
  }

  public void visitIfBranch(@NotNull LamaIfBranch o) {
    visitPsiElement(o);
  }

  public void visitIfStatement(@NotNull LamaIfStatement o) {
    visitExpression(o);
  }

  public void visitImportStatement(@NotNull LamaImportStatement o) {
    visitExpression(o);
  }

  public void visitInfixExpression(@NotNull LamaInfixExpression o) {
    visitExpression(o);
  }

  public void visitInfixOperator(@NotNull LamaInfixOperator o) {
    visitOperator(o);
  }

  public void visitInfixOperatorDefinition(@NotNull LamaInfixOperatorDefinition o) {
    visitControlFlowHolder(o);
    // visitDefinition(o);
  }

  public void visitLazyExpression(@NotNull LamaLazyExpression o) {
    visitExpression(o);
  }

  public void visitListConsOperator(@NotNull LamaListConsOperator o) {
    visitOperator(o);
  }

  public void visitListExpression(@NotNull LamaListExpression o) {
    visitExpression(o);
  }

  public void visitListPattern(@NotNull LamaListPattern o) {
    visitPattern(o);
  }

  public void visitMulDivModOperator(@NotNull LamaMulDivModOperator o) {
    visitOperator(o);
  }

  public void visitNumericLiteral(@NotNull LamaNumericLiteral o) {
    visitExpression(o);
  }

  public void visitOperator(@NotNull LamaOperator o) {
    visitPsiElement(o);
    // visitPsiNamedElement(o);
  }

  public void visitOperatorExpression(@NotNull LamaOperatorExpression o) {
    visitExpression(o);
  }

  public void visitOrOperator(@NotNull LamaOrOperator o) {
    visitOperator(o);
  }

  public void visitParameterList(@NotNull LamaParameterList o) {
    visitPsiElement(o);
  }

  public void visitParenthesizedExpression(@NotNull LamaParenthesizedExpression o) {
    visitExpression(o);
  }

  public void visitPattern(@NotNull LamaPattern o) {
    visitPsiElement(o);
  }

  public void visitPlusMinusOperator(@NotNull LamaPlusMinusOperator o) {
    visitOperator(o);
  }

  public void visitSOrAtPattern(@NotNull LamaSOrAtPattern o) {
    visitPattern(o);
    // visitPsiNameIdentifierOwner(o);
  }

  public void visitSOrCallExpression(@NotNull LamaSOrCallExpression o) {
    visitExpression(o);
  }

  public void visitScope(@NotNull LamaScope o) {
    visitControlFlowHolder(o);
  }

  public void visitSharpPattern(@NotNull LamaSharpPattern o) {
    visitPattern(o);
  }

  public void visitSkipExpression(@NotNull LamaSkipExpression o) {
    visitExpression(o);
  }

  public void visitStringLiteral(@NotNull LamaStringLiteral o) {
    visitExpression(o);
    // visitPsiNamedElement(o);
  }

  public void visitSubscriptionExpression(@NotNull LamaSubscriptionExpression o) {
    visitExpression(o);
  }

  public void visitSyntaxBinding(@NotNull LamaSyntaxBinding o) {
    visitPsiElement(o);
  }

  public void visitSyntaxExpression(@NotNull LamaSyntaxExpression o) {
    visitExpression(o);
  }

  public void visitSyntaxPrimaryArray(@NotNull LamaSyntaxPrimaryArray o) {
    visitSyntaxPrimaryExpression(o);
  }

  public void visitSyntaxPrimaryCall(@NotNull LamaSyntaxPrimaryCall o) {
    visitSyntaxPrimaryExpression(o);
  }

  public void visitSyntaxPrimaryExpression(@NotNull LamaSyntaxPrimaryExpression o) {
    visitPsiElement(o);
  }

  public void visitSyntaxPrimaryExpressionFrom(@NotNull LamaSyntaxPrimaryExpressionFrom o) {
    visitSyntaxPrimaryExpression(o);
  }

  public void visitSyntaxPrimaryParenthesized(@NotNull LamaSyntaxPrimaryParenthesized o) {
    visitSyntaxPrimaryExpression(o);
  }

  public void visitSyntaxSeq(@NotNull LamaSyntaxSeq o) {
    visitPsiElement(o);
  }

  public void visitVariableDefinition(@NotNull LamaVariableDefinition o) {
    visitDefinition(o);
  }

  public void visitVariableDefinitionSeries(@NotNull LamaVariableDefinitionSeries o) {
    visitPsiElement(o);
  }

  public void visitWhileStatement(@NotNull LamaWhileStatement o) {
    visitExpression(o);
  }

  public void visitWildcardPattern(@NotNull LamaWildcardPattern o) {
    visitPattern(o);
  }

  public void visitControlFlowHolder(@NotNull LamaControlFlowHolder o) {
    visitPsiElement(o);
  }

  public void visitDefinition(@NotNull LamaDefinition o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull LamaPsiElement o) {
    visitElement(o);
  }

}
