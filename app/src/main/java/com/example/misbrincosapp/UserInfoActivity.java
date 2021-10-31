package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdStudent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class UserInfoActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    TextView name;
    TextView email;
    TextView lessons;
    TextView totalOfLessons;
    TextView totalTimeOfPackage;
    TextView packageName;
    String studentId;
    Button logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        logOut=findViewById(R.id.log_out);
        onClickLogOut(logOut);
    }
    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getUid().equals("XIEfOnMpi7dsmtV6mT7JEGpJCiC3")) {
                getAdminInfo();
            } else {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    studentId = getStudentIdWithEmail(currentUser.getEmail());
                    getNormalUserInfo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            finish();
        }
    }
    private String getStudentIdWithEmail(String email){
        BdStudent bdStudent= new BdStudent();
        String cc = "";
        if(bdStudent.getConnection()!=null){
            Toast.makeText(UserInfoActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> ccs = bdStudent.searchStudentCcWithEmail(email);
            if(1==ccs.size()){
                for (int i = 0; i <ccs.size() ; i++) {
                    cc= ccs.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(UserInfoActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(UserInfoActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return cc;
    }
    private String getStudentName(){
        BdStudent bdStudent= new BdStudent();
        String name = "";
        if(bdStudent.getConnection()!=null){
            Toast.makeText(UserInfoActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> names = bdStudent.searchStudentCc(studentId);
            if(1==names.size()){
                for (int i = 0; i <names.size() ; i++) {
                    name= names.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(UserInfoActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(UserInfoActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return name;
    }
    private int getStudentTotalOfLessons(){
        BdStudent bdStudent= new BdStudent();
        int total = 0;
        if(bdStudent.getConnection()!=null){
            Toast.makeText(UserInfoActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> totals = bdStudent.searchStudentTotalOfLesson(studentId);
            if(1==totals.size()){
                for (int i = 0; i <totals.size() ; i++) {
                    total= totals.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(UserInfoActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(UserInfoActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return total;
    }
    private String getStudentLesson(){
        BdStudent bdStudent= new BdStudent();
        StringBuilder lesson = new StringBuilder();
        if(bdStudent.getConnection()!=null){
            Toast.makeText(UserInfoActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> lessons = bdStudent.searchStudentLesson(studentId);
            if(1==lessons.size()){
                for (int i = 0; i <lessons.size() ; i++) {
                    lesson.append(lessons.get(i)).append(", ");
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(UserInfoActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(UserInfoActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return lesson.toString();
    }
    private Date getDateExpiredPackage(){
        BdStudent bdStudent= new BdStudent();
        Date date = null;
        if(bdStudent.getConnection()!=null){
            Toast.makeText(UserInfoActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Date> dates = bdStudent.searchStudentDateExpiredPackage(studentId);
            if(1==dates.size()){
                for (int i = 0; i <dates.size() ; i++) {
                    date = dates.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(UserInfoActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(UserInfoActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return date;
    }
    private String getStudentPackagePlan(){
        BdStudent bdStudent= new BdStudent();
        String plan = "";
        if(bdStudent.getConnection()!=null){
            Toast.makeText(UserInfoActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> plans = bdStudent.searchStudentPackagePlan(studentId);
            if(1==plans.size()){
                for (int i = 0; i <plans.size() ; i++) {
                    plan= plans.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(UserInfoActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(UserInfoActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return plan;
    }
    @NotNull
    private Calendar getDateOfToday() {
        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        return today;
    }
    private long totalOfDays(){
        long days= 0;
        Calendar today = getDateOfToday();
        Date expDate= getDateExpiredPackage();
        Calendar expCalDate= new GregorianCalendar();
        expCalDate.setTime(expDate);
        if((today.getTime()).equals(expCalDate.getTime())){
            days = 1;
            return days ;
        }else{
            if((today.getTime()).after(expCalDate.getTime())){
                days = 0;
                return  days;
            }else {
                long daysBetween = Duration.between((today.getTime().toInstant()),(expCalDate.getTime().toInstant())).toDays();
                days= daysBetween;
                return days;
            }
        }
    }
    private void getNormalUserInfo() {
        name= findViewById(R.id.userName);
        name.setText(getStudentName());
        email = findViewById(R.id.userEmail);
        email.setText(currentUser.getEmail());
        lessons = findViewById(R.id.userLessons);
        lessons.setText(getStudentLesson());
        totalOfLessons = findViewById(R.id.userTotalOfLessons);
        totalOfLessons.setText(""+getStudentTotalOfLessons());
        totalTimeOfPackage = findViewById(R.id.userTotalTimeOfPackage);
        totalTimeOfPackage.setText(""+totalOfDays()+" days");
        packageName = findViewById(R.id.userPackage);
        packageName.setText(getStudentPackagePlan());
    }

    private void getAdminInfo() {
        name= findViewById(R.id.userName);
        name.setText("ADMIN");
        email = findViewById(R.id.userEmail);
        email.setText(currentUser.getEmail());

    }

    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_user_info);
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
    public void onClickLogOut(Button logOut){
        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
            }
        });
    }
}