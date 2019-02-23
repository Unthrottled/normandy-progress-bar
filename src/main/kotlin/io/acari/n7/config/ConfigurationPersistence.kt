package io.acari.n7.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

import java.util.Optional


@State(name = "NormandyConfig", storages = [Storage("normandy_theme.xml")])
class ConfigurationPersistence : PersistentStateComponent<ConfigurationPersistence>, Cloneable {

  // They are public so they can be serialized
  var primaryThemeColor = "#FFFFFF"
  var secondaryThemeColor = "#000000"
  var contrailColor = "#A47FD8"
  var isAllowedToBeOverridden = true

  var externalSecondaryColor = NOT_SET
  var externalContrailColor = NOT_SET
  var externalThemeSet = NOT_SET

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

  companion object {

    @JvmStatic
    val instance: Optional<ConfigurationPersistence>
      get() = Optional.ofNullable(ServiceManager.getService(ConfigurationPersistence::class.java))
  }
}

fun <T> T?.toOptional(): Optional<T> = Optional.ofNullable(this)


const val NOT_SET = "NOT_SET"