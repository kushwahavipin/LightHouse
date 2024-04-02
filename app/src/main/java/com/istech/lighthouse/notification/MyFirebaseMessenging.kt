package com.istech.lighthouse.notification

import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.StrictMode
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.text.HtmlCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.istech.lighthouse.R
import com.istech.lighthouse.activities.MainActivity
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MyFirebaseMessenging() : FirebaseMessagingService() {
    var params: Map<String, String>? = null
    var title: String? = ""
    var message: String? = ""
    var ID: String? = ""
    var type: String? = ""
    private val TAG = "MyFirebaseMessenging"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
//        if (remoteMessage.data != null) {
//            params = remoteMessage.data
//            recieveNotification()

//        }
        Log.d(TAG, "onMessageReceived: " + remoteMessage.notification )
        if (remoteMessage.notification != null) {
            title = remoteMessage.notification!!.title
            message = remoteMessage.notification!!.body

            showNotification(title!!, message!!)
        }
    }

    private fun getCustomDesign(
        title: String, message: String
    ): RemoteViews? {
        val remoteViews = RemoteViews(
            applicationContext.packageName,
            R.layout.notification
        )
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.message, message)
        remoteViews.setImageViewResource(R.id.icon, R.mipmap.app_icon)
        return remoteViews
    }

    private fun showNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        val channel_id = "notification_channel"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        var pendingIntent:PendingIntent?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
             pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE
            )
        }else{
            pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        var builder = NotificationCompat.Builder(applicationContext, channel_id)
            .setSmallIcon(R.drawable.pushnotification)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(message)
            //.setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))

//        builder =
//            builder.setContent(
//                getCustomDesign(title, message)
//            )
        val notificationManager = getSystemService(
            NOTIFICATION_SERVICE
        ) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channel_id, "web_app", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
        notificationManager.notify(0, builder.build())
    }

    private fun recieveNotification() {
//        if (params == null) {
//            return
//        }
//        Commn.log("notification_recieved:" + params.toString())
//        title = params!![Notification_Const.title]
//        message = params!![Notification_Const.message]
        type = params!![Notification_Const.type]
        ID = params!![Notification_Const.ID]
        val requestID = System.currentTimeMillis().toInt()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Notification_Const.TYPE, type + "")
        intent.putExtra(Notification_Const.ID, ID + "")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        var pendingIntent:PendingIntent?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
             pendingIntent = PendingIntent.getActivity(
                applicationContext, requestID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
                        or PendingIntent.FLAG_IMMUTABLE
            )
        }else{
            pendingIntent = PendingIntent.getActivity(
                applicationContext, requestID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
                        or PendingIntent.FLAG_ONE_SHOT
            )
        }

        val CHANNEL_ID = "01"
        val boldTitle = "<strong>$title</strong>"
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, CHANNEL_ID)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setContentTitle(
                HtmlCompat.fromHtml(
                    boldTitle,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            )
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
//                .setColor(getResources().getColor(R.color.colorPrimary))
//                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.app_icon)
                //.setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.app_icon)
           // )
            if (message != null) {
                if (!message!!.isEmpty()) {
                    notificationBuilder.setStyle(
                        NotificationCompat.BigTextStyle().bigText(message + "")
                    )
                }
            }
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.app_icon)
        }


        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            val name = "Channel_001"
            val description = "Channel Description"
            //String description = getString(R.string.channel_description);
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            //NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager?.createNotificationChannel(channel)
        }
        val random = Random()
        val ran = random.nextInt(100)
        //NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager?.notify(ran, notificationBuilder.build())
    }

    private fun getImage(url_recived: String): Bitmap? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var bitmap: Bitmap? = null
        try {
            val url = URL(url_recived)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }
}
