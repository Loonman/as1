package com.example.ryan.habittracker;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ryan on 2016-09-13.
 */
public class HabitDataStore
{
    private static HabitDataStore dataStore = new HabitDataStore();
    private static final String file = "habits.sav";
    private HabitDataStore() {}

    public static HabitDataStore getInstance()
    {
        return dataStore;
    }

    public ArrayList<HabitHistory> getHabitHistory()
    {
        //lol nerd figure out how to get my objects off the FS
        return null;
    }

    public void writeHabitHistory(ArrayList<HabitHistory> habitsList)
    {
        //Serialize the habits and put it on the FS
    }

}
