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
import com.example.misbrincosapp.BD.BdTeacher;

import java.util.ArrayList;

public class ViewTeacherActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    TextView nameTextView;
    TextView ccTextView;
    TextView emailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teacher);
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
                getTeacherToActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }

    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_view_teacher);
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
    private void getTeacherToActivity (){
        //The empty text textview to fill with the information get by db
        nameTextView= findViewById(R.id.viewTextTeacherName);
        ccTextView = findViewById(R.id.viewTextTeacherCc);
        emailTextView = findViewById(R.id.viewTextTeacherEmail);
        //In this method you take the key that was send by the Intent in the ShowStudentsActivity.java
        Intent key= getIntent();
        String keyTeacher = key.getExtras().getString("CC");
        //The key its the same as the key of the table
        String name = searchTeacherName(keyTeacher);
        String cc= searchTeacherCc(keyTeacher);
        String Email = searchTeacherEmail(keyTeacher);

        nameTextView.setText(name);
        ccTextView.setText(cc);
        emailTextView.setText(Email);
    }

    private String searchTeacherName(String keyTeacher){
        BdTeacher bdTeacher= new BdTeacher();
        String nameTeacher = "";
        if(bdTeacher.getConnection()!=null){
            Toast.makeText(ViewTeacherActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> name = bdTeacher.searchTeacherName(keyTeacher);
            if(1==name.size()){
                for (int i = 0; i <name.size() ; i++) {
                    nameTeacher= name.get(i);
                }
                bdTeacher.dropConnection();
            }else{
                Toast.makeText(ViewTeacherActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewTeacherActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return nameTeacher;
    }
    private String searchTeacherCc(String keyTeacher){
        BdTeacher bdTeacher= new BdTeacher();
        String ccTeacher = "";
        if(bdTeacher.getConnection()!=null){
            Toast.makeText(ViewTeacherActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> cc = bdTeacher.searchTeacherCc(keyTeacher);
            if(1==cc.size()){
                for (int i = 0; i <cc.size() ; i++) {
                    ccTeacher= cc.get(i);
                }
                bdTeacher.dropConnection();
            }else{
                Toast.makeText(ViewTeacherActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewTeacherActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return ccTeacher;
    }

    private String searchTeacherEmail(String keyTeacher){
        BdTeacher bdTeacher= new BdTeacher();
        String emailTeacher = "";
        if(bdTeacher.getConnection()!=null){
            Toast.makeText(ViewTeacherActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> email = bdTeacher.searchTeacherEmail(keyTeacher);
            if(1==email.size()){
                for (int i = 0; i <email.size() ; i++) {
                    emailTeacher= email.get(i);
                }
                bdTeacher.dropConnection();
            }else{
                Toast.makeText(ViewTeacherActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewTeacherActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return emailTeacher;
    }
}