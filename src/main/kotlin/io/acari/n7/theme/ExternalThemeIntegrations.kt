package io.acari.n7.theme

import io.acari.n7.config.ConfigurationPersistence
import io.acari.n7.config.NOT_SET
import io.acari.n7.config.toOptional
import java.util.*

object ExternalThemeIntegrations {

  val secondaryThemeColor: Optional<String>
    get() = getExternalThemeFromConfig { it.externalSecondaryColor }

  val contrailColor: Optional<String>
    get() = getExternalThemeFromConfig { it.externalContrailColor }

  private fun getExternalThemeFromConfig(valueExtractor3000: (ConfigurationPersistence) -> String): Optional<String> {
    return ConfigurationPersistence.instance.filter { it.externalThemeSet != NOT_SET }
        .filter { it.isAllowedToBeOverridden }
        .map(valueExtractor3000)
        .filter { it != NOT_SET }
  }

  fun consumeThemeChangedInformation(themeChangedInformation: ThemeChangedInformation) {

    ConfigurationPersistence.instance.ifPresent {
      it.externalSecondaryColor = getSecondaryColorFromTheme(themeChangedInformation)
      it.externalContrailColor = getAccentColorFromTheme(themeChangedInformation)
      it.externalThemeSet = themeChangedInformation.externalTheme.name
    }
  }

  private fun getSecondaryColorFromTheme(themeChangedInformation: ThemeChangedInformation) =
      convertToHex(themeChangedInformation.isDark.toOptional()
        .map {
          if (it) themeChangedInformation.contrastColor
          else themeChangedInformation.foregroundColor
        }
        .filter(Objects::nonNull))

  private fun getAccentColorFromTheme(themeChangedInformation: HasAccent) =
      convertToHex(themeChangedInformation.accentColor.toOptional())

  private fun convertToHex(hex: Optional<String?>) =
      hex.map { "#${it!!}" }
          .orElse(NOT_SET)


  fun consumeAccentChangedInformation(accentChangedInformation: AccentChangedInformation) {
    ConfigurationPersistence.instance.ifPresent {
      it.externalContrailColor = getAccentColorFromTheme(accentChangedInformation)
      it.externalThemeSet = accentChangedInformation.externalTheme.name
    }
  }
}