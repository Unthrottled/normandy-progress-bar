package io.acari.n7.config.ui

import com.intellij.ide.ui.UISettings
import com.intellij.openapi.Disposable
import com.intellij.ui.IdeBorderFactory
import com.intellij.util.ui.JBUI

import javax.swing.*
import java.awt.*
import java.util.ArrayList

/**
 * @author spleaner
 * @author Konstantin Bulenkov
 */
class ContrailColorsConfigurablePanel : JPanel(), Disposable {
  private val myLocalTable: FileColorSettingsTable

  val isModified: Boolean
    get() = myLocalTable.isModified

  init {
    layout = BorderLayout()

    val topPanel = JPanel()
    topPanel.layout = BoxLayout(topPanel, BoxLayout.X_AXIS)

    add(topPanel, BorderLayout.NORTH)

    val mainPanel = JPanel(GridLayout(2, 1))
    mainPanel.preferredSize = JBUI.size(300, 500)
    mainPanel.border = BorderFactory.createEmptyBorder(5, 5, 0, 0)

    val localConfigurations = mutableListOf(ContrailColorConfiguration("#FFFFFF", "#FFFFFF"))
    myLocalTable = object : FileColorSettingsTable(localConfigurations) {
      override fun apply(configurations: List<ContrailColorConfiguration>) {
        val copied = ArrayList<ContrailColorConfiguration>()
        try {
          for (configuration in configurations) {
            copied.add(configuration.copy())
          }
        } catch (e: CloneNotSupportedException) {//
        }

//        manager.getModel().setConfigurations(copied, false)
      }
    }


    val localPanel = JPanel(BorderLayout())
    localPanel.border = IdeBorderFactory.createTitledBorder("Local contrailColors", false)
    mainPanel.add(localPanel)


    myLocalTable.getEmptyText().setText("No local contrailColors")
  }

  override fun dispose() {}

  fun apply() {
    myLocalTable.apply()
    UISettings.instance.fireUISettingsChanged()
  }

  fun reset() {
    if (myLocalTable.isModified) myLocalTable.reset()
  }
}
