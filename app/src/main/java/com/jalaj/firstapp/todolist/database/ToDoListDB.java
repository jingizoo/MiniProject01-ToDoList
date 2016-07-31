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
        String template = "CREATE TABLE IF NOT EXISTS "+ tableName + "(";

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
    public String dropTable(String tableName)
    {

       return "DROP TABLE IF EXISTS "+ tableName;
    }
    public void fillMetaData()
    {
        database = getWritableDatabase();

       if (getSelectSQL(TD_CATEGORY_TBL,new String[]{"count(1)"}," 1=1 ",new String[]{}).getInt(0)>0) return;
        //Reminder Table
        ContentValues values = new ContentValues();
        values.put("description","ONE_TIME");
        database.delete(TD_CATEGORY_TBL,"1=1",null);
        database.delete(TD_REMINDER_MASTER_TBL,"1=1",null);
        database.insert(TD_REMINDER_MASTER_TBL,null,values);

        values = new ContentValues();
        values.put("description","ARCHIVED");
        database.insert(TD_CATEGORY_TBL,null,values);
        values = new ContentValues();
        values.put("description","HOUSEHOLD");
        database.insert(TD_CATEGORY_TBL,null,values);
        values.put("description","OFFICE");
        database.insert(TD_CATEGORY_TBL,null,values);
        values.put("description","FRIENDS");
        database.insert(TD_CATEGORY_TBL,null,values);





        database.close();

    }

    public void createTables()
    {

        database = getWritableDatabase();
        String [][] colArray0 = {{"category_id","INTEGER","PRIMARY KEY"},{"description","TEXT"," "}};
        //database.execSQL( dropTable(TD_CATEGORY_TBL));
        database.execSQL(getCreateQuery(TD_CATEGORY_TBL,colArray0));
        //create reminder master table
        String [][] colArray1 = {{"reminder_id","INTEGER","PRIMARY KEY"},{"description","TEXT"," "}};
       // database.execSQL( dropTable(TD_REMINDER_MASTER_TBL));
        database.execSQL(getCreateQuery(TD_REMINDER_MASTER_TBL,colArray1));
        //create note master table
        //database.execSQL(dropTable(TD_NOTE_MASTER_TBL));
        String [][] colArray2 = {{"note_id","INTEGER","PRIMARY KEY"},{"content","TEXT"," "},{"last_updated_dttm","TEXT"," "},{"created_dttm","TEXT"," "}};
        database.execSQL(getCreateQuery(TD_NOTE_MASTER_TBL,colArray2));
        //create category table
        String [][] colArray3 = {{"note_id","INTEGER",""},{"category_id","INTEGER"," "}};
       // database.execSQL( dropTable(TD_NOTE_CATEGORY_TBL));
        database.execSQL(getCreateQuery(TD_NOTE_CATEGORY_TBL,colArray3));
        //create category table
        String [][] colArray4 = {{"note_id","INTEGER",""},{"priority","TEXT"," "},{"done_flag","INTEGER",""}};
       // database.execSQL(dropTable(TD_NOTE_PRIORITY_TBL));
        database.execSQL(getCreateQuery(TD_NOTE_PRIORITY_TBL,colArray4));
        //create category table
        String [][] colArray5 = {{"note_id","INTEGER",""},{"reminder_id","INTEGER"," "},{"remind_dttm","TEXT"," "}};
       // database.execSQL(dropTable(TD_NOTE_REMINDER_TBL));
        database.execSQL(getCreateQuery(TD_NOTE_REMINDER_TBL,colArray5));
        database.close();
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
        database.close();
        return cursor;

    }
    private void testData()
    {

    }

    public boolean insertDataToTable(String tableName, String [][] valueArray)
    {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i=0; i<valueArray.length;i++)
        values.put(valueArray[i][0],valueArray[i][1]);

        database.insert(tableName,null,values);

        database.close();



        return  true;
    }

    public boolean updateData(String tableName, String[][] updateValues, String whereClause, String [] whereParams)
    {
        database = getWritableDatabase();

        ContentValues updateParams = new ContentValues();
        for (int i=0; i<updateValues.length;i++)
            updateParams.put(updateValues[i][0],updateValues[i][1]);

        //Log.d("Total Updated",""+"");
        database.update(tableName,updateParams,whereClause,whereParams);

        database.close();



        return  true;
    }
    public boolean deleteData(String tableName, String whereClause, String [] whereParams)
    {
        database = getWritableDatabase();

        ContentValues updateParams = new ContentValues();

        database.delete(tableName,whereClause,whereParams);

        database.close();



        return  true;
    }

}
