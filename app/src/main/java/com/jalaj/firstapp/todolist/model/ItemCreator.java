package com.jalaj.firstapp.todolist.model;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jalaj.firstapp.todolist.MainActivity;
import com.jalaj.firstapp.todolist.R;
import com.jalaj.firstapp.todolist.adapter.NoteListAdapter;
import com.jalaj.firstapp.todolist.database.ToDoListDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jalajmehta on 7/25/16.
 */

public class ItemCreator {

    ToDoListItem toDoListItem;

    List<String> allCat;
    List <String> allReminderTypes;


public void showItemCreator (Context ctx, final ArrayList<ToDoListItem> arrayList, final NoteListAdapter noteListAdapter)
{
    final Initiator initiator = new Initiator(ctx);
    allCat = initiator.getAllCategoryDescription();
    allReminderTypes = initiator.getAllReminderDecription();

    AlertDialog.Builder aDB = new AlertDialog.Builder(ctx);
        aDB.setView(R.layout.layout_note_creator);
    final AlertDialog aD = aDB.create();
    View view = LayoutInflater.from(ctx).inflate(R.layout.layout_note_creator,null);
    final Button tdAdBtnAddNote = (Button)view.findViewById(R.id.tdAdBtnAddNote);
    final Spinner tdAdSpinCategory = (Spinner)view.findViewById(R.id.tdAdSpinCategory);

    ArrayAdapter<String> dataAdapterCategory = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, allCat);
        tdAdSpinCategory.setAdapter(dataAdapterCategory);
    final Spinner tdAdSpinReminder = (Spinner)view.findViewById(R.id.tdAdSpinReminder);
    final EditText tdAdEdTxtNoteContent = (EditText) view.findViewById(R.id.tdAdEdTxContent);
    ArrayAdapter<String> dataAdapterReminder  = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, allReminderTypes);
        tdAdSpinReminder.setAdapter(dataAdapterReminder);

    final Context fCtx = ctx;
        tdAdBtnAddNote.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            toDoListItem = new ToDoListItem(0,tdAdEdTxtNoteContent.getText().toString(),initiator.lookUpCategoryIdByDescription(tdAdSpinReminder.getSelectedItem().toString()),initiator.lookUpReminderIdByDescription(tdAdSpinCategory.getSelectedItem().toString()),0);
            arrayList.add(toDoListItem);
            noteListAdapter.notifyDataSetChanged();

            ToDoListDB toDoListDB = new ToDoListDB(fCtx);
            toDoListDB.insertDataToTable(toDoListDB.TD_NOTE_MASTER_TBL,new String[][]{{"content",toDoListItem.getNoteContent()},{"last_updated_dttm",new Date().toString()},{"created_dttm",new Date().toString()}});
            Cursor cursorNote = toDoListDB.getSelectSQL(toDoListDB.TD_NOTE_MASTER_TBL,new String[]{"max(note_id)"},"1=1",new String[]{});
            toDoListItem.setNote_id(cursorNote.getInt(0));
            //Cursor cursorReminder = toDoListDB.getSelectSQL(toDoListDB.TD_REMINDER_MASTER_TBL,new String[]{"max(reminder_id)"},"description=?",new String[]{toDoListItem.getReminder()});
            //Cursor cursorCategory = toDoListDB.getSelectSQL(toDoListDB.TD_CATEGORY_TBL,new String[]{"max(category_id)"},"description=?",new String[]{toDoListItem.getCategory()});

            toDoListDB.insertDataToTable(toDoListDB.TD_NOTE_CATEGORY_TBL,new String[][]{{"note_id",toDoListItem.getNote_id()+""},{"category_id",toDoListItem.getCategory_id()+""}});
            toDoListDB.insertDataToTable(toDoListDB.TD_NOTE_REMINDER_TBL,new String[][]{{"note_id",toDoListItem.getNote_id()+""},{"reminder_id",toDoListItem.getReminder_id()+""}});

            toDoListDB.insertDataToTable(toDoListDB.TD_NOTE_PRIORITY_TBL,new String[][]{{"note_id",toDoListItem.getNote_id()+""},{"priority","HIGH"},{"done_flag","0"}});

            aD.dismiss();


        }
    });
        aD.setView(view);
        aD.show();
}



}
