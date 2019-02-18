package io.acari.n7.theme

import com.intellij.ui.ColorUtil
import com.intellij.ui.Gray
import io.acari.n7.config.NormandyConfigPersistence
import java.awt.Color

object NormandyTheme {
  val jetWashColor = { _: Color -> ColorUtil.fromHex("#d6f5f8") }
  val outerJetWashColor = { _: Color -> ColorUtil.fromHex("#A47FD8") }
  val backgroundColorFunction = { backgroundColor: Color -> backgroundColor }

  val colors = arrayOf(outerJetWashColor, jetWashColor,
      outerJetWashColor, jetWashColor,
      outerJetWashColor, backgroundColorFunction, backgroundColorFunction, backgroundColorFunction, outerJetWashColor,
      jetWashColor, outerJetWashColor,
      jetWashColor, outerJetWashColor)
  val SCALING_FACTOR = 1.0f / colors.size
  val jetWashScales = colors.mapIndexed { index, _ -> SCALING_FACTOR * (index + 1) }
      .toFloatArray()


  fun borderColor(): Color = NormandyConfigPersistence.instance
      .map { it.borderColor }
      .map(ColorUtil::fromHex)
      .orElseGet { Gray._240 }

  fun primaryColorString(): String =
      NormandyConfigPersistence.instance
          .map { it.primaryThemeColor }
          .orElseGet { ColorUtil.toHex(Color.CYAN) }

  fun secondaryColorString(): String =
      NormandyConfigPersistence.instance
          .map { it.secondaryThemeColor }
          .orElseGet { ColorUtil.toHex(Color.PINK) }
}