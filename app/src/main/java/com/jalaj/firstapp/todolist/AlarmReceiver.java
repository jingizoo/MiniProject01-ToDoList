package com.jalaj.firstapp.todolist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jalajmehta on 7/27/16.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("Note_Content"), Toast.LENGTH_LONG).show();
        Log.d("Alarm Running","Alarm Running");

        NotificationManager NM;
        NM=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification(android.R.drawable.stat_notify_more,context.getResources().getString(R.string.app_name),System.currentTimeMillis());
        PendingIntent pending= PendingIntent.getActivity(context, 0, new Intent(),0);
        notify.setLatestEventInfo(context, context.getResources().getString(R.string.app_name), intent.getStringExtra("Note_Content"),pending);
        NM.notify(0, notify);
    }


}
