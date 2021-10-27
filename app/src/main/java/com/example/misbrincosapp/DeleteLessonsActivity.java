package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdStudent;
import com.example.misbrincosapp.model.Lesson;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DeleteLessonsActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button deleteButton;
    AutoCompleteTextView autoCompleteTextViewCcLesson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_lessons);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        deleteButton = (Button) findViewById(R.id.button_delete_lesson);
        onClickDelete(deleteButton);
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_delete_lessons);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.Toolbar_TitleText);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //Get info of student selected
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                setNameLessonsOptions();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
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
    private void onClickDelete(Button deleteButton) {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextViewCcLesson = findViewById(R.id.inputDeleteLessonName);
                //Use this to do something with the key of the db when you have a autoCompleteTextView
                String lessonName=autoCompleteTextViewCcLesson.getText().toString();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    BdLessons bdLessons = new BdLessons();
                    if(bdLessons.getConnection()!=null){
                        Toast.makeText(DeleteLessonsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                        bdLessons.deleteLesson(lessonName);
                        bdLessons.dropConnection();
                        finish();

                    }else{
                        Toast.makeText(DeleteLessonsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void setNameLessonsOptions() {
        int size=0;
        ArrayList<String> names = new ArrayList<String>();
        BdLessons bdLessons = new BdLessons();
        if(bdLessons.getConnection()!=null){
            Toast.makeText(DeleteLessonsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            names= bdLessons.searchName();
            size=names.size();
        }else{
            Toast.makeText(DeleteLessonsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] LESSONSNAME = new String[size];
        for (int i = 0; i <size ; i++) {
            String name = names.get(i);
            LESSONSNAME[i]=name;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LESSONSNAME);
        AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                findViewById(R.id.inputDeleteLessonName);
        ccStudent.setAdapter(adapter);
    }
}