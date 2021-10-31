package com.example.misbrincosapp;

import android.app.Activity;
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
import com.example.misbrincosapp.BD.BdSessions;
import com.example.misbrincosapp.BD.BdStudent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.misbrincosapp.BD.BdTeacher;

import java.util.ArrayList;

public class DeleteTeachersActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button deleteButton;
    AutoCompleteTextView autoCompleteTextViewIdTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_teachers);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        deleteButton = (Button) findViewById(R.id.button_delete_teacher);
        onClickDelete(deleteButton);
    }

    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_delete_teachers);
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
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                setccTeacherOptions();
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
                autoCompleteTextViewIdTeacher = findViewById(R.id.inputDeleteTeacherCc);
                //Use this to do something with the key of the db when you have a autoCompleteTextView
                String ccTeacherString = autoCompleteTextViewIdTeacher.getText().toString();
                String ccTeacher = ccTeacherString;
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    BdTeacher bdTeacher = new BdTeacher();
                    if (bdTeacher.getConnection() != null) {
                        Toast.makeText(DeleteTeachersActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                        bdTeacher.deleteTeacher(ccTeacher);
                        bdTeacher.dropConnection();
                        finish();

                    } else {
                        Toast.makeText(DeleteTeachersActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setccTeacherOptions() {
        int size = 0;
        ArrayList<String> cc = new ArrayList<String>();
        BdTeacher bdTeacher = new BdTeacher();
        if (bdTeacher.getConnection() != null) {
            Toast.makeText(DeleteTeachersActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            cc = bdTeacher.searchCc();
            size = cc.size();
        } else {
            Toast.makeText(DeleteTeachersActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] TEACHERSCC = new String[size];
        for (int i = 0; i < size; i++) {
            String ccT = "" + cc.get(i);
            TEACHERSCC[i] = ccT;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, TEACHERSCC);
        AutoCompleteTextView ccTeachers = (AutoCompleteTextView)
                findViewById(R.id.inputDeleteTeacherCc);
        ccTeachers.setAdapter(adapter);
        //Change options
    }
}
