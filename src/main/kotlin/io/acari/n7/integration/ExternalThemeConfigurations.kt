package io.acari.n7.integration

import io.acari.n7.config.ConfigurationPersistence
import io.acari.n7.config.NOT_SET
import java.util.*

object ExternalThemeConfigurations {
  val secondaryThemeColor: Optional<String>
    get() = getExternalThemeFromConfig { it.externalSecondaryColor }

  val contrailColor: Optional<String>
    get() = getExternalThemeFromConfig { it.externalContrailColor }

  val isEnabled: Boolean
    get() = false

  private fun getExternalThemeFromConfig(valueExtractor3000: (ConfigurationPersistence) -> String): Optional<String> =
      ConfigurationPersistence.instance.filter { it.externalThemeSet != NOT_SET }
          .filter { it.useThemeAccent }
          .map(valueExtractor3000)
          .filter { it != NOT_SET }
}