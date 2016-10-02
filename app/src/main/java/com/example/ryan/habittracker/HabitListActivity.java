package com.example.ryan.habittracker;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Main activity of the app, used to display habits to the user in 2 lists, all habits and todays
 * habits.
 *
 * This activity is also responsible for kicking off the other activites for adding habits and
 * deleting/modifying habits.
 */
public class HabitListActivity extends AppCompatActivity implements Notifiable
{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static ArrayList<Habit> habitList;
    private static ArrayList<Habit> todayHabitList;
    private static HabitDataStore dataStore;
    private static habitViewAdapter todayHabitsAdapter;
    private static habitViewAdapter allHabitsAdapter;
    private static final int TODAY_SECTION_NUMBER = 0;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        dataStore = HabitDataStore.getInstance();
        dataStore.loadHabitHistory(this);
        dataStore.addObserver(this);
        habitList = dataStore.getHabits();
        todayHabitList = this.getTodaysHabits();

        allHabitsAdapter = new habitViewAdapter(habitList, this);
        todayHabitsAdapter = new habitViewAdapter(todayHabitList, this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent myIntent = new Intent(getApplicationContext(), AddHabitActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(myIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_habit_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void notifyDataChanged()
    {
        this.habitList.clear();
        this.habitList.addAll(dataStore.getHabits());
        this.todayHabitList.clear();
        this.todayHabitList.addAll(getTodaysHabits());

        todayHabitsAdapter.notifyDataSetChanged();
        allHabitsAdapter.notifyDataSetChanged();
    }

    public ArrayList<Habit> getTodaysHabits()
    {
        ArrayList<Habit> todayHabits = new ArrayList<Habit>();
        int Today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        for (Habit habit: habitList)
        {
            if (habit.activeOn(Today))
            {
                todayHabits.add(habit);
            }
        }
        return todayHabits;
    }

    /**
     * Created by Ryan on 2016-09-23.
     * Custom Fragment for displaying a list of habits to the user
     */
    public static class HabitsFragment extends Fragment
    {

        private ListView habitsListView;
        private habitViewAdapter adapter;

        public HabitsFragment()
        {}

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static HabitsFragment newInstance(int sectionNumber)
        {
            HabitsFragment fragment = new HabitsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_habit_list, container, false);
            habitsListView = (ListView) rootView.findViewById(R.id.habitListView);
            habitsListView.setAdapter(adapter);

            habitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    //Generate an intent then open the detail view
                    Intent myIntent = new Intent(getActivity().getApplicationContext(), EditHabitActivity.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    myIntent.putExtra("Habit", (Habit)adapter.getItem(position));
                    getActivity().getApplicationContext().startActivity(myIntent);
                }
            });

            return rootView;
        }

        public void setAdapter(habitViewAdapter adapter)
        {
            this.adapter = adapter;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            HabitsFragment fragment = HabitsFragment.newInstance(position + 1);
            if (position == TODAY_SECTION_NUMBER)
            {
                fragment.setAdapter(todayHabitsAdapter);
            }
            else
            {
                fragment.setAdapter(allHabitsAdapter);
            }
            return fragment;
        }

        @Override
        public int getCount()
        {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0:
                    return "Today's Habits";
                case 1:
                    return "All Habits";
            }
            return null;
        }
    }
}
