package com.example.fcmapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.contracts.Returns
import kotlin.random.Random


/**
 * Ahmed ali
 * 24/01/2021
 */
private const val CHANNEL_ID = "channel id"
private const val CHANNEL_NAME = "channel name"

class FirebaseService : FirebaseMessagingService() {
    private val notificationBuilder by lazy { NotificationCompat.Builder(this, CHANNEL_ID) }
    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    private val notificationId by lazy { Random.nextInt() }

    companion object {
        var sharePerf: SharedPreferences? = null
        var token: String?
            get() {
                return sharePerf?.getString("token", "")
            }
            set(value) {
                sharePerf!!.edit().putString("token", value).apply()
            }
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        token = newToken
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel by lazy {
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    IMPORTANCE_HIGH
                )
            }
            with(notificationChannel) {
                description = "description channel"
                lightColor = android.R.color.holo_blue_light
                enableLights(true)
            }

            notificationManager.createNotificationChannel(notificationChannel)

        }

        var intent = Intent(this, MainActivity::class.java)
        var pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        notificationBuilder.apply {
            setContentTitle(message.data["title"])
            setContentText(message.data["message"])
            setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            setAutoCancel(true)
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
            setContentIntent(pendingIntent)
        }
        notificationManager.notify(notificationId, notificationBuilder.build())


    }
}