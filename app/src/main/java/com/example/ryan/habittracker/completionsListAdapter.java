package com.example.ryan.habittracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ryan on 2016-09-26.
 */
// Buttons in a listview example taken from http://stackoverflow.com/a/23021960
public class completionsListAdapter extends BaseAdapter implements ListAdapter
{
    private Habit habit;
    private Context context;

    public completionsListAdapter(Habit habit, Context context) {
        this.habit = habit;
        this.context = context;
    }

    @Override
    public int getCount() {
        return habit.getCompletions().size();
    }

    @Override
    public Object getItem(int pos) {
        return habit.getCompletions().get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.completions_list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.habitName);
        listItemText.setText(habit.getCompletions().toString());

        //Handle buttons and add onClickListeners
        Button addBtn = (Button)view.findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                habit.deleteCompletion(habit.getCompletions().get(position));
                notifyDataSetChanged();
                HabitDataStore.getInstance().saveHabitHistory(context);
            }
        });

        return view;
    }
}