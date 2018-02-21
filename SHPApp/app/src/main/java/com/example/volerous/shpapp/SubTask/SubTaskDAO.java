package com.example.volerous.shpapp.SubTask;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by root on 2/15/18.
 */
@Dao
public interface SubTaskDAO {
    @Insert
    public void insertSubTasks(List<SubTask> subTasks);
    @Insert
    public void insertSubTask(SubTask subTask);
    @Delete
    public void deleteSubTask(SubTask subTask);
    @Update
    public void updateSubTask(SubTask subTask);
//    @Query("Select * from Subtask where (Select * from Task where Task.id = SubTask.parent_task_id AND Task.completed = 0)")
//    public SubTask[] getallSubtasks();
    @Query("select * from Subtask where subtask.parent_task_id = :id")
    public List<SubTask> getAllSubtasksForTask(Integer id);
}
