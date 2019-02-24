package io.acari.n7.config.ui.wip

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.search.scope.packageSet.NamedScope
import com.intellij.ui.ComboboxSpeedSearch
import com.intellij.ui.tabs.ColorSelectionComponent
import com.intellij.util.ArrayUtil
import com.intellij.util.ui.JBUI
import io.acari.n7.config.ui.wip.ContrailColorConfiguration

import javax.swing.*
import java.awt.*
import java.util.*

class ContrailColorConfigurationEditDialog(configuration: ContrailColorConfiguration?) : DialogWrapper(true) {
  var configuration: ContrailColorConfiguration? = null
    private set
  var scopeComboBox: JComboBox<*>? = null
    private set
  private val myColorSelectionComponent: ColorSelectionComponent

  private val myScopeNames = HashMap<String, NamedScope>()

  private val colorName: String?
    get() = myColorSelectionComponent.selectedColorName

  init {

    title = if (configuration == null) "Add Color Label" else "Edit Color Label"
    setResizable(false)

    this.configuration = configuration
    myColorSelectionComponent = ColorSelectionComponent()
    myColorSelectionComponent.setChangeListener { updateOKButton() }

    init()
    updateCustomButton()
//    if (this.configuration != null && !StringUtil.isEmpty(this.configuration!!.getScopeName())) {
//      scopeComboBox!!.selectedItem = this.configuration!!.getScopeName()
//    }
    updateOKButton()
  }

  override fun createNorthPanel(): JComponent? {
    val scopeList = ArrayList<NamedScope>()
//    val scopeHolders = NamedScopeManager.getAllNamedScopeHolders(project)
//    for (scopeHolder in scopeHolders) {
//      val scopes = scopeHolder.scopes
//      Collections.addAll(scopeList, *scopes)
//    }
//    CustomScopesProviderEx.filterNoSettingsScopes(project, scopeList)
//    for (scope in scopeList) {
//      myScopeNames[scope.name] = scope
//    }

    scopeComboBox = JComboBox(ArrayUtil.toStringArray(myScopeNames.keys))
    scopeComboBox!!.addActionListener {
      updateCustomButton()
      updateOKButton()
    }
    ComboboxSpeedSearch(scopeComboBox!!)

    val pathLabel = JLabel("Scope:")
    pathLabel.setDisplayedMnemonic('S')
    pathLabel.labelFor = scopeComboBox
    val colorLabel = JLabel("Color:")

    val result = JPanel(GridBagLayout())
    val gbc = GridBagConstraints()
    gbc.fill = GridBagConstraints.BOTH
    gbc.insets = JBUI.insets(5)
    gbc.gridx = 0
    result.add(pathLabel, gbc)
    result.add(colorLabel, gbc)
    gbc.gridx = 1
    gbc.weightx = 1.0
    result.add(scopeComboBox!!, gbc)
    result.add(myColorSelectionComponent, gbc)
    return result
  }

  private fun updateCustomButton() {
    val item = scopeComboBox!!.selectedItem
    if (item is String) {
//      var color: Color? = if (configuration == null) null else ColorUtil.fromHex(configuration!!.getColorName(), null)
//      val scope = myScopeNames[item]
//      val colorName = if (scope is FileColorName) (scope as FileColorName).colorName() else null
//
//      if (color == null && StringUtil.isNotEmpty(colorName)) {
//        color = ColorUtil.fromHex(colorName)
//      }
//
//      if (color != null) {
//        if (StringUtil.isNotEmpty(colorName) && color == myManager.getColor(colorName)) {
//          myColorSelectionComponent.setSelectedColor(colorName)
//        } else {
//          myColorSelectionComponent.setCustomButtonColor(color)
//        }
//      }
    }
  }

  override fun doOKAction() {
    close(DialogWrapper.OK_EXIT_CODE)

    if (configuration != null) {
//      configuration!!.setScopeName(scopeComboBox!!.selectedItem as String)
//      configuration!!.setColorName(colorName)
    } else {
//      configuration = ContrailColorConfiguration(scopeComboBox!!.selectedItem as String, colorName)
    }
  }

  override fun getPreferredFocusedComponent(): JComponent? {
    return if (scopeComboBox!!.isEnabled) scopeComboBox else myColorSelectionComponent
  }

  private fun updateOKButton() {
    okAction.isEnabled = isOKActionEnabled
  }

  override fun isOKActionEnabled(): Boolean {
    val scopeName = scopeComboBox!!.selectedItem as String
    return scopeName != null && scopeName.length > 0 && colorName != null
  }

  override fun createCenterPanel(): JComponent? {
    return null
  }
}
