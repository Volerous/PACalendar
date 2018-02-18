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
public interface SubTaskDAO {
    @Insert
    public void putSubTasks(SubTask... subTasks);
    @Insert
    public void putSubTask(SubTask subTask);
    @Delete
    public void deleteSubTask(SubTask subTask);
    @Update
    public void updateSubTask(SubTask subTask);
    @Query("Select * from subtask where subtask.parent_task.completed != false")
    public SubTask[] getallSubtasks();
    @Query("select * from subtask where subtask.id == :id")
    public SubTask getSubTask(Integer id);
}
