package io.acari.n7.config

import com.intellij.openapi.options.SearchableConfigurable
import javax.swing.JComponent

class NormandyUIConfigComponent : SearchableConfigurable {
  companion object {
    const val CONFIG_ID = "io.acari.n7.config.theme"
  }

  override fun getId(): String = CONFIG_ID

  override fun getDisplayName(): String = "Normandy UI Config"

  override fun apply() {
  }

  override fun createComponent(): JComponent? = NormandyUIForm().content

  //todo: implement
  override fun isModified(): Boolean = false

}

