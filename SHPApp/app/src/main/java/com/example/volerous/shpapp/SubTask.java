package com.example.volerous.shpapp;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by KGMCD on 1/29/2018.
 */
@Entity
class SubTask {
    @PrimaryKey
    Integer id;
    String title;
    @Embedded
    Task parent_task;
    Boolean completed;
}
