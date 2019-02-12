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
import javax.swing.plaf.ColorUIResource
import java.awt.*

class NormandyForm {

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  // Generated using JFormDesigner non-commercial license
  private var content: JPanel? = null
  private var explLabel: JLabel? = null
  private var expTextArea: JTextArea? = null
  private var separator1: JSeparator? = null
  private var backgroundColorLabel: JLabel? = null
  private var backgroundColor: ColorPanel? = null
  private var foregroundColorLabel: JLabel? = null
  private var foregroundColor: ColorPanel? = null
  private var labelColorLabel: JLabel? = null
  private var labelColor: ColorPanel? = null
  private var selectionBackgroundColorLabel: JLabel? = null
  private var selectionBackgroundColor: ColorPanel? = null
  private var resetTabDefaultsBtn: JButton? = null
  private var isContrastModeCheckbox: JCheckBox? = null

  var textColor: Color?
    get() = labelColor!!.selectedColor
    private set(labelColor) {
      this.labelColor!!.selectedColor = labelColor
    }

  var isContrastMode: Boolean
    get() = isContrastModeCheckbox!!.isSelected
    private set(isContrastMode) {
      isContrastModeCheckbox!!.isSelected = isContrastMode
    }

  init {
    initComponents()
    loadConfigs()

    // Reset tab defaults
    resetTabDefaultsBtn!!.addActionListener { e ->
      setSelectionBackgroundColor(MTCustomDefaults.selectionBackgroundColor)
      textColor = MTCustomDefaults.textColor
      setForegroundColor(MTCustomDefaults.foregroundColor)
      setBackgroundColor(MTCustomDefaults.backgroundColor)
    }
  }

  private fun loadConfigs() {
    setSelectionBackgroundColor(MTCustomDefaults.selectionBackgroundColor)
    textColor = MTCustomDefaults.textColor
    setForegroundColor(MTCustomDefaults.foregroundColor)
    setBackgroundColor(MTCustomDefaults.backgroundColor)
  }

  fun getContent(): JComponent? {
    return content
  }

  fun getBackgroundColor(): Color? {
    return backgroundColor!!.selectedColor
  }

  private fun setBackgroundColor(backgroundColor: Color) {
    this.backgroundColor!!.selectedColor = backgroundColor
  }

  fun getForegroundColor(): Color? {
    return foregroundColor!!.selectedColor
  }

  private fun setForegroundColor(foregroundColor: Color) {
    this.foregroundColor!!.selectedColor = foregroundColor
  }

  fun getSelectionBackgroundColor(): Color? {
    return selectionBackgroundColor!!.selectedColor
  }

  private fun setSelectionBackgroundColor(selectionBackgroundColor: Color) {
    this.selectionBackgroundColor!!.selectedColor = selectionBackgroundColor
  }

  // JFormDesigner - End of variables declaration  //GEN-END:variables

  private fun initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner non-commercial license
    content = JPanel()
    val panel1 = JPanel()
    explLabel = JLabel()
    expTextArea = JTextArea()
    separator1 = JSeparator()
    backgroundColorLabel = JLabel()
    backgroundColor = ColorPanel()
    foregroundColorLabel = JLabel()
    foregroundColor = ColorPanel()
    labelColorLabel = JLabel()
    labelColor = ColorPanel()
    selectionBackgroundColorLabel = JLabel()
    selectionBackgroundColor = ColorPanel()
    resetTabDefaultsBtn = JButton()
    isContrastModeCheckbox = JCheckBox()


    //======== content ========
    run {
      content!!.autoscrolls = true
      content!!.isRequestFocusEnabled = false
      content!!.verifyInputWhenFocusTarget = false
      content!!.border = null
      content!!.layout = GridLayoutManager(3, 1, Insets(0, 0, 0, 0), -1, -1)

      //======== panel1 ========
      run {
        panel1.border = TitledBorder(EtchedBorder(), "MTForm.customColorsTitle")
        panel1.layout = GridLayoutManager(18, 2, Insets(0, 3, 0, 0), -1, -1)

        //---- explLabel ----
        explLabel!!.text = "MTForm.explLabel.text"
        explLabel!!.foreground = UIManager.getColor("#FFFFFF")
        panel1.add(explLabel!!, GridConstraints(0, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- expTextArea ----
        expTextArea!!.background = UIManager.getColor("Panel.background")
        expTextArea!!.font = UIManager.getFont("Panel.font")
        expTextArea!!.text = "MTForm.expTextArea.text"
        expTextArea!!.rows = 2
        expTextArea!!.wrapStyleWord = true
        expTextArea!!.isEditable = false
        expTextArea!!.border = null
        panel1.add(expTextArea!!, GridConstraints(1, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
        panel1.add(separator1!!, GridConstraints(2, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- backgroundColorLabel ----
        backgroundColorLabel!!.text = "MTColorForm.background"
        backgroundColorLabel!!.toolTipText = "MTCustomThemeForm.backgroundColor.toolTipText"
        panel1.add(backgroundColorLabel!!, GridConstraints(3, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
        panel1.add(backgroundColor!!, GridConstraints(3, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- foregroundColorLabel ----
        foregroundColorLabel!!.text = "MTForm.foregroundColorLabel.text"
        foregroundColorLabel!!.toolTipText = "MTForm.foregroundColorLabel.toolTipText"
        panel1.add(foregroundColorLabel!!, GridConstraints(4, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
        panel1.add(foregroundColor!!, GridConstraints(4, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- labelColorLabel ----
        labelColorLabel!!.text = "MTForm.labelColorLabel.text"
        labelColorLabel!!.toolTipText = "MTForm.labelColorLabel.toolTipText"
        panel1.add(labelColorLabel!!, GridConstraints(5, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
        panel1.add(labelColor!!, GridConstraints(5, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- selectionBackgroundColorLabel ----
        selectionBackgroundColorLabel!!.text = "MTForm.selectionBackgroundColorLabel.text"
        selectionBackgroundColorLabel!!.toolTipText = "MTForm.selectionBackgroundColorLabel.toolTipText"
        panel1.add(selectionBackgroundColorLabel!!, GridConstraints(6, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
        panel1.add(selectionBackgroundColor!!, GridConstraints(6, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

        //---- isContrastModeCheckbox ----
        isContrastModeCheckbox!!.label = "mt.contrast"
        isContrastModeCheckbox!!.text = "mt.contrast"
        isContrastModeCheckbox!!.toolTipText = "mt.contrast.tooltip"
        panel1.add(isContrastModeCheckbox!!, GridConstraints(7, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

      }
      content!!.add(panel1, GridConstraints(0, 0, 1, 1,
          GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
          GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))

      //---- resetTabDefaultsBtn ----
      resetTabDefaultsBtn!!.text = "mt.resetCustomTheme.title"
      resetTabDefaultsBtn!!.toolTipText = "mt.resetdefaults.tooltip"
      panel1.add(resetTabDefaultsBtn!!, GridConstraints(17, 0, 1, 1,
          GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
          GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null))
    }
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }

  /**
   * Default colors for Custom theme
   */
  enum class MTCustomDefaults {
    ;

    companion object {
      val selectionBackgroundColor = ColorUIResource(0x546E7A)
      val textColor = ColorUIResource(0x607D8B)
      val foregroundColor = ColorUIResource(0xB0BEC5)
      val backgroundColor = ColorUIResource(0x263238)
    }
  }

}
