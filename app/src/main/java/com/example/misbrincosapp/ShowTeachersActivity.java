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
import android.widget.EditText;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdStudent;
import com.example.misbrincosapp.BD.BdTeacher;
import com.example.misbrincosapp.model.Student;
import com.example.misbrincosapp.model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ShowTeachersActivity extends AppCompatActivity implements TeachersAdapter.ListItemClick {
    private static final int showValue = 7;
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    ArrayList<Teacher> teachers;
    BdTeacher bdteacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teachers);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        recyclerView = findViewById(R.id.recycle_show_teachers);
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
                getTeachersToActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_show_teachers);
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
    private void getTeachersToActivity() {
        teachers = new ArrayList<Teacher>();
        //Loop that brings the lessons from db
        bdteacher =  new BdTeacher();
        if(bdteacher.getConnection()!=null){
            Toast.makeText(ShowTeachersActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> names= bdteacher.searchName();
            ArrayList<String> cc = bdteacher.searchCc();
            ArrayList<String> emailS = bdteacher.searchEmail();

            if((names.size()==cc.size())&&(names.size()==emailS.size())) {
                Teacher Teacher = null;
                for (int i = 0; i < names.size(); i++) {
                    String name = names.get(i);
                    String cedula = cc.get(i);
                    String email = emailS.get(i);
                    Teacher = new Teacher(cedula, name, email);
                    teachers.add(Teacher);
                }
                bdteacher.dropConnection();
                setTeacherAdpater(teachers);
            }else{
                Toast.makeText(ShowTeachersActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ShowTeachersActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        //setStudentsAdpater();
    }

    private void setTeacherAdpater(ArrayList<com.example.misbrincosapp.model.Teacher> teachers) {
        TeachersAdapter teacherAdapter = new TeachersAdapter(teachers, this);
        recyclerView.setAdapter(teacherAdapter);
    }

    @Override
    public void onListItemClick(int clickedItem) {
        int size = teachers.size();
        for (int i = 0; i < size; i++) {
            if (i == clickedItem) {
                Teacher TeacherClicked = teachers.get(i);
                String TeacherClickedName =  TeacherClicked.getcC();
                //Intent with the key of the table
                Intent intent = new Intent(ShowTeachersActivity.this, ViewTeacherActivity.class);
                intent.putExtra("CC", TeacherClickedName);
                startActivity(intent);
            }
        }
    }
}