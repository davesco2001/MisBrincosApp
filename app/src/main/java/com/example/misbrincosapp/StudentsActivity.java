package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.misbrincosapp.model.Function;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class StudentsActivity extends AppCompatActivity implements FunctionAdapter.ListItemClick {
    //CRUD
    private static final int createValue = 6;
    private static final int showValue = 7;
    private static final int editValue = 8;
    private static final int deleteValue = 9;
    //EXTERNAL FUNCTIONS
    private static final int addPackageToStudent = 10;
    //OTHER
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    ArrayList<Function> functionsAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        recyclerView = findViewById(R.id.recycle_functions_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentUser = mAuth.getCurrentUser();
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_students);
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
    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            getFunctionStudents();
        } else {
            finish();
        }
    }

    private void getFunctionStudents() {
        functionsAdmin = new ArrayList<Function>();
        functionsAdmin.add(new Function(getString(R.string.create_student), createValue));
        functionsAdmin.add(new Function(getString(R.string.show_students), showValue));
        functionsAdmin.add(new Function(getString(R.string.edit_student), editValue));
        functionsAdmin.add(new Function(getString(R.string.add_package), addPackageToStudent));
        FunctionAdapter functionAdapter = new FunctionAdapter(functionsAdmin, this);
        recyclerView.setAdapter(functionAdapter);
    }

    @Override
    public void onListItemClick(int clickedItem) {
        int size = functionsAdmin.size();
        for (int i = 0; i < size; i++) {
            if (i == clickedItem) {
                Function functionSelected = functionsAdmin.get(i);
                String name = functionSelected.getName();
                if (name.equals(getString(R.string.create_student))) {
                    Intent intent = new Intent(StudentsActivity.this, CreateStudentsActivity.class);
                    startActivity(intent);
                }
                if (name.equals(getString(R.string.show_students))) {
                    Intent intent = new Intent(StudentsActivity.this, ShowStudentsActivity.class);
                    startActivity(intent);
                }
                if (name.equals(getString(R.string.edit_student))) {
                    Intent intent = new Intent(StudentsActivity.this, EditStudentsActivity.class);
                    startActivity(intent);
                }
                if (name.equals(getString(R.string.add_package))) {
                    Intent intent = new Intent(StudentsActivity.this, AddPackageToStudentsActivity.class);
                    startActivity(intent);
                }
            }

        }
    }
}