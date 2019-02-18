package io.acari.n7.theme

import io.acari.n7.config.ConfigurationPersistence
import io.acari.n7.config.NOT_SET
import io.acari.n7.config.toOptional
import java.util.*

object ExternalThemeIntegrations {

  val secondaryThemeColor: Optional<String>
    get() = getExternalThemeFromConfig { it.externalSecondaryColor }

  val jetWashColor: Optional<String>
    get() = getExternalThemeFromConfig { it.externalJetWashColor }

  private fun getExternalThemeFromConfig(valueExtractor3000: (ConfigurationPersistence)->String): Optional<String> {
    return ConfigurationPersistence.instance.filter { it.externalThemeSet }
        .filter { it.isAllowedToBeOverridden }
        .map(valueExtractor3000)
        .filter { it != NOT_SET }
  }

  fun consumeThemeChangedInformation(themeChangedInformation: ThemeChangedInformation) {

    ConfigurationPersistence.instance.ifPresent {
      it.externalSecondaryColor = "#${themeChangedInformation.contrastColor}"
      it.externalJetWashColor = "#${themeChangedInformation.accentColor}"
      it.externalThemeSet = true
    }
  }

  fun consumeAccentChangedInformation(accentChangedInformation: AccentChangedInformation) {
    ConfigurationPersistence.instance.ifPresent {
      it.externalJetWashColor = "#${accentChangedInformation.accentColor}"
      it.externalThemeSet = true
    }
  }
}