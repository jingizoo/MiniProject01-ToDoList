package com.jalaj.firstapp.todolist.model;

/**
 * Created by jalajmehta on 7/25/16.
 */

public class CategoryTypes {
    int category_id;
    String description;

    public CategoryTypes(int category_id, String description) {
        this.category_id = category_id;
        this.description = description;
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
}
