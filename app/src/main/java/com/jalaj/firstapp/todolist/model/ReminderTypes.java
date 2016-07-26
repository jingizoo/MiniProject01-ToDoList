package com.jalaj.firstapp.todolist.model;

/**
 * Created by jalajmehta on 7/25/16.
 */

public class ReminderTypes {
    int reminder_id;
    String description;

    public ReminderTypes(int reminder_id, String description) {
        this.reminder_id = reminder_id;
        this.description = description;
    }

    public int getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(int reminder_id) {
        this.reminder_id = reminder_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
