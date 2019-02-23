package io.acari.n7.theme

import com.intellij.ui.ColorUtil
import io.acari.n7.config.ThemeConfiguration
import java.awt.Color

object NormandyTheme {
  private val jetWashColor = { _: Color -> ColorUtil.fromHex(ThemeConfiguration.contrailColor.orElse("#A47FD8")) }
  private val outerJetWashColor = { backgroundColor: Color -> backgroundColor }
  private val backgroundColorFunction = { backgroundColor: Color -> backgroundColor }

  val colors = arrayOf(outerJetWashColor, jetWashColor,
      outerJetWashColor, jetWashColor,
      outerJetWashColor, backgroundColorFunction, backgroundColorFunction, backgroundColorFunction, outerJetWashColor,
      jetWashColor, outerJetWashColor,
      jetWashColor, outerJetWashColor, jetWashColor, outerJetWashColor)
  private val SCALING_FACTOR = 1.0f / colors.size
  val jetWashScales = colors.mapIndexed { index, _ -> SCALING_FACTOR * (index + 1) }
      .toFloatArray()

  fun primaryColorString(): String =
      ThemeConfiguration.primaryThemeColor
          .orElseGet { ColorUtil.toHex(Color.CYAN) }

  fun secondaryColorString(): String =
      ThemeConfiguration.secondaryThemeColor
          .orElseGet { ColorUtil.toHex(Color.PINK) }
}