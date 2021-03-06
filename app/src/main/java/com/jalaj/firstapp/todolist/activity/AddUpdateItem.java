package com.jalaj.firstapp.todolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.jalaj.firstapp.todolist.Initiator;
import com.jalaj.firstapp.todolist.R;
import com.jalaj.firstapp.todolist.adapter.NoteListAdapter;
import com.jalaj.firstapp.todolist.database.ToDoListDB;
import com.jalaj.firstapp.todolist.dialogues.ItemCreator;
import com.jalaj.firstapp.todolist.model.ToDoListItem;

import java.util.ArrayList;

public class AddUpdateItem extends AppCompatActivity {

    FloatingActionButton fab;
    ArrayList<ToDoListItem> arrayList;
    NoteListAdapter noteListAdapter;
    Initiator initiator;
    public static int activityCatId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initiator =new Initiator(this);
        initiator =  Initiator.getInstance(this);
        arrayList = new ArrayList<>();
       // Log.d("Integer",intent.getStringExtra("category_id"));
        activityCatId = Integer.parseInt(intent.getStringExtra("category_id"));
        arrayList = initiator.getAllNotes(activityCatId);
        noteListAdapter = NoteListAdapter.getInstance(arrayList,this);

        ListView tdListViewNoteFront = (ListView)findViewById(R.id.tdlstVwNoteFront);
        tdListViewNoteFront.setAdapter(noteListAdapter);


        //implement floating action button to add a new note

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCreator itemCreator = new ItemCreator();
                itemCreator.showItemCreator(AddUpdateItem.this,noteListAdapter);

                LayoutInflater layoutInflater = LayoutInflater.from(AddUpdateItem.this);
                layoutInflater.inflate(R.layout.layout_todolist_front,null);
            }
        });
        // Log.d("Hi","Hi");








    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
