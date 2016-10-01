package com.example.ryan.habittracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Ryan on 2016-09-13.
 * 
 * Habit class for representing a user's habit
 * Contains:
 * a list of completions represented as as an ArrayList of calendars storing the exact time
 * that the habit was completed.
 *
 * The count of completions
 *
 * The name of the habit as supplied by the user
 *
 * The days of the week that the habit is to be completed, supplied by the user
 *
 * A uuid to uniquely identify each habit -- this allows for overriding equals and also letting a
 * user have two habits with the same name.
 */
public class Habit implements Serializable
{
    private ArrayList<Calendar> CompletionHistory;

    private int completionCount;

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

        this.completionCount = 0;
    }

    public String getName()
    {
        return this.name;
    }

    public Calendar getCreateTime()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(creationTime.getTime());
        return cal;
    }

    @Override
    public String toString()
    {
        return this.getName() + " " + String.valueOf(this.completionCount);
    }


    // When we pass habits between activities with an intent they must be serialized.
    // When deserialized it is no longer the same object as was passed in
    // To get around this we override equals such that it compares uuids
    @Override
    public boolean equals(Object otherHabit)
    {
        if (otherHabit ==  null || otherHabit.getClass() != getClass())
        {
            return false;
        }
        if (this.uuid.equals(((Habit)otherHabit).uuid))
        {
            return true;
        }
        if (this == otherHabit)
        {
            return true;
        }
        return false;
    }

    // Allows the user to set the creation time in case they want a habit to begin activity in the
    // future
    public void setCreationTime(Calendar cal)
    {
        this.creationTime = cal;
    }

    public int getCompletionCount()
    {
        return this.completionCount;
    }

    public ArrayList<Calendar> getCompletions()
    {
        return new ArrayList<Calendar>(this.CompletionHistory);
    }

    public void addCompletion()
    {
        // Getting a new instance of calendar allows us to get the current completion time with ease
        this.CompletionHistory.add(Calendar.getInstance());
        this.completionCount ++;
    }

    public void deleteCompletion(Calendar calendar)
    {
        this.CompletionHistory.remove(calendar);
        this.completionCount--;
    }

    // Determine if a habit is active on a day of the week -- Day should be passed in as one of the
    // static fields in Calendar such as Calendar.MONDAY
    public boolean activeOn(int Day)
    {
        return this.daysOfWeek.contains(Day);
    }

}
