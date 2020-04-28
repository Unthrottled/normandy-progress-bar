package io.unthrottled.n7.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.DumbAware
import io.unthrottled.n7.config.ui.NormandyConfigComponent

/**
 * Used for the toolbar and dropdown icon action.
 */
class ConfigAction : AnAction(), DumbAware {
  override fun actionPerformed(event: AnActionEvent) {
    ShowSettingsUtil.getInstance()
        .showSettingsDialog(event.project, NormandyConfigComponent::class.java)
  }
}