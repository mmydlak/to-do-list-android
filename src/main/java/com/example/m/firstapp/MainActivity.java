package com.example.m.firstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView toDoRecycleList;
    private ToDoListAdapter adapter;
    private List<ToDoTask> taskList;
    static final int GET_TASK_REQUEST = 1;
    static final int EDIT_TASK_REQUEST = 2;
    private String sharedPrefKey = "main";

    public void save() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor sharedPrefEditor = sharedPrefs.edit();
        Gson gson = new Gson();

        String jsonTaskList = gson.toJson(taskList);

        sharedPrefEditor.putString(sharedPrefKey, jsonTaskList);
        sharedPrefEditor.commit();
    }

    private ArrayList<ToDoTask> restore() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String jsonTaskList = sharedPrefs.getString(sharedPrefKey, null);
        Type type = new TypeToken<ArrayList<ToDoTask>>() {}.getType();
        ArrayList<ToDoTask> arrayList = null;
        arrayList = gson.fromJson(jsonTaskList, type);
        return arrayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadTaskList();
//        String newTask = getIntent().getStringExtra("newTask");
//        if(newTask != null && newTask.length()!=0) {
//            taskList.add(new Gson().fromJson(newTask, ToDoTask.class));
//        }

        toDoRecycleList = findViewById(R.id.toDoList);
        adapter = new ToDoListAdapter(taskList, this);
        toDoRecycleList.setLayoutManager(new LinearLayoutManager(this));
        toDoRecycleList.setAdapter(adapter);

        //Toast.makeText(this, "LISTA onCreate()", Toast.LENGTH_LONG).show();

    }

    void loadTaskList() {
        this.taskList = new ArrayList<>();
        if (restore() != null) {
            taskList.clear();
            taskList.addAll(restore());
        }
//        this.taskList.add(new ToDoTask("Zad1", "opis1", new GregorianCalendar(1990,12,12).getTime()));
//        this.taskList.add(new ToDoTask("Zad2", "sa", new GregorianCalendar(2100,12,12).getTime()));
//        this.taskList.add(new ToDoTask("Zad3", "aaaaaaa", new GregorianCalendar(2300,12,12).getTime()));
//        this.taskList.add(new ToDoTask("Zad 4", "sadas", new GregorianCalendar(190,12,12).getTime()));
//        this.taskList.add(new ToDoTask("Zad 5", "sadas", new GregorianCalendar(11990,12,12).getTime()));
//        this.taskList.add(new ToDoTask("Zad 6", "sadas", new GregorianCalendar(2020,12,12).getTime()));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "LISTA onRestart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this, "LISTA onStart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "LISTA onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        save();
        //Toast.makeText(this, "LISTA onPause()", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        //Toast.makeText(this, "LISTA onStop()", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //Toast.makeText(this, "LISTA onDestroy()", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    public void handleOnClick(View view) {
        switch(view.getId()){
            case R.id.addNewTaskBtn:
                openNewTaskActivity(GET_TASK_REQUEST);
                break;
            default:
                break;
        }
    }

    public void openNewTaskActivity(int requestCode){
        Intent intent = new Intent(this, TaskEditActivity.class);
        startActivityForResult(intent, requestCode);
    }

    public void openEditTaskActivity(int requestCode, int position){
        Intent intent = new Intent(this, TaskEditActivity.class);
        intent.putExtra("position", position);
        String jsonTask = new Gson().toJson(taskList.get(position));
        intent.putExtra("taskToEdit", jsonTask);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_TASK_REQUEST) {
            if(resultCode == RESULT_OK){
                String newTask = data.getStringExtra("newTask");
                taskList.add(new Gson().fromJson(newTask, ToDoTask.class));
                adapter.notifyDataSetChanged();
                //Toast.makeText(this, "OnActivityCreate", Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == EDIT_TASK_REQUEST){
            if(resultCode == RESULT_OK){
                String task = data.getStringExtra("newTask");
                int position = data.getIntExtra("position", -1);
                taskList.set(position, new Gson().fromJson(task, ToDoTask.class));
                adapter.notifyDataSetChanged();
                //Toast.makeText(this, "OnActivityCreate - edit", Toast.LENGTH_LONG).show();
            }
        }
    }

}
