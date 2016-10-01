package com.example.ryan.habittracker;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ryan on 2016-10-01.
 */

public class HabitDataStoreTest extends ActivityInstrumentationTestCase2
{
    public HabitDataStoreTest()
    {
        super(com.example.ryan.habittracker.HabitListActivity.class);
    }

    public void testSingleton()
    {
        HabitDataStore ds1 = HabitDataStore.getInstance();
        HabitDataStore ds2 = HabitDataStore.getInstance();
        assertEquals(ds1, ds2);
    }
    @UiThreadTest
    public void testAddHabit()
    {
        HabitDataStore dataStore = HabitDataStore.getInstance();
        dataStore.emptyStore(getActivity());
        ArrayList<Integer> days = new ArrayList<Integer>();
        days.add(Calendar.MONDAY);
        Habit habit = new Habit("Test", days);
        dataStore.addHabit(habit, getActivity());

        assertEquals(habit, dataStore.getHabits().get(0));
    }
    @UiThreadTest
    public void testRemoveHabit()
    {
        HabitDataStore dataStore = HabitDataStore.getInstance();
        dataStore.emptyStore(getActivity());
        ArrayList<Integer> days = new ArrayList<Integer>();
        days.add(Calendar.MONDAY);
        Habit habit = new Habit("Test", days);
        dataStore.addHabit(habit, getActivity());
        dataStore.removeHabit(habit, getActivity());

        assertTrue(dataStore.getHabits().isEmpty());
    }
    @UiThreadTest
    public void testGetHabit()
    {
        HabitDataStore dataStore = HabitDataStore.getInstance();
        dataStore.emptyStore(getActivity());
        ArrayList<Integer> days = new ArrayList<Integer>();
        days.add(Calendar.MONDAY);
        Habit habit = new Habit("Test", days);
        dataStore.addHabit(habit, getActivity());
        try
        {
            assertEquals(habit, dataStore.getHabit(habit));
        }
        catch(IOException e)
        {
        //do nothing
        }
    }

    @UiThreadTest
    public void testThrowsIOException()
    {
        HabitDataStore dataStore = HabitDataStore.getInstance();
        dataStore.emptyStore(getActivity());
        ArrayList<Integer> days = new ArrayList<Integer>();
        days.add(Calendar.MONDAY);
        Habit habit = new Habit("Test", days);
        dataStore.addHabit(habit, getActivity());
        Habit habit2 = new Habit("Test2", days);
        try
        {
            dataStore.getHabit(habit2);
        }
        catch (IOException e)
        {
            assertTrue(Boolean.TRUE);
        }
    }

    public void testEmptyStore()
    {
        HabitDataStore dataStore = HabitDataStore.getInstance();
        dataStore.emptyStore(getActivity());

        assertTrue(dataStore.getHabits().isEmpty());
    }

}
