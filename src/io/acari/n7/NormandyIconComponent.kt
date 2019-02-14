package io.acari.n7

import com.intellij.ide.ui.LafManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.util.IconLoader
import com.intellij.util.SVGLoader
import com.intellij.util.messages.MessageBusConnection
import io.acari.n7.config.CONFIGURATION_TOPIC
import io.acari.n7.config.NormandyConfigurationSubcriber

/**
 * Forged in the flames of battle by alex.
 */
class NormandyIconComponent : BaseComponent {

  companion object {
    private val colorPatcher = NormandyColorPatcher()

    init {
      setColorPatcher()
    }

    fun getNormandyIcon() = IconLoader.getIcon("/normandy.svg")
    fun getNormandyToCitadelIcon() = IconLoader.getIcon("/normandyToCitadel.svg")

    private fun setColorPatcher() {
      SVGLoader.setColorPatcher(colorPatcher)
    }
  }

  lateinit var messageBus: MessageBusConnection

  override fun initComponent() {

    messageBus = ApplicationManager.getApplication().messageBus.connect()
    messageBus.subscribe(CONFIGURATION_TOPIC, NormandyConfigurationSubcriber {
      setColorPatcher()
      //todo: set color patcher back.
    })

    LafManager.getInstance().addLafManagerListener {
      println("theme changed")
    }
  }

  override fun disposeComponent() {
    if (this::messageBus.isInitialized) {
      messageBus.disconnect()
    }
  }
}


