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
public interface TaskDAO {
    @Insert
    public void putTasks(Task... tasks);
    @Insert
    public void putTask(Task task);
    @Update
    public void updateTask(Task task);
    @Delete
    public void deleteTask(Task task);
    @Query("SELECT * FROM Task where task.completed != false")
    public Task[] getAllTasks();
    @Query("select * from task where task.id == :id")
    public Task getTask(Integer id);
}
