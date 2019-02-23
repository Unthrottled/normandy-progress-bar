package io.acari.n7.theme

import io.acari.n7.config.ConfigurationPersistence
import io.acari.n7.config.NOT_SET
import java.util.*

object ExternalThemeIntegrations {

  val secondaryThemeColor: Optional<String>
    get() = getExternalThemeFromConfig { it.externalSecondaryColor }

  val jetWashColor: Optional<String>
    get() = getExternalThemeFromConfig { it.externalContrailColor }

  private fun getExternalThemeFromConfig(valueExtractor3000: (ConfigurationPersistence)->String): Optional<String> {
    return ConfigurationPersistence.instance.filter { it.externalThemeSet != NOT_SET}
        .filter { it.isAllowedToBeOverridden }
        .map(valueExtractor3000)
        .filter { it != NOT_SET }
  }

  fun consumeThemeChangedInformation(themeChangedInformation: ThemeChangedInformation) {

    ConfigurationPersistence.instance.ifPresent {
      it.externalSecondaryColor = "#${themeChangedInformation.contrastColor}"
      it.externalContrailColor = "#${themeChangedInformation.accentColor}"
      it.externalThemeSet = themeChangedInformation.externalTheme.name
    }
  }

  fun consumeAccentChangedInformation(accentChangedInformation: AccentChangedInformation) {
    ConfigurationPersistence.instance.ifPresent {
      it.externalContrailColor = "#${accentChangedInformation.accentColor}"
      it.externalThemeSet = accentChangedInformation.externalTheme.name
    }
  }
}