package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.model.Lesson;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ShowLessonsActivity extends AppCompatActivity implements LessonsAdapter.ListItemClick {

    private static final int showValue = 7;
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    ArrayList<Lesson> lessons;
    BdLessons bdLessons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lessons);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        recyclerView = findViewById(R.id.recycle_show_lessons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentUser = mAuth.getCurrentUser();
    }
    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            getLessonsToActivity();
        } else {
            finish();
        }
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_show_lessons);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.Toolbar_TitleText);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void onBackClick() {
        finish();
    }

    private void getLessonsToActivity() {
        lessons = new ArrayList<Lesson>();
        //Loop that brings the lessons from db
        bdLessons = new BdLessons();
        if(bdLessons.getConnection()!=null){
            Toast.makeText(ShowLessonsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> names= bdLessons.searchName();
            ArrayList<Integer> durations = bdLessons.searchDuration();
            ArrayList<Integer> limits = bdLessons.searchLimitOfDays();
            if((names.size()==durations.size())&&(names.size()==limits.size())){
                for (int i = 0; i <names.size() ; i++) {
                    String name = names.get(i);
                    int duration = durations.get(i);
                    int limit = limits.get(i);
                    Lesson lesson = new Lesson(name, duration, limit);
                    lessons.add(lesson);
                }
                bdLessons.dropConnection();
                setLessonsAdapter(lessons);
            }else{
                Toast.makeText(ShowLessonsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ShowLessonsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }

    }
    private void setLessonsAdapter(ArrayList<Lesson> lessons){
        LessonsAdapter lessonsAdapter = new LessonsAdapter(lessons, this);
        recyclerView.setAdapter(lessonsAdapter);
    }
    @Override
    public void onListItemClick(int clickedItem) {
        int size = lessons.size();
        for (int i = 0; i < size; i++) {
            if (i == clickedItem) {
                Lesson lessonClicked = lessons.get(i);
                String lessonClickedName=lessonClicked.getName();
                //Intent with the key of the table
                Intent intent = new Intent(ShowLessonsActivity.this, ViewLessonActivity.class);
                intent.putExtra("NAME", lessonClickedName);
                startActivity(intent);
            }

        }
    }
}