package io.acari.n7.integration

import com.intellij.compiler.server.CustomBuilderMessageHandler
import com.intellij.ide.AppLifecycleListener
import com.intellij.ide.ui.LafManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.progress.util.ProgressWindow
import com.intellij.util.SVGLoader
import com.intellij.util.messages.MessageBusConnection
import io.acari.n7.config.CONFIGURATION_TOPIC
import io.acari.n7.config.NormandyConfigurationSubscriber
import io.acari.n7.icon.NormandyColorPatcher
import io.acari.n7.icon.SvgLoaderHacker
import io.acari.n7.util.LegacySupportUtility

class NormandyIntegrationComponent : BaseComponent {

  companion object {

    private val messageBus: MessageBusConnection = ApplicationManager.getApplication().messageBus.connect()

    init {
      setSVGColorPatcher()
      subscribeToTopics()

    }

    /**
     * Subscribes to a bunch of topics to avoid any other plugin from
     * overriding the svg color patcher. Thankfully the Normandy's color patcher
     * saves any other color patcher an applies it as well, successfully preserving
     * any other plugin theme behaviour with a custom color patcher. Current there
     * can only be one color patcher at a time, I made it to where their can be more than one.
     *
     */
    private fun subscribeToTopics() {
      messageBus.subscribe(CONFIGURATION_TOPIC, NormandyConfigurationSubscriber { setSVGColorPatcher() })

      messageBus.subscribe(ProgressWindow.TOPIC, ProgressWindow.Listener { setSVGColorPatcher() })

    LegacySupportUtility.invokeClassSafely(OVERRIDE_CLASS) {
      //Only available for Intellij, should contribute code to allow for all.
      messageBus.subscribe(CustomBuilderMessageHandler.TOPIC, ExternalThemeListener { setSVGColorPatcher() })
    }

      messageBus.subscribe(AppLifecycleListener.TOPIC, AppLifecycleSubscriber { setSVGColorPatcher() })
    }


    /**
     * Enables the ability to color the Normandy and Alliance Icon.
     */
    private fun setSVGColorPatcher() {
      SVGLoader.setColorPatcher(SvgLoaderHacker.collectOtherPatcher()
          .map { NormandyColorPatcher(it) }
          .orElseGet { NormandyColorPatcher() })
    }

  }

  override fun initComponent() {
    setSVGColorPatcher()
    LafManager.getInstance().addLafManagerListener { setSVGColorPatcher() }
  }

  override fun disposeComponent() {
    messageBus.disconnect()
  }
}

