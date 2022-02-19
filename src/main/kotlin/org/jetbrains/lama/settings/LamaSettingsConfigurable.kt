package org.jetbrains.lama.settings

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.util.SystemInfo
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.layout.ValidationInfoBuilder
import org.jetbrains.lama.compiler.LamacManager
import org.jetbrains.lama.messages.LamaBundle

class LamaSettingsConfigurable : BoundSearchableConfigurable(
  LamaBundle.message("lama.settings"),
  LamaBundle.message("lama.settings"),
  "Settings.Lama"
) {

  private var compilerHomeInput: String = LamacManager.compilerHome

  override fun apply() {
    super.apply()
    LamacManager.compilerHome = compilerHomeInput
  }

  @Suppress("UnstableApiUsage")
  override fun createPanel(): DialogPanel {
    return panel {
      row(LamaBundle.message("lama.settings.lamac.path.label")) {
        textFieldWithBrowseButton(
          LamaBundle.message("lama.settings.lamac.path.chooser"),
          null,
          FileChooserDescriptorFactory.createSingleFolderDescriptor()
        ).bindText(::compilerHomeInput)
          .horizontalAlign(HorizontalAlign.FILL)
          .validationOnInput { validatePath(it) }
          .validationOnApply { validatePath(it) }
      }
      if (SystemInfo.isMac) {
        row {
          checkBox(LamaBundle.message("lama.settings.lima.checkbox")).bindSelected(LamacManager::limaEnabled)
        }
      }
    }
  }

  private fun ValidationInfoBuilder.validatePath(it: TextFieldWithBrowseButton): ValidationInfo? {
    return error(LamaBundle.message("lama.settings.lamac.path.error")).takeIf { _ -> !LamacManager.isCompilerHomeOk(it.text) }
  }

  override fun reset() {
    super.reset()
    compilerHomeInput = LamacManager.compilerHome
  }
}
