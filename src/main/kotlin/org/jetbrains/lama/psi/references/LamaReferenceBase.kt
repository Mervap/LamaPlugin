package org.jetbrains.lama.psi.references

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.ResolveResult
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.util.IncorrectOperationException
import org.jetbrains.lama.psi.api.LamaPsiElement

@Suppress("MemberVisibilityCanBePrivate")
abstract class LamaReferenceBase<T : LamaPsiElement>(protected val psiElement: T) : PsiPolyVariantReference {

  @Throws(IncorrectOperationException::class)
  override fun bindToElement(element: PsiElement) = null

  override fun isReferenceTo(element: PsiElement): Boolean {
    return when (val resolve = resolve()) {
      is PsiNameIdentifierOwner -> resolve === element || resolve.identifyingElement === element
      else -> resolve === element
    }
  }

  override fun isSoft() = false

  override fun getCanonicalText() = element.text!!

  override fun getElement() = psiElement

  override fun getRangeInElement() = psiElement.node.textRange.shiftRight(-psiElement.node.startOffset)

  override fun handleElementRename(newElementName: String): PsiElement? = null

  override fun resolve(): PsiElement? {
    val results = multiResolve(false)
    return if (results.size == 1) results.first().element else null
  }

  fun multiResolve(): List<PsiElement> = multiResolve(false).mapNotNull { it.element }

  override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
    val project = psiElement.project
    return ResolveCache.getInstance(project).resolveWithCaching(this, Resolver(), false, false)
  }

  protected abstract fun resolveImpl(incompleteCode: Boolean): Array<ResolveResult>

  private class Resolver<T : LamaPsiElement> : ResolveCache.PolyVariantResolver<LamaReferenceBase<T>> {
    override fun resolve(reference: LamaReferenceBase<T>, incompleteCode: Boolean): Array<ResolveResult> {
      return reference.resolveImpl(incompleteCode)
    }
  }
}