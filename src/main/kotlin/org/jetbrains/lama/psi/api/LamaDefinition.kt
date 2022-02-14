package org.jetbrains.lama.psi.api

interface LamaDefinition : LamaPsiElement {
  val isPublic: Boolean
  val isTopLevel: Boolean
}
