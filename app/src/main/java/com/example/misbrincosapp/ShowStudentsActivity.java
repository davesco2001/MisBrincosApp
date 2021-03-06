package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdStudent;
import com.example.misbrincosapp.model.Lesson;
import com.example.misbrincosapp.model.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ShowStudentsActivity extends AppCompatActivity implements StudentsAdapter.ListItemClick{

    private static final int showValue = 7;
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    ArrayList<Student> students;
    BdStudent bdStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_students);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        recyclerView = findViewById(R.id.recycle_show_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                getStudentsToActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_show_students);
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

    private void getStudentsToActivity() {
        students = new ArrayList<Student>();
        //Loop that brings the lessons from db
        bdStudent = new BdStudent();
        if(bdStudent.getConnection()!=null){
            Toast.makeText(ShowStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> names= bdStudent.searchName();
            ArrayList<String> cc = bdStudent.searchCc();
            ArrayList<String> number = bdStudent.searchPhoneNumber();
            ArrayList<String> emailS = bdStudent.searchEmail();

            if((names.size()==cc.size())&&(names.size()==number.size())&&(names.size()==emailS.size())){
                for (int i = 0; i <names.size() ; i++) {
                    String name = names.get(i);
                    String  cedula = cc.get(i);
                    String  tel = number.get(i);
                    String  email = emailS.get(i);
                    Student Student = new Student( cedula,name, tel,email);
                    students.add(Student);
                }
                bdStudent.dropConnection();
                setStudentsAdpater(students);
            }else{
                Toast.makeText(ShowStudentsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ShowStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        //setStudentsAdpater();
    }

    private void setStudentsAdpater(ArrayList<Student> students) {
        StudentsAdapter studentsAdapter = new StudentsAdapter(students, this);
        recyclerView.setAdapter(studentsAdapter);
    }

    @Override
    public void onListItemClick(int clickedItem) {
        int size = students.size();
        for (int i = 0; i < size; i++) {
            if (i == clickedItem) {
                Student StudentClicked = students.get(i);
                String StudentClickedName = StudentClicked.getCc();
                //Intent with the key of the table
                Intent intent = new Intent(ShowStudentsActivity.this, ViewStudentActivity.class);
                intent.putExtra("CC", StudentClickedName);
                startActivity(intent);
            }
        }
    }
}