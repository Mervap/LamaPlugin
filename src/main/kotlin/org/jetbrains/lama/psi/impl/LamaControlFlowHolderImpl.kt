package org.jetbrains.lama.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import org.jetbrains.lama.psi.LamaPsiUtil.controlFlowContainer
import org.jetbrains.lama.psi.api.LamaControlFlowHolder
import org.jetbrains.lama.psi.controlFlow.LamaControlFlow
import org.jetbrains.lama.psi.controlFlow.LocalAnalysisResult
import org.jetbrains.lama.psi.controlFlow.analyzeLocals
import org.jetbrains.lama.psi.controlFlow.buildControlFlow

abstract class LamaControlFlowHolderImpl<T : StubElement<*>> : LamaBaseElementImpl<T>, LamaControlFlowHolder {
  constructor(node: ASTNode) : super(node)
  @Suppress("UNCHECKED_CAST")
  constructor(stub: StubElement<*>, nodeType: IStubElementType<*, *>) : super(stub as T, nodeType)

  override val controlFlow: LamaControlFlow
    get() {
      return CachedValuesManager.getCachedValue(this) {
        CachedValueProvider.Result(buildControlFlow(this), this)
      }
    }

  private val analysisResults: Map<LamaControlFlowHolder, LocalAnalysisResult>
    get() {
      return CachedValuesManager.getCachedValue(this) {
        CachedValueProvider.Result(analyzeLocals(), this)
      }
    }

  override val localAnalysisResult: LocalAnalysisResult
    get() {
      var parentHolder = controlFlowContainer
      while (parentHolder != null) {
        val ancestor = parentHolder.controlFlowContainer ?: break
        parentHolder = ancestor
      }
      if (parentHolder == null) {
        return analysisResults.getValue(this)
      }
      require(parentHolder is LamaControlFlowHolderImpl<*>) { "Actual type is ${parentHolder.javaClass}" }
      return parentHolder.analysisResults.getValue(this)
    }
}