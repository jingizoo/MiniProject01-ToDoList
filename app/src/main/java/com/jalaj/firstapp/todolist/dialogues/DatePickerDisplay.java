package com.jalaj.firstapp.todolist.dialogues;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.jalaj.firstapp.todolist.ReminderAlarm;
import com.jalaj.firstapp.todolist.adapter.NoteListAdapter;
import com.jalaj.firstapp.todolist.database.ToDoListDB;
import com.jalaj.firstapp.todolist.model.ToDoListItem;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

/**
 * Created by jalajmehta on 7/27/16.
 */

public class DatePickerDisplay  {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    View view;
    Context ctx;
    Calendar calendar;
    int yearCurrent, monthCurrent, dayCurrent, hourCurrent, minuteCurrent, seconds;
    Date datePickerDate, timePickerTime;
    ToDoListItem toDoListItem;
NoteListAdapter adapter;

    public DatePickerDisplay(Context ctx, View view, ToDoListItem toDoListItem, NoteListAdapter adapter) {
        this.ctx = ctx;
        this.view = view;
        calendar = Calendar.getInstance();
        yearCurrent = calendar.get(Calendar.YEAR);
        monthCurrent = calendar.get(Calendar.MONTH);
        dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);
        hourCurrent = calendar.get(calendar.HOUR_OF_DAY);
        minuteCurrent = calendar.get(calendar.MINUTE);
        this.toDoListItem = toDoListItem;
        this.adapter = adapter;
    }


    public void pickDate() {
        DatePickerDialog dpd = new DatePickerDialog(ctx,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
Log.d("Month",monthOfYear+" "+year);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                        try {
                            datePickerDate = simpleDateFormat.parse(year + "." + (monthOfYear+1) + "." + dayOfMonth+" 00:00:00");
                           Log.d ("datePickerDate",datePickerDate.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        pickTime();
                    }
                }, yearCurrent, monthCurrent, dayCurrent);
        dpd.show();

    }

    public void pickTime() {
        TimePickerDialog tpd = new TimePickerDialog(ctx,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // Display Selected time in textbox
                        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                        try {
                            timePickerTime = formatter.parse(hourOfDay + ":" + minute + ":00");
                            ToDoListDB toDoListDB = new ToDoListDB(ctx);
                            Calendar cal = new GregorianCalendar();
                            cal.setTime(datePickerDate);



                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
                            Date date = simpleDateFormat.parse(cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH)+" "+hourOfDay+":"+minute);

                           // toDoListItem.setRemind_dttm(cal.get(Calendar.YEAR)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DAY_OF_MONTH)+" "+hourOfDay+":"+minute);
                            toDoListItem.setRemind_dttm(date.toString());
                            toDoListDB.updateData(ToDoListDB.TD_NOTE_REMINDER_TBL,new String[][]{{"remind_dttm",toDoListItem.getRemind_dttm()}}," note_id=?", new String[]{toDoListItem.getNote_id()+""});
                           // Log.d("datePick ",toDoListItem.getRemind_dttm()+toDoListItem.getNote_id());
                            adapter.notifyDataSetChanged();
                            ReminderAlarm reminderAlarm = new ReminderAlarm(ctx,toDoListItem);
                            reminderAlarm.cancelAlarm();
                            reminderAlarm.setOneTimeAlarm(cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH)+" "+hourOfDay+":"+minute);
                           // Log.d("datetimeset",datePickerDate.toString()+timePickerTime.toString());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, hourCurrent, minuteCurrent, true);
        tpd.show();



    }
}
