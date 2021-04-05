package io.unthrottled.n7.config.ui

import com.intellij.openapi.options.SearchableConfigurable
import io.unthrottled.n7.config.ConfigurationPersistence
import io.unthrottled.n7.config.ui.ConfigurationManager.configToThemeConfig
import io.unthrottled.n7.theme.ThemeConfigurations
import javax.swing.JComponent

class NormandyConfigComponent : SearchableConfigurable {
  companion object {
    const val CONFIG_ID = "io.unthrottled.n7.config.theme"
  }

  private var initialConfig = ConfigurationPersistence.instance
    .map { configToThemeConfig(it) }
    .orElseGet { ThemeConfigurations() }

  private var normandyForm =
    NormandyForm(initialConfig)

  override fun getId(): String = CONFIG_ID

  override fun getDisplayName(): String = "SSV Normandy Configuration"

  override fun cancel() {
    ConfigurationManager.applyConfigurations(initialConfig) {}
  }

  override fun reset() {
    ConfigurationManager.applyConfigurations(initialConfig) {}
  }

  /**
   * When ever the user accepts changes to the configuration.
   */
  override fun apply() {
    ConfigurationManager.applyConfigurations(normandyForm.myThemeConfigurations) {
      val config = normandyForm.myThemeConfigurations
      initialConfig = config
      normandyForm = NormandyForm(config)
    }
  }

  override fun createComponent(): JComponent? = normandyForm.getContent()

  /**
   * Helps with the reset link that comes in the upper right hand corner, or the apply button.
   * I dunno which it helps, I just made it does the do ¯\_(ツ)_/¯
   */
  override fun isModified(): Boolean = normandyForm.isModified()
}
