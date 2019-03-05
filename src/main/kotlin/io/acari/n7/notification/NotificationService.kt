package io.acari.n7.notification

import com.intellij.ide.BrowserUtil
import com.intellij.notification.Notification
import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.notification.impl.NotificationsManagerImpl
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.BalloonLayoutData
import com.intellij.ui.awt.RelativePoint
import io.acari.n7.util.toOptional
import java.awt.Point

object NotificationService {

  fun showUserUpdates(project: Project) {
    val notification = NotificationGroup(
        "NORMANDY_UPDATE",
        NotificationDisplayType.STICKY_BALLOON,
        true
    ).createNotification(
        "SSV Normandy Progress Bar updated to $VERSION",
        "Thank you for updating", UPDATE_MESSAGE,
        NotificationType.INFORMATION) { clickedNotification, hyperLinkEvent ->
      hyperLinkEvent.url.toOptional()
          .map { { BrowserUtil.browse(it) } }
          .orElseGet { { BrowserUtil.browse(hyperLinkEvent.description) } }()
      clickedNotification.expire()
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