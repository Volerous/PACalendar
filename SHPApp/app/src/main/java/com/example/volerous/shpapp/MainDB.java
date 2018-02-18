package com.example.volerous.shpapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by root on 2/15/18.
 */
@Database(entities = {Task.class, SubTask.class}, version = 1)
public abstract class MainDB extends RoomDatabase{
    public abstract TaskDAO taskDAO();
    public abstract SubTaskDAO subTaskDAO();
}
