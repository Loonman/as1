package com.example.ryan.habittracker;

/**
 * Created by Ryan on 2016-09-26.
 *
 * Interface to support the Observer pattern, used in EditHabitActivity and HabitListActivity
 * so that the HabitDataStore can notify them that the dataset has changed
 */
public interface Notifiable
{
    public void notifyDataChanged();
}
