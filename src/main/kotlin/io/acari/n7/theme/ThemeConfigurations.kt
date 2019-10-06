package io.acari.n7.theme

import io.acari.n7.config.ExternalTheme
import java.awt.Color

data class ThemeConfigurations(
    val contrail: Color = ThemeDefaults.contrailColor,
    val primaryColor: Color = ThemeDefaults.primaryColor,
    val secondaryColor: Color = ThemeDefaults.secondaryColor,
    val isRainbowMode: Boolean = false,
    val isTransparentBackground: Boolean = false,
    val shouldOverride: Boolean = true,
    val externalTheme: ExternalTheme = ExternalTheme.NOT_SET
)