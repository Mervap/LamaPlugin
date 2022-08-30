@file:Suppress("UNUSED_PARAMETER")

package org.jetbrains.lama.psi.impl

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.util.IncorrectOperationException
import org.jetbrains.lama.parser.LamaElementTypes
import org.jetbrains.lama.psi.LamaPsiUtil.isImportIdentifier
import org.jetbrains.lama.psi.api.*
import org.jetbrains.lama.psi.elementTypes.LamaElementFactory
import org.jetbrains.lama.psi.references.LamaIdentifierReference
import org.jetbrains.lama.psi.references.LamaOperatorReference
import org.jetbrains.lama.psi.references.LamaReferenceBase
import org.jetbrains.lama.psi.references.LamaUnitReference
import org.jetbrains.lama.psi.stubs.LAMA_INFIX_ASSOCIATIVITY_TYPES
import org.jetbrains.lama.psi.stubs.LamaInfixAssociativity

internal object LamaPsiImplUtil {

  @JvmStatic
  fun getName(identifierExpression: LamaIdentifierExpression): String = identifierExpression.text

  @JvmStatic
  fun setName(identifier: LamaIdentifierExpression, name: String): PsiElement {
    return identifier.replace(LamaElementFactory.createLamaIdentifierFromText(identifier.project, name))
  }

  @JvmStatic
  fun getName(charLiteral: LamaCharLiteral): String = charLiteral.text.trim('\'')

  @JvmStatic
  fun setName(charLiteral: LamaCharLiteral, name: String): PsiElement {
    val oldText = charLiteral.text
    val newText = oldText.replaceRange(1, oldText.length - 1, name)
    val replacement = LamaElementFactory.createLamaExpressionFromText(charLiteral.project, newText)
    return charLiteral.replace(replacement)
  }

  @JvmStatic
  fun getName(stringLiteral: LamaStringLiteral): String = stringLiteral.text.trim('\'')

  @JvmStatic
  fun setName(stringLiteral: LamaStringLiteral, name: String): PsiElement {
    val oldText = stringLiteral.text
    val newText = oldText.replaceRange(1, oldText.length - 1, name)
    val replacement = LamaElementFactory.createLamaExpressionFromText(stringLiteral.project, newText)
    return stringLiteral.replace(replacement)
  }

  @JvmStatic
  fun getReference(expression: LamaExpression): LamaReferenceBase<*>? = null

  @JvmStatic
  fun getReference(identifier: LamaIdentifierExpression): LamaReferenceBase<*> {
    return if (identifier.isImportIdentifier()) {
      LamaUnitReference(identifier)
    }
    else {
      LamaIdentifierReference(identifier)
    }
  }

  @JvmStatic
  fun getReference(operator: LamaOperator): LamaReferenceBase<*> = LamaOperatorReference(operator)

  @JvmStatic
  fun getName(operator: LamaOperator): String = operator.text

  @JvmStatic
  fun setName(operator: LamaOperator, name: String): PsiElement {
    val replacement = LamaElementFactory.createLamaExpressionFromText(operator.project, "1 $name 1").children[1]
    return operator.replace(replacement)
  }

  @JvmStatic
  fun getNameIdentifier(definition: LamaInfixOperatorDefinition): LamaOperator {
    return definition.nameOperator
  }

  @JvmStatic
  fun getLeftExpr(expr: LamaOperatorExpression): LamaExpression? {
    val expressions = PsiTreeUtil.getChildrenOfType(expr, LamaExpression::class.java)
    return if (expressions.isNullOrEmpty()) null else expressions[0]
  }

  @JvmStatic
  fun getRightExpr(expr: LamaOperatorExpression): LamaExpression? {
    val expressions = PsiTreeUtil.getChildrenOfType(expr, LamaExpression::class.java)
    return if (expressions == null || expressions.size != 2) null else expressions[1]
  }

  @JvmStatic
  fun getOperator(expr: LamaOperatorExpression): LamaOperator? {
    return PsiTreeUtil.getChildOfType(expr, LamaOperator::class.java)
  }

  @JvmStatic
  fun setName(definition: LamaVariableDefinition, name: String): PsiElement {
    val nameIdentifier = definition.nameIdentifier ?: throw IncorrectOperationException("Empty name: $this")
    nameIdentifier.name = name
    return definition
  }

  @JvmStatic
  fun getName(definition: LamaVariableDefinitionImpl): String {
    return definition.greenStub?.name ?: definition.nameIdentifier?.name ?: "<unnamed>"
  }

  @JvmStatic
  fun getNameIdentifier(assignment: LamaVariableDefinition): LamaIdentifierExpression? {
    return assignment.firstChild as? LamaIdentifierExpression
  }

  @JvmStatic
  fun setName(definition: LamaFunctionDefinition, name: String): PsiElement {
    val nameIdentifier = definition.nameIdentifier ?: throw IncorrectOperationException("Empty name: $this")
    nameIdentifier.name = name
    return definition
  }

  @JvmStatic
  fun getName(definition: LamaFunctionDefinitionImpl): String {
    return definition.greenStub?.name ?: definition.nameIdentifier?.name ?: "<unnamed>"
  }

  @JvmStatic
  fun getNameIdentifier(assignment: LamaFunctionDefinition): LamaIdentifierExpression? {
    return PsiTreeUtil.getChildOfType(assignment, LamaIdentifierExpression::class.java)
  }

  @JvmStatic
  fun setName(definition: LamaInfixOperatorDefinition, name: String): PsiElement {
    definition.nameOperator.name = name
    return definition
  }

  @JvmStatic
  fun getName(definition: LamaInfixOperatorDefinitionImpl): String {
    return definition.greenStub?.name ?: definition.nameOperator.name
  }

  @JvmStatic
  fun getDefaultValue(variable: LamaVariableDefinitionImpl): String? {
    val stub = variable.greenStub
    if (stub != null) {
      return stub.defaultValue
    }
    return variable.defaultValueExpression?.text
  }

  @JvmStatic
  fun isPublic(variable: LamaVariableDefinitionImpl): Boolean {
    return variable.greenStub?.isPublic ?: isFirstChildPublicKeyword(variable.parent)
  }

  @JvmStatic
  fun isPublic(function: LamaFunctionDefinitionImpl): Boolean {
    return function.greenStub?.isPublic ?: isFirstChildPublicKeyword(function)
  }

  @JvmStatic
  fun isPublic(infix: LamaInfixOperatorDefinitionImpl): Boolean {
    return infix.greenStub?.isPublic ?: isFirstChildPublicKeyword(infix)
  }

  @JvmStatic
  private fun isFirstChildPublicKeyword(definition: PsiElement): Boolean {
    return definition.firstChild?.elementType == LamaElementTypes.LAMA_PUBLIC
  }

  @JvmStatic
  fun getParameters(function: LamaFunctionDefinitionImpl): String {
    return function.greenStub?.parameters ?: function.parameterList?.text ?: "()"
  }

  @JvmStatic
  fun getParameters(infix: LamaInfixOperatorDefinitionImpl): String {
    return infix.greenStub?.parameters ?: infix.parameterList?.text ?: "()"
  }

  @JvmStatic
  fun getAssociativity(infix: LamaInfixOperatorDefinitionImpl): LamaInfixAssociativity {
    val stub = infix.greenStub
    if (stub != null) {
      return stub.associativity
    }

    val associativity = infix.children.firstOrNull { it.elementType in LAMA_INFIX_ASSOCIATIVITY_TYPES }
    return when (associativity?.elementType) {
      LamaElementTypes.LAMA_INFIXL -> LamaInfixAssociativity.INFIXL
      LamaElementTypes.LAMA_INFIXR -> LamaInfixAssociativity.INFIXR
      else -> LamaInfixAssociativity.INFIX
    }
  }

  @JvmStatic
  fun isTopLevel(definition: LamaVariableDefinitionImpl): Boolean {
    return definition.greenStub?.isTopLevel ?: getIsTopLevelImpl(definition)
  }

  @JvmStatic
  fun isTopLevel(definition: LamaFunctionDefinitionImpl): Boolean {
    return definition.greenStub?.isTopLevel ?: getIsTopLevelImpl(definition)
  }

  @JvmStatic
  fun isTopLevel(definition: LamaInfixOperatorDefinitionImpl): Boolean {
    return definition.greenStub?.isTopLevel ?: getIsTopLevelImpl(definition)
  }

  @JvmStatic
  fun getIsTopLevelImpl(definition: LamaDefinition): Boolean {
    val parentScope = PsiTreeUtil.getParentOfType(definition, LamaScope::class.java) ?: return true
    return parentScope.parent is LamaFile
  }

  @JvmStatic
  fun getCondition(branch: LamaIfBranch): LamaExpressionSeries? {
    if (branch.firstChild.elementType == LamaElementTypes.LAMA_THEN) {
      return (branch.parent as? LamaIfStatement)?.expressionSeries
    }
    return PsiTreeUtil.getChildOfType(branch, LamaExpressionSeries::class.java)
  }

  @JvmStatic
  fun getScope(branch: LamaIfBranch): LamaScope? {
    return PsiTreeUtil.getChildOfType(branch, LamaScope::class.java)
  }

  @JvmStatic
  fun getExpression(sOrCall: LamaSOrCallExpression): LamaExpression {
    return requireNotNull(PsiTreeUtil.getChildOfType(sOrCall, LamaExpression::class.java))
  }

  @JvmStatic
  fun getArgumentList(sOrCall: LamaSOrCallExpression): LamaArgumentList? {
    return PsiTreeUtil.getChildOfType(sOrCall, LamaArgumentList::class.java)
  }

  @JvmStatic
  fun getOperator(expr: LamaAssignmentExpression): LamaAssignmentOperator? {
    return PsiTreeUtil.getChildOfType(expr, LamaAssignmentOperator::class.java)
  }

  @JvmStatic
  fun getAssignee(expr: LamaAssignmentExpression): LamaExpression {
    return requireNotNull(PsiTreeUtil.getChildOfType(expr, LamaExpression::class.java))
  }

  @JvmStatic
  fun getAssignment(expr: LamaAssignmentExpression): LamaExpression? {
    val expressions = PsiTreeUtil.getChildrenOfType(expr, LamaExpression::class.java)
    return if (expressions == null || expressions.size != 2) null else expressions[1]
  }

  @JvmStatic
  fun getFunctionBody(expr: LamaFunctionExpression): LamaFunctionBody? {
    return PsiTreeUtil.getChildOfType(expr, LamaFunctionBody::class.java)
  }

  @JvmStatic
  fun getParameterList(expr: LamaFunctionExpression): LamaParameterList {
    return requireNotNull(PsiTreeUtil.getChildOfType(expr, LamaParameterList::class.java))
  }

  @JvmStatic
  fun getName(pattern: LamaSOrAtPattern): String = pattern.nameIdentifier.name

  @JvmStatic
  fun getNameIdentifier(pattern: LamaSOrAtPattern): LamaIdentifierExpression = pattern.identifierExpression

  @JvmStatic
  fun setName(pattern: LamaSOrAtPattern, name: String): PsiElement {
    pattern.nameIdentifier.name = name
    return pattern
  }
}
