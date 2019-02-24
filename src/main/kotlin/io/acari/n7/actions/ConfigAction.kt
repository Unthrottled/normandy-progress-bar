package io.acari.n7.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil
import io.acari.n7.config.ui.NormandyConfigComponent

/**
 * Used for the toolbar and dropdown icon action.
 */
class ConfigAction : AnAction() {
  override fun actionPerformed(event: AnActionEvent) {
    ShowSettingsUtil.getInstance()
        .showSettingsDialog(event.project, NormandyConfigComponent::class.java)
  }
}