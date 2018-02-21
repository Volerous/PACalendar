package com.example.volerous.shpapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.volerous.shpapp.SubTask.SubTask;
import com.example.volerous.shpapp.SubTask.SubTaskDAO;
import com.example.volerous.shpapp.Tag.Tag;
import com.example.volerous.shpapp.Tag.TagDAO;
import com.example.volerous.shpapp.Task.Task;
import com.example.volerous.shpapp.Task.TaskDAO;

/**
 * Created by root on 2/15/18.
 */
@Database(entities = {Task.class, SubTask.class, Tag.class,TaskHasTags.class}, version = 1)
public abstract class MainDB extends RoomDatabase{
    public abstract TaskDAO taskDAO();
    public abstract SubTaskDAO subTaskDAO();
    public abstract TagDAO tagDAO();
    public abstract TaskHasTagsDAO taskHasTagsDAO();
}
