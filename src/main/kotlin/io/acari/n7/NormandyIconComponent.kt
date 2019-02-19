package io.acari.n7

import com.google.gson.Gson
import com.intellij.compiler.server.CustomBuilderMessageHandler
import com.intellij.ide.AppLifecycleListener
import com.intellij.ide.ui.LafManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.progress.util.ProgressWindow
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.util.Ref
import com.intellij.util.SVGLoader
import com.intellij.util.messages.MessageBusConnection
import io.acari.n7.config.CONFIGURATION_TOPIC
import io.acari.n7.theme.ExternalThemeIntegrations
import io.acari.n7.config.NormandyConfigurationSubcriber
import io.acari.n7.theme.AccentChangedInformation
import io.acari.n7.theme.ExternalTheme
import io.acari.n7.theme.ThemeChangedInformation

val DOKI_DOKI = "io.acari.DDLCTheme"
val MATERIAL_UI = "com.chrisrm.idea.MaterialThemeUI"

class NormandyIconComponent : BaseComponent {

  companion object {

    init {
      setColorPatcher()
    }

    fun getNormandyIcon() = IconLoader.getIcon("/normandy.svg")
    fun getNormandyToCitadelIcon() = IconLoader.getIcon("/normandyToCitadel.svg")

    private fun setColorPatcher() {
      SVGLoader.setColorPatcher(SvgLoaderHacker.collectOtherPatcher()
          .map { NormandyColorPatcher(it) }
          .orElseGet { NormandyColorPatcher() })
    }
  }

  private lateinit var messageBus: MessageBusConnection

  override fun initComponent() {
    setColorPatcher()
    messageBus = ApplicationManager.getApplication().messageBus.connect()

    messageBus.subscribe(CONFIGURATION_TOPIC, NormandyConfigurationSubcriber {
      setColorPatcher()
    })

    messageBus.subscribe(ProgressWindow.TOPIC, ProgressWindow.Listener {
      setColorPatcher()
    })

    messageBus.subscribe(CustomBuilderMessageHandler.TOPIC, CustomBuilderMessageHandler{
      messageId,messageType,payload->
      when(messageId){
        DOKI_DOKI, MATERIAL_UI -> {
          handleThemedChanged(messageType, payload, getExternalTheme(messageId))
          setColorPatcher()
        }
      }
    })

    messageBus.subscribe(AppLifecycleListener.TOPIC, AppLifecycleSubscriber {
      setColorPatcher()
    })

    LafManager.getInstance().addLafManagerListener {
      setColorPatcher()
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
        val delta = Gson().fromJson(payload, ThemeChangedInformation::class.java)
        ExternalThemeIntegrations.consumeThemeChangedInformation(delta.copy(externalTheme = externalTheme))
      }
      "Accent Changed"-> {
        val delta = Gson().fromJson(payload, AccentChangedInformation::class.java)
        ExternalThemeIntegrations.consumeAccentChangedInformation(delta.copy(externalTheme = externalTheme))
      }
    }

  }

  override fun disposeComponent() {
    if (this::messageBus.isInitialized) {
      messageBus.disconnect()
    }
  }
}

class AppLifecycleSubscriber(private val fundy: () -> Unit) : AppLifecycleListener {

  override fun appFrameCreated(commandLineArgs: Array<out String>?, willOpenProject: Ref<Boolean>) = fundy()

  override fun appStarting(projectFromCommandLine: Project?) = fundy()
}


