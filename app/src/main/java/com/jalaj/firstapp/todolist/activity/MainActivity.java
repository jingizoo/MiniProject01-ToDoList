package com.jalaj.firstapp.todolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jalaj.firstapp.todolist.R;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, CategorySelectionAndAdd.class);
        startActivity(intent);
}
}
