package io.acari.n7.config

import io.acari.n7.theme.ExternalThemeConfigurations
import io.acari.n7.toOptional
import java.util.*

object ThemeConfiguration {

  val primaryThemeColor: Optional<String>
    get() {
      return getCorrectColor({ it.primaryThemeColor })
      { Optional.empty() }
    }

  val secondaryThemeColor: Optional<String>
    get() {
      return getCorrectColor({ it.secondaryThemeColor })
      { ExternalThemeConfigurations.secondaryThemeColor }
    }

  val contrailColor: Optional<String>
    get() {
      return getCorrectColor({ it.contrailColor })
      { ExternalThemeConfigurations.contrailColor }
    }

  /**
   * If the theme is allowed to be overridden by an external
   * integration, us that color, otherwise fall back and use the
   * Normandy theme default for the current attribute.
   *
   */
  private fun <T> getCorrectColor(userConfiguration: (ConfigurationPersistence) -> T, externalConfiguration: () -> Optional<T>): Optional<T> =
      ConfigurationPersistence.instance
          .flatMap {
            if (it.isAllowedToBeOverridden) externalConfiguration()
                .map { it.toOptional() }
                .orElseGet { userConfiguration(it).toOptional() }
            else userConfiguration(it).toOptional()
          }

}