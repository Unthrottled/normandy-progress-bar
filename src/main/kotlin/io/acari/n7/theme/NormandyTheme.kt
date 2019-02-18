package io.acari.n7.theme

import com.intellij.ui.ColorUtil
import com.intellij.ui.Gray
import io.acari.n7.config.NormandyConfigPersistence
import io.acari.n7.config.NormandyConfiguration
import java.awt.Color

object NormandyTheme {
  val jetWashColor = { _: Color -> ColorUtil.fromHex(NormandyConfiguration.jetWashColor.orElse("#d6f5f8")) }
  val outerJetWashColor = { _: Color -> ColorUtil.fromHex("#d6f5f8") }
  val backgroundColorFunction = { backgroundColor: Color -> backgroundColor }

  val colors = arrayOf(outerJetWashColor, jetWashColor,
      outerJetWashColor, jetWashColor,
      outerJetWashColor, backgroundColorFunction, backgroundColorFunction, backgroundColorFunction, outerJetWashColor,
      jetWashColor, outerJetWashColor,
      jetWashColor, outerJetWashColor)
  val SCALING_FACTOR = 1.0f / colors.size
  val jetWashScales = colors.mapIndexed { index, _ -> SCALING_FACTOR * (index + 1) }
      .toFloatArray()


  fun borderColor(): Color = NormandyConfiguration.borderColor
      .map(ColorUtil::fromHex)
      .orElseGet { Gray._240 }

  fun primaryColorString(): String =
      NormandyConfiguration.primaryThemeColor
          .orElseGet { ColorUtil.toHex(Color.CYAN) }

  fun secondaryColorString(): String =
      NormandyConfiguration.secondaryThemeColor
          .orElseGet { ColorUtil.toHex(Color.PINK) }
}