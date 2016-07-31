package com.jalaj.firstapp.todolist.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jalaj.firstapp.todolist.Initiator;
import com.jalaj.firstapp.todolist.R;
import com.jalaj.firstapp.todolist.adapter.CategoryListAdapter;
import com.jalaj.firstapp.todolist.database.ToDoListDB;
import com.jalaj.firstapp.todolist.model.CategoryTypes;

import java.util.ArrayList;

public class CategorySelectionAndAdd extends AppCompatActivity {
    GridView tdFrontGridView;
    ArrayList arrayList;
    CategoryListAdapter categoryListAdapter;
    private int itemPosition;
    private static boolean firstTime=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ToDoListDB toDoListDB = new ToDoListDB(this);
        toDoListDB.createTables();
        toDoListDB.fillMetaData();

        setContentView(R.layout.activity_category_selection_and_add);
        tdFrontGridView = (GridView)findViewById(R.id.tdGridViewFront);
        Initiator initiator = Initiator.getInstance(this);
        arrayList = initiator.getAllCategoryTypes();
        categoryListAdapter = new CategoryListAdapter(arrayList,this);
        tdFrontGridView.setAdapter(categoryListAdapter);
        registerForContextMenu(tdFrontGridView);
       // Log.d("I am here", "OnCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstTime)
        categoryListAdapter.notifyDataSetChanged();
        else firstTime = false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        itemPosition = ((AdapterView.AdapterContextMenuInfo)menuInfo).position;
        if (itemPosition!=0)
        menu.add(1,1,1,"Move to Archive...");


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        if (item.getItemId()==1 && itemPosition != 0) {

            //Log.d("Hiii",(((CategoryTypes)categoryListAdapter.getItem(itemPosition))).getCategory_id()+"");
            CategoryListAdapter categoryListAdapter = CategoryListAdapter.getInstance();
            ToDoListDB toDoListDB = new ToDoListDB(this);
            toDoListDB.updateData(ToDoListDB.TD_NOTE_CATEGORY_TBL,new String[][]{{"category_id","1"}}," category_id=? ",new String[]{((CategoryTypes)categoryListAdapter.getItem(itemPosition)).getCategory_id()+""});
            toDoListDB.deleteData(ToDoListDB.TD_CATEGORY_TBL," category_id=? ",new String[]{((CategoryTypes)categoryListAdapter.getItem(itemPosition)).getCategory_id()+""});

            categoryListAdapter.refreshArrayList();
            categoryListAdapter.notifyDataSetChanged();

        }
        else
            Toast.makeText(this,"You cannot delete archives",Toast.LENGTH_LONG).show();
        return super.onContextItemSelected(item);
        }


    }

