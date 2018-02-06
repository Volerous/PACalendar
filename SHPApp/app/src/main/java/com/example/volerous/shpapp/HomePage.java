package com.example.volerous.shpapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
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

import java.util.ArrayList;
import java.util.List;


public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Task> tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayList<Task> tasks = new ArrayList<Task>();
        TaskAdapter taskAdapter = new TaskAdapter(this, tasks);
        ListView task_lv = findViewById(R.id.task_list_view);
        task_lv.setAdapter(taskAdapter);
        Task task_to_insert = new Task("test",false,"123456",3,null,null,null);
        taskAdapter.add(task_to_insert);
        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    searchDB();
//                } catch (JSONException | NullPointerException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home_page_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void searchDB() throws JSONException, NullPointerException {
//        final TextView mTextView = findViewById(R.id.mTextView);
//        RequestQueue mRequestQueue;
//
//        // Instantiate the cache
//        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
//
//        // Set up the network to use HttpURLConnection as the HTTP client.
//        Network network = new BasicNetwork(new HurlStack());
//
//        // Instantiate the RequestQueue with the cache and network.
//        mRequestQueue = new RequestQueue(cache, network);
//
//        // Start the queue
//        mRequestQueue.start();
//
//        String url = "http://192.168.0.12:5000/_todo";
//
//        // Formulate the request and handle the response.
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            mTextView.setText("success");
//                            insertTasks(response.getJSONArray("todos"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        mTextView.setText("Failed");
//                    }
//                });
//        // Add the request to the RequestQueue.
//        mRequestQueue.add(jsObjRequest);
//    }

    private void insertTasks(JSONArray jarr) throws JSONException {
        List<Task> tasks = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < jarr.length() - 1; i++) {
            JSONObject jobj = jarr.getJSONObject(i);
            Integer id = jobj.getInt("id");
            String title = jobj.getString("title");
            Boolean completed = jobj.getBoolean("completed");
            String color = jobj.getString("color");
            String description = jobj.getString("description");
            Integer priority = jobj.getInt("priority");
//            LocalDateTime due_date = jobj.getString("due_date");
            JSONArray jtags = jobj.getJSONArray("tags");
            for (Integer j = 0; j < jtags.length() - 1; j++) {

                JSONObject jobj1 = jtags.getJSONObject(j);
                tags.add(new Tag(jobj1.getString("title"), jobj1.getString("color")));
            }
            tasks.add(new Task(id, title, completed, color, priority, tags, description, null));
            tags.clear();
        }
        this.tasks = tasks;
    }


    public void showClick(View view) {
        Context context = getApplicationContext();
        CharSequence toast_text = "Item clicked";

    }

    public void onItemClick(View view) {
        String selected = ((TextView) view.findViewById(R.id.task_list_view)).getText().toString();
        Toast.makeText(getApplicationContext(),selected,Toast.LENGTH_LONG).show();
    }
}
