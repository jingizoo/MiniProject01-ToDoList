package com.jalaj.firstapp.todolist.listener;

import android.view.ContextMenu;
import android.view.View;

/**
 * Created by jalajmehta on 7/30/16.
 */

public class CategoryLongClickMenu implements View.OnLongClickListener, View.OnCreateContextMenuListener{
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1,1,1,"Delete...");
    }

    @Override
    public boolean onLongClick(View v) {

        return false;
    }
}
