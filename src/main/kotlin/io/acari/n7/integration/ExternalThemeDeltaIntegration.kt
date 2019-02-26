package io.acari.n7.integration

import io.acari.n7.config.AccentDelta
import io.acari.n7.config.HasAccent
import io.acari.n7.config.ThemeDeltas
import io.acari.n7.config.ConfigurationPersistence
import io.acari.n7.config.NOT_SET
import io.acari.n7.util.toOptional
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
            if (it) themeDeltas.treeSelectionBackground ?: themeDeltas.contrastColor
            else themeDeltas.foregroundColor
          }
          .filter(Objects::nonNull))

  private fun getAccentColorFromTheme(themeChangedInformation: HasAccent) =
      convertToHex(themeChangedInformation.accentColor.toOptional())

  private fun convertToHex(hex: Optional<String?>) =
      hex.map { "#${it!!}" }
          .orElse(NOT_SET)
}