package io.acari.n7.config

import java.awt.Color

data class ThemeConfigurations(
    val borderColor: Color = NormandyThemeDefaults.borderColor,
    val primaryColor: Color = NormandyThemeDefaults.primaryColor,
    val secondaryColor: Color = NormandyThemeDefaults.secondaryColor,
    val shouldOverride: Boolean = true
)