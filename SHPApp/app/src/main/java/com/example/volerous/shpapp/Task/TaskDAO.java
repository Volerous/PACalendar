package com.example.volerous.shpapp.Task;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by root on 2/15/18.
 */
@Dao
public abstract class TaskDAO {
    @Insert
    public abstract void insertTask(Task task);
    @Update
    public abstract void updateTask(Task task);
    @Delete
    public abstract void deleteTask(Task task);
    @Query("SELECT id,title,completed,color,priority,description FROM Task where Task.completed = 0")
    @Transaction
    public abstract List<TaskEntity> getAllTasks();
    @Query("select * from Task where Task.id = :id")
    public abstract Task getTask(Integer id);

}
