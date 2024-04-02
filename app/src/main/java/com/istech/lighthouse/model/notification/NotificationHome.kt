package com.istech.lighthouse.model.notification

data class NotificationHome(
    val `data`: ArrayList<NotificationModel>,
    val message: String,
    val success: Boolean
)
