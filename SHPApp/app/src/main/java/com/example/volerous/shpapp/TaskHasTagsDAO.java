package com.example.volerous.shpapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.volerous.shpapp.Tag.Tag;

import java.util.List;

/**
 * Created by root on 2/19/18.
 */
@Dao
public abstract class TaskHasTagsDAO {
    @Insert
    public abstract void insertTaskHasTags(TaskHasTags taskHasTags);
    @Delete
    public  abstract void deleteTaskHasTags(TaskHasTags taskHasTags);
    @Update
    public abstract void updateTaskHasTags(TaskHasTags taskHasTags);
    @Query("Select id,title,color from Tag INNER JOIN task_has_tags on task_has_tags.task_id = tag.id AND tag.id = :id")
    public abstract List<Tag> getTagsForTask(Integer id);
}
