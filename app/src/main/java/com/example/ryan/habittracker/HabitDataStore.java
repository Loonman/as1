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
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Ryan on 2016-09-13.
 */
public class HabitDataStore
{
    private static HabitDataStore dataStore = new HabitDataStore();
    private static final String FILE = "habits.json";
    private ArrayList<HabitHistory> habits;
    private Gson gson;
    private HabitDataStore(){ gson = new Gson(); }


    public static HabitDataStore getInstance()
    {
        return dataStore;
    }

    public ArrayList<HabitHistory> getHabitHistory(Context context)
    {
        try
        {
            FileInputStream fileStream = context.openFileInput(FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fileStream));
            Gson gson = new Gson();
            Type habitListType = new TypeToken<ArrayList<HabitHistory>>(){}.getType();

            habits = gson.fromJson(in, habitListType);

            return new ArrayList<HabitHistory>(habits);
        }
        catch (FileNotFoundException e)
        {
            return new ArrayList<HabitHistory>();
        }
        catch (IOException e)
        {
            throw new RuntimeException();
        }

    }

    public void writeHabitHistory(Context context, ArrayList<HabitHistory> habitsList)
    {
        try
        {
            //0 means overwrite the entire file, because calculating diffs is stupid
            FileOutputStream foStream = context.openFileOutput(FILE, 0);

            OutputStreamWriter osWriter = new OutputStreamWriter(foStream);

            gson.toJson(habitsList, osWriter);
            osWriter.flush();
        }
        catch (FileNotFoundException e)
        {
            //Do nothing
        }
        catch (IOException e)
        {
            throw new RuntimeException();
        }

    }

}
