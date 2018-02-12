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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 2/9/18.
 */

public class ObjectDAO {
    private RequestQueue mRequestQueue;

    ObjectDAO (String type, Context context) {
        com.android.volley.Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        com.android.volley.Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        this.mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        this.mRequestQueue.start();
    }
    public void get(String type,JSONObject jobj) throws JSONException, NullPointerException {
        String url = "http://192.168.0.12:5000/_"+type;

        // Formulate the request and handle the response.
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        // Add the request to the RequestQueue.
        this.mRequestQueue.add(jsObjRequest);
    }
//    public JSONArray insert(String type) {
//    }

}
