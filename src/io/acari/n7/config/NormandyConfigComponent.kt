package io.acari.n7.config

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.SearchableConfigurable
import org.jdesktop.swingx.color.ColorUtil
import javax.swing.JComponent

class NormandyConfigComponent : SearchableConfigurable {
  companion object {
    const val CONFIG_ID = "io.acari.n7.config.theme"
  }

  private val normandyForm = lazy {
    val themeConfigurations = NormandyConfig.instance
        .map { configToThemConfig(it) }.orElseGet { ThemeConfigurations() }
    NormandyForm(themeConfigurations)
  }

  private fun configToThemConfig(it: NormandyConfig): ThemeConfigurations {
    return ThemeConfigurations(
        com.intellij.ui.ColorUtil.fromHex(it.borderColor),
        com.intellij.ui.ColorUtil.fromHex(it.primaryThemeColor),
        com.intellij.ui.ColorUtil.fromHex(it.secondaryThemeColor),
        it.isAllowedToBeOverridden
    )
  }


  override fun getId(): String = CONFIG_ID

  override fun getDisplayName(): String = "Normandy UI Config"

  override fun apply() {
    NormandyConfig.instance
        .ifPresent {
          it.isAllowedToBeOverridden = normandyForm.value.shouldOverride
          it.borderColor = ColorUtil.toHexString(normandyForm.value.getBorderColor())
          it.primaryThemeColor = ColorUtil.toHexString(normandyForm.value.getPrimaryColor())
          it.secondaryThemeColor = ColorUtil.toHexString(normandyForm.value.getSecondaryColor())
          ApplicationManager.getApplication().messageBus
              .syncPublisher(CONFIGURATION_TOPIC)
              .configurationChanged(configToThemConfig(it))
        }
  }

  override fun createComponent(): JComponent? {
    return normandyForm.value.getContent()
  }

  //todo: implement
  override fun isModified(): Boolean = true

}

