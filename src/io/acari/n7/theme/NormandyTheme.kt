package io.acari.n7.theme

import com.intellij.ui.ColorUtil
import com.intellij.ui.Gray
import io.acari.n7.config.NormandyConfig
import java.awt.Color

object NormandyTheme {
  val jetWashColor = { _: Color -> ColorUtil.fromHex("#d6f5f8") }
  val outerJetWashColor = { _: Color -> ColorUtil.fromHex("#a38dbe") }
  val backgroundColorFunction = { backgroundColor: Color -> backgroundColor }

  val colors = arrayOf(outerJetWashColor, jetWashColor,
      outerJetWashColor, jetWashColor,
      outerJetWashColor, backgroundColorFunction, backgroundColorFunction, outerJetWashColor,
      jetWashColor, outerJetWashColor,
      jetWashColor, outerJetWashColor)
  val SCALING_FACTOR = 1.0f / colors.size
  val jetWashScales = colors.mapIndexed { index, _ -> SCALING_FACTOR * index }
      .toFloatArray()


  fun borderColor(): Color = NormandyConfig.getInstance()
      .map { it.borderColor }
      .map(ColorUtil::fromHex)
      .orElseGet { Gray._240 }

  fun primaryColorString(): String =
      NormandyConfig.getInstance()
          .map { it.primaryThemeColor }
          .orElseGet { ColorUtil.toHex(Color.CYAN) }

  fun secondaryColorString(): String =
      NormandyConfig.getInstance()
          .map { it.secondaryThemeColor }
          .orElseGet { ColorUtil.toHex(Color.PINK) }
}