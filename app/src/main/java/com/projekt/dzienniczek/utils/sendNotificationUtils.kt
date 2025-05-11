package com.projekt.dzienniczek.utils

import com.google.firebase.database.FirebaseDatabase


fun sendNotificationToUser(user: String?, message: String?) {
    val ref = FirebaseDatabase.getInstance().getReference()
    val notifications = ref.child("notificationRequests")

    val notification: MutableMap<String, String?> = mutableMapOf()
    notification["username"] = user
    notification["message"] = message

    notifications.push().setValue(notification)
}