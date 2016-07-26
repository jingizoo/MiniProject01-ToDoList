package com.jalaj.firstapp.todolist;

import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.jalaj.firstapp.todolist.adapter.NoteListAdapter;
import com.jalaj.firstapp.todolist.database.ToDoListDB;
import com.jalaj.firstapp.todolist.model.Initiator;
import com.jalaj.firstapp.todolist.model.ItemCreator;
import com.jalaj.firstapp.todolist.model.ToDoListItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
FloatingActionButton fab;
    ArrayList<ToDoListItem> arrayList;
    NoteListAdapter noteListAdapter;
Initiator initiator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       initiator = new Initiator(this);
       // arrayList = new ArrayList<>();
       arrayList = initiator.getAllNotes();
         noteListAdapter = new NoteListAdapter(arrayList,this);

        ListView tdListViewNoteFront = (ListView)findViewById(R.id.tdlstVwNoteFront);
        tdListViewNoteFront.setAdapter(noteListAdapter);


        //implement floating action button to add a new note

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCreator itemCreator = new ItemCreator();
                itemCreator.showItemCreator(MainActivity.this,arrayList,noteListAdapter);

                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                layoutInflater.inflate(R.layout.layout_todolist_front,null);
            }
        });
       // Log.d("Hi","Hi");

       //ToDoListDB toDoListDB = new ToDoListDB(this);
       // toDoListDB.createTables();
       // toDoListDB.fillMetaData();






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
