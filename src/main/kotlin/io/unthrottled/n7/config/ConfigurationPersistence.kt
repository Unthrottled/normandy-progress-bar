package io.unthrottled.n7.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.ui.ColorUtil
import com.intellij.util.xmlb.XmlSerializerUtil
import io.unthrottled.n7.theme.ThemeDefaults
import java.util.Optional

@State(name = "NormandyConfig", storages = [Storage("normandy_theme.xml")])
class ConfigurationPersistence : PersistentStateComponent<ConfigurationPersistence>, Cloneable {

  // They are public so they can be serialized
  var primaryThemeColor = "#${ColorUtil.toHex(ThemeDefaults.primaryColor)}"
  var secondaryThemeColor = "#${ColorUtil.toHex(ThemeDefaults.secondaryColor)}"
  var contrailColor = "#${ColorUtil.toHex(ThemeDefaults.contrailColor)}"
  var backgroundColor = "#${ColorUtil.toHex(ThemeDefaults.backgroundColor)}"
  var isCustomBackground = false
  var isRainbowMode = false
  var isTransparentBackground = false
  var useThemeAccent = false

  var userId: String = ""

  var externalSecondaryColor = NOT_SET
  var externalContrailColor = NOT_SET
  var externalThemeSet = NOT_SET

  var version = NOT_SET

  public override fun clone(): Any {
    return XmlSerializerUtil.createCopy(this)
  }

  override fun getState(): ConfigurationPersistence? {
    return this
  }

  /**
   * Load the state from XML
   *
   * @param state the ConfigurationPersistence instance
   */
  override fun loadState(state: ConfigurationPersistence) {
    XmlSerializerUtil.copyBean(state, this)
  }

  override fun toString(): String {
    return "ConfigurationPersistence(primaryThemeColor='$primaryThemeColor', secondaryThemeColor='$secondaryThemeColor', contrailColor='$contrailColor', isRainbowMode=$isRainbowMode, isTransparentBackground=$isTransparentBackground, useThemeAccent=$useThemeAccent, externalSecondaryColor='$externalSecondaryColor', externalContrailColor='$externalContrailColor', externalThemeSet='$externalThemeSet', version='$version')"
  }

  companion object {

    @JvmStatic
    val instance: Optional<ConfigurationPersistence>
      get() = Optional.ofNullable(ServiceManager.getService(ConfigurationPersistence::class.java))
  }
}

const val NOT_SET = "NOT_SET"
