package io.acari.n7

import com.google.gson.Gson
import com.intellij.compiler.server.CustomBuilderMessageHandler
import io.acari.n7.theme.AccentDelta
import io.acari.n7.theme.ExternalTheme
import io.acari.n7.theme.ExternalThemeDeltaIntegration
import io.acari.n7.theme.ThemeDeltas

const val DOKI_DOKI = "io.acari.DDLCTheme"
const val MATERIAL_UI = "com.chrisrm.idea.MaterialThemeUI"

class ExternalThemeListener(private val onThemeChange: ()->Unit): CustomBuilderMessageHandler {
  override fun messageReceived(messageId: String, messageType: String, payload: String) {
    when(messageId){
      DOKI_DOKI, MATERIAL_UI -> {
        handleThemedChanged(messageType, payload, getExternalTheme(messageId))
        onThemeChange()
      }
    }
  }

  private fun getExternalTheme(pluginId: String): ExternalTheme =
      when(pluginId){
        DOKI_DOKI -> ExternalTheme.DOKI_DOKI
        MATERIAL_UI -> ExternalTheme.MATERIAL_UI
        else -> ExternalTheme.NOT_SET
      }

  private fun handleThemedChanged(changeType: String, payload: String, externalTheme: ExternalTheme) {
    when(changeType){
      "Theme Changed" -> {
        val delta = Gson().fromJson(payload, ThemeDeltas::class.java)
        ExternalThemeDeltaIntegration.consumeThemeDelta(delta.copy(externalTheme = externalTheme))
      }
      "Accent Changed"-> {
        val delta = Gson().fromJson(payload, AccentDelta::class.java)
        ExternalThemeDeltaIntegration.consumeAccentDelta(delta.copy(externalTheme = externalTheme))
      }
    }
  }
}