package com.example.ryan.habittracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Ryan on 2016-09-13.
 */
public class HabitHistory implements Serializable
{
    private ArrayList<Calendar> CompletionHistory;

    private String name;

    private Calendar creationTime;

    private ArrayList<Integer> daysOfWeek; //The days of the week that it should recur

    public HabitHistory(String name, ArrayList<Integer> daysOfWeek)
    {
        this.name = name;

        this.daysOfWeek = daysOfWeek;

        this.creationTime = Calendar.getInstance(); //The current time
    }

    public String getName()
    {
        return this.name;
    }

    public String getCreateTime()
    {
        return this.creationTime.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.CANADA) + " "  +
               this.creationTime.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.LONG, Locale.CANADA) + ", " +
               this.creationTime.getDisplayName(Calendar.YEAR, Calendar.LONG, Locale.CANADA);
    }

    public void addCompletion()
    {
        this.CompletionHistory.add(Calendar.getInstance());
    }

}
