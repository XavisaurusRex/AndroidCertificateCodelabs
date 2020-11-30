package cat.devsofthecoast.codelabstraining.exercice01

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import cat.devsofthecoast.codelabstraining.R
import cat.devsofthecoast.codelabstraining.R.layout
import kotlinx.android.synthetic.main.activity_exercice1.*


class Exercice1Activity : AppCompatActivity() {

    private val mReceiver = NotificationReceiver()
    private val mNotifyManager: NotificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_exercice1)
        createNotificationChannel()
        configListeners()

        registerReceiver(mReceiver, IntentFilter(ACTION_UPDATE_NOTIFICATION));
        registerReceiver(mReceiver, IntentFilter(ACTION_NOTIFICATION_DELETED));
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Mascot Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true);
            notificationChannel.lightColor = Color.RED;
            notificationChannel.enableVibration(true);
            notificationChannel.description = "Notification from Mascot";
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    private fun configListeners() {
        btnNotifyMe.setOnClickListener {
            sendNotification()
        }
        btnUpdateMe.setOnClickListener {
            updateNotification()
        }
        btnCancelMe.setOnClickListener {
            cancelNotification()
        }
    }

    private fun sendNotification() {
        val updatePendingIntent = PendingIntent.getBroadcast(
            this,
            NOTIFICATION_ID,
            Intent(ACTION_UPDATE_NOTIFICATION),
            PendingIntent.FLAG_ONE_SHOT
        )

        mNotifyManager.notify(
            NOTIFICATION_ID,
            getNotificationBuilder()
                .addAction(R.drawable.ic_refresh, "Update Notification", updatePendingIntent)
                .build()
        )
        btnNotifyMe.isEnabled = false
        btnUpdateMe.isEnabled = true
        btnCancelMe.isEnabled = true
    }

    private fun updateNotification() {
        mNotifyManager.notify(
            NOTIFICATION_ID,
            getNotificationBuilder().setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.mascot_1))
                    .setBigContentTitle("Notification Updated!")
            ).build()
        )
        btnUpdateMe.isEnabled = false
    }

    private fun cancelNotification() {
        mNotifyManager.cancel(NOTIFICATION_ID);
        btnNotifyMe.isEnabled = true
        btnCancelMe.isEnabled = false
    }


    private fun getNotificationBuilder(): NotificationCompat.Builder =
        NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID).apply {
            setContentTitle("You've been notified!")
            setContentText("This is your notification text.")
            setSmallIcon(R.drawable.ic_consola)
            setContentIntent(
                PendingIntent.getActivity(
                    this@Exercice1Activity,
                    NOTIFICATION_ID,
                    Intent(this@Exercice1Activity, Exercice1Activity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            setAutoCancel(true)
            setDeleteIntent(PendingIntent.getBroadcast(
                this@Exercice1Activity,
                NOTIFICATION_ID,
                Intent(ACTION_NOTIFICATION_DELETED),
                PendingIntent.FLAG_ONE_SHOT
            ))
            priority = NotificationCompat.PRIORITY_HIGH
            setDefaults(NotificationCompat.DEFAULT_ALL)
        }

    companion object {
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        private const val NOTIFICATION_ID = 0
        private const val ACTION_UPDATE_NOTIFICATION =
            "cat.devsofthecoast.codelabstraining.exercice01.ACTION_UPDATE_NOTIFICATION"
        private const val ACTION_NOTIFICATION_DELETED =
            "cat.devsofthecoast.codelabstraining.exercice01.ACTION_NOTIFICATION_DELETED"
    }

    inner class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action == ACTION_UPDATE_NOTIFICATION){
                updateNotification()
            } else if(intent?.action == ACTION_NOTIFICATION_DELETED)  {
                btnNotifyMe.isEnabled = true
                btnUpdateMe.isEnabled = false
                btnCancelMe.isEnabled = false
            }
        }
    }
}