package com.example.volerous.shpapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KGMCD on 1/26/2018.
 */

public class Tag {
    public Integer id;
    public String title;
    public String color;

    public Tag(Integer id,String title, String color) {
        this.id = id;
        this.title = title;
        this.color = color;
    }
    Tag(String title, String color){
        this.title = title;
        this.color = color;
    }
    public void insert() {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("title", this.title);
            jobj.put("color", this.color);
        } catch (JSONException e) {
            e.getMessage();
        }
    }
    public void delete() {

    }
}
