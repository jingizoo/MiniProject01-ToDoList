package com.jalaj.firstapp.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.PublicKey;

/**
 * Created by jalajmehta on 7/24/16.
 */

public class ToDoListDB extends SQLiteOpenHelper {

    public static final String name = "TODOLIST";
    public static final int version = 101;
    public static final String TD_CATEGORY_TBL = "TD_CATEGORY_TBL";
    public static final String TD_NOTE_MASTER_TBL = "TD_NOTE_MASTER_TBL";
    public static final String TD_NOTE_REMINDER_TBL = "TD_NOTE_REMINDER_TBL";
    public static final String TD_REMINDER_MASTER_TBL = "TD_REMINDER_MASTER_TBL";
    public static final String TD_NOTE_CATEGORY_TBL = "TD_NOTE_CATEGORY_TBL";
    public static final String TD_NOTE_PRIORITY_TBL ="TD_NOTE_PRIORITY_TBL";

    SQLiteDatabase database;

    public ToDoListDB(Context context) {
        super(context, name, null, version);
        //createTables();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create category table
        database = db;


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private String getCreateQuery(String tableName, String [][] colName)
    {
        String template = "CREATE TABLE "+ tableName + "(";

        for(int i=0;i<colName.length;i++)
        {
            if (i<colName.length-1)
                 template = template + " "+ colName[i][0]+" "+colName[i][1]+" "+colName[i][2]+",";
            else
                template = template + " "+ colName[i][0]+" "+colName[i][1]+" "+colName[i][2]+")";
        }
        Log.d("Template",template);
        return template;
    }

    public void fillMetaData()
    {
        database = getWritableDatabase();

        //Reminder Table
        ContentValues values = new ContentValues();
        values.put("description","ONE_TIME");
        database.delete(TD_REMINDER_MASTER_TBL,"1=1",null);
        database.insert(TD_REMINDER_MASTER_TBL,null,values);

        values = new ContentValues();
        values.put("description","HOUSEHOLD");
        values.put("description","OFFICE");
        values.put("description","FRIENDS");
        database.delete(TD_CATEGORY_TBL,"1=1",null);
        database.insert(TD_CATEGORY_TBL,null,values);

        database.close();

    }

    private void createTables()
    {

        database = getWritableDatabase();
        String [][] colArray0 = {{"category_id","INTEGER","PRIMARY KEY"},{"description","TEXT"," "}};
        database.execSQL(getCreateQuery(TD_CATEGORY_TBL,colArray0));
        //create reminder master table
        String [][] colArray1 = {{"reminder_id","INTEGER","PRIMARY KEY"},{"description","TEXT"," "}};
        database.execSQL(getCreateQuery(TD_REMINDER_MASTER_TBL,colArray1));
        //create note master table
        String [][] colArray2 = {{"note_id","INTEGER","PRIMARY KEY"},{"content","TEXT"," "},{"last_updated_dttm","TEXT"," "},{"created_dttm","TEXT"," "}};
        database.execSQL(getCreateQuery(TD_NOTE_MASTER_TBL,colArray2));
        //create category table
        String [][] colArray3 = {{"note_id","INTEGER","PRIMARY KEY"},{"category_id","INTEGER"," "}};
        database.execSQL(getCreateQuery(TD_NOTE_CATEGORY_TBL,colArray3));
        //create category table
        String [][] colArray4 = {{"note_id","INTEGER","PRIMARY KEY"},{"priority","TEXT"," "},{"done_flag","INTEGER",""}};
        database.execSQL(getCreateQuery(TD_NOTE_PRIORITY_TBL,colArray4));
        //create category table
        String [][] colArray5 = {{"note_id","INTEGER","PRIMARY KEY"},{"reminder_id","TEXT"," "}};
        database.execSQL(getCreateQuery(TD_NOTE_REMINDER_TBL,colArray5));
    }
    public Cursor getSelectSQL(String tableName, String [] colName, String whereClause, String [] parameters)
    {
        String selectSQL="";
        SQLiteDatabase database = getReadableDatabase();

        String template = "SELECT ";
        for(int i=0;i<colName.length;i++)
        {
            if (i<colName.length-1)
                template = template + " "+ colName[i]+" ,";
            else
                template = template + " "+ colName[i]+" FROM "+ tableName+ " WHERE " + whereClause;
        }
       Cursor cursor =  database.rawQuery(template,parameters);
        cursor.moveToFirst();
        return cursor;
    }
    private void testData()
    {

    }

}
