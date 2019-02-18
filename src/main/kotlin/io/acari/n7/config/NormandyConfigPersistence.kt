package io.acari.n7.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

import java.util.Optional


@State(name = "NormandyConfig", storages = [Storage("normandy_theme.xml")])
class NormandyConfigPersistence : PersistentStateComponent<NormandyConfigPersistence>, Cloneable {

  // They are public so they can be serialized
  var primaryThemeColor = "#FFFFFF"
  var secondaryThemeColor = "#000000"
  var jetWash = "#a38dbe,#d6f5f8,#a38dbe,#d6f5f8,#a38dbe,TRANSPARENT,TRANSPARENT,#a38dbe,#d6f5f8,#a38dbe,#d6f5f8,#a38dbe"
  var borderColor = "#EFEFEF"
  var isAllowedToBeOverridden = true

  public override fun clone(): Any {
    return XmlSerializerUtil.createCopy(this)
  }

  override fun getState(): NormandyConfigPersistence? {
    return this
  }

  /**
   * Load the state from XML
   *
   * @param state the NormandyConfigPersistence instance
   */
  override fun loadState(state: NormandyConfigPersistence) {
    XmlSerializerUtil.copyBean(state, this)
  }

  companion object {

    @JvmStatic
    val instance: Optional<NormandyConfigPersistence>
      get() = Optional.ofNullable(ServiceManager.getService(NormandyConfigPersistence::class.java))
  }
}

object NormandyConfiguration {

  val primaryThemeColor: String
    get() {
      return "aoeu"
    }
  val secondaryThemeColor: String
    get() {
      return "aoeu"
    }

  val borderColor: String
    get() {
      return "aoeu"
    }

}

object ExternalThemeIntegrations {

}