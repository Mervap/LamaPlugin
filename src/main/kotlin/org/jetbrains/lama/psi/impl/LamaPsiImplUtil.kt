@file:Suppress("UNUSED_PARAMETER")

package org.jetbrains.lama.psi.impl

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.util.IncorrectOperationException
import org.jetbrains.lama.parser.LamaElementTypes
import org.jetbrains.lama.psi.elementTypes.LamaElementFactory
import org.jetbrains.lama.psi.api.*
import org.jetbrains.lama.psi.references.LamaReferenceBase
import org.jetbrains.lama.psi.stubs.LAMA_INFIX_ASSOCIATIVITY_TYPES
import org.jetbrains.lama.psi.stubs.LamaInfixAssociativity

internal object LamaPsiImplUtil {

  @JvmStatic
  fun getName(identifierExpression: LamaIdentifierExpression): String = identifierExpression.text

  @JvmStatic
  fun setName(identifier: LamaIdentifierExpression, name: String): PsiElement {
    return identifier.replace(LamaElementFactory.createLamaPsiElementFromText(identifier.project, name))
  }

  @JvmStatic
  fun getName(charLiteral: LamaCharLiteral): String = charLiteral.text.trim('\'')

  @JvmStatic
  fun setName(charLiteral: LamaCharLiteral, name: String): PsiElement {
    val oldText = charLiteral.text
    val newText = oldText.replaceRange(1, oldText.length - 1, name)
    val replacement = LamaElementFactory.createLamaPsiElementFromText(charLiteral.project, newText)
    return charLiteral.replace(replacement)
  }

  @JvmStatic
  fun getName(stringLiteral: LamaStringLiteral): String = stringLiteral.text.trim('\'')

  @JvmStatic
  fun setName(stringLiteral: LamaStringLiteral, name: String): PsiElement {
    val oldText = stringLiteral.text
    val newText = oldText.replaceRange(1, oldText.length - 1, name)
    val replacement = LamaElementFactory.createLamaPsiElementFromText(stringLiteral.project, newText)
    return stringLiteral.replace(replacement)
  }

  @JvmStatic
  fun getReference(expression: LamaExpression): LamaReferenceBase<*>? = null

  @JvmStatic
  fun getReference(operator: LamaOperator): LamaReferenceBase<*>? = null

  @JvmStatic
  fun getName(operator: LamaOperator): String = operator.text

  @JvmStatic
  fun setName(operator: LamaInfixOperator, name: String): PsiElement {
    val replacement = LamaElementFactory.createLamaPsiElementFromText(operator.project, "1 $name 1").children[1]
    return operator.replace(replacement)
  }

  @JvmStatic
  fun getLeftExpr(expr: LamaOperatorExpression): LamaExpression? {
    val expressions = PsiTreeUtil.getChildrenOfType(expr, LamaExpression::class.java)
    return if (expressions == null || expressions.isEmpty()) null else expressions[0]
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
    nameIdentifier.setName(name)
    return definition
  }

  @JvmStatic
  fun getName(definition: LamaVariableDefinitionImpl): String {
    return definition.greenStub?.name ?: definition.nameIdentifier?.name ?: "<unnamed>"
  }

  @JvmStatic
  fun getNameIdentifier(assignment: LamaVariableDefinition): PsiNamedElement? {
    return assignment.firstChild as? PsiNamedElement
  }

  @JvmStatic
  fun setName(definition: LamaFunctionDefinition, name: String): PsiElement {
    val nameIdentifier = definition.nameIdentifier ?: throw IncorrectOperationException("Empty name: $this")
    nameIdentifier.setName(name)
    return definition
  }

  @JvmStatic
  fun getName(definition: LamaFunctionDefinitionImpl): String {
    return definition.greenStub?.name ?: definition.nameIdentifier?.name ?: "<unnamed>"
  }

  @JvmStatic
  fun getNameIdentifier(assignment: LamaFunctionDefinition): PsiNamedElement? {
    return PsiTreeUtil.getChildOfType(assignment, LamaIdentifierExpression::class.java)
  }


  @JvmStatic
  fun setName(definition: LamaInfixOperatorDefinition, name: String): PsiElement {
    val nameOperator = definition.nameOperator
    val replacement = LamaElementFactory.createLamaPsiElementFromText(nameOperator.project, "1 $name 1").children[1]
    return nameOperator.replace(replacement)
  }

  @JvmStatic
  fun getName(definition: LamaInfixOperatorDefinitionImpl): String {
    return definition.greenStub?.name ?: definition.nameOperator.name ?: "<unnamed>"
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
  fun isPublic(function: LamaFunctionDefinitionImpl): Boolean {
    return function.greenStub?.isPublic ?: (function.firstChild?.elementType == LamaElementTypes.LAMA_PUBLIC)
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
  fun getIsTopLevel(definition: LamaVariableDefinitionImpl): Boolean {
    return definition.greenStub?.isTopLevel ?: getIsTopLevelImpl(definition)
  }

  @JvmStatic
  fun getIsTopLevel(definition: LamaFunctionDefinitionImpl): Boolean {
    return definition.greenStub?.isTopLevel ?: getIsTopLevelImpl(definition)
  }

  @JvmStatic
  fun getIsTopLevel(definition: LamaInfixOperatorDefinitionImpl): Boolean {
    return definition.greenStub?.isTopLevel ?: getIsTopLevelImpl(definition)
  }

  @JvmStatic
  fun getIsTopLevelImpl(definition: LamaDefinition): Boolean {
    val parentScope = PsiTreeUtil.getParentOfType(definition, LamaScope::class.java) ?: return true
    return parentScope.parent is LamaFile
  }
}
