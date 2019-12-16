package io.acari.n7.config.ui

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ModalityState
import com.intellij.ui.ColorPanel
import com.intellij.ui.layout.panel
import com.intellij.util.Alarm
import io.acari.n7.theme.ThemeConfigurations
import io.acari.n7.theme.ThemeDefaults
import java.awt.Color
import javax.swing.*

/**
 * Most of is code converted from an automatic form generation engine, so pls forgive.
 */
class NormandyForm(private val themeConfigurations: ThemeConfigurations): Disposable {

  val myThemeConfigurations = themeConfigurations.copy()
  private val alarm: Alarm = Alarm(this)

  fun isModified(): Boolean =
      myThemeConfigurations != themeConfigurations

  fun getContent(): JComponent? {
    val primaryColor = ColorPanel()
    val secondaryColor = ColorPanel()
    val contrailColor = ColorPanel()
    val progressBar = createLoadingIndicator(true, Color.GREEN, false)!!
    return panel {
      titledRow("Normandy Theme Customization"){
        titledRow("Color Customization") {
          row {
            cell {
              label("Primary Color")
              primaryColor()
            }
          }
          row {
            cell {
              label("Secondary Color")
              secondaryColor()
            }
          }
          row {
            cell {
              label("Contrail Color")
              contrailColor()
            }
          }
        }
      }
      titledRow("Appearance Options"){
        row {
          checkBox("Use Theme Accent")
        }
        row {
          checkBox("Rainbow Mode")
        }
        row {
          checkBox("Transparent Background")
        }
      }
      row {
        button("Restore Defaults"){
          ThemeDefaults.secondaryColor
          ThemeDefaults.primaryColor
          ThemeDefaults.contrailColor
        }
      }
      row {
        progressBar()
      }
    }
  }

  private fun createLoadingIndicator(indeterminate: Boolean, foreground: Color, modeless: Boolean): JComponent? {
    val text = if (indeterminate) "indeterminate" else "determinate"
    val label = JLabel(text)
    val progress = JProgressBar(0, 100)
    progress.isIndeterminate = indeterminate
    progress.value = 0
    progress.foreground = foreground
    if (modeless) {
      progress.putClientProperty("ProgressBar.stripeWidth", 2)
    }
    alarm.addRequest(createRunnable(progress), 200, ModalityState.any())

    val panel = JPanel()
    panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
    panel.add(label)
    panel.add(progress)
    panel.add(Box.createVerticalStrut(5))

    return panel
  }

  private fun createRunnable(progress: JProgressBar): () -> Unit {
    return {
      if (progress.value < progress.maximum) {
        progress.value += 1
        alarm.addRequest(createRunnable(progress), 100)
      }
    }
  }

  override fun dispose() {
    alarm.dispose()
  }
}