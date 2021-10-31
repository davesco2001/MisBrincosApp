package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;

public class ViewLessonActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    TextView nameTextView;
    TextView limitTextView;
    TextView durationTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lesson);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
    }
    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                getLessonToActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            finish();
        }
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_view_lesson);
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
    private void getLessonToActivity() {
        //The empty text textview to fill with the information get by db
        nameTextView= findViewById(R.id.viewTextLessonName);
        durationTextView = findViewById(R.id.viewTextLessonDuration);
        limitTextView = findViewById(R.id.viewTextLessonLimit);
        //Get the key from ShowLessonsActivity
        Intent key= getIntent();
        String keyLesson = key.getExtras().getString("NAME");
        //Create 1 method for each search
        String name = searchLessonName(keyLesson);
        int duration= searchLessonDuration(keyLesson);
        int limit =searchLessonLimit(keyLesson);
        nameTextView.setText(name);
        limitTextView.setText(""+limit);
        durationTextView.setText(""+duration);
    }

    private int searchLessonLimit(String keyLesson) {
        BdLessons bdLessons= new BdLessons();
        int limitDaysOfCancelation = 0;
        if(bdLessons.getConnection()!=null){
            Toast.makeText(ViewLessonActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> limit = bdLessons.searchLessonLimitOfDays(keyLesson);
            if(1==limit.size()){
                for (int i = 0; i <limit.size() ; i++) {
                    limitDaysOfCancelation= limit.get(i);
                }
                bdLessons.dropConnection();
            }else{
                Toast.makeText(ViewLessonActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewLessonActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return limitDaysOfCancelation;
    }

    private int searchLessonDuration(String keyLesson) {
        BdLessons bdLessons= new BdLessons();
        int durationOfTheLesson = 0;
        if(bdLessons.getConnection()!=null){
            Toast.makeText(ViewLessonActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> duration = bdLessons.searchLessonDuration(keyLesson);
            if(1==duration.size()){
                for (int i = 0; i <duration.size() ; i++) {
                    durationOfTheLesson= duration.get(i);
                }
                bdLessons.dropConnection();
            }else{
                Toast.makeText(ViewLessonActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewLessonActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return durationOfTheLesson;
    }

    private String searchLessonName(String keyLesson) {
        BdLessons bdLessons= new BdLessons();
        String nameLesson = "";
        if(bdLessons.getConnection()!=null){
            Toast.makeText(ViewLessonActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> name = bdLessons.searchLessonName(keyLesson);
            if(1==name.size()){
                for (int i = 0; i <name.size() ; i++) {
                    nameLesson= name.get(i);
                }
                bdLessons.dropConnection();
            }else{
                Toast.makeText(ViewLessonActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewLessonActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return nameLesson;
    }
}