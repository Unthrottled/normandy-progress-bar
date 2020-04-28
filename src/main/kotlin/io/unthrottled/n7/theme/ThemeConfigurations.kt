package io.unthrottled.n7.theme

import io.unthrottled.n7.config.ExternalTheme
import java.awt.Color

data class ThemeConfigurations(
  var contrail: Color = ThemeDefaults.contrailColor,
  var primaryColor: Color = ThemeDefaults.primaryColor,
  var secondaryColor: Color = ThemeDefaults.secondaryColor,
  var isRainbowMode: Boolean = false,
  var isTransparentBackground: Boolean = false,
  var shouldUseThemeAccents: Boolean = true,
  var externalTheme: ExternalTheme = ExternalTheme.NOT_SET
)