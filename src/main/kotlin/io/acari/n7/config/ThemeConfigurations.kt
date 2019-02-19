package io.acari.n7.config

import io.acari.n7.theme.ExternalTheme
import java.awt.Color

data class ThemeConfigurations(
    val borderColor: Color = ThemeDefaults.borderColor,
    val primaryColor: Color = ThemeDefaults.primaryColor,
    val secondaryColor: Color = ThemeDefaults.secondaryColor,
    val shouldOverride: Boolean = true,
    val externalTheme: ExternalTheme = ExternalTheme.NOT_SET
)