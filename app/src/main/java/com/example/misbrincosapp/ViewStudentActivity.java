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
import com.example.misbrincosapp.BD.BdStudent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ViewStudentActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    TextView nameTextView;
    TextView numberTextView;
    TextView ccTextView;
    TextView emailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
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
                getStudentToActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }

    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_view_student);
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
    private void getStudentToActivity() {
        //The empty text textview to fill with the information get by db
        nameTextView= findViewById(R.id.viewTextStudentName);
        ccTextView = findViewById(R.id.viewTextStudentCc);
        numberTextView = findViewById(R.id.viewTextStudentPhone);
        emailTextView = findViewById(R.id.viewTextStudentEmail);
        //In this method you take the key that was send by the Intent in the ShowStudentsActivity.java
        Intent key= getIntent();
        String keyStudent = key.getExtras().getString("NAME");
        //The key its the same as the key of the table
        String name = searchStudentName(keyStudent);
        String cc= searchStudentCc(keyStudent);
        String number =searchStudentPhone(keyStudent);
        String Email = searchStudentEmail(keyStudent);

        nameTextView.setText(name);
        ccTextView.setText(cc);
        numberTextView.setText(""+number);
        emailTextView.setText(""+Email);
    }

    private String searchStudentName(String keyStudent){
        BdStudent bdStudent= new BdStudent();
        String nameStudent = "";
        if(bdStudent.getConnection()!=null){
            Toast.makeText(ViewStudentActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> name = bdStudent.searchStudentName(keyStudent);
            if(1==name.size()){
                for (int i = 0; i <name.size() ; i++) {
                    nameStudent= name.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(ViewStudentActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewStudentActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return nameStudent;
    }
    private String searchStudentCc(String keyStudent){
        BdStudent bdStudent= new BdStudent();
        String ccStudent = "";
        if(bdStudent.getConnection()!=null){
            Toast.makeText(ViewStudentActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> cc = bdStudent.searchStudentCc(keyStudent);
            if(1==cc.size()){
                for (int i = 0; i <cc.size() ; i++) {
                    ccStudent= cc.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(ViewStudentActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewStudentActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return ccStudent;
    }
    private String searchStudentPhone(String keyStudent){
        BdStudent bdStudent= new BdStudent();
        String PhoneStudent = "";
        if(bdStudent.getConnection()!=null){
            Toast.makeText(ViewStudentActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> number = bdStudent.searchStudentPhoneNumber(keyStudent);
            if(1==number.size()){
                for (int i = 0; i <number.size() ; i++) {
                    PhoneStudent= number.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(ViewStudentActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewStudentActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return PhoneStudent;
    }

    private String searchStudentEmail(String keyStudent){
        BdStudent bdStudent= new BdStudent();
        String emailStudent = "";
        if(bdStudent.getConnection()!=null){
            Toast.makeText(ViewStudentActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> email = bdStudent.searchStudentEmail(keyStudent);
            if(1==email.size()){
                for (int i = 0; i <email.size() ; i++) {
                    emailStudent= email.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(ViewStudentActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewStudentActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return emailStudent;
    }
}