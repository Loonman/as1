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
    private ArrayList<Notifiable> notifiables;
    private Gson gson;

    private HabitDataStore()
    {
        habits = new ArrayList<Habit>();
        notifiables = new ArrayList<Notifiable>();
        gson = new Gson();
    }


    public static HabitDataStore getInstance()
    {
        return dataStore;
    }

    public void addHabit(Habit habit, Context context)
    {
        this.habits.add(habit);
        this.saveHabitHistory(context);
        notifyObservers();
    }

    public void removeHabit(Habit habitToRemove, Context context)
    {
        ArrayList<Habit> habitsToRemove = new ArrayList<Habit>();
        for (Habit habit: habits)
        {
            if (habit.equals(habitToRemove))
            {
                habitsToRemove.add(habit);
            }
        }
        habits.removeAll(habitsToRemove);
        this.saveHabitHistory(context);
        notifyObservers();
    }

    public ArrayList<Habit> getHabits()
    {
        if (this.habits == null)
        {
            this.habits = new ArrayList<Habit>();
        }
        return new ArrayList<Habit>(this.habits);
    }

    public void addObserver(Notifiable n)
    {
        this.notifiables.add(n);
    }

    public void removeObserver(Notifiable n)
    {
        this.notifiables.remove(n);
    }

    private void notifyObservers()
    {
        for (Notifiable n: this.notifiables)
        {
            n.notifyDataChanged();
        }
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

            this.notifyObservers();
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
