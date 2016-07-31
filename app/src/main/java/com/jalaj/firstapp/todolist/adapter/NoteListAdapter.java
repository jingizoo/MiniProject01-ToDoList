package com.jalaj.firstapp.todolist.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jalaj.firstapp.todolist.dialogues.DatePickerDisplay;
import com.jalaj.firstapp.todolist.R;
import com.jalaj.firstapp.todolist.Initiator;
import com.jalaj.firstapp.todolist.dialogues.ItemUpdator;
import com.jalaj.firstapp.todolist.model.ToDoListItem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jalajmehta on 7/24/16.
 */

public class NoteListAdapter extends BaseAdapter{
LayoutInflater layoutInflater;
    ArrayList<ToDoListItem>  arrayList;
    Context ctx;
   static boolean  showSeperator,objectExists;
    public static NoteListAdapter noteListAdapter;

    public static NoteListAdapter getInstance(ArrayList<ToDoListItem> arrayList, Context ctx)
    {
        if (!objectExists){
            noteListAdapter = new NoteListAdapter(arrayList,ctx);
        return noteListAdapter;}
        else
           return noteListAdapter;

    }
    public static NoteListAdapter getNoteListAdapterInstance()
    {
        if (objectExists)
        return noteListAdapter;
        else return null;
    }
    private  NoteListAdapter(ArrayList<ToDoListItem> arrayList, Context ctx)
    {

        layoutInflater = LayoutInflater.from(ctx);
        this.arrayList = arrayList;
        this.ctx = ctx;


    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context newCtx = this.ctx;
        final ToDoListItem toDoListItem = arrayList.get(position);
        final NoteListAdapter noteListAdapter = this;
        convertView = layoutInflater.inflate(R.layout.layout_todolist_front,parent,false);

      //  LinearLayout childLayout1 = (LinearLayout) convertView.findViewById(R.id.layout_note_front_c1);
        final View argView = convertView;
        final TextView tdTxtNote = (TextView) convertView.findViewById(R.id.tdTxtNote);

        tdTxtNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ItemUpdator itemUpdator= new ItemUpdator(toDoListItem,newCtx,noteListAdapter);
                itemUpdator.updateNote(position);
                notifyDataSetChanged();
                return false;
            }
        });
        final TextView tdTxtSeparator = (TextView)convertView.findViewById(R.id.tdTxtSeparator) ;
        final TextView tdTxtReminderInfo = (TextView)convertView.findViewById(R.id.tdTxtReminderInfo) ;

        Button btnRemindNote = (Button)convertView.findViewById(R.id.tdBtnReminder);

        btnRemindNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDisplay datePickerDisplay = new DatePickerDisplay(newCtx,argView,toDoListItem,noteListAdapter);
                datePickerDisplay.pickDate();

            }
        });

        Button btnDeleteNote = (Button)convertView.findViewById(R.id.tdBtnDelete);
        btnDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(position);
                notifyDataSetChanged();
                toDoListItem.deleteItem(newCtx);
            }
        });


        tdTxtNote.setText(toDoListItem.getNoteContent());
        //tdTxtNote.setBackgroundColor(getResource().getColor(R.color.tdWhite);
        CheckBox tdCbNote = (CheckBox)convertView.findViewById(R.id.tdCbNote);

        if (toDoListItem.getDone_flag()==1){
            tdTxtNote.setPaintFlags(tdTxtNote.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            tdCbNote.setChecked(true);
        }
        tdCbNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("CBL","CheckBoxLisntner");
                if (((CheckBox) v).isChecked()) {
                    tdTxtNote.setPaintFlags(tdTxtNote.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    toDoListItem.setDone_flag(1);
                    Collections.sort(arrayList);
                    notifyDataSetChanged();
                }
                else {
                    tdTxtNote.setPaintFlags(0);
                    toDoListItem.setDone_flag(0);
                    Collections.sort(arrayList);
                    notifyDataSetChanged();
                }



                toDoListItem.saveItem(newCtx);
            }
        });
        Initiator initiator =     Initiator.getInstance(ctx);
        String reminderInfo = toDoListItem.getRemind_dttm();
        if (reminderInfo.compareTo("Elapsed")==0 && (toDoListItem.getDone_flag()==0))

            tdTxtReminderInfo.setBackgroundColor(argView.getResources().getColor(R.color.tdRed));

        else
        if (reminderInfo.compareTo("Not Set")==0 && (toDoListItem.getDone_flag()==0))
            tdTxtReminderInfo.setBackgroundColor(argView.getResources().getColor(R.color.tdYellow));
        else
            tdTxtReminderInfo.setBackgroundColor(argView.getResources().getColor(R.color.tdGreen));
        tdTxtReminderInfo.setText(reminderInfo);

        if (position==0){
            showSeperator = true;
        }
        else {


            if (toDoListItem.getCategory_id() != arrayList.get(position-1).getCategory_id()){
                showSeperator = true;
            }
            else showSeperator = false;
        }
        if (showSeperator){

            tdTxtSeparator.setText(initiator.categoryHashmap.get(arrayList.get(position).getCategory_id()));
            tdTxtSeparator.setVisibility(View.VISIBLE);
        }
        else   tdTxtSeparator.setVisibility(View.GONE);

        return convertView;
    }

    public ArrayList<ToDoListItem> getArrayList()
    {
        return arrayList;
    }
}
