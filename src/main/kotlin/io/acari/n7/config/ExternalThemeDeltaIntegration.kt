package io.acari.n7.config

import io.acari.n7.config.util.ConfigurationPersistence
import io.acari.n7.config.util.NOT_SET
import io.acari.n7.toOptional
import java.util.*

object ExternalThemeDeltaIntegration {

  fun consumeThemeDelta(themeDeltas: ThemeDeltas) =
      ConfigurationPersistence.instance.ifPresent {
        it.externalSecondaryColor = getSecondaryColorFromTheme(themeDeltas)
        it.externalContrailColor = getAccentColorFromTheme(themeDeltas)
        it.externalThemeSet = themeDeltas.externalTheme.name
      }

  fun consumeAccentDelta(accentDelta: AccentDelta) {
    ConfigurationPersistence.instance.ifPresent {
      it.externalContrailColor = getAccentColorFromTheme(accentDelta)
      it.externalThemeSet = accentDelta.externalTheme.name
    }
  }

  private fun getSecondaryColorFromTheme(themeDeltas: ThemeDeltas) =
      convertToHex(themeDeltas.isDark.toOptional()
          .map {
            if (it) themeDeltas.contrastColor
            else themeDeltas.foregroundColor
          }
          .filter(Objects::nonNull))

  private fun getAccentColorFromTheme(themeChangedInformation: HasAccent) =
      convertToHex(themeChangedInformation.accentColor.toOptional())

  private fun convertToHex(hex: Optional<String?>) =
      hex.map { "#${it!!}" }
          .orElse(NOT_SET)
}