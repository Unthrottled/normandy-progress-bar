package io.acari.n7.config

import javax.swing.plaf.ColorUIResource

/**
 * Default colors for Custom theme
 */
enum class ThemeDefaults {
  ;

  //    todo: should probably make these actually what they are
  companion object {
    val secondaryColor = ColorUIResource(0x000000)
    val primaryColor = ColorUIResource(0xFFFFFF)
    val borderColor = ColorUIResource(0xEFEFEF)// todo: should be something like set foreground
  }
}