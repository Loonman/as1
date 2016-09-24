package com.example.ryan.habittracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Ryan on 2016-09-13.
 */
public class HabitHistory implements Serializable
{
    private ArrayList<Calendar> CompletionHistory;

    private int completions;

    private String name;

    private Calendar creationTime;

    private ArrayList<Integer> daysOfWeek; //The days of the week that it should recur

    private UUID uuid;

    public HabitHistory(String name, ArrayList<Integer> daysOfWeek)
    {
        this.name = name;

        this.daysOfWeek = daysOfWeek;

        this.creationTime = Calendar.getInstance(); //The current time

        this.uuid = UUID.randomUUID();

        this.CompletionHistory = new ArrayList<Calendar>();

        this.completions = 0;
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

    @Override
    public String toString()
    {
        return this.getName() + " " + String.valueOf(this.completions);
    }

    public void addCompletion()
    {
        this.CompletionHistory.add(Calendar.getInstance());
        this.completions ++;
    }

}
