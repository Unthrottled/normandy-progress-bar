package io.acari.n7

import com.intellij.ide.ui.LafManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.BaseComponent
import com.intellij.util.messages.MessageBusConnection
import io.acari.java.n7.NormandyUIFactory
import javax.swing.UIManager

class NormandyComponent : BaseComponent {

  private val messageBus: MessageBusConnection = ApplicationManager.getApplication().messageBus.connect()

  override fun initComponent() {
    makeNormandyAsProgressBar()
    LafManager.getInstance().addLafManagerListener { makeNormandyAsProgressBar() }
  }

  private fun makeNormandyAsProgressBar() {
    UIManager.put("ProgressBarUI", NormandyUIFactory::class.java.name)
    UIManager.getDefaults()[NormandyUIFactory::class.java.name] = NormandyUIFactory::class.java
  }

  override fun disposeComponent() {
    messageBus.disconnect()
  }
}