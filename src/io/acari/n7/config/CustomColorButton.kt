package io.acari.n7.config

import com.intellij.ui.ColorChooser
import com.intellij.ui.ColorUtil
import com.intellij.ui.JBColor
import com.intellij.ui.tabs.ColorButtonBase
import com.intellij.util.ui.UIUtil
import java.awt.Color
import java.awt.event.ActionEvent
import javax.swing.plaf.ButtonUI

class CustomColorButton constructor() : ColorButton(ColorSelectionComponent.CUSTOM_COLOR_NAME, JBColor.WHITE) {

  val color: Color = ColorUtil.fromHex("#FFFFFF")
//    get() = if (myColor == null) JBColor.WHITE else myColor

  init {
    myColor
  }

  override fun doPerformAction(e: ActionEvent) {
    val color = ColorChooser.chooseColor(this, "Choose Color", myColor)
    if (color != null) {
      myColor = color
    }
    isSelected = myColor != null
    stateChanged()
  }


  override fun createUI(): ButtonUI {
    return object : ColorButtonUI() {
      override fun getUnfocusedBorderColor(button: ColorButtonBase): Color? {
        return if (UIUtil.isUnderDarcula()) JBColor.GRAY else super.getUnfocusedBorderColor(button)
      }
    }
  }

  override fun getForeground(): Color {
    return if (getModel().isSelected) JBColor.BLACK else JBColor.GRAY
  }
}