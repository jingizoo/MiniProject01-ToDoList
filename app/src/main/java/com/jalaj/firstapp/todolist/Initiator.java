package com.jalaj.firstapp.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jalaj.firstapp.todolist.database.ToDoListDB;
import com.jalaj.firstapp.todolist.model.CategoryTypes;
import com.jalaj.firstapp.todolist.model.ReminderTypes;
import com.jalaj.firstapp.todolist.model.ToDoListItem;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jalajmehta on 7/25/16.
 */

public class Initiator {
    public static Context ctx;
     ArrayList<CategoryTypes> categoryTypesArrayList;
     List<ReminderTypes> reminderTypesArrayList;
    public static final HashMap<Integer,String> categoryHashmap = new HashMap<>();
    public static  final HashMap<Integer,String> reminderHashmap = new HashMap<>();
    private static Initiator instance;
    private  static boolean instanceExists;
    public Initiator(Context ctx) {
        this.ctx = ctx;
        categoryTypesArrayList = getAllCategoryTypes();
        reminderTypesArrayList = getAllReminderTypes();
    }

    public static Initiator getInstance(Context ctx){

        if (!instanceExists) {

            instance = new Initiator(ctx);
            instanceExists = true;

        }
        return instance;
    }

    public ArrayList<CategoryTypes> getAllCategoryTypes( ){

        ToDoListDB toDoListDB = new ToDoListDB(ctx);
        Cursor cursor = toDoListDB.getSelectSQL(ToDoListDB.TD_CATEGORY_TBL,new String[]{"description, category_id"},"1=1",new String[]{});
       // Log.d("First Column ",cursor.getString(0));
        ArrayList <CategoryTypes> list = new ArrayList<CategoryTypes>();
        do {
            list.add(new CategoryTypes(cursor.getInt(1),cursor.getString(0)));
            categoryHashmap.put(cursor.getInt(1),cursor.getString(0));
           // Log.d("dowhile",cursor.getString(0));
        } while (cursor.moveToNext());

    return  list;
    }

    public List<ReminderTypes> getAllReminderTypes( ){
        ToDoListDB toDoListDB = new ToDoListDB(ctx);
        Cursor cursor = toDoListDB.getSelectSQL(ToDoListDB.TD_REMINDER_MASTER_TBL,new String[]{"description","reminder_id"},"1=1",new String[]{});
       // Log.d("First COlumn ",cursor.getString(0));
        List <ReminderTypes> list = new ArrayList<ReminderTypes>();
        do {
            list.add(new ReminderTypes(cursor.getInt(0),cursor.getString(0)));
            reminderHashmap.put(cursor.getInt(0),cursor.getString(0));
        } while (cursor.moveToNext());
        return  list;
    }

    private ArrayList getAllNotes(String sql, String[] params){

        ToDoListItem toDoListItem;
        ArrayList<ToDoListItem> arrayList = new ArrayList<>();
        ToDoListDB toDoListDB = new ToDoListDB(ctx);
        SQLiteDatabase database = toDoListDB.getReadableDatabase();


      //  Log.d("Initiator",sql);
        Cursor cursor = database.rawQuery(sql,params);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            do {

               // Log.d("Data:", cursor.getString(1) + cursor.getString(6));
                toDoListItem = new ToDoListItem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
                toDoListItem.setRemind_dttm(cursor.getString(6));

                arrayList.add(toDoListItem);
            } while (cursor.moveToNext());


        }

        return arrayList;

    }
    public ArrayList<ToDoListItem> getAllNotes()
    {
        String selectSQL = "SELECT distinct master.note_id, master.content,  reminder.reminder_id, category.category_id, priority.done_flag, master.last_updated_dttm , reminder.remind_dttm from "+ToDoListDB.TD_NOTE_MASTER_TBL+" master, "+
                ToDoListDB.TD_NOTE_REMINDER_TBL+" reminder, "+ ToDoListDB.TD_NOTE_CATEGORY_TBL+" category ,"+ToDoListDB.TD_NOTE_PRIORITY_TBL+" priority "+"WHERE reminder.note_id = master.note_id and  " +
                "category.note_id = master.note_id and priority.note_id = master.note_id order by category.category_id, priority.done_flag, master.last_updated_dttm";
        // String selectSQL = "Select * FROM TD_NOTE_MASTER_TBL";
       return getAllNotes(selectSQL,new String[]{});

    }

    public ArrayList getAllNotes(int category_id){

        String selectSQL = "SELECT distinct master.note_id, master.content,  reminder.reminder_id, category.category_id, priority.done_flag, master.last_updated_dttm , reminder.remind_dttm from "+ToDoListDB.TD_NOTE_MASTER_TBL+" master, "+
                ToDoListDB.TD_NOTE_REMINDER_TBL+" reminder, "+ ToDoListDB.TD_NOTE_CATEGORY_TBL+" category ,"+ToDoListDB.TD_NOTE_PRIORITY_TBL+" priority "+"WHERE reminder.note_id = master.note_id and  " +
                "category.note_id = master.note_id and priority.note_id = master.note_id and category.category_id = ? order by category.category_id, priority.done_flag, master.last_updated_dttm";
        // String selectSQL = "Select * FROM TD_NOTE_MASTER_TBL";

        return  getAllNotes(selectSQL,new String[]{category_id+""});

    }

    public  List <String> getAllCategoryDescription(){

        List<String> list= new ArrayList<String>();

        for (Map.Entry<Integer,String> e : categoryHashmap.entrySet()) {
          list.add(e.getValue());

        }

        return list;
    }

    public  List <String> getAllReminderDecription(){

        List<String> list= new ArrayList<String>();
        for (Map.Entry<Integer,String> e : reminderHashmap.entrySet()) {
            list.add(e.getValue());

        }

        return list;
    }

    public int lookUpReminderIdByDescription(String description){
        for (int i=0;i<this.reminderTypesArrayList.size();i++){
            if (reminderTypesArrayList.get(i).description.compareTo(description)==0)
                return i;
        }
    return -1;
    }
    public int lookUpCategoryIdByDescription(String description){

        //Log.d(description,description);
        for (Map.Entry<Integer,String> e : categoryHashmap.entrySet()) {
            //Log.d(e.getValue(),description);
           if (e.getValue().toUpperCase().compareTo(description.toUpperCase())==0) {

               return e.getKey();
           }

        }
        return -1;
    }

    public int getTotalElapsed(int category_id)
    {

        String selectSQL = "SELECT distinct master.note_id, master.content,  reminder.reminder_id, category.category_id, priority.done_flag, master.last_updated_dttm , reminder.remind_dttm from "+ToDoListDB.TD_NOTE_MASTER_TBL+" master, "+
                ToDoListDB.TD_NOTE_REMINDER_TBL+" reminder, "+ ToDoListDB.TD_NOTE_CATEGORY_TBL+" category ,"+ToDoListDB.TD_NOTE_PRIORITY_TBL+" priority "+"WHERE reminder.note_id = master.note_id and  " +
                "category.note_id = master.note_id and priority.note_id = master.note_id and category.category_id = ? and remind_dttm like  'E%' and done_flag=0 order by category.category_id, priority.done_flag, master.last_updated_dttm";

        return  getAllNotes(selectSQL,new String[]{category_id+""}).size();
    }

    public int getTotalPending(int category_id)
    {

        String selectSQL = "SELECT distinct master.note_id, master.content,  reminder.reminder_id, category.category_id, priority.done_flag, master.last_updated_dttm , reminder.remind_dttm from "+ToDoListDB.TD_NOTE_MASTER_TBL+" master, "+
                ToDoListDB.TD_NOTE_REMINDER_TBL+" reminder, "+ ToDoListDB.TD_NOTE_CATEGORY_TBL+" category ,"+ToDoListDB.TD_NOTE_PRIORITY_TBL+" priority "+"WHERE reminder.note_id = master.note_id and  " +
                "category.note_id = master.note_id and priority.note_id = master.note_id and category.category_id = ? and remind_dttm < ? and done_flag=0 order by category.category_id, priority.done_flag, master.last_updated_dttm";

        return  getAllNotes(selectSQL,new String[]{category_id+"",new java.util.GregorianCalendar().getTime().toString()}).size();


    }

    public int getTotalScheduledOrDone(int category_id)
    {

        String selectSQL = "SELECT distinct master.note_id, master.content,  reminder.reminder_id, category.category_id, priority.done_flag, master.last_updated_dttm , reminder.remind_dttm from "+ToDoListDB.TD_NOTE_MASTER_TBL+" master, "+
                ToDoListDB.TD_NOTE_REMINDER_TBL+" reminder, "+ ToDoListDB.TD_NOTE_CATEGORY_TBL+" category ,"+ToDoListDB.TD_NOTE_PRIORITY_TBL+" priority "+"WHERE reminder.note_id = master.note_id and  " +
                "category.note_id = master.note_id and priority.note_id = master.note_id and category.category_id = ? and ((remind_dttm < ? and done_flag=0) or (done_flag=1)) order by category.category_id, priority.done_flag, master.last_updated_dttm";

        return  getAllNotes(selectSQL,new String[]{category_id+"",new java.util.GregorianCalendar().getTime().toString()}).size();


    }


}
