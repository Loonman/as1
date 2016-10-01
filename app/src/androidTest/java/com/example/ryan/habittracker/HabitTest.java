package com.example.ryan.habittracker;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ryan on 2016-10-01.
 */

public class HabitTest extends ActivityInstrumentationTestCase2
{
    public HabitTest()
    {
        super(com.example.ryan.habittracker.HabitListActivity.class);
    }

    public void testGetName()
    {
        ArrayList<Integer> days = new ArrayList<Integer>();
        days.add(Calendar.MONDAY);
        Habit habit = new Habit("Test", days);
        assertEquals("Test", habit.getName());
    }

    public void testActiveOn()
    {
        ArrayList<Integer> days = new ArrayList<Integer>();
        days.add(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        Habit habit = new Habit("Test", days);
        assertTrue(habit.activeOn(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)));
    }

    public void testGetCompletionCount()
    {
        ArrayList<Integer> days = new ArrayList<Integer>();
        days.add(Calendar.MONDAY);
        Habit habit = new Habit("Test", days);
        assertEquals(0, habit.getCompletionCount());
    }

    public void testAddCompletion()
    {
        ArrayList<Integer> days = new ArrayList<Integer>();
        days.add(Calendar.MONDAY);
        Habit habit = new Habit("Test", days);
        habit.addCompletion();
        assertEquals(1, habit.getCompletionCount());
    }

    public void testRemoveCompletion()
    {
        ArrayList<Integer> days = new ArrayList<Integer>();
        days.add(Calendar.MONDAY);
        Habit habit = new Habit("Test", days);
        habit.addCompletion();
        habit.deleteCompletion(habit.getCompletions().get(0));
        assertEquals(0, habit.getCompletionCount());
    }

    public void testGetCompletions()
    {
        ArrayList<Integer> days = new ArrayList<Integer>();
        days.add(Calendar.MONDAY);
        Habit habit = new Habit("Test", days);
        habit.addCompletion();
        assertEquals(1, habit.getCompletions().size());
    }

    public void testGetCreationTime()
    {
        ArrayList<Integer> days = new ArrayList<Integer>();
        days.add(Calendar.MONDAY);
        Habit habit = new Habit("Test", days);
        Calendar test = Calendar.getInstance();
        habit.setCreationTime(test);
        assertEquals(test, habit.getCreateTime());
    }
}
