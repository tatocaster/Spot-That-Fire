package me.tatocaster.nasaappchallenge.common

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import me.tatocaster.nasaappchallenge.R
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeActivity
import timber.log.Timber

class AppFirebaseMessagingService : FirebaseMessagingService() {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // Check if message contains a data payload.
        /*if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            handleNow();
        }*/

        // Check if message contains a notification payload.
        if (remoteMessage!!.notification != null) {
            Timber.d("Message Notification Body: %s", remoteMessage.notification!!.body)
            sendNotification(remoteMessage.notification!!.title, remoteMessage.notification!!.body)
        }
    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(title: String?, messageBody: String?) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)

        //To set large icon in notification
        val icon1 = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

        //Assign inbox style notification
        val bigText = NotificationCompat.BigTextStyle()
        bigText.bigText(messageBody)
        bigText.setBigContentTitle(title)
        bigText.setSummaryText("Spot That Fire")

        //build notification
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_done) // needs notification icon
                //                        .setContentTitle(title)
                //                        .setContentText(messageBody.substring(0, messageBody.length() > 100 ? 100 : messageBody.length() - 1))
                .setLargeIcon(icon1)
                .setAutoCancel(true)
                .setColor(Color.BLUE)
                .setContentIntent(pendingIntent)
                .setStyle(bigText)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(token: String?) {
        Timber.d("firebase token %s", token)
    }

    companion object {

        private val TAG = "AppFirebaseMessaging"
        val FCM_DISABLED = "FCM_DISABLED"
        val FCM_DEVICE_ID = "FCM_DEVICE_ID"
    }
}