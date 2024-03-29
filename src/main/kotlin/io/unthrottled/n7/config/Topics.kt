package io.unthrottled.n7.config

import com.intellij.util.messages.Topic
import io.unthrottled.n7.theme.ThemeConfigurations

val CONFIGURATION_TOPIC: Topic<NormandyConfigurationListener> = Topic.create("Normandy Progress Bar UI Configuration Changed", NormandyConfigurationListener::class.java)

interface NormandyConfigurationListener : BaseListener<ThemeConfigurations>

class NormandyConfigurationSubscriber(consumer: (ThemeConfigurations) -> Unit) :
  NormandyConfigurationListener, ASubscriber<ThemeConfigurations>(consumer)

interface BaseListener<T> {
  fun consumeChanges(delta: T)
}

open class ASubscriber<T>(private val consumer: (T) -> Unit) : BaseListener<T> {
  override fun consumeChanges(delta: T) = consumer(delta)
}
