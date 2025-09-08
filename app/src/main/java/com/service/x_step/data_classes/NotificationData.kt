package com.service.x_step.data_classes

data class NotificationData(
    val title: String = "Notification",
    val body: String = "You have an update.",
    val fcmToken: String = ""
)