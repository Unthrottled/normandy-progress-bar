package io.acari.n7.theme

import java.lang.IllegalArgumentException

data class ThemeChangedInformation(val accentColor: String, val contrastColor: String, val externalTheme: ExternalTheme)

data class AccentChangedInformation(val accentColor: String, val externalTheme: ExternalTheme)

enum class ExternalTheme(val displayName: String){
  MATERIAL_UI("Material UI"), DOKI_DOKI("Doki-Doki Theme"), NOT_SET("LUL DUNNO");

  companion object {
    fun byName(name: String) = try{
      valueOf(name)
    } catch (illegalArgumentException: IllegalArgumentException){
      NOT_SET
    }
  }
}