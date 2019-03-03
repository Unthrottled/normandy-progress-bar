package io.acari.n7.theme

import com.intellij.ui.ColorUtil
import java.awt.Color

object NormandyTheme {
  private val contrailColor = { _: Color -> ColorUtil.fromHex(ThemeConfiguration.contrailColor) }
  private val outerContrailColor = { backgroundColor: Color -> backgroundColor }
  private val backgroundColorFunction = { backgroundColor: Color -> backgroundColor }

  val contrailColors = arrayOf(contrailColor, outerContrailColor,
      outerContrailColor, contrailColor,
      outerContrailColor, backgroundColorFunction, backgroundColorFunction, backgroundColorFunction, outerContrailColor,
      contrailColor, outerContrailColor,
      contrailColor, outerContrailColor, outerContrailColor, contrailColor)
  private val CONTRAIL_SCALING_FACTOR = 1.0f / contrailColors.size
  val contrailScales = contrailColors.mapIndexed { index, _ -> CONTRAIL_SCALING_FACTOR * (index + 1) }
      .toFloatArray()

  fun primaryColorString(): String =
      ThemeConfiguration.primaryThemeColor

  fun secondaryColorString(): String =
      ThemeConfiguration.secondaryThemeColor
}