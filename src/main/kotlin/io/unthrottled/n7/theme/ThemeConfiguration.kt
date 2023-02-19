package io.unthrottled.n7.theme

import com.intellij.ui.ColorUtil
import io.unthrottled.n7.config.ConfigurationPersistence
import io.unthrottled.n7.integration.ExternalThemeConfigurations
import io.unthrottled.n7.util.toHexString
import io.unthrottled.n7.util.toOptional
import java.awt.Color
import java.util.Optional
import java.util.Optional.empty
import java.util.Optional.of
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

  val isCustomBackground: Boolean
    get() = ConfigurationPersistence.instance
      .map { it.isCustomBackground }
      .orElseGet { false }

  val primaryThemeColor: String
    get() {
      return getCorrectColor({ it.primaryThemeColor }) { empty() }
        .orElseGet { "#${ColorUtil.toHex(ThemeDefaults.primaryColor)}" }
    }

  val secondaryThemeColor: String
    get() {
      return getCorrectColor({ it.secondaryThemeColor }) { ExternalThemeConfigurations.secondaryThemeColor }
        .orElseGet { "#${ColorUtil.toHex(ThemeDefaults.secondaryColor)}" }
    }

  val backgroundColor: String
    get() {
      return getCorrectColor({ it.backgroundColor }) { empty() }
        .orElseGet { "#${ColorUtil.toHex(ThemeDefaults.secondaryColor)}" }
    }

  val contrailColor: String
    get() {
      return getCorrectColor({ it.contrailColor }) {
        val defaults = UIManager.getLookAndFeelDefaults()
        val themeAccent = defaults["Doki.Accent.color"]
          ?: defaults["LiveIndicator.color"]
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
  private fun <T> getCorrectColor(
    userConfiguration: (ConfigurationPersistence) -> T,
    externalConfiguration: () -> Optional<T>
  ): Optional<T> =
    ConfigurationPersistence.instance
      .flatMap {
        if (it.useThemeAccent) {
          externalConfiguration()
            .map { externalConfig -> of(externalConfig) }
            .orElseGet { userConfiguration(it).toOptional() }
        } else {
          userConfiguration(it).toOptional()
        }
      }
}
