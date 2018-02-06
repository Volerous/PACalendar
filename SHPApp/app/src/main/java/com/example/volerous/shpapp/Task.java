package com.example.volerous.shpapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Created by KGMCD on 1/26/2018.
 * Task Class definition
 */
public class Task {
    private Integer id;
    private String title;
    private Boolean completed;
    private String color;
    private Integer priority;
    private List<Tag> tags;
    private String description;
    private LocalDateTime due_date;
    private List<SubTask> subtasks;

    Task(Integer id, String title, Boolean completed, String color, Integer priority,
         List<Tag> tags, String description, LocalDateTime due_date) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.color = color;
        this.priority = priority;
        this.tags = tags;
        this.description = description;
        this.due_date = due_date;
    }

    Task(String title, Boolean completed, String color, Integer priority,
         List<Tag> tags, String description, LocalDateTime due_date) {
        this.title = title;
        this.completed = completed;
        this.color = color;
        this.priority = priority;
        this.tags = tags;
        this.description = description;
        this.due_date = due_date;
    }

    Task() {
    }

    public void insert() {
        JSONObject insert_json = new JSONObject();
        try {
            insert_json.put("title", this.title);
            insert_json.put("completed", this.completed);
            insert_json.put("color", this.color);
            insert_json.put("due_date", this.due_date);
            insert_json.put("priority", this.priority);
            insert_json.put("description", this.description);
        } catch (JSONException e) {
            e.getMessage();
        }
    }

    public void delete() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public String getColor() {
        return this.color;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public String getDescription() {
        return this.description;
    }

    public List<SubTask> getSubtasks() {
        return this.subtasks;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubtasks(List<SubTask> subtasks) {
        this.subtasks = subtasks;
    }


}
