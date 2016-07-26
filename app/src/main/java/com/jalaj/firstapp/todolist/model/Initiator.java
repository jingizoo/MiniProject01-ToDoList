package com.jalaj.firstapp.todolist.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jalaj.firstapp.todolist.database.ToDoListDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalajmehta on 7/25/16.
 */

public class Initiator {
    Context ctx;
    List<CategoryTypes> categoryTypesArrayList;
    List<ReminderTypes> reminderTypesArrayList;
    public Initiator(Context ctx) {
        this.ctx = ctx;
        categoryTypesArrayList = getAllCategoryTypes();
        reminderTypesArrayList = getAllReminderTypes();
    }

    public List<CategoryTypes> getAllCategoryTypes( ){

        ToDoListDB toDoListDB = new ToDoListDB(ctx);
        Cursor cursor = toDoListDB.getSelectSQL(ToDoListDB.TD_CATEGORY_TBL,new String[]{"description, category_id"},"1=1",new String[]{});
        Log.d("First COlumn ",cursor.getString(0));
        List <CategoryTypes> list = new ArrayList<CategoryTypes>();
        do {
            list.add(new CategoryTypes(cursor.getInt(1),cursor.getString(0)));
        } while (cursor.moveToNext());
    return  list;
    }

    public List<ReminderTypes> getAllReminderTypes( ){
        ToDoListDB toDoListDB = new ToDoListDB(ctx);
        Cursor cursor = toDoListDB.getSelectSQL(ToDoListDB.TD_REMINDER_MASTER_TBL,new String[]{"description","reminder_id"},"1=1",new String[]{});
        Log.d("First COlumn ",cursor.getString(0));
        List <ReminderTypes> list = new ArrayList<ReminderTypes>();
        do {
            list.add(new ReminderTypes(cursor.getInt(0),cursor.getString(0)));
        } while (cursor.moveToNext());
        return  list;
    }

    public ArrayList<ToDoListItem> getAllNotes()
    {
        ToDoListItem toDoListItem;
        ArrayList<ToDoListItem> arrayList = new ArrayList<>();
        ToDoListDB toDoListDB = new ToDoListDB(ctx);
        SQLiteDatabase database = toDoListDB.getReadableDatabase();

        String selectSQL = "SELECT distinct master.note_id, master.content,  reminder.reminder_id, category.category_id, priority.done_flag, master.last_updated_dttm from "+ToDoListDB.TD_NOTE_MASTER_TBL+" master, "+
              ToDoListDB.TD_NOTE_REMINDER_TBL+" reminder, "+ ToDoListDB.TD_NOTE_CATEGORY_TBL+" category ,"+ToDoListDB.TD_NOTE_PRIORITY_TBL+" priority "+"WHERE reminder.note_id = master.note_id and  " +
               "category.note_id = master.note_id and priority.note_id = master.note_id order by priority.done_flag, master.last_updated_dttm";
       // String selectSQL = "Select * FROM TD_NOTE_MASTER_TBL";
        Log.d("Initiator","getAllNotes");
        Cursor cursor = database.rawQuery(selectSQL,new String[]{});

        cursor.moveToFirst();
       if (cursor.getCount() > 0) {

           do {

               Log.d("Data:", cursor.getInt(0) + cursor.getString(1));
               toDoListItem = new ToDoListItem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
               arrayList.add(toDoListItem);
           } while (cursor.moveToNext());


       }
        return arrayList;
    }


    public  List <String> getAllCategoryDescription(){

        List<String> list= new ArrayList<String>();

        for (int i=0;i<this.categoryTypesArrayList.size();i++){
            list.add(this.categoryTypesArrayList.get(i).description);
        }

        return list;
    }

    public  List <String> getAllReminderDecription(){

        List<String> list= new ArrayList<String>();

        for (int i=0;i<this.reminderTypesArrayList.size();i++){
            list.add(this.reminderTypesArrayList.get(i).description);
        }

        return list;
    }

    public int lookUpReminderIdByDescription(String description){
        for (int i=0;i<this.reminderTypesArrayList.size();i++){
            if (reminderTypesArrayList.get(i).description==description)
                return i;
        }
    return -1;
    }
    public int lookUpCategoryIdByDescription(String description){
        for (int i=0;i<this.categoryTypesArrayList.size();i++){
            if (categoryTypesArrayList.get(i).description==description)
                return i;
        }
        return -1;
    }
}
