package io.unthrottled.n7.notification

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType
import com.intellij.notification.SingletonNotificationManager
import com.intellij.openapi.project.Project

object NotificationService {
  private val notificationManager by lazy {
    SingletonNotificationManager(
      NotificationGroup("Normandy Updates",
        NotificationDisplayType.STICKY_BALLOON, true),
      NotificationType.INFORMATION)
  }

  fun showUserUpdates(project: Project, currentVersion: String) {
    notificationManager.notify(
      "SSV Normandy Progress Bar updated to $currentVersion",
      UPDATE_MESSAGE,
      project,
      NotificationListener.URL_OPENING_LISTENER
    )
  }
}