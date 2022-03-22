package com.mubin.appscheduler.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class OpenAppBroadcast  : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context == null || intent == null) return

        val packageName = intent.getStringExtra("package") ?: ""

        val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(launchIntent)

    }

}