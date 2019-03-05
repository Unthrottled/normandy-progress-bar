package io.acari.n7.notification

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project

import io.acari.n7.config.ConfigurationPersistence

const val VERSION = "v1.2.0"
val UPDATE_MESSAGE =
    """
          <br/>
          JetBrains 2019 Build Support!<br><br>
          Now supports any JetBrains IDE on the 2019 build! (I know I said I did it last time, I lied...)<br><br>
          Added Notifications for updates.<br><br>
          Material UI theme integration is the next feature to be worked, stay tuned!<br>
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

