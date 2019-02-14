package io.acari.n7.config

import com.intellij.util.messages.Topic

val CONFIGURATION_TOPIC: Topic<NormandyConfigurationListener> = Topic.create("Normandy Progress Bar UI Configuration Changed", NormandyConfigurationListener::class.java)

interface NormandyConfigurationListener {
  fun configurationChanged(themeConfigurations: ThemeConfigurations)
}

class NormandyConfigurationSubscriber(private val consumer: (ThemeConfigurations) -> Unit): NormandyConfigurationListener {
  override fun configurationChanged(themeConfigurations: ThemeConfigurations) = consumer(themeConfigurations)
}