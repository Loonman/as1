package com.example.ryan.habittracker;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Ryan on 2016-09-13.
 */
public class HabitDataStore
{
    private static HabitDataStore dataStore = new HabitDataStore();
    private static final String FILE = "habits.json";
    private ArrayList<Habit> habits;
    private Gson gson;

    private HabitDataStore()
    {
        habits = new ArrayList<Habit>();
        gson = new Gson();
    }


    public static HabitDataStore getInstance()
    {
        return dataStore;
    }

    public void add(Habit habit)
    {
        this.habits.add(habit);
    }

    public void remove(Habit habitToRemove)
    {
        habits.remove(habitToRemove);
    }

    public ArrayList<Habit> getHabits()
    {
        if (this.habits == null)
        {
            this.habits = new ArrayList<Habit>();
        }
        return new ArrayList<Habit>(this.habits);
    }

    public void loadHabitHistory(Context context)
    {
        try
        {
            FileInputStream fileStream = context.openFileInput(FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fileStream));
            Gson gson = new Gson();
            Type habitListType = new TypeToken<ArrayList<Habit>>(){}.getType();

            habits = gson.fromJson(in, habitListType);

        }

        catch (FileNotFoundException e)
        {
        }

        catch (IOException e)
        {
            throw new RuntimeException();
        }

    }

    public void saveHabitHistory(Context context)
    {
        try
        {
            //0 means overwrite the entire file
            FileOutputStream foStream = context.openFileOutput(FILE, 0);

            OutputStreamWriter osWriter = new OutputStreamWriter(foStream);

            gson.toJson(this.habits, osWriter);

            osWriter.flush();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            throw new RuntimeException();
        }

    }

}
