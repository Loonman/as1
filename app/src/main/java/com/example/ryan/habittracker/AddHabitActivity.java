package com.example.ryan.habittracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddHabitActivity extends AppCompatActivity
{
    private static Calendar creationTime;
    private static TextView dateText;
    private HabitDataStore dataStore;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        creationTime = Calendar.getInstance();
        dateText = (TextView)findViewById(R.id.activeDate);
        dataStore = HabitDataStore.getInstance();
        this.showDate();
    }

    //Adapted from https://developer.android.com/guide/topics/ui/controls/pickers.html
    public static class DatePickerFragment extends DialogFragment
                        implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            setDate(year, month, day);
            showDate();
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        this.showDate();
    }

    public static void setDate(int year, int month, int day)
    {
        creationTime.set(Calendar.YEAR, year);
        creationTime.set(Calendar.MONTH, month);
        creationTime.set(Calendar.DAY_OF_MONTH, day);
    }

    public static void showDate()
    {
        dateText.setText(dateFormat.format(creationTime.getTime()));
    }

    public void createHabit(View v)
    {
        EditText habitName = (EditText) findViewById(R.id.habitNameEdit);
        CheckBox sun = (CheckBox) findViewById(R.id.Sunday);
        CheckBox mon = (CheckBox) findViewById(R.id.Monday);
        CheckBox tue = (CheckBox) findViewById(R.id.Tuesday);
        CheckBox wed = (CheckBox) findViewById(R.id.Wednesday);
        CheckBox thu = (CheckBox) findViewById(R.id.Thursday);
        CheckBox fri = (CheckBox) findViewById(R.id.Friday);
        CheckBox sat = (CheckBox) findViewById(R.id.Saturday);

        ArrayList<Integer> daysOfWeek = new ArrayList<Integer>();
        if (sun.isChecked())
        {
            daysOfWeek.add(Calendar.SUNDAY);
        }
        if (mon.isChecked())
        {
            daysOfWeek.add(Calendar.MONDAY);
        }
        if (tue.isChecked())
        {
            daysOfWeek.add(Calendar.TUESDAY);
        }
        if (wed.isChecked())
        {
            daysOfWeek.add(Calendar.WEDNESDAY);
        }
        if (thu.isChecked())
        {
            daysOfWeek.add(Calendar.THURSDAY);
        }
        if (fri.isChecked())
        {
            daysOfWeek.add(Calendar.FRIDAY);
        }
        if (sat.isChecked())
        {
            daysOfWeek.add(Calendar.SATURDAY);
        }
        Habit newHabit = new Habit(habitName.getText().toString(), daysOfWeek);

        newHabit.setCreationTime(creationTime);
        dataStore.addHabit(newHabit, this);
        finish();
    }
    public void cancel(View v)
    {
        finish();
    }
}
