package io.unthrottled.n7.notification

import com.intellij.notification.Notification
import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType
import com.intellij.notification.impl.NotificationsManagerImpl
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.BalloonLayoutData
import com.intellij.ui.IconManager
import com.intellij.ui.awt.RelativePoint
import io.unthrottled.n7.service.ApplicationService
import java.awt.Point

object NotificationService {
  private val NOTIFICATION_ICON = IconManager.getInstance().getIcon(
    "/alliance.svg",
    NotificationService::class.java
  )

  private val notificationGroup =
    NotificationGroup(
      "normandy.progress.bar.updates",
      NotificationDisplayType.BALLOON,
      false,
      "normandy.progress.bar.updates",
      NOTIFICATION_ICON
    )

  fun showUserUpdates(project: Project, currentVersion: String) {
    showNotification(
      project,
      notificationGroup.createNotification(
        "SSV Normandy Progress Bar updated to $currentVersion",
        UPDATE_MESSAGE,
        NotificationType.INFORMATION
      )
        .setListener(NotificationListener.UrlOpeningListener(false))
        .setIcon(NOTIFICATION_ICON)
    )
  }

  private fun showNotification(
    project: Project,
    updateNotification: Notification
  ) {
    try {
      val ideFrame =
        WindowManager.getInstance().getIdeFrame(project) ?: WindowManager.getInstance().allProjectFrames.first()
      val frameBounds = ideFrame.component.bounds
      val notificationPosition = RelativePoint(ideFrame.component, Point(
        frameBounds.x + frameBounds.width / 2,
        20
      ))
      val balloon = NotificationsManagerImpl.createBalloon(
        ideFrame,
        updateNotification,
        true,
        false,
        BalloonLayoutData.fullContent(),
        ApplicationService.instance
      )
      balloon.show(notificationPosition, Balloon.Position.below)
    } catch (e: Throwable) {
      updateNotification.notify(project)
    }
  }
}