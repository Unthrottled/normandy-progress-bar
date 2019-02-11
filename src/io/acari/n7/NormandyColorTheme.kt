package io.acari.n7

import com.intellij.ui.ColorUtil
import com.intellij.ui.Gray
import java.awt.Color

class NormandyColorTheme {
  companion object {
    val DEFAULT_PRIMARY_COLOR: Color = ColorUtil.fromHex("#FFFFFF")
    val DEFAULT_SECONDARY_COLOR: Color = ColorUtil.fromHex("#000000")
    const val DEFAULT_JETWASH_COLOR: String = ""
    val DEFAULT_BORDER_COLOR: Color = Gray._240
  }

}