package io.acari.n7.config

data class ThemeDeltas(
  val isDark: Boolean?,
  override val accentColor: String?,
  val contrastColor: String?,
  val foregroundColor: String?,
  val treeSelectionBackground: String?,
  val externalTheme: ExternalTheme
) : HasAccent

data class AccentDelta(
  override val accentColor: String?,
  val externalTheme: ExternalTheme
) : HasAccent

interface HasAccent {
  val accentColor: String?
}

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