package com.example.volerous.shpapp.Tag;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.volerous.shpapp.Task.Task;

/**
 * Created by root on 2/15/18.
 */
@Dao
public abstract class TagDAO {
    @Insert
    public abstract void putTask(Task task);
    @Delete
    public abstract void deleteTask(Task task);
    @Update
    public abstract void updateTask(Task task);
    @Query("select * from tag")
    public abstract Tag[] getallTags();
    @Query("select * from tag where tag.id = :id")
    public abstract Tag getTag(Integer id);
}
