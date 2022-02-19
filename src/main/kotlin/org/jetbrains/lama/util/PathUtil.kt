package org.jetbrains.lama.util

import java.nio.file.InvalidPathException
import java.nio.file.Path

object PathUtil {
  fun String.safePath(): Path? {
    return try {
      Path.of(this)
    }
    catch (e: InvalidPathException) {
      null
    }
  }
}