package com.example.ryan.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditHabitActivity extends AppCompatActivity
{
    private static completionsListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);

    }
}