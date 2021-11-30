package edu.umich.mahira.fridgefriend

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class ReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        createNotificationChannel()

        val reminderButton: Button = findViewById(R.id.setReminderButton)

        reminderButton.setOnClickListener {
            Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show()
            val intent: Intent = Intent(this, ReminderBroadcast::class.java)

            val pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

            val alarmManager: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

            val currentTime: Long = System.currentTimeMillis()

            val tenSeconds = 1000 * 10

            alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime+tenSeconds, pendingIntent)

            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    private fun createNotificationChannel() {
        val name: CharSequence = "fridgeFriendReminderChannel"
        val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
        val channel: NotificationChannel = NotificationChannel("notifyChannel", name, importance)
        channel.description = "Hello World"

        val notificiationManger: NotificationManager = getSystemService(NotificationManager::class.java)
        notificiationManger.createNotificationChannel(channel)


    }
}