package com.example.volerous.shpapp.SubTask;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by KGMCD on 1/29/2018.
 */
@Entity
public class SubTask {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String title;
    public Integer parent_task_id;
    public Boolean completed;
}
