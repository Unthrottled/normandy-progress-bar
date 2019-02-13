package io.acari.n7.config

import com.intellij.openapi.options.SearchableConfigurable
import org.jdesktop.swingx.color.ColorUtil
import javax.swing.JComponent

class NormandyConfigComponent : SearchableConfigurable {
  companion object {
    const val CONFIG_ID = "io.acari.n7.config.theme"
  }

  private val normandyForm = lazy {
    val themeConfigurations = NormandyConfig.getInstance()
        .map {
          ThemeConfigurations(
              com.intellij.ui.ColorUtil.fromHex(it.borderColor),
              com.intellij.ui.ColorUtil.fromHex(it.primaryThemeColor),
              com.intellij.ui.ColorUtil.fromHex(it.secondaryThemeColor),
              it.allowedToBeOverridden
          )
        }.orElseGet { ThemeConfigurations() }
    NormandyForm(themeConfigurations)
  }


  override fun getId(): String = CONFIG_ID

  override fun getDisplayName(): String = "Normandy UI Config"

  override fun apply() {
    NormandyConfig.getInstance()
        .ifPresent {
          it.allowedToBeOverridden = normandyForm.value.shouldOverride
          it.borderColor = ColorUtil.toHexString(normandyForm.value.getBorderColor())
          it.primaryThemeColor = ColorUtil.toHexString(normandyForm.value.getPrimaryColor())
          it.secondaryThemeColor = ColorUtil.toHexString(normandyForm.value.getSecondaryColor())
        }
  }

  override fun createComponent(): JComponent? {
    return normandyForm.value.getContent()
  }

  //todo: implement
  override fun isModified(): Boolean = true

}

