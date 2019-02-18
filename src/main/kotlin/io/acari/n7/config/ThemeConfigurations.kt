package io.acari.n7.config

import java.awt.Color

data class ThemeConfigurations(
    val borderColor: Color = ThemeDefaults.borderColor,
    val primaryColor: Color = ThemeDefaults.primaryColor,
    val secondaryColor: Color = ThemeDefaults.secondaryColor,
    val shouldOverride: Boolean = true
)