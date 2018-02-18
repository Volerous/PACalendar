package com.example.volerous.shpapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * Created by root on 2/15/18.
 */
@Dao
public interface TagDAO {
    @Insert
    public void putTask(Task task);
    @Delete
    public void deleteTask(Task task);
    @Update
    public void updateTask(Task task);
    @Query("select * from tag")
    public Tag[] getallTags();
    @Query("select * from tag where tag.id == :id")
    public Tag getTag();
}
