package com.example.volerous.shpapp.Task;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.example.volerous.shpapp.SubTask.SubTask;

import java.util.List;

/**
 * Created by root on 2/19/18.
 */

public class TaskEntity {
    @Embedded
    public
    Task task;
    @Relation(parentColumn = "id", entityColumn = "parent_task_id")
    List<SubTask> subtasks;
}
