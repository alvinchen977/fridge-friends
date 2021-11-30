package edu.umich.mahira.fridgefriend

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.R
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import okhttp3.internal.notify


class ReminderBroadcast: BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(p0, "notifyChannel")
        builder.setContentTitle("Fridge Friend")
        builder.setContentText("Take a look at your fridge")
        builder.setSmallIcon(R.drawable.ic_input_add)

        val notficationManger: NotificationManagerCompat = NotificationManagerCompat.from(p0)
        notficationManger.notify(200, builder.build())
    }

}