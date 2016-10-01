package com.example.ryan.habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditHabitActivity extends AppCompatActivity implements Notifiable
{
    private static completionsListAdapter adapter;
    private Habit habitToView;
    private ListView completionListView;
    private TextView habitName;
    private TextView completionCount;
    private HabitDataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);
        dataStore = HabitDataStore.getInstance();
        dataStore.addObserver(this);
        habitName = (TextView)findViewById(R.id.editHabitName);
        completionCount = (TextView)findViewById(R.id.completionsNumber);

        Intent intent = getIntent();
        try
        {
            habitToView = dataStore.getHabit((Habit)intent.getSerializableExtra("Habit"));
        }
        catch (IOException e)
        {
            throw new RuntimeException();
        }
        habitName.setText(habitToView.getName());
        completionCount.setText(String.valueOf(habitToView.getCompletionCount()));


        completionListView = (ListView) findViewById(R.id.completionsList);
        adapter = new completionsListAdapter(habitToView, this);
        completionListView.setAdapter(adapter);

    }

    @Override
    public void notifyDataChanged()
    {
        completionCount.setText(String.valueOf(habitToView.getCompletionCount()));
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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dataStore.removeObserver(this);
    }
}
