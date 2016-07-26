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

import com.jalaj.firstapp.todolist.R;
import com.jalaj.firstapp.todolist.model.ToDoListItem;

import java.util.ArrayList;

/**
 * Created by jalajmehta on 7/24/16.
 */

public class NoteListAdapter extends BaseAdapter{
LayoutInflater layoutInflater;
    ArrayList<ToDoListItem>  arrayList;
    Context ctx;
    public  NoteListAdapter(ArrayList<ToDoListItem> arrayList, Context ctx)
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

        convertView = layoutInflater.inflate(R.layout.layout_todolist_front,parent,false);
        final TextView tdTxtNote = (TextView) convertView.findViewById(R.id.tdTxtNote);

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
        CheckBox tdCbNote = (CheckBox)convertView.findViewById(R.id.tdCbNote);

        if (toDoListItem.getDone_flag()==1)
            tdTxtNote.setPaintFlags(tdTxtNote.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        tdCbNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CBL","CheckBoxLisntner");
                if (((CheckBox) v).isChecked())
                    tdTxtNote.setPaintFlags(tdTxtNote.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                toDoListItem.setDone_flag(1);
                toDoListItem.saveItem(newCtx);
            }
        });
        return convertView;
    }
}
