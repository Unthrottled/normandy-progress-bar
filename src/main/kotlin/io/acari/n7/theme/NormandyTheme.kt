package io.acari.n7.theme

import com.intellij.ui.ColorUtil
import java.awt.Color

object NormandyTheme {
  private val pastelRainbow = arrayOf({_:Color -> ColorUtil.fromHex("FF9AA2")},
      { ColorUtil.fromHex("FFB7B2")},
      { ColorUtil.fromHex("FFDAC1")},
      { ColorUtil.fromHex("E2F0CB")},
      { ColorUtil.fromHex("B5EAD7")},
      { ColorUtil.fromHex("C7CEEA")})

  private val contrailColor = { _: Color -> ColorUtil.fromHex(ThemeConfiguration.contrailColor) }
  private val outerContrailColor = { backgroundColor: Color -> backgroundColor }
  private val backgroundColorFunction = { backgroundColor: Color -> backgroundColor }

  val contrailColors: Array<(Color) -> Color>
    get() = if (ThemeConfiguration.isRainbowMode) {
      arrayOf(
          {_:Color -> ColorUtil.fromHex("F60000")},
          { ColorUtil.fromHex("FF8C00")},
          { ColorUtil.fromHex("FFEE00")},
          { ColorUtil.fromHex("4DE94C")},
          { ColorUtil.fromHex("3783FF")},
          { ColorUtil.fromHex("4815AA")}
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