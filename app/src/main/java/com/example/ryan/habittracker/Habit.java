package com.example.ryan.habittracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Ryan on 2016-09-13.
 */
public class Habit implements Serializable
{
    private ArrayList<Calendar> CompletionHistory;

    private int completions;

    private String name;

    private Calendar creationTime;

    private ArrayList<Integer> daysOfWeek; //The days of the week that it should recur

    private UUID uuid;

    public Habit(String name, ArrayList<Integer> daysOfWeek)
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
        return this.creationTime.get(Calendar.MONTH) + " "  +
               this.creationTime.get(Calendar.DAY_OF_MONTH) + ", " +
               this.creationTime.get(Calendar.YEAR);
    }

    @Override
    public String toString()
    {
        return this.getName() + " " + String.valueOf(this.completions);
    }

    @Override
    public boolean equals(Object otherHabit)
    {
        if (otherHabit ==  null || otherHabit.getClass() != getClass())
        {
            return false;
        }
        if (this.uuid == ((Habit)otherHabit).uuid)
        {
            return true;
        }
        if (this == otherHabit)
        {
            return true;
        }
        return false;
    }

    public void addCompletion()
    {
        this.CompletionHistory.add(Calendar.getInstance());
        this.completions ++;
    }

    public void setCreationTime(int year, int month, int day)
    {
        this.creationTime.set(Calendar.YEAR, year);
        this.creationTime.set(Calendar.MONTH, month);
        this.creationTime.set(Calendar.DAY_OF_MONTH, day);

    }

}
