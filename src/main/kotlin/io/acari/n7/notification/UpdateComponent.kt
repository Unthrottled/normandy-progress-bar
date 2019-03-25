package io.acari.n7.notification

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project

import io.acari.n7.config.ConfigurationPersistence

const val VERSION = "v1.2.1"
val UPDATE_MESSAGE =
    """
          <br/>
          Addressed the issue where only the port side of the Normandy had dark outlines.<br>
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

