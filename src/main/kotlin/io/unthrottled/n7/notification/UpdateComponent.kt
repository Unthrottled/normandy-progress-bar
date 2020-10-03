package io.unthrottled.n7.notification

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.startup.StartupManager
import io.unthrottled.n7.config.ConfigurationPersistence
import io.unthrottled.n7.util.toOptional
import java.util.Optional

const val PLUGIN_ID = "io.acari.normandy.progress.bar"

val UPDATE_MESSAGE =
  """
      What's New?<br>
      <ul>
        <li>2020.3 Build Support</li>
      </ul>
      <br>
      <br>Please see the <a href="https://github.com/Unthrottled/normandy-progress-bar/blob/master/docs/CHANGELOG.md">Changelog</a> for more details.
      <br>
      Thanks for downloading!
      <br><br>
      <img alt='Thanks for downloading!' src="https://raw.githubusercontent.com/Unthrottled/normandy-progress-bar/master/assets/normandy.gif" width='256'>
       <br><br><br><br><br><br><br><br>
       Thanks!
        """.trimIndent()

class UpdateComponent : Disposable {

  private val connection = ApplicationManager.getApplication().messageBus.connect()

  init {
    connection.subscribe(ProjectManager.TOPIC, object : ProjectManagerListener {
      override fun projectOpened(project: Project) {
        getVersion()
          .flatMap {
            ConfigurationPersistence.instance
              .map { config -> config to it }
          }
          .filter {
            it.first.version != it.second
          }
          .ifPresent { configAndVersion ->
            val (config, currentVersion) = configAndVersion
            config.version = currentVersion
            StartupManager.getInstance(project).runWhenProjectIsInitialized {
              NotificationService.showUserUpdates(project, currentVersion)
            }
          }
      }
    })
  }

  private fun getVersion(): Optional<String> =
    PluginManagerCore.getPlugin(PluginId.getId(PLUGIN_ID))
      .toOptional()
      .map { it.version }

  override fun dispose() {
    connection.dispose()
  }
}
