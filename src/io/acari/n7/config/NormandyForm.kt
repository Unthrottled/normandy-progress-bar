/*
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2018 Chris Magnussen and Elior Boukhobza
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package io.acari.n7.config

import com.intellij.ui.ColorPanel
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager

import javax.swing.*
import javax.swing.border.EtchedBorder
import javax.swing.border.TitledBorder
import java.awt.*

class ThemeConfigurations(
    val borderColor: Color = NormandyThemeDefaults.borderColor,
    val primaryColor: Color = NormandyThemeDefaults.primaryColor,
    val secondaryColor: Color = NormandyThemeDefaults.secondaryColor,
    val shouldOverride: Boolean = true
)

class NormandyForm(themeConfigurations: ThemeConfigurations) {

  private var content: JPanel? = null
  private var borderColorLabel: JLabel? = null
  private var borderColor: ColorPanel? = null
  private var primaryColorLabel: JLabel? = null
  private var primaryColor: ColorPanel? = null
  private var secondaryColorLabel: JLabel? = null
  private var secondaryColor: ColorPanel? = null
  private var resetTabDefaultsBtn: JButton? = null
  private var shouldOverrideCheckbox: JCheckBox? = null

  var textColor: Color?
    get() = secondaryColor!!.selectedColor
    private set(secondaryColor) {
      this.secondaryColor!!.selectedColor = secondaryColor
    }

  var shouldOverride: Boolean
    get() = shouldOverrideCheckbox!!.isSelected
    private set(shouldOverride) {
      shouldOverrideCheckbox!!.isSelected = shouldOverride
    }

  init {
    initComponents()
    loadConfigs(themeConfigurations)

    // Reset tab defaults
    resetTabDefaultsBtn!!.addActionListener { e ->
      setSecondary(NormandyThemeDefaults.secondaryColor)
      setPrimaryColor(NormandyThemeDefaults.primaryColor)
      setBorderColor(NormandyThemeDefaults.borderColor)
    }
  }

  private fun loadConfigs(themeConfigurations: ThemeConfigurations) {
    setPrimaryColor(themeConfigurations.primaryColor)
    setSecondary(themeConfigurations.secondaryColor)
    setBorderColor(themeConfigurations.borderColor)
    shouldOverride = themeConfigurations.shouldOverride
  }

  fun getContent(): JComponent? {
    return content
  }

  fun getBorderColor(): Color? {
    return borderColor!!.selectedColor
  }

  private fun setBorderColor(borderColor: Color) {
    this.borderColor!!.selectedColor = borderColor
  }

  fun getPrimaryColor(): Color? {
    return primaryColor!!.selectedColor
  }

  fun getSecondaryColor(): Color? {
    return secondaryColor!!.selectedColor
  }

  private fun setPrimaryColor(primaryColor: Color) {
    this.primaryColor!!.selectedColor = primaryColor
  }

  private fun setSecondary(primaryColor: Color) {
    this.secondaryColor!!.selectedColor = primaryColor
  }


  // JFormDesigner - End of variables declaration  //GEN-END:variables

  private fun initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner non-commercial license
    content = JPanel()
    val panel1 = JPanel()
    borderColorLabel = JLabel()
    borderColor = ColorPanel()
    primaryColorLabel = JLabel()
    primaryColor = ColorPanel()
    secondaryColorLabel = JLabel()
    secondaryColor = ColorPanel()
    resetTabDefaultsBtn = JButton()
    shouldOverrideCheckbox = JCheckBox()


    //======== content ========
    run {
      content!!.autoscrolls = true
      content!!.isRequestFocusEnabled = false
      content!!.verifyInputWhenFocusTarget = false
      content!!.border = null
      content!!.layout = GridLayoutManager(3, 1, Insets(0, 0, 0, 0), -1, -1)

      //======== panel1 ========
      run {
        panel1.border = TitledBorder(EtchedBorder(), "Normandy Theme Customization")
        panel1.layout = GridLayoutManager(18, 2, Insets(0, 3, 0, 0), -1, -1)

        //---- borderColorLabel ----
        borderColorLabel!!.text = "Border Color"
        borderColorLabel!!.toolTipText = "The progress bar border color."
        panel1.add(borderColorLabel!!, GridConstraints(3, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
        panel1.add(borderColor!!, GridConstraints(3, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- primaryColorLabel ----
        primaryColorLabel!!.text = "Normandy's Primary Color"
        primaryColorLabel!!.toolTipText = "The upper half of the Normandy"
        panel1.add(primaryColorLabel!!, GridConstraints(4, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
        panel1.add(primaryColor!!, GridConstraints(4, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- secondaryColor ----
        secondaryColorLabel!!.text = "Normandy's Secondary Color"
        secondaryColorLabel!!.toolTipText = "The lower half of the Normandy."
        panel1.add(secondaryColorLabel!!, GridConstraints(5, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
        panel1.add(secondaryColor!!, GridConstraints(5, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- shouldOverrideCheckbox ----
        shouldOverrideCheckbox!!.label = "Allow Theme Override"
        shouldOverrideCheckbox!!.text = "Allow Theme Override"
        shouldOverrideCheckbox!!.toolTipText = "Allow other application to override your theme."
        panel1.add(shouldOverrideCheckbox!!, GridConstraints(7, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

      }
      content!!.add(panel1, GridConstraints(0, 0, 1, 1,
          GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
          GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

      //---- resetTabDefaultsBtn ----
      resetTabDefaultsBtn!!.text = "Restore Defaults"
      resetTabDefaultsBtn!!.toolTipText = "Restore Defaults"
      panel1.add(resetTabDefaultsBtn!!, GridConstraints(17, 0, 1, 1,
          GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
          GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
    }
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }

}

