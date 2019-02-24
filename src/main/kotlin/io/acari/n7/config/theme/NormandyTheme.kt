package io.acari.n7.config.theme

import com.intellij.ui.ColorUtil
import io.acari.n7.config.ThemeConfiguration
import java.awt.Color

object NormandyTheme {
  private val contrailColor = { _: Color -> ColorUtil.fromHex(ThemeConfiguration.contrailColor.orElse("#A47FD8")) }
  private val outerContrailColor = { backgroundColor: Color -> backgroundColor }
  private val backgroundColorFunction = { backgroundColor: Color -> backgroundColor }

  val contrailColors = arrayOf(outerContrailColor, contrailColor,
      outerContrailColor, contrailColor,
      outerContrailColor, backgroundColorFunction, backgroundColorFunction, backgroundColorFunction, outerContrailColor,
      contrailColor, outerContrailColor,
      contrailColor, outerContrailColor, contrailColor, outerContrailColor)
  private val CONTRAIL_SCALING_FACTOR = 1.0f / contrailColors.size
  val contrailScales = contrailColors.mapIndexed { index, _ -> CONTRAIL_SCALING_FACTOR * (index + 1) }
      .toFloatArray()

  fun primaryColorString(): String =
      ThemeConfiguration.primaryThemeColor
          .orElseGet { ColorUtil.toHex(Color.CYAN) }

  fun secondaryColorString(): String =
      ThemeConfiguration.secondaryThemeColor
          .orElseGet { ColorUtil.toHex(Color.PINK) }
}