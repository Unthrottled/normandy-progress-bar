package io.acari.n7.config

import com.intellij.icons.AllIcons
import com.intellij.ide.ui.UISettings
import com.intellij.ide.util.scopeChooser.EditScopesDialog
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.MessageType
import com.intellij.ui.AnActionButton
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.ArrayList

/**
 * @author spleaner
 * @author Konstantin Bulenkov
 */
class JetWashColorsConfigurablePanel : JPanel(), Disposable {
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

    val localConfigurations = mutableListOf(JetWashColorConfiguration("#FFFFFF","#FFFFFF"))
    myLocalTable = object : FileColorSettingsTable(localConfigurations) {
      override fun apply(configurations: List<JetWashColorConfiguration>) {
        val copied = ArrayList<JetWashColorConfiguration>()
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
    localPanel.border = IdeBorderFactory.createTitledBorder("Local colors", false)
    mainPanel.add(localPanel)


    myLocalTable.getEmptyText().setText("No local colors")
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
