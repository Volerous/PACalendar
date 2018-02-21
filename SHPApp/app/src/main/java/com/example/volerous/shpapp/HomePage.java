package com.example.volerous.shpapp;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.volerous.shpapp.SubTask.SubTask;
import com.example.volerous.shpapp.SubTask.SubTaskDAO;
import com.example.volerous.shpapp.Task.Task;
import com.example.volerous.shpapp.Task.TaskDAO;
import com.example.volerous.shpapp.Task.TaskEntity;

import java.util.ArrayList;
import java.util.List;


public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TaskAdapter taskAdapter;
    private TaskDAO taskDAO;
    private SubTaskDAO subTaskDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainDB mainDB = Room.databaseBuilder(getApplicationContext(),MainDB.class, "mainDB").allowMainThreadQueries().build();
        this.taskDAO = mainDB.taskDAO();
        this.subTaskDAO = mainDB.subTaskDAO();
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayList<Task> tasks = new ArrayList<Task>();
        this.taskAdapter = new TaskAdapter(this, tasks);
        ListView task_lv = findViewById(R.id.task_list_view);
        task_lv.setAdapter(this.taskAdapter);
        final Task task_to_insert = new Task("test",false,"123456",3,null,null);
        this.taskDAO.insertTask(task_to_insert);
        for (Task task: getAllTasks()){
            this.taskDAO.deleteTask(task);
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDAO.insertTask(task_to_insert);
                taskAdapter.add(task_to_insert);
            }
        });
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





    public List<Task> getAllTasks(){
        List<Task> ret = new ArrayList<Task>();
        List<TaskEntity> tasks = this.taskDAO.getAllTasks();
        for (TaskEntity task: tasks){
            List<SubTask> subTasks = this.subTaskDAO.getAllSubtasksForTask(task.task.getId());
            task.task.setSubtasks(subTasks);
//            List<Tag> tags = this.taskHasTagsDAO.getTagsForTask(task.task.getId());
//            task.task.setTags(tags);
            ret.add(task.task);
        }
        return ret;
    }
}
