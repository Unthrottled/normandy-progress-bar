package io.acari.n7.integration

import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Ref

class AppLifecycleSubscriber(private val fundy: () -> Unit) : AppLifecycleListener {

  override fun appFrameCreated(commandLineArgs: Array<out String>?, willOpenProject: Ref<Boolean>) = fundy()

  override fun appStarting(projectFromCommandLine: Project?) = fundy()
}