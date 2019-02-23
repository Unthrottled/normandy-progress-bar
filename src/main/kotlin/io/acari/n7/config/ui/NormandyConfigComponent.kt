package io.acari.n7.config.ui

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.SearchableConfigurable
import io.acari.n7.config.CONFIGURATION_TOPIC
import io.acari.n7.config.ConfigurationPersistence
import io.acari.n7.config.ThemeConfigurations
import io.acari.n7.theme.ExternalTheme
import org.jdesktop.swingx.color.ColorUtil
import javax.swing.JComponent

class NormandyConfigComponent : SearchableConfigurable {
  companion object {
    const val CONFIG_ID = "io.acari.n7.config.theme"
  }

  private var normandyForm = {
    val themeConfigurations = ConfigurationPersistence.instance
        .map { configToThemConfig(it) }.orElseGet { ThemeConfigurations() }
    NormandyForm(themeConfigurations)
  }()

  private fun configToThemConfig(it: ConfigurationPersistence): ThemeConfigurations {
    return ThemeConfigurations(
        com.intellij.ui.ColorUtil.fromHex(it.contrailColor),
        com.intellij.ui.ColorUtil.fromHex(it.primaryThemeColor),
        com.intellij.ui.ColorUtil.fromHex(it.secondaryThemeColor),
        it.isAllowedToBeOverridden,
        ExternalTheme.byName(it.externalThemeSet)
    )
  }

  override fun getId(): String = CONFIG_ID

  override fun getDisplayName(): String = "SSV Normandy Configuration"

  override fun apply() {
    ConfigurationPersistence.instance
        .ifPresent {
          it.isAllowedToBeOverridden = normandyForm.shouldOverride
          it.contrailColor = ColorUtil.toHexString(normandyForm.getContrailColor())
          it.primaryThemeColor = ColorUtil.toHexString(normandyForm.getPrimaryColor())
          it.secondaryThemeColor = ColorUtil.toHexString(normandyForm.getSecondaryColor())
          normandyForm = NormandyForm(configToThemConfig(it))
          ApplicationManager.getApplication().messageBus
              .syncPublisher(CONFIGURATION_TOPIC)
              .consumeChanges(configToThemConfig(it))
        }
  }

  override fun createComponent(): JComponent? {
    return normandyForm.getContent()
  }

  override fun isModified(): Boolean = normandyForm.isModified()

}

