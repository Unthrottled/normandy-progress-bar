package io.unthrottled.n7.notification

import com.intellij.notification.*
import com.intellij.openapi.project.Project

object NotificationService {
  private val notificationManager by lazy {
    SingletonNotificationManager(
        NotificationGroup("Normandy Updates",
            NotificationDisplayType.STICKY_BALLOON, true),
        NotificationType.INFORMATION)
  }

  fun showUserUpdates(project: Project) {
    notificationManager.notify(
        "SSV Normandy Progress Bar updated to $VERSION",
        UPDATE_MESSAGE,
        project,
        NotificationListener.URL_OPENING_LISTENER
    )
  }
}