package edu.umich.mahira.fridgefriend

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast

class ReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        createNotificationChannel()

        val reminderButton: Button = findViewById(R.id.setReminderButton)
        val oneDay: CheckBox = findViewById(R.id.checkBox1)
        val threeDays: CheckBox = findViewById(R.id.checkBox2)
        val fiveDays: CheckBox = findViewById(R.id.checkBox3)
        val oneWeek: CheckBox = findViewById(R.id.checkBox4)
        val threeSeconds: CheckBox = findViewById(R.id.checkBox5)

        reminderButton.setOnClickListener {
            Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show()
            val intent: Intent = Intent(this, ReminderBroadcast::class.java)

            val pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

            val alarmManager: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

            val currentTime: Long = System.currentTimeMillis()

            if (oneDay.isChecked) {
                val oneday = 1000 * 10 * 60 * 60 * 24
                alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime+oneday, pendingIntent)
            }
            if (threeDays.isChecked) {
                val threedays = 1000 * 10 * 60 * 60 * 24 * 3
                alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime+threedays, pendingIntent)
            }
            if (fiveDays.isChecked) {
                val fivedays = 1000 * 10 * 60 * 60 * 24 * 5
                alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime+fivedays, pendingIntent)
            }
            if (oneWeek.isChecked) {
                val oneweek = 1000 * 10 * 60 * 60 * 24 * 7
                alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime+oneweek, pendingIntent)
            }
            if (threeSeconds.isChecked) {
                val threeseconds = 1000 * 3
                alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime+threeseconds, pendingIntent)
            }

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