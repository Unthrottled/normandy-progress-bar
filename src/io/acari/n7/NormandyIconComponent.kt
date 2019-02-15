package io.acari.n7

import com.intellij.ide.ui.LafManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.util.IconLoader
import com.intellij.util.SVGLoader
import com.intellij.util.messages.MessageBusConnection
import io.acari.n7.config.CONFIGURATION_TOPIC
import io.acari.n7.config.NormandyConfigurationSubcriber

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

  lateinit var messageBus: MessageBusConnection

  override fun initComponent() {
    setColorPatcher()
    messageBus = ApplicationManager.getApplication().messageBus.connect()

    //todo: listen for the project initializiation
    messageBus.subscribe(CONFIGURATION_TOPIC, NormandyConfigurationSubcriber {
      setColorPatcher()
    })

    LafManager.getInstance().addLafManagerListener {
      setColorPatcher()
    }
  }

  override fun disposeComponent() {
    if (this::messageBus.isInitialized) {
      messageBus.disconnect()
    }
  }
}


