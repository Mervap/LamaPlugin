package org.jetbrains.lama.psi.controlFlow

import com.intellij.codeInsight.controlflow.ControlFlowUtil
import com.intellij.codeInsight.controlflow.Instruction
import com.intellij.codeInsight.controlflow.impl.ControlFlowImpl
import com.intellij.psi.PsiElement
import java.util.*

class LamaControlFlow(instructions: Array<Instruction>): ControlFlowImpl(instructions) {
  private val reachable = BitSet()
  private val element2Instruction = instructions.mapNotNull { instruction -> instruction.element?.let { it to instruction } }.toMap()

  init {
    ControlFlowUtil.process(this.instructions, 0) { instruction ->
      reachable.set(instruction.num());
      true
    }
  }

  fun getInstructionByElement(element: PsiElement): Instruction? {
    return element2Instruction[element]
  }

  fun isReachable(instruction: Instruction): Boolean {
    if (instruction !== instructions[instruction.num()]) {
      throw IllegalArgumentException("Instruction is not from LamaControlFlow")
    }
    return reachable.get(instruction.num())
  }
}