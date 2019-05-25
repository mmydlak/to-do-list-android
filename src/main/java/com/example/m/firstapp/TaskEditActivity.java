package com.example.m.firstapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskEditActivity extends AppCompatActivity {

    private TextView titleView;
    private TextView descView;
    private TextView dateView;
    private int position;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    static final int RETURN_TASK_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleView = findViewById(R.id.titleView);
        descView = findViewById(R.id.descView);
        dateView = findViewById(R.id.dateView);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                dateView.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        position = getIntent().getIntExtra("position", -1);
        if(position != -1){
            ToDoTask toEditTask = new Gson().fromJson(getIntent().getStringExtra("taskToEdit"), ToDoTask.class);
            titleView.setText(toEditTask.getTitle());
            descView.setText(toEditTask.getDescription());
            dateView.setText(new SimpleDateFormat("dd/M/yyyy").format(toEditTask.getDate()));
        }

    }

    public ToDoTask parseInfoToTask(){
        String title = titleView.getText().toString();
        String desc = descView.getText().toString();

        Date date = null;
        String dateInString = dateView.getText().toString();
        try {
            date = new SimpleDateFormat("dd/M/yyyy").parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new ToDoTask(title, desc, date);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "onRestart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this, "onStart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        //Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        //Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


    public void updateDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dateDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
        dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dateDialog.show();
    }

    public void passTask(View view) {
        if(titleView.getText().length() == 0 || dateView.getText().length() == 0) {
            Toast.makeText(this, "Task title and date cannot be empty!", Toast.LENGTH_LONG).show();
        }
        else {
            ToDoTask task = parseInfoToTask();
            String jsonTask = new Gson().toJson(task);
            Intent intent = new Intent();
            intent.putExtra("newTask", jsonTask);
            intent.putExtra("position", position);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
