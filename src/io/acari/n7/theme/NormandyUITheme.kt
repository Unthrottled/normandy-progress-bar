package io.acari.n7.theme

import com.intellij.ui.ColorUtil
import com.intellij.ui.Gray
import io.acari.n7.config.NormandyUIConfig
import java.awt.Color

object NormandyUITheme {


  fun borderColor(): Color = NormandyUIConfig.getInstance()
      .map { it.borderColor }
      .map(ColorUtil::fromHex)
      .orElseGet { Gray._240 }

  fun primaryColorString(): String =
      NormandyUIConfig.getInstance()
          .map { it.primaryThemeColor }
          .orElseGet { ColorUtil.toHex(Color.CYAN) }

  fun secondaryColorString(): String =
      NormandyUIConfig.getInstance()
          .map { it.secondaryThemeColor }
          .orElseGet { ColorUtil.toHex(Color.PINK) }
}