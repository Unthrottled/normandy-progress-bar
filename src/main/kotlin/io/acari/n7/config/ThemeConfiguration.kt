package io.acari.n7.config

import io.acari.n7.theme.ExternalThemeConfigurations
import java.util.*

object ThemeConfiguration {

  val primaryThemeColor: Optional<String>
    get() {
      return getChanges({ it.primaryThemeColor }) { Optional.empty() }
    }

  val secondaryThemeColor: Optional<String>
    get() {
      return getChanges({ it.secondaryThemeColor }) { ExternalThemeConfigurations.secondaryThemeColor }
    }

  val contrailColor: Optional<String>
    get() {
      return getChanges({ it.contrailColor }) { ExternalThemeConfigurations.contrailColor }
    }

  private fun <T> getChanges(userConfiguration: (ConfigurationPersistence) -> T, externalConfiguration: () -> Optional<T>): Optional<T> =
      ConfigurationPersistence.instance
          .flatMap {
            if (it.isAllowedToBeOverridden) externalConfiguration()
                .map { it.toOptional() }
                .orElseGet { userConfiguration(it).toOptional() }
            else userConfiguration(it).toOptional()
          }

}