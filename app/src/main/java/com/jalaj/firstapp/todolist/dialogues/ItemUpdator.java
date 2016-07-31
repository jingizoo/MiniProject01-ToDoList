package com.jalaj.firstapp.todolist.dialogues;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jalaj.firstapp.todolist.Initiator;
import com.jalaj.firstapp.todolist.R;
import com.jalaj.firstapp.todolist.adapter.NoteListAdapter;
import com.jalaj.firstapp.todolist.database.ToDoListDB;
import com.jalaj.firstapp.todolist.model.CategoryTypes;
import com.jalaj.firstapp.todolist.model.ToDoListItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jalajmehta on 7/29/16.
 */

public class ItemUpdator {

    private ToDoListItem toDoListItem;
    Context ctx;
    final NoteListAdapter adapter;
    public ItemUpdator(ToDoListItem toDoListItem, Context ctx, final NoteListAdapter adapter) {
        this.toDoListItem = toDoListItem;
        this.ctx = ctx;
        this.adapter = adapter;
    }

    public void updateNote(final int position)
    {
        final Initiator initiator =  Initiator.getInstance(ctx);
        ArrayList<CategoryTypes> allCat = Initiator.getInstance(ctx).getAllCategoryTypes();
        List allReminderTypes = initiator.getAllReminderDecription();

        AlertDialog.Builder aDB = new AlertDialog.Builder(ctx);
        aDB.setView(R.layout.layout_note_creator);
        final AlertDialog aD = aDB.create();
        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_note_creator,null);
        final Button tdAdBtnAddNote = (Button)view.findViewById(R.id.tdAdBtnAddNote);
        final Spinner tdAdSpinCategory = (Spinner)view.findViewById(R.id.tdAdSpinCategory);

        ArrayAdapter<CategoryTypes> dataAdapterCategory = new ArrayAdapter<CategoryTypes>(ctx, android.R.layout.simple_spinner_item, allCat);
        tdAdSpinCategory.setAdapter(dataAdapterCategory);
       // Log.d("category Id",toDoListItem.getCategory_id()+"");
        tdAdSpinCategory.setSelection(findPositionById(toDoListItem.getCategory_id(),tdAdSpinCategory));

        final Spinner tdAdSpinReminder = (Spinner)view.findViewById(R.id.tdAdSpinReminder);

        final EditText tdAdEdTxtNoteContent = (EditText) view.findViewById(R.id.tdAdEdTxContent);
        tdAdEdTxtNoteContent.setText(toDoListItem.getNoteContent());
        ArrayAdapter<String> dataAdapterReminder  = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, allReminderTypes);
        tdAdSpinReminder.setAdapter(dataAdapterReminder);

        final Context fCtx = ctx;

        aD.setView(view);
        aD.show();
        tdAdBtnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Log.d("Note Content ",adapter.getArrayList().get(position).getNoteContent()+" "+initiator.lookUpCategoryIdByDescription(tdAdSpinCategory.getSelectedItem().toString())+" "+adapter.getArrayList().get(position).getCategory_id());
                if (initiator.lookUpCategoryIdByDescription(tdAdSpinCategory.getSelectedItem().toString())!=adapter.getArrayList().get(position).getCategory_id()) adapter.getArrayList().remove(position);
                toDoListItem.setNoteContent(tdAdEdTxtNoteContent.getText().toString());
                toDoListItem.setCategory_id(initiator.lookUpCategoryIdByDescription(tdAdSpinCategory.getSelectedItem().toString()));
                toDoListItem.setDone_flag(0);
                toDoListItem.setReminder_id(initiator.lookUpReminderIdByDescription(tdAdSpinReminder.getSelectedItem().toString()));

                ToDoListDB toDoListDB = new ToDoListDB(fCtx);
                toDoListDB.updateData(toDoListDB.TD_NOTE_MASTER_TBL,new String[][]{{"content",toDoListItem.getNoteContent()},{"last_updated_dttm",new Date().toString()}}," note_id = ? ",new String[]{toDoListItem.getNote_id()+""});
                toDoListDB.updateData(toDoListDB.TD_NOTE_CATEGORY_TBL,new String[][]{{"category_id",toDoListItem.getCategory_id()+""}}," note_id=? ", new String[]{toDoListItem.getNote_id()+""});
                toDoListDB.updateData(toDoListDB.TD_NOTE_REMINDER_TBL,new String[][]{{"reminder_id",toDoListItem.getReminder_id()+""}}," note_id=? ", new String[]{toDoListItem.getNote_id()+""});
                Collections.sort(adapter.getArrayList());
                adapter.notifyDataSetChanged();

                aD.dismiss();


                }
            });

    }
      int findPositionById(int id, Spinner spinner)
    {
        int position=0;
        for (int i=0;i<spinner.getCount();i++) {
          //  Log.d("findPositionById",spinner.getAdapter().getItemId(i)+""+spinner.getAdapter().getItem(i).toString());
            if (((CategoryTypes)spinner.getAdapter().getItem(i)).getCategory_id() == id) return i;
        }
        return position;
    }
    }


