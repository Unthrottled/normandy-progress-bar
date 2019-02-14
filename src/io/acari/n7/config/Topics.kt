package io.acari.n7.config

import com.intellij.util.messages.Topic

val CONFIGURATION_TOPIC: Topic<NormandyConfigurationListener> = Topic.create("Normandy Progress Bar UI Configuration Changed", NormandyConfigurationListener::class.java)
val DOKI_DOKI_THEME_TOPIC: Topic<DokiDokiThemeListener> = Topic.create("Theme Changes", DokiDokiThemeListener::class.java)

interface NormandyConfigurationListener : BaseListener<ThemeConfigurations>
class NormandyConfigurationSubcriber(consumer: (ThemeConfigurations) -> Unit) :
    NormandyConfigurationListener, ASubscriber<ThemeConfigurations>(consumer)

interface BaseListener<T> {
  fun consumeChanges(delta: T)
}

interface DokiDokiThemeListener : BaseListener<Any>

class DokiDokiThemeSubcriber(consumer: (Any) -> Unit) :
    DokiDokiThemeListener, ASubscriber<Any>(consumer)

open class ASubscriber<T>(private val consumer: (T) -> Unit) : BaseListener<T> {
  override fun consumeChanges(delta: T) = consumer(delta)
}