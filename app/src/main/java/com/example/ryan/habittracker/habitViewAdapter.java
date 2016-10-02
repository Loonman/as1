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

/**
 * Created by Ryan on 2016-09-26.
 * Custom adapter for displaying list items with buttons
 * Buttons in a listview example taken from http://stackoverflow.com/a/23021960
 */
//
public class habitViewAdapter extends BaseAdapter implements ListAdapter
{
    private ArrayList<Habit> list = new ArrayList<Habit>();
    private Context context;

    public habitViewAdapter(ArrayList<Habit> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
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
            view = inflater.inflate(R.layout.habits_list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.habitName);
        listItemText.setText(list.get(position).getName());

        //Handle buttons and addHabit onClickListeners
        Button addBtn = (Button)view.findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            addCompletion(position);
            }
        });

        return view;
    }

    private void addCompletion(int position)
    {
        list.get(position).addCompletion();
        notifyDataSetChanged();
        HabitDataStore.getInstance().saveHabitHistory(context);
    }
}