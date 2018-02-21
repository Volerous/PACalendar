package com.example.volerous.shpapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import com.example.volerous.shpapp.Tag.Tag;
import com.example.volerous.shpapp.Task.Task;

/**
 * Created by root on 2/19/18.
 */
@Entity(tableName = "task_has_tags",
        primaryKeys = {"task_id","tag_id"}, foreignKeys = {
        @ForeignKey(entity = Task.class, parentColumns = "id", childColumns = "task_id"),
        @ForeignKey(entity = Tag.class,parentColumns = "id",childColumns = "tag_id")})
class TaskHasTags {
    @ColumnInfo(name = "task_id")
    @NonNull
    final Integer task_id;
    @ColumnInfo(name = "tag_id")
    @NonNull
    final Integer tag_id;

    TaskHasTags(final Integer task_id, final Integer tag_id){
        this.task_id = task_id;
        this.tag_id = tag_id;
    }
}
