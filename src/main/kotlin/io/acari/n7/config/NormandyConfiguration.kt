package io.acari.n7.config

import io.acari.n7.theme.ExternalThemeIntegrations
import java.util.*

object NormandyConfiguration {

  val primaryThemeColor: Optional<String>
    get() {
      return getChanges({ it.primaryThemeColor }) { Optional.empty() }
    }

  val secondaryThemeColor: Optional<String>
    get() {
      return getChanges({ it.secondaryThemeColor }) { ExternalThemeIntegrations.secondaryThemeColor }
    }

  val borderColor: Optional<String>
    get() {
      return getChanges({ it.borderColor }) { Optional.empty() }
    }

  val jetWashColor: Optional<String>
    get() {
      return getChanges({ it.jetWashColor }) { ExternalThemeIntegrations.jetWashColor }
    }

  private fun <T> getChanges(userConfiguration: (NormandyConfigPersistence) -> T, externalConfiguration: () -> Optional<T>): Optional<T> =
      NormandyConfigPersistence.instance
          .flatMap {
            if (it.isAllowedToBeOverridden) externalConfiguration().map { it.toOptional() }
                .orElseGet { userConfiguration(it).toOptional() }
            else userConfiguration(it).toOptional()
          }

}