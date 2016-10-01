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
 *
 * HabitDataStore is a singleton class that acts as the gatekeeper to persistent storage of habits
 * for a user.
 *
 * The first activity to make use of the HabitDataStore should invoke the loadHabitHistory method
 * since the method requires a Context to access the filesystem.
 *
 * Maintains a static HabitDataStore to serve to classes which call getInstance()
 *
 * Maintains a list of Habits that are served to classes requesting habits through getHabits() as a copy
 *  -- This prevents consumers from editing the master list without the habitDataStore being aware
 *
 * This list can be edited through addHabit and removeHabit which directly interact with the internal
 * list
 *
 * HabitDataStore also acts as a controller, by calling addObserver and removeObserver a class which
 * implements Notifiable can then be notified of data changes by habitDataStore when it or another
 * class calls addHabit or removeHabit. This occurs through the notifyObservers method
 *
 * Idiosyncracies of HabitDataStore:
 * --To remove a completion of a habit one should use the getHabit method to ensure they have a reference
 *   to the internal habit of the data store and then call deleteCompletion on the completion
 * --The first activity to instantiate a datastore must call loadHabitHistory to get the persistent
 *   history
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

    //This can retrieve habits if we received a serialized copy or only have access to the UUID
    public Habit getHabit(Habit habitCopy) throws IOException
    {
        for (Habit habit: habits)
        {
            if (habit.equals(habitCopy))
            {
                return habit;
            }
        }
        throw new IOException();
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
