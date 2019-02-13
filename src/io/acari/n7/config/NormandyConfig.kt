package io.acari.n7.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

import java.util.Optional


@State(name = "NormandyConfig", storages = [Storage("normandy_theme.xml")])
class NormandyConfig : PersistentStateComponent<NormandyConfig>, Cloneable {

  // They are public so they can be serialized
  var primaryThemeColor = "#FFFFFF"
  var secondaryThemeColor = "#000000"
  var jetWash = "#a38dbe,#d6f5f8,#a38dbe,#d6f5f8,#a38dbe,TRANSPARENT,TRANSPARENT,#a38dbe,#d6f5f8,#a38dbe,#d6f5f8,#a38dbe"
  var borderColor = "#EFEFEF"
  var isAllowedToBeOverridden = true

  public override fun clone(): Any {
    return XmlSerializerUtil.createCopy(this)
  }


  /**
   * Convenience method to reset settings
   */
  fun resetSettings() {
    primaryThemeColor = ""
    secondaryThemeColor = ""
    jetWash = ""
    borderColor = ""
  }


  override fun getState(): NormandyConfig? {
    return this
  }

  /**
   * Load the state from XML
   *
   * @param state the MTConfig instance
   */
  override fun loadState(state: NormandyConfig) {
    XmlSerializerUtil.copyBean(state, this)
  }

  companion object {

    @JvmStatic
    val instance: Optional<NormandyConfig>
      get() = Optional.ofNullable(ServiceManager.getService(NormandyConfig::class.java))
  }


}
