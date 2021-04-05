package io.unthrottled.n7.config.ui

import com.intellij.openapi.application.ApplicationManager
import io.unthrottled.n7.config.CONFIGURATION_TOPIC
import io.unthrottled.n7.config.ConfigurationPersistence
import io.unthrottled.n7.config.ExternalTheme
import io.unthrottled.n7.theme.ThemeConfigurations
import io.unthrottled.n7.util.toColor
import io.unthrottled.n7.util.toHexString

object ConfigurationManager {

  fun applyConfigurations(config: ThemeConfigurations, onApply: () -> Unit) {
    ConfigurationPersistence.instance
      .ifPresent {
        it.useThemeAccent = config.shouldUseThemeAccents
        it.contrailColor = config.contrail.toHexString()
        it.primaryThemeColor = config.primaryColor.toHexString()
        it.secondaryThemeColor = config.secondaryColor.toHexString()
        it.backgroundColor = config.backgroundColor.toHexString()
        it.isCustomBackground = config.isCustomBackground
        it.isRainbowMode = config.isRainbowMode
        it.isTransparentBackground = config.isTransparentBackground
        ApplicationManager.getApplication().messageBus
          .syncPublisher(CONFIGURATION_TOPIC)
          .consumeChanges(config)
        onApply()
      }
  }

  fun configToThemeConfig(it: ConfigurationPersistence): ThemeConfigurations {
    return ThemeConfigurations(
      it.contrailColor.toColor(),
      it.primaryThemeColor.toColor(),
      it.secondaryThemeColor.toColor(),
      it.backgroundColor.toColor(),
      it.isCustomBackground,
      it.isRainbowMode,
      it.isTransparentBackground,
      it.useThemeAccent,
      ExternalTheme.byName(it.externalThemeSet)
    )
  }
}
