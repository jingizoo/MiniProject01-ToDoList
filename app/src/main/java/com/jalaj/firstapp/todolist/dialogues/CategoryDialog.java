package com.jalaj.firstapp.todolist.dialogues;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jalaj.firstapp.todolist.Initiator;
import com.jalaj.firstapp.todolist.R;
import com.jalaj.firstapp.todolist.adapter.CategoryListAdapter;
import com.jalaj.firstapp.todolist.database.ToDoListDB;
import com.jalaj.firstapp.todolist.model.CategoryTypes;

/**
 * Created by jalajmehta on 7/30/16.
 */

public class CategoryDialog {

    public void showCategoryDialog(Context ctx)
    {
        final Context finCtx = ctx;
        AlertDialog.Builder aDB = new AlertDialog.Builder(ctx);
        final AlertDialog aD = aDB.create();
        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_category_add_dialog,null);
        Button btnAddNewCategory = (Button)view.findViewById(R.id.btnAddNewCategory);
        final EditText tdTxtNewCategory = (EditText)view.findViewById(R.id.tdTxtNewCategory);
        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Initiator.getInstance(finCtx).lookUpCategoryIdByDescription(tdTxtNewCategory.getText()+"") == -1 && tdTxtNewCategory.getText().toString().compareTo("")!=0)
                {
                    CategoryTypes categoryTypes = new CategoryTypes(-2, tdTxtNewCategory.getText() + "");
                    categoryTypes.createNewCategory(finCtx);
                    Initiator.getInstance(finCtx).getAllCategoryTypes();
                    CategoryListAdapter categoryListAdapter = CategoryListAdapter.getInstance();
                    categoryListAdapter.refreshArrayList();
                }
                else {
                    Toast.makeText(finCtx,"Category Already Exists",Toast.LENGTH_SHORT).show();

                }
                aD.dismiss();
            }
        });
        aD.setView(view);
        aD.show();

    }
}
