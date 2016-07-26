package com.jalaj.firstapp.todolist.model;

import android.content.Context;

import com.jalaj.firstapp.todolist.database.ToDoListDB;

/**
 * Created by jalajmehta on 7/24/16.
 */

public class ToDoListItem {
    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    int note_id;
    String noteContent;
    int reminder_id;

    public int getDone_flag() {
        return done_flag;
    }

    public void setDone_flag(int done_flag) {
        this.done_flag = done_flag;
    }

    int done_flag;
    int category_id;

    public ToDoListItem(int note_id, String noteContent, int reminder_id, int category_id, int done_flag) {
        this.note_id = note_id;
        this.noteContent = noteContent;
        this.reminder_id = reminder_id;
        this.category_id = category_id;
        this.done_flag = done_flag;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public int getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(int reminder_id) {
        this.reminder_id = reminder_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public ToDoListItem[] getAllExistingItemsFromDB()
    {
        return null;
    }

    public void saveItem(Context ctx)
    {
       ToDoListDB toDoListDB = new ToDoListDB(ctx);
        toDoListDB.updateData(ToDoListDB.TD_NOTE_PRIORITY_TBL,new String[][]{{"done_flag",this.done_flag+""}},"  note_id = ?",new String[]{note_id+""});
    }

    public void  deleteItem(Context ctx)
    {
        ToDoListDB toDoListDB = new ToDoListDB(ctx);
        toDoListDB.deleteData(ToDoListDB.TD_NOTE_MASTER_TBL,"  note_id = ?",new String[]{note_id+""});


    }
}
