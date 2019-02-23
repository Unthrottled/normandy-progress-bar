package io.acari.n7.theme

data class ThemeChangedInformation(val isDark: Boolean, val accentColor: String, val contrastColor: String, val foregroundColor: String, val externalTheme: ExternalTheme)

data class AccentChangedInformation(val accentColor: String, val externalTheme: ExternalTheme)

enum class ExternalTheme(val displayName: String) {
  MATERIAL_UI("Material UI"), DOKI_DOKI("Doki-Doki Theme"), NOT_SET("LUL DUNNO");

  companion object {
    fun byName(name: String) = try {
      valueOf(name)
    } catch (illegalArgumentException: IllegalArgumentException) {
      NOT_SET
    }
  }
}