package io.acari.n7.theme

import io.acari.n7.config.ExternalTheme
import java.awt.Color

data class ThemeConfigurations(
    var contrail: Color = ThemeDefaults.contrailColor,
    var primaryColor: Color = ThemeDefaults.primaryColor,
    var secondaryColor: Color = ThemeDefaults.secondaryColor,
    var isRainbowMode: Boolean = false,
    var isTransparentBackground: Boolean = false,
    var shouldOverride: Boolean = true,
    var externalTheme: ExternalTheme = ExternalTheme.NOT_SET
)