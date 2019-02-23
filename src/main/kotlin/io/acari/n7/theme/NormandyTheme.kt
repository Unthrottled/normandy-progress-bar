package io.acari.n7.theme

import com.intellij.ui.ColorUtil
import com.intellij.ui.Gray
import io.acari.n7.config.ThemeConfiguration
import java.awt.Color

object NormandyTheme {
  val jetWashColor = { _: Color -> ColorUtil.fromHex(ThemeConfiguration.jetWashColor.orElse("#A47FD8")) }
  val outerJetWashColor = { _: Color -> ColorUtil.fromHex("#C0DADD") }
  val backgroundColorFunction = { backgroundColor: Color -> backgroundColor }

  val colors = arrayOf(outerJetWashColor, jetWashColor,
      outerJetWashColor, jetWashColor,
      outerJetWashColor, backgroundColorFunction, backgroundColorFunction, backgroundColorFunction, outerJetWashColor,
      jetWashColor, outerJetWashColor,
      jetWashColor, outerJetWashColor, jetWashColor, outerJetWashColor)
  val SCALING_FACTOR = 1.0f / colors.size
  val jetWashScales = colors.mapIndexed { index, _ -> SCALING_FACTOR * (index + 1) }
      .toFloatArray()

  fun primaryColorString(): String =
      ThemeConfiguration.primaryThemeColor
          .orElseGet { ColorUtil.toHex(Color.CYAN) }

  fun secondaryColorString(): String =
      ThemeConfiguration.secondaryThemeColor
          .orElseGet { ColorUtil.toHex(Color.PINK) }
}