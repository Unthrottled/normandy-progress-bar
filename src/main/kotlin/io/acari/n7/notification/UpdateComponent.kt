package io.acari.n7.notification

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project

import io.acari.n7.config.ConfigurationPersistence

const val VERSION = "v1.4.1"
val UPDATE_MESSAGE =
    """
      What's New?<br>
      <ul>
      <li>Better 2019.2 build support!</li>
      <br>
      Thanks again for downloading <b>Normandy Progress Bar UI</b>! •‿•<br>
          <br>See <a href="https://github.com/cyclic-reference/normandy-progress-bar/blob/master/docs/CHANGELOG.md">Changelog</a> for more details.
        """.trimIndent()

class UpdateComponent(private val project: Project) : ProjectComponent {

  override fun projectOpened() {
    ConfigurationPersistence.instance
        .filter { it.version != VERSION }
        .ifPresent { config ->
          config.version = VERSION
          NotificationService.showUserUpdates(project)
        }
  }
}

