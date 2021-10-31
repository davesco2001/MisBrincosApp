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
import com.example.misbrincosapp.BD.BdStudent;
import com.example.misbrincosapp.BD.BdTeacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateTeachersActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button createButton;
    EditText nameTeacher;
    EditText cc;
    EditText email;
    BdTeacher bdTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_teachers);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        createButton = (Button) findViewById(R.id.button_create_teachers);
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
        toolbar = findViewById(R.id.toolbar_create_teachers);
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
                if(correctInputs) { if(correctInputs){
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        dbInteraction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(CreateTeachersActivity.this, R.string.bad_inputs, Toast.LENGTH_SHORT).show();
                }
                }
            }
        })
        ;}
    private void dbInteraction() {
        nameTeacher = (EditText) findViewById(R.id.inputTeachersName);
        cc = (EditText) findViewById(R.id.inputTeachersCc);
        email = (EditText) findViewById(R.id.inputTeachersEmail);
        String name = nameTeacher.getText().toString();
        String emailT = email.getText().toString();
        String  cedulaT = cc.getText().toString();

        bdTeacher = new BdTeacher();
        if(bdTeacher.getConnection()!=null){
            Toast.makeText(CreateTeachersActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            bdTeacher.addTeacher(name ,cedulaT , emailT);
            bdTeacher.dropConnection();
            finish();

        }else{
            Toast.makeText(CreateTeachersActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validationInputs() {
        nameTeacher = (EditText) findViewById(R.id.inputTeachersName);
        cc = (EditText) findViewById(R.id.inputTeachersCc);
        email = (EditText) findViewById(R.id.inputTeachersEmail);
        String nameInput=nameTeacher.getText().toString();
        //Validate that inputs are empty
        if((nameTeacher.getText().toString().equals(""))&&(nameInput.length()>60)&&(cc.getText().toString().equals(""))&&(cc.length()>10)&&(email.getText().toString().equals("")&&(email.length()>30))){
            return false;
        }else{
            return true;
        }
    }
}


