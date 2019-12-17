package io.acari.n7.config.ui

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.SearchableConfigurable
import io.acari.n7.config.CONFIGURATION_TOPIC
import io.acari.n7.config.ConfigurationPersistence
import io.acari.n7.theme.ThemeConfigurations
import io.acari.n7.config.ExternalTheme
import org.jdesktop.swingx.color.ColorUtil
import javax.swing.JComponent

class NormandyConfigComponent : SearchableConfigurable {
  companion object {
    const val CONFIG_ID = "io.acari.n7.config.theme"
  }

  private var initialConfig = ConfigurationPersistence.instance
      .map { configToThemeConfig(it) }
      .orElseGet { ThemeConfigurations() }

  private var normandyForm =
      NormandyForm(initialConfig)

  override fun getId(): String = CONFIG_ID

  override fun getDisplayName(): String = "SSV Normandy Configuration"

  override fun cancel() {
    ConfigurationPersistence.instance
        .ifPresent {
          applyConfigurations(it, initialConfig)
        }
  }

  /**
   * When ever the user accepts changes to the configuration.
   */
  override fun apply() {
    ConfigurationPersistence.instance
        .ifPresent {
          val config = normandyForm.myThemeConfigurations
          initialConfig = config
          applyConfigurations(it, config)

          normandyForm.dispose()
          normandyForm = NormandyForm(configToThemeConfig(it))
        }
  }

  private fun applyConfigurations(it: ConfigurationPersistence, config: ThemeConfigurations) {
    it.isAllowedToBeOverridden = config.shouldOverride
    it.contrailColor = ColorUtil.toHexString(config.contrail)
    it.primaryThemeColor = ColorUtil.toHexString(config.primaryColor)
    it.secondaryThemeColor = ColorUtil.toHexString(config.secondaryColor)
    it.isRainbowMode = config.isRainbowMode
    it.isTransparentBackground = config.isTransparentBackground
    ApplicationManager.getApplication().messageBus
        .syncPublisher(CONFIGURATION_TOPIC)
        .consumeChanges(configToThemeConfig(it))
  }

  override fun createComponent(): JComponent? = normandyForm.getContent()

  /**
   * Helps with the reset link that comes in the upper right hand corner, or the apply button.
   * I dunno which it helps, I just made it does the do ¯\_(ツ)_/¯
   */
  override fun isModified(): Boolean = normandyForm.isModified()

  private fun configToThemeConfig(it: ConfigurationPersistence): ThemeConfigurations {
    return ThemeConfigurations(
        com.intellij.ui.ColorUtil.fromHex(it.contrailColor),
        com.intellij.ui.ColorUtil.fromHex(it.primaryThemeColor),
        com.intellij.ui.ColorUtil.fromHex(it.secondaryThemeColor),
        it.isRainbowMode,
        it.isTransparentBackground,
        it.isAllowedToBeOverridden,
        ExternalTheme.byName(it.externalThemeSet)
    )
  }

}

