package com.jalaj.firstapp.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jalaj.firstapp.todolist.database.ToDoListDB;
import com.jalaj.firstapp.todolist.model.ReminderTypes;
import com.jalaj.firstapp.todolist.model.ToDoListItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by jalajmehta on 7/27/16.
 */

public class ReminderAlarm {
    private PendingIntent pendingIntent;
    Intent alarmIntent;
    Context ctx;
    ToDoListItem toDoListItem;
    Intent broadCastIntent;
   public ReminderAlarm(Context ctx, ToDoListItem toDoListItem) {
         alarmIntent = new Intent(ctx, AlarmReceiver.class);
      Log.d("ReminderAlarm",toDoListItem.getNoteContent());
     alarmIntent.putExtra("Note_Content",toDoListItem.getNoteContent());

       this.ctx = ctx;
       this.toDoListItem = toDoListItem;
    }

    public void setOneTimeAlarm(String timeOfReminder)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Calendar calendar = new GregorianCalendar();
        try {
            Log.d("Time",timeOfReminder+" "+simpleDateFormat.parse(timeOfReminder));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            //simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            calendar.setTime(simpleDateFormat.parse(timeOfReminder));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        AlarmManager manager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;
        Log.d("TimeMS",calendar.getTimeInMillis()+"");
        pendingIntent = PendingIntent.getBroadcast(ctx, toDoListItem.getNote_id(), alarmIntent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(ctx, "Alarm Set", Toast.LENGTH_SHORT).show();


    }

    public void cancelAlarm()
    {
        pendingIntent = PendingIntent.getActivity(ctx,toDoListItem.getNote_id(),alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent.cancel();
    }
}
