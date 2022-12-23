package io.unthrottled.n7.config.ui

import io.unthrottled.n7.theme.ThemeConfigurations
import javax.swing.JComponent

class NormandyForm(themeConfigurations: ThemeConfigurations) {

  private val normandyPanel = NormandyConfigUI(themeConfigurations)

  fun themeConfigs(): ThemeConfigurations = normandyPanel.currentThemeConfig

  fun isModified(): Boolean =
    normandyPanel.isModified

  fun getContent(): JComponent? {
    return normandyPanel.rootPanel
  }
}
