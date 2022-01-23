package org.jetbrains.lama.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.jetbrains.lama.lexer.LamaLexer
import org.jetbrains.lama.psi.LamaElementType
import org.jetbrains.lama.psi.api.LamaFile
import org.jetbrains.lama.psi.impl.LamaFileElementType
import org.jetbrains.lama.psi.impl.LamaFileImpl

class LamaParserDefinition : ParserDefinition {
  private val myCommentTokens = TokenSet.create(END_OF_LINE_COMMENT, BLOCK_COMMENT)
  private val myStringLiteralTokens = TokenSet.create(LamaElementTypes.LAMA_STRING)
  override fun createLexer(project: Project): Lexer = LamaLexer()

  override fun getFileNodeType(): IFileElementType = FILE
  override fun getCommentTokens(): TokenSet = myCommentTokens
  override fun getStringLiteralElements(): TokenSet = myStringLiteralTokens

  override fun createParser(project: Project): PsiParser = LamaParser()
  override fun createElement(node: ASTNode): PsiElement {
    return LamaElementTypes.Factory.createElement(node)
  }

  override fun createFile(viewProvider: FileViewProvider): LamaFile {
    return LamaFileImpl(viewProvider)
  }

  override fun spaceExistanceTypeBetweenTokens(left: ASTNode, right: ASTNode): ParserDefinition.SpaceRequirements {
    return ParserDefinition.SpaceRequirements.MAY
  }

  companion object {
    @JvmField val FILE: LamaFileElementType = LamaFileElementType()
    @JvmField val END_OF_LINE_COMMENT: IElementType = LamaElementType("END_OF_LINE_COMMENT")
    @JvmField val BLOCK_COMMENT: IElementType = LamaElementType("BLOCK_COMMENT")
    @JvmField val BAD_CHARACTER: IElementType = LamaElementType("BAD_CHARACTER")
  }
}