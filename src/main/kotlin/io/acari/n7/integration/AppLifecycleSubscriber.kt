package io.acari.n7.integration

import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.project.Project

class AppLifecycleSubscriber(private val fundy: () -> Unit) : AppLifecycleListener {

  override fun appStarting(projectFromCommandLine: Project?) = fundy()
}