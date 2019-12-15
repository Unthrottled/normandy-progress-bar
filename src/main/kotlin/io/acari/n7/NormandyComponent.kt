package io.acari.n7

import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.project.DumbAware
import com.intellij.util.messages.MessageBusConnection
import io.acari.n7.ui.NormandyUI
import javax.swing.UIManager

class NormandyComponent : BaseComponent, DumbAware {

  private val messageBus: MessageBusConnection = ApplicationManager.getApplication().messageBus.connect()

  override fun initComponent() {
    makeNormandyAsProgressBar()
    messageBus.subscribe(LafManagerListener.TOPIC, LafManagerListener {
      makeNormandyAsProgressBar()
    })
  }

  private fun makeNormandyAsProgressBar() {
    UIManager.put("ProgressBarUI", NormandyUI::class.java.name)
    UIManager.getDefaults()[NormandyUI::class.java.name] = NormandyUI::class.java
  }

  override fun disposeComponent() {
    messageBus.disconnect()
  }
}