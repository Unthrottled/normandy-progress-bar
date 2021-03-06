package io.unthrottled.n7.config.ui

import com.intellij.ui.ColorPanel
import com.intellij.ui.layout.panel
import com.intellij.ui.scale.JBUIScale
import io.unthrottled.n7.theme.ThemeConfigurations
import io.unthrottled.n7.theme.ThemeDefaults
import java.awt.Color
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JProgressBar

class NormandyForm(private val themeConfigurations: ThemeConfigurations) {

  val myThemeConfigurations = themeConfigurations.copy()

  fun isModified(): Boolean =
      myThemeConfigurations != themeConfigurations

  fun getContent(): JComponent? {
    val primaryColor = ColorPanel()
    primaryColor.selectedColor = themeConfigurations.primaryColor
    primaryColor.addActionListener {
      myThemeConfigurations.primaryColor = primaryColor.selectedColor!!
      ConfigurationManager.applyConfigurations(myThemeConfigurations) {}
    }

    val secondaryColor = ColorPanel()
    secondaryColor.selectedColor = themeConfigurations.secondaryColor
    secondaryColor.addActionListener {
      myThemeConfigurations.secondaryColor = secondaryColor.selectedColor!!
      ConfigurationManager.applyConfigurations(myThemeConfigurations) {}
    }

    val contrailColor = ColorPanel()
    contrailColor.selectedColor = themeConfigurations.contrail
    contrailColor.addActionListener {
      myThemeConfigurations.contrail = contrailColor.selectedColor!!
      ConfigurationManager.applyConfigurations(myThemeConfigurations) {}
    }

    val customBackgroundColor = ColorPanel()
    customBackgroundColor.selectedColor = themeConfigurations.backgroundColor
    customBackgroundColor.addActionListener {
      myThemeConfigurations.backgroundColor = customBackgroundColor.selectedColor!!
      ConfigurationManager.applyConfigurations(myThemeConfigurations) {}
    }

    val progressBar = createLoadingIndicator(true, Color.GREEN, false)!!
    return panel {
      row {
        progressBar()
      }
      titledRow("Normandy Theme Customization") {
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
          row {
            cell {
              label("Background Color")
              customBackgroundColor()
              checkBox("Enable", myThemeConfigurations.isCustomBackground,
                actionListener = { _, component ->
                  myThemeConfigurations.isCustomBackground = component.isSelected
                  ConfigurationManager.applyConfigurations(myThemeConfigurations) {}
                })
            }
          }
        }
      }
      titledRow("Appearance Options") {
        row {
          checkBox("Use Theme Accent",
              myThemeConfigurations.shouldUseThemeAccents,
              comment = "If your current theme has a concept of an accent, then use that color.",
              actionListener = { _, component ->
                myThemeConfigurations.shouldUseThemeAccents = component.isSelected
                ConfigurationManager.applyConfigurations(myThemeConfigurations) {}
              })
        }
        row {
          checkBox("Rainbow Mode", myThemeConfigurations.isRainbowMode,
              actionListener = { _, component ->
                myThemeConfigurations.isRainbowMode = component.isSelected
                ConfigurationManager.applyConfigurations(myThemeConfigurations) {}
              })
        }
        row {
          checkBox("Transparent Background", myThemeConfigurations.isTransparentBackground,
              actionListener = { _, component ->
                myThemeConfigurations.isTransparentBackground = component.isSelected
                ConfigurationManager.applyConfigurations(myThemeConfigurations) {}
              })
        }
      }
      row {
        button("Restore Defaults") {
          myThemeConfigurations.primaryColor = ThemeDefaults.primaryColor
          myThemeConfigurations.secondaryColor = ThemeDefaults.secondaryColor
          myThemeConfigurations.contrail = ThemeDefaults.contrailColor
          ConfigurationManager.applyConfigurations(myThemeConfigurations) {}
        }
      }
    }
  }

  private fun createLoadingIndicator(indeterminate: Boolean, foreground: Color, modeless: Boolean): JComponent? {
    val progress = JProgressBar(0, 100)
    progress.isIndeterminate = indeterminate
    progress.value = 0
    progress.foreground = foreground
    if (modeless) {
      progress.putClientProperty("ProgressBar.stripeWidth", 2)
    }

    progress.preferredSize = Dimension(500, JBUIScale.scale(25))

    return progress
  }
}