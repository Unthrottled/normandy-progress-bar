package io.unthrottled.n7.notification

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.ProjectManagerListener
import io.unthrottled.n7.config.ConfigurationPersistence
import io.unthrottled.n7.util.toOptional
import java.util.Optional

const val PLUGIN_ID = "io.acari.normandy.progress.bar"

val UPDATE_MESSAGE =
  """
      What's New?<br>
      <ul>
        <li>2020.2 Build Support</li>
      </ul>
      <br>
      Thanks again for downloading <b>Normandy Progress Bar UI</b>! •‿•<br>
          <br>See <a href="https://github.com/Unthrottled/normandy-progress-bar/blob/master/docs/CHANGELOG.md">Changelog</a> for more details.
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
            NotificationService.showUserUpdates(project, currentVersion)
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
