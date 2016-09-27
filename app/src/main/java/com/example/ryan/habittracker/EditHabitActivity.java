package com.example.ryan.habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditHabitActivity extends AppCompatActivity implements Notifiable
{
    private static completionsListAdapter adapter;
    private ArrayList<Calendar> completionHist;
    private Habit habitToView;
    private int position;
    private ListView completionListView;
    HabitDataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);
        dataStore = HabitDataStore.getInstance();
        TextView textView = (TextView)findViewById(R.id.editHabitName);

        Intent intent = getIntent();
        position = (Integer)intent.getIntExtra("position", 0);
        habitToView = dataStore.getHabits().get(position);
        textView.setText(habitToView.getName());


        completionListView = (ListView) findViewById(R.id.completionsList);
        adapter = new completionsListAdapter(habitToView, this);
        completionListView.setAdapter(adapter);

    }

    @Override
    public void notifyDataChanged()
    {
        //Do nothing
    }

    public void back(View v)
    {
        dataStore.saveHabitHistory(this);
        finish();
    }

    public void deleteHabit(View v)
    {
        dataStore.removeHabit(habitToView, this);
        finish();
    }
}
