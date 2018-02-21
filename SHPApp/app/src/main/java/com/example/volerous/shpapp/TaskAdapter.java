package com.example.volerous.shpapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.volerous.shpapp.Task.Task;

import java.util.ArrayList;

/**
 * Created by root on 2/6/18.
 */

public class TaskAdapter extends ArrayAdapter {
    TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Task task = (Task) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }
        CheckBox task_item = convertView.findViewById(R.id.task_checkbox);
        TextView task_title = convertView.findViewById(R.id.task_title);
        assert task != null;
        task_title.setText(task.getTitle());
        task_item.setChecked(task.getCompleted());
        LinearLayout task_ll = convertView.findViewById(R.id.task_ll);
        task_ll.setBackgroundResource(R.color.md_amber_400);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"test",Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

}