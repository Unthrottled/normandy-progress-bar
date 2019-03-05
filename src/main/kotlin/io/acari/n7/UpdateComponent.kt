package io.acari.n7

import com.intellij.ide.BrowserUtil
import com.intellij.notification.Notification
import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.notification.impl.NotificationsManagerImpl
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.BalloonLayoutData
import com.intellij.ui.awt.RelativePoint

import io.acari.n7.config.ConfigurationPersistence
import io.acari.n7.util.toOptional
import java.awt.Point

private const val VERSION = "v1.2.0"

class UpdateComponent(private val project: Project) : ProjectComponent {

  override fun projectOpened() {
    ConfigurationPersistence.instance
        .ifPresent { config ->
          val savedVersion = config.version
          if (savedVersion != VERSION) {
            config.version = VERSION
            NotificationService.showUserUpdates(project)
          }
        }
  }
}

object NotificationService {

  fun showUserUpdates(project: Project) {
    val notification = NotificationGroup(
        "NORMANDY_UPDATE",
        NotificationDisplayType.STICKY_BALLOON,
        true
    ).createNotification(
        "SSV Normandy Progress Bar updated to $VERSION",
        "Thank you for updating",
        """
          <br/>
          JetBrains 2019 Build Support!<br><br>
          Now supports any JetBrains IDE on the 2019 build! (I know I said I did it last time, I lied...)<br><br>
          Added Notifications for updates.<br><br>
          Material UI theme integration is the next feature to be worked, stay tuned!<br>
          <br>See <a href="https://github.com/cyclic-reference/normandy-progress-bar/blob/master/docs/CHANGELOG.md">Changelog</a> for more details.
        """.trimIndent(),
        NotificationType.INFORMATION) { notif, hyperLinkEvent ->
      hyperLinkEvent.url.toOptional()
          .map { { BrowserUtil.browse(it) } }
          .orElseGet { { BrowserUtil.browse(hyperLinkEvent.description) } }()
      notif.expire()
    }
    showNotification(project, notification)
  }

  private fun showNotification(project: Project, notification: Notification) {
    val frame = WindowManager.getInstance().getIdeFrame(project)
    val bounds = frame.component.bounds
    val target = RelativePoint(frame.component, Point(bounds.x + bounds.width, 0))
    try {
      val balloon = NotificationsManagerImpl.createBalloon(frame,
          notification, true, true,
          BalloonLayoutData.fullContent()
      ) { }
      balloon.show(target, Balloon.Position.atRight)
    } catch (e: Throwable) {
      notification.notify(project)
    }

  }
}
