package com.example.volerous.shpapp.Tag;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KGMCD on 1/26/2018.
 */
@Entity
public class Tag {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String title;
    public String color;
    @Ignore
    public Tag(String title, String color) {

    }

    public Tag(Integer id,String title, String color) {
        this.id = id;
        this.title = title;
        this.color = color;
    }
//    Tag(String title, String color){
//        this.title = title;
//        this.color = color;
//    }
//    public void insert() {
//
//    }
//    public void delete() {
//
//    }
//    public JSONObject genObject() {
//        JSONObject jobj = new JSONObject();
//        try {
//            jobj.put("title", this.title);
//            jobj.put("color", this.color);
//        } catch (JSONException e) {
//            e.getMessage();
//        }
//        return jobj;
//    }
}
