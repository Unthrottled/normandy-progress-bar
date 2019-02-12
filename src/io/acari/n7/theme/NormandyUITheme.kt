package io.acari.n7.theme

import com.intellij.ui.ColorUtil
import com.intellij.ui.Gray
import io.acari.n7.config.NormandyUIConfig

object NormandyUITheme {


  fun borderColor() = NormandyUIConfig.getInstance()
      .map { it.borderColor }
      .map(ColorUtil::fromHex)
      .orElseGet { Gray._240 }
}