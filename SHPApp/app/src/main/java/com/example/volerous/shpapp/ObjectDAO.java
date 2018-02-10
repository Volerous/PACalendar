package com.example.volerous.shpapp;

import android.annotation.SuppressLint;
import android.net.Network;
import android.support.constraint.solver.Cache;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 2/9/18.
 */

public class ObjectDAO {
    private RequestQueue mRequestQueue;

    ObjectDAO (String type) {
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        this.mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        this.mRequestQueue.start();
        this.type
    }
    public void get(String type) throws JSONException, NullPointerException {
        final TextView mTextView = mTextView.findViewById();
        String url = "http://192.168.0.12:5000/_"+type;

        // Formulate the request and handle the response.
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mTextView.setText("success");
                            insertTasks(response.getJSONArray("todos"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        mTextView.setText("Failed");
                    }
                });
        // Add the request to the RequestQueue.
        this.mRequestQueue.add(jsObjRequest);
    }
    public JSONArray insert(String type) {
    }

}
