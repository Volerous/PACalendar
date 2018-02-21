package com.example.volerous.shpapp.Task;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.volerous.shpapp.SubTask.SubTask;
import com.example.volerous.shpapp.Tag.Tag;

import java.time.LocalDateTime;
import java.util.List;



/**
 * Created by KGMCD on 1/26/2018.
 * Task Class definition
 */
@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String title;
    private Boolean completed;
    private String color;
    private Integer priority;
//    @Ignore
//    private List<Tag> tags;
    private String description;
    @Ignore
    private String due_date;
    @Ignore
    private List<SubTask> subtasks;

    public Task(Integer id, String title, Boolean completed, String color, Integer priority,
         List<Tag> tags, String description, String due_date) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.color = color;
        this.priority = priority;
//        this.tags = tags;
        this.description = description;
        this.due_date = due_date;
    }

    public Task(String title, Boolean completed, String color, Integer priority,
                List<Tag> tags, String description, String due_date) {
        this.title = title;
        this.completed = completed;
        this.color = color;
        this.priority = priority;
//        this.tags = tags;
        this.description = description;
        this.due_date = due_date;
    }

    public Task(String title, Boolean completed, String color, Integer priority,
                String description, String due_date) {
        this.title = title;
        this.completed = completed;
        this.color = color;
        this.priority = priority;
        this.description = description;
        this.due_date = due_date;
    }

    Task() {
    }

//    public void insert() {
//        JSONObject insert_json = new JSONObject();
//        try {
//            insert_json.put("title", this.title);
//            insert_json.put("completed", this.completed);
//            insert_json.put("color", this.color);
//            insert_json.put("due_date", this.due_date);
//            insert_json.put("priority", this.priority);
//            insert_json.put("description", this.description);
//        } catch (JSONException e) {
//            e.getMessage();
//        }
//    }
//
//    public void delete() {
//    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

//    public List<Tag> getTags() {
//        return this.tags;
//    }
//
//    public void setTags(List<Tag> tags) {
//        this.tags = tags;
//    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SubTask> getSubtasks() {
        return this.subtasks;
    }

    public void setSubtasks(List<SubTask> subtasks) {
        this.subtasks = subtasks;
    }


}
