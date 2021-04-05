package io.unthrottled.n7

import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.project.DumbAware
import io.unthrottled.n7.config.ConfigurationPersistence
import io.unthrottled.n7.ui.NormandyUI
import java.util.UUID
import javax.swing.UIManager

class NormandyComponent : LafManagerListener, DumbAware {

  init {
    registerUser()
    makeNormandyAsProgressBar()
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

  override fun lookAndFeelChanged(source: LafManager) {
    makeNormandyAsProgressBar()
  }
}
