package io.acari.n7.config.ui

import com.intellij.ui.ColorPanel
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import io.acari.n7.config.ExternalTheme
import io.acari.n7.integration.ExternalThemeConfigurations
import io.acari.n7.theme.ThemeConfigurations
import io.acari.n7.theme.ThemeDefaults
import java.awt.Color
import java.awt.Insets
import javax.swing.*
import javax.swing.border.EtchedBorder
import javax.swing.border.TitledBorder

/**
 * Most of is code converted from an automatic form generation engine, so pls forgive.
 */
class NormandyForm(private val themeConfigurations: ThemeConfigurations) {

  private lateinit var content: JPanel
  private lateinit var contrailColorLabel: JLabel
  private lateinit var contrailColor: ColorPanel
  private lateinit var primaryColorLabel: JLabel
  private lateinit var primaryColor: ColorPanel
  private lateinit var secondaryColorLabel: JLabel
  private lateinit var externalThemeLabel: JLabel
  private lateinit var secondaryColor: ColorPanel
  private lateinit var resetTabDefaultsBtn: JButton
  private lateinit var shouldOverrideCheckbox: JCheckBox
  private lateinit var rainbowModeCheckbox: JCheckBox


  fun isModified(): Boolean =
      themeConfigurations.shouldOverride != shouldOverride ||
          themeConfigurations.contrail != getContrailColor() ||
          themeConfigurations.primaryColor != getPrimaryColor() ||
          themeConfigurations.isRainbowMode != isRainbowMode ||
          themeConfigurations.secondaryColor != getSecondaryColor()

  var shouldOverride: Boolean
    get() = shouldOverrideCheckbox.isSelected
    private set(shouldOverride) {
      shouldOverrideCheckbox.isSelected = shouldOverride
    }

  var isRainbowMode: Boolean
    get() = rainbowModeCheckbox.isSelected
    private set(shouldOverride) {
      rainbowModeCheckbox.isSelected = shouldOverride
    }

  init {
    initComponents()
    loadConfigs(themeConfigurations)

    // Reset tab defaults
    resetTabDefaultsBtn.addActionListener {
      setSecondary(ThemeDefaults.secondaryColor)
      setPrimaryColor(ThemeDefaults.primaryColor)
      setContrailColor(ThemeDefaults.contrailColor)
    }
  }

  private fun loadConfigs(themeConfigurations: ThemeConfigurations) {
    setPrimaryColor(themeConfigurations.primaryColor)
    setSecondary(themeConfigurations.secondaryColor)
    setContrailColor(themeConfigurations.contrail)
    shouldOverride = themeConfigurations.shouldOverride
    isRainbowMode = themeConfigurations.isRainbowMode
  }

  fun getContent(): JComponent? {
    return content
  }

  fun getContrailColor(): Color? {
    return contrailColor.selectedColor
  }

  private fun setContrailColor(contrailColor: Color) {
    this.contrailColor.selectedColor = contrailColor
  }

  fun getPrimaryColor(): Color? {
    return primaryColor.selectedColor
  }

  fun getSecondaryColor(): Color? {
    return secondaryColor.selectedColor
  }

  private fun setPrimaryColor(primaryColor: Color) {
    this.primaryColor.selectedColor = primaryColor
  }

  private fun setSecondary(primaryColor: Color) {
    this.secondaryColor.selectedColor = primaryColor
  }


  private fun initComponents() {
    content = JPanel()
    val panel1 = JPanel()
    contrailColorLabel = JLabel()
    contrailColor = ColorPanel()
    primaryColorLabel = JLabel()
    primaryColor = ColorPanel()
    secondaryColorLabel = JLabel()
    externalThemeLabel = JLabel()
    secondaryColor = ColorPanel()
    resetTabDefaultsBtn = JButton()
    shouldOverrideCheckbox = JCheckBox()
    rainbowModeCheckbox = JCheckBox()


    //======== content ========
    run {
      content.autoscrolls = true
      content.isRequestFocusEnabled = false
      content.verifyInputWhenFocusTarget = false
      content.border = null
      content.layout = GridLayoutManager(3, 1, Insets(0, 0, 0, 0), -1, -1)

      //======== panel1 ========
      run {
        panel1.border = TitledBorder(EtchedBorder(), "Normandy Theme Customization")
        panel1.layout = GridLayoutManager(18, 2, Insets(0, 3, 0, 0), -1, -1)

        //---- primaryColorLabel ----
        primaryColorLabel.text = "Primary Color"
        primaryColorLabel.toolTipText = "The upper half of the Normandy"
        panel1.add(primaryColorLabel, GridConstraints(3, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
        panel1.add(primaryColor, GridConstraints(3, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- secondaryColor ----
        secondaryColorLabel.text = "Secondary Color"
        secondaryColorLabel.toolTipText = "The lower half of the Normandy."
        panel1.add(secondaryColorLabel, GridConstraints(4, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
        panel1.add(secondaryColor, GridConstraints(4, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- contrailColorLabel ----
        contrailColorLabel.text = "Contrail Color"
        contrailColorLabel.toolTipText = "Choose the color of Normandy's contrail."
        panel1.add(contrailColorLabel, GridConstraints(5, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
        panel1.add(contrailColor, GridConstraints(5, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- shouldOverrideCheckbox ----
        shouldOverrideCheckbox.text = "Allow Theme Override"
        shouldOverrideCheckbox.toolTipText = "Allow other application to override your theme."

        val overrideEnabled = ExternalThemeConfigurations.isEnabled
        if (overrideEnabled) {
          shouldOverrideCheckbox.addItemListener {
            removeOldMessage(panel1)
            displayOverride(it.stateChange == 1, panel1)
          }
        } else {
          shouldOverrideCheckbox.isEnabled = overrideEnabled
          createMessage("Theme override not available on this IDE, yet.", panel1)
        }

        panel1.add(shouldOverrideCheckbox, GridConstraints(7, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- rainbowModeCheckbox ----
        rainbowModeCheckbox.text = "Rainbow Mode"
        rainbowModeCheckbox.toolTipText = "Enhanced RGB experience"

        panel1.add(rainbowModeCheckbox, GridConstraints(9, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
      }
      content.add(panel1, GridConstraints(0, 0, 1, 1,
          GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
          GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

      //---- resetTabDefaultsBtn ----
      resetTabDefaultsBtn.text = "Restore Defaults"
      resetTabDefaultsBtn.toolTipText = "Restore Defaults"
      panel1.add(resetTabDefaultsBtn, GridConstraints(17, 0, 1, 1,
          GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
          GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
    }
  }

  private fun displayOverride(shouldShow: Boolean, panel1: JPanel) {
    if (themeConfigurations.externalTheme != ExternalTheme.NOT_SET
        && shouldShow) {
      val s = "${themeConfigurations.externalTheme.displayName} is set to override!"
      createMessage(s, panel1)
    }
  }

  private fun createMessage(message: String, panel1: JPanel) {
    externalThemeLabel.text = message
    externalThemeLabel.toolTipText = "The lower half of the Normandy."
    panel1.add(externalThemeLabel, GridConstraints(8, 1, 1, 1,
        GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
        GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
  }

  private fun removeOldMessage(panel1: JPanel) = try {
    panel1.remove(8)
  } catch (e: Throwable) {
  }

}

