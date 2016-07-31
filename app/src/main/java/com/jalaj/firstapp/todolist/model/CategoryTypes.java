package com.jalaj.firstapp.todolist.model;

import android.content.Context;

import com.jalaj.firstapp.todolist.database.ToDoListDB;

/**
 * Created by jalajmehta on 7/25/16.
 */

public class CategoryTypes implements  Comparable<CategoryTypes>{
    int category_id;
    String description;

    public CategoryTypes(int category_id, String description) {
        this.category_id = category_id;
        this.description = description;
    }
    public CategoryTypes(String description, int category_id ) {
        this.category_id = category_id;
        this.description = description;
    }
    public CategoryTypes(int category_id) {
        this.category_id = category_id;

    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(CategoryTypes another) {
        return (this.getCategory_id()+"").compareTo(another.getCategory_id()+"");
    }

    public String toString() {
        return description;
    }

    public void createNewCategory(Context finCtx){


        ToDoListDB toDoListDB = new ToDoListDB(finCtx);
        toDoListDB.insertDataToTable(ToDoListDB.TD_CATEGORY_TBL,new String[][]{{"description",getDescription()+""}});

    }
}
