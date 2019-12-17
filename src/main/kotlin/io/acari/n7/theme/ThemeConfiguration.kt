package io.acari.n7.theme

import com.intellij.ui.ColorUtil
import io.acari.n7.config.ConfigurationPersistence
import io.acari.n7.integration.ExternalThemeConfigurations
import io.acari.n7.util.toHexString
import io.acari.n7.util.toOptional
import java.awt.Color
import java.util.*
import javax.swing.UIManager

object ThemeConfiguration {

  val isRainbowMode: Boolean
  get() = ConfigurationPersistence.instance
      .map { it.isRainbowMode }
      .orElseGet { false }

  val isTransparentBackground: Boolean
  get() = ConfigurationPersistence.instance
      .map { it.isTransparentBackground }
      .orElseGet { false }

  val primaryThemeColor: String
    get() {
      return getCorrectColor({ it.primaryThemeColor })
      { Optional.empty() }
          .orElseGet { "#${ColorUtil.toHex(ThemeDefaults.primaryColor)}" }
    }

  val secondaryThemeColor: String
    get() {
      return getCorrectColor({ it.secondaryThemeColor })
      { ExternalThemeConfigurations.secondaryThemeColor }
          .orElseGet { "#${ColorUtil.toHex(ThemeDefaults.secondaryColor)}" }
    }

  val contrailColor: String
    get() {
      return getCorrectColor({ it.contrailColor })
      {
        val defaults = UIManager.getLookAndFeelDefaults()
        val themeAccent = defaults["Doki.Accent.color"]
            ?: defaults["material.tab.borderColor"]
        themeAccent.toOptional()
            .filter { it is Color }
            .map { it as Color }
            .map { it.toHexString() }
      }
          .orElseGet { "#${ColorUtil.toHex(ThemeDefaults.contrailColor)}" }
    }

  /**
   * If the theme is allowed to be overridden by an external
   * integration, us that color, otherwise fall back and use the
   * Normandy theme default for the current attribute.
   *
   */
  private fun <T> getCorrectColor(userConfiguration: (ConfigurationPersistence) -> T,
                                  externalConfiguration: () -> Optional<T>): Optional<T> =
      ConfigurationPersistence.instance
          .flatMap {
            if (it.isAllowedToBeOverridden) externalConfiguration()
                .map { externalConfig -> externalConfig.toOptional() }
                .orElseGet { userConfiguration(it).toOptional() }
            else userConfiguration(it).toOptional()
          }

}