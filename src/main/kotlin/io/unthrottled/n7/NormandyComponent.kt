package io.unthrottled.n7

import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.DumbAware
import com.intellij.util.messages.MessageBusConnection
import io.unthrottled.n7.config.ConfigurationPersistence
import io.unthrottled.n7.ui.NormandyUI
import java.util.UUID
import javax.swing.UIManager

class NormandyComponent : Disposable, DumbAware {

  private val messageBus: MessageBusConnection = ApplicationManager.getApplication().messageBus.connect()

  init {
    registerUser()

    makeNormandyAsProgressBar()
    messageBus.subscribe(LafManagerListener.TOPIC, LafManagerListener {
      makeNormandyAsProgressBar()
    })
  }

  private fun registerUser() {
    ConfigurationPersistence.instance
      .filter { it.userId.isEmpty() }
      .ifPresent {
        it.userId = UUID.randomUUID().toString()
      }
  }

  private fun makeNormandyAsProgressBar() {
    UIManager.put("ProgressBarUI", NormandyUI::class.java.name)
    UIManager.getDefaults()[NormandyUI::class.java.name] = NormandyUI::class.java
  }

  override fun dispose() {
    messageBus.disconnect()
  }
}