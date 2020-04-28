package io.acari.n7.theme

import com.intellij.ui.ColorUtil
import java.awt.Color

object NormandyTheme {
  val RED_COLOR_STRING = "F60000"
  val RED = ColorUtil.fromHex(RED_COLOR_STRING)
  val ORANGE_COLOR_STRING = "FF8C00"
  val ORANGE = ColorUtil.fromHex(ORANGE_COLOR_STRING)
  val YELLOW_COLOR_STRING = "FFEE00"
  val YELLOW = ColorUtil.fromHex(YELLOW_COLOR_STRING)
  val GREEN_COLOR_STRING = "4DE94C"
  val GREEN = ColorUtil.fromHex(GREEN_COLOR_STRING)
  val BLUE_COLOR_STRING = "3783FF"
  val BLUE = ColorUtil.fromHex(BLUE_COLOR_STRING)
  val INDIGO_COLOR_STRING = "4815AA"
  val INDIGO = ColorUtil.fromHex(INDIGO_COLOR_STRING)
  val VIOLET_COLOR_STRING = "901DAA"
  val VIOLET = ColorUtil.fromHex(VIOLET_COLOR_STRING)

  private val pastelRainbow = arrayOf({ _: Color -> ColorUtil.fromHex("FF9AA2") },
      { ColorUtil.fromHex("FFB7B2") },
      { ColorUtil.fromHex("FFDAC1") },
      { ColorUtil.fromHex("E2F0CB") },
      { ColorUtil.fromHex("B5EAD7") },
      { ColorUtil.fromHex("C7CEEA") })

  private val contrailColor = { _: Color -> ColorUtil.fromHex(ThemeConfiguration.contrailColor) }
  private val outerContrailColor = { backgroundColor: Color -> backgroundColor }
  private val backgroundColorFunction = { backgroundColor: Color -> backgroundColor }

  val contrailColors: Array<(Color) -> Color>
    get() = if (ThemeConfiguration.isRainbowMode) {
      arrayOf(
          { _: Color -> RED },
          { ORANGE },
          { YELLOW },
          { GREEN },
          { BLUE },
          { INDIGO },
          { VIOLET }
      )
    } else {
      arrayOf(contrailColor, outerContrailColor,
          outerContrailColor, contrailColor,
          outerContrailColor, backgroundColorFunction, backgroundColorFunction, backgroundColorFunction, outerContrailColor,
          contrailColor, outerContrailColor,
          contrailColor, outerContrailColor, outerContrailColor, contrailColor)
    }

  val contrailScales: FloatArray
    get() {
      val contrailScalingFactor = 1.0f / contrailColors.size
      return contrailColors
          .mapIndexed { index, _ -> contrailScalingFactor * (index + 1) }
          .toFloatArray()
    }

  fun primaryColorString(): String =
      ThemeConfiguration.primaryThemeColor

  fun secondaryColorString(): String =
      ThemeConfiguration.secondaryThemeColor
}