package com.example.volerous.shpapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Network;
import android.support.constraint.solver.Cache;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.volerous.shpapp.Tag.Tag;
import com.example.volerous.shpapp.Task.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/9/18.
 */

public class ObjectDAO {
    private RequestQueue mRequestQueue;
    private JSONArray jarr;

    ObjectDAO (String type, Context context) {
        com.android.volley.Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        com.android.volley.Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        this.mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        this.mRequestQueue.start();
    }
    private JSONArray _get(String type) throws JSONException, NullPointerException {
        String url = "http://192.168.0.12:5000/_"+type;
        // Formulate the request and handle the response.
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jarr = response.getJSONArray("todos");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        // Add the request to the RequestQueue.
        this.mRequestQueue.add(jsObjRequest);
        return jarr;
    }
//    public JSONArray insert(String type) {
//    }
    public List<Task> getTodo()throws JSONException{
        JSONArray jarr = _get("todo");
        List<Task> tasks = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < jarr.length(); i++) {
            JSONObject jobj = jarr.getJSONObject(i);
            Integer id = jobj.getInt("id");
            String title = jobj.getString("title");
            Boolean completed = jobj.getBoolean("completed");
            String color = jobj.getString("color");
            String description = jobj.getString("description");
            Integer priority = jobj.getInt("priority");
            String due_date = jobj.getString("due_date");
            JSONArray jtags = jobj.getJSONArray("tags");
            for (Integer j = 0; j < jtags.length() - 1; j++) {

                JSONObject jobj1 = jtags.getJSONObject(j);
                tags.add(new Tag(jobj1.getString("title"), jobj1.getString("color")));
            }
            tasks.add(new Task(id,title,completed,color,priority,tags,description,due_date));
        }
        return tasks;
    }
    public JSONArray getTags() throws  JSONException{
        JSONArray jarr = _get("tag");
        return jarr;
    }
//    public JSONArray getTagsByPart(String part)throws JSONException {
//        JSONArray jarr = _get("string");
//    }
}
