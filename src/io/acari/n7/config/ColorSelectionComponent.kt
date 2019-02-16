package io.acari.n7.config

import com.intellij.ui.ColorChooser
import com.intellij.ui.ColorUtil
import com.intellij.ui.FileColorManager
import com.intellij.ui.JBColor
import com.intellij.ui.tabs.ColorButtonBase
import com.intellij.util.ObjectUtils
import com.intellij.util.ui.UIUtil

import javax.swing.*
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener
import javax.swing.plaf.ButtonUI
import java.awt.*
import java.awt.event.ActionEvent
import java.util.LinkedHashMap

class ColorSelectionComponent : JPanel(GridLayout(1, 0, 5, 5)) {
  private val myColorToButtonMap = LinkedHashMap<String, CustomColorButton>()
  private val myButtonGroup = ButtonGroup()
  private var myChangeListener: ChangeListener? = null

  val colorNames: Collection<String>
    get() = myColorToButtonMap.keys

  val selectedColorName: String?
    get() {
      for (name in myColorToButtonMap.keys) {
        val button = myColorToButtonMap[name]
        if (!(button?.isSelected() ?: false)) continue
        if (button is CustomColorButton) {
          val color = ColorUtil.toHex(button.color)
          val colorName = ColorUtil.toHex(button.color)
          return colorName ?: color
        }
        return name
      }
      return null
    }

  init {
    isOpaque = false
  }

  fun setChangeListener(changeListener: ChangeListener) {
    myChangeListener = changeListener
  }

  fun setSelectedColor(colorName: String?) {
    val button = myColorToButtonMap[colorName]
    if (button != null) {
      button.isSelected = true
    }
  }

  fun getColorName(color: Color?): String? {
    if (color == null) return null
    for (name in myColorToButtonMap.keys) {
      if (color.rgb == myColorToButtonMap[name]?.color?.rgb) {
        return name
      }
    }
    return null
  }

  fun addCustomColorButton() {
    val customButton = CustomColorButton()
    myButtonGroup.add(customButton)
    add(customButton)
    myColorToButtonMap[customButton.text] = customButton
  }

  fun addColorButton(name: String, color: Color) {
    val colorButton = CustomColorButton()
    myButtonGroup.add(colorButton)
    add(colorButton)
    myColorToButtonMap[name] = colorButton
  }

  fun setCustomButtonColor(color: Color) {
    val button = myColorToButtonMap[CUSTOM_COLOR_NAME] as CustomColorButton
    button.setColor(color)
    button.isSelected = true
    button.repaint()
  }

  fun initDefault(selectedColorName: String?) {
    addCustomColorButton()
    setSelectedColor(selectedColorName)
  }

  private open inner class ColorButton protected constructor(text: String, color: Color) : ColorButtonBase(text, color) {

    override fun doPerformAction(e: ActionEvent) {
      stateChanged()
    }
  }

  fun stateChanged() {
    if (myChangeListener != null) {
      myChangeListener!!.stateChanged(ChangeEvent(this))
    }
  }

  companion object {
    val CUSTOM_COLOR_NAME = "Custom"
  }
}

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
    return object : ColorButtonBase.ColorButtonUI() {
      override fun getUnfocusedBorderColor(button: ColorButtonBase): Color? {
        return if (UIUtil.isUnderDarcula()) JBColor.GRAY else super.getUnfocusedBorderColor(button)
      }
    }
  }

  override fun getForeground(): Color {
    return if (getModel().isSelected) JBColor.BLACK else JBColor.GRAY
  }
}

open class ColorButton protected constructor(text: String, color: Color) : ColorButtonBase(text, color) {

  override fun doPerformAction(e: ActionEvent) {
    stateChanged()
  }
}

fun stateChanged() {
//  if (myChangeListener != null) {
//    myChangeListener.stateChanged(ChangeEvent(this))
//  }
}

