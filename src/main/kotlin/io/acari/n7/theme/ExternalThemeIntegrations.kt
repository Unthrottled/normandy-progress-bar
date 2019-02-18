package io.acari.n7.theme

import io.acari.n7.config.toOptional
import java.util.*

object ExternalThemeIntegrations {

  private lateinit var _secondaryThemeColor: String
  private lateinit var _jetWashColor: String

  val secondaryThemeColor: Optional<String>
    get() = if (this::_secondaryThemeColor.isInitialized) _secondaryThemeColor.toOptional()
    else Optional.empty()

  val jetWashColor: Optional<String>
    get() = if (this::_jetWashColor.isInitialized) _jetWashColor.toOptional()
    else Optional.empty()

  fun consumeThemeChangedInformation(themeChangedInformation: ThemeChangedInformation) {
    _secondaryThemeColor = "#${themeChangedInformation.contrastColor}"
    _jetWashColor = "#${themeChangedInformation.accentColor}"

  }

  fun consumeAccentChangedInformation(accentChangedInformation: AccentChangedInformation) {
    _jetWashColor = "#${accentChangedInformation.accentColor}"
  }
}