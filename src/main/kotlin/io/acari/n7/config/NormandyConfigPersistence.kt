package io.acari.n7.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import io.acari.n7.theme.AccentChangedInformation
import io.acari.n7.theme.ThemeChangedInformation

import java.util.Optional


@State(name = "NormandyConfig", storages = [Storage("normandy_theme.xml")])
class NormandyConfigPersistence : PersistentStateComponent<NormandyConfigPersistence>, Cloneable {

  // They are public so they can be serialized
  var primaryThemeColor = "#FFFFFF"
  var secondaryThemeColor = "#000000"
  var jetWash = "#a38dbe,#d6f5f8,#a38dbe,#d6f5f8,#a38dbe,TRANSPARENT,TRANSPARENT,#a38dbe,#d6f5f8,#a38dbe,#d6f5f8,#a38dbe"
  var borderColor = "#EFEFEF"
  var jetWashColor = "#d6f5f8"
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

  val primaryThemeColor: Optional<String>
    get() {
      return getChanges({ it.primaryThemeColor }) { Optional.empty() }
    }

  val secondaryThemeColor: Optional<String>
    get() {
      return getChanges({ it.secondaryThemeColor }) { ExternalThemeIntegrations.secondaryThemeColor }
    }

  val borderColor: Optional<String>
    get() {
      return getChanges({ it.borderColor }) { Optional.empty() }
    }

  val jetWashColor: Optional<String>
    get() {
      return getChanges({ it.jetWashColor }) { ExternalThemeIntegrations.jetWashColor }
    }

  private fun <T> getChanges(userConfiguration: (NormandyConfigPersistence) -> T, externalConfiguration: () -> Optional<T>): Optional<T> =
      NormandyConfigPersistence.instance
          .flatMap {
            if (it.isAllowedToBeOverridden) externalConfiguration().map { it.toOptional() }
                .orElseGet { userConfiguration(it).toOptional() }
            else userConfiguration(it).toOptional()
          }

}

fun <T> T?.toOptional(): Optional<T> = Optional.ofNullable(this)


object ExternalThemeIntegrations {

  private lateinit var _secondaryThemeColor: String
  private lateinit var _jetWashColor: String

  val secondaryThemeColor: Optional<String>
    get() = if (this::_secondaryThemeColor.isInitialized) _secondaryThemeColor.toOptional() else Optional.empty()

  val jetWashColor: Optional<String>
    get() = if (this::_jetWashColor.isInitialized) _jetWashColor.toOptional() else Optional.empty()

  fun consumeThemeChangedInformation(themeChangedInformation: ThemeChangedInformation) {
    _secondaryThemeColor = "#${themeChangedInformation.contrastColor}"
    _jetWashColor = "#${themeChangedInformation.accentColor}"

  }

  fun consumeAccentChangedInformation(accentChangedInformation: AccentChangedInformation) {
    _jetWashColor = "#${accentChangedInformation.accentColor}"
  }
}
