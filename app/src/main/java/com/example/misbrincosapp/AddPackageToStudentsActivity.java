package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddPackageToStudentsActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button addPackageToStudentButton;
    private static final String[] STUDENTSCC= new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };;
    private static final String[] PACKAGEID= new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };;
    private static final String[] LESSONSNAMES= new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_package_to_students);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        addPackageToStudentButton = (Button) findViewById(R.id.button_add_package_to_student);
        onClickAdd(addPackageToStudentButton);
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_add_package_to_student);
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
            setCcStudentOptions();
            setIdPackageOptions();
            setNameLessonOptions();
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
    private void onClickAdd(Button createButton) {
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void setCcStudentOptions() {
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STUDENTSCC);
        AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                findViewById(R.id.inputPakcageToStudentsCc);
        ccStudent.setAdapter(adapter);
    }
    private void setIdPackageOptions() {
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PACKAGEID);
        AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                findViewById(R.id.inputPackageToStudentId);
        ccStudent.setAdapter(adapter);
    }
    private void setNameLessonOptions() {
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LESSONSNAMES);
        MultiAutoCompleteTextView ccStudent = findViewById(R.id.inputPackageToStudentName);
        ccStudent.setAdapter(adapter);
        ccStudent.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}