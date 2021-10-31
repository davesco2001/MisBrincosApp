package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateLessonsActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button createButton;
    EditText nameLesson;
    EditText durationLesson;
    EditText limitLesson;
    BdLessons bdLessons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lessons);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        createButton = (Button) findViewById(R.id.button_create_lessons);
        onClickCreate(createButton);
    }
    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
        } else {
            finish();
        }
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_create_lessons);
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
    private void onClickCreate(Button createButton) {
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correctInputs = validationInputs();
                if(correctInputs){
                    Toast.makeText(CreateLessonsActivity.this, R.string.creating_lesson, Toast.LENGTH_SHORT).show();
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        dbInteraction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(CreateLessonsActivity.this, R.string.bad_inputs, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void dbInteraction() {
        nameLesson = (EditText) findViewById(R.id.inputLessonssName);
        durationLesson = (EditText) findViewById(R.id.inputLessonDuration);
        limitLesson = (EditText) findViewById(R.id.inputLessonLimit);
        String name = nameLesson.getText().toString();
        int duration = Integer.parseInt(durationLesson.getText().toString());
        int limit = Integer.parseInt(limitLesson.getText().toString());
        bdLessons = new BdLessons();
        if(bdLessons.getConnection()!=null){
            Toast.makeText(CreateLessonsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            bdLessons.addLesson(name, duration, limit);
            bdLessons.dropConnection();
            finish();

        }else{
            Toast.makeText(CreateLessonsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validationInputs() {
        nameLesson = (EditText) findViewById(R.id.inputLessonssName);
        durationLesson = (EditText) findViewById(R.id.inputLessonDuration);
        limitLesson = (EditText) findViewById(R.id.inputLessonLimit);
        String nameInput=nameLesson.getText().toString();
        //Validate that inputs are empty
        if((nameLesson.getText().toString().equals(""))&&(nameInput.length()>10)&&(durationLesson.getText().toString().equals(""))&&(limitLesson.getText().toString().equals(""))){
            return false;
        }else{
            return true;
        }
    }
}