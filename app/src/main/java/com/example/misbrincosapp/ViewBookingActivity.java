package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdBookings;
import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdStudent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ViewBookingActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    TextView lessonName;
    TextView bookingId;
    TextView bookingDate;
    TextView bookingDay;
    TextView bookingHour;
    TextView classRoomNumber;
    TextView teacherName;
    String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);
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
                studentId = getStudentIdWithEmail(currentUser.getEmail());
                getBookingToActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            finish();
        }
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_view_lesson);
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
    private String getStudentIdWithEmail(String email){
        BdStudent bdStudent= new BdStudent();
        String cc = "";
        if(bdStudent.getConnection()!=null){
            Toast.makeText(ViewBookingActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> ccs = bdStudent.searchStudentCcWithEmail(email);
            if(1==ccs.size()){
                for (int i = 0; i <ccs.size() ; i++) {
                    cc= ccs.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(ViewBookingActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewBookingActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return cc;
    }
    private void getBookingToActivity() {
        lessonName = findViewById(R.id.viewTextBookingLessonName);
        bookingId = findViewById(R.id.viewTextBookingId);
        bookingDate = findViewById(R.id.viewTextBookingDate);
        bookingDay = findViewById(R.id.viewTextBookingDay);
        bookingHour = findViewById(R.id.viewTextBookingHour);
        classRoomNumber = findViewById(R.id.viewTextBookingClassroom);
        teacherName = findViewById(R.id.viewTextBookingTeacher);

        Intent key= getIntent();
        int keyBooking = Integer.parseInt(key.getExtras().getString("ID"));

        String lessonNameString = searchLessonName(keyBooking);
        //int duration= searchLessonDuration(keyBooking);
        //int limit =searchLessonLimit(keyBooking);
        String date = (String) DateFormat.format("yyyy-MM-dd", searchDate(keyBooking));
        String day = searchDay(keyBooking);
        String hour = (String) DateFormat.format("hh:mm:ss", searchHour(keyBooking));
        int classroom = searchClassroomNumber(keyBooking); 
        String teacherNames = searchTeacherName(keyBooking);
        
        lessonName.setText(lessonNameString);
        bookingId.setText(keyBooking);
        bookingDate.setText(date);
        bookingDay.setText(day);
        bookingHour.setText(hour);
        classRoomNumber.setText(classroom);
        teacherName.setText(teacherNames);
        
    }

    private String searchTeacherName(int keyBooking) {
        BdBookings bdBookings= new BdBookings();
        String nameTeacher = "";
        if(bdBookings.getConnection()!=null){
            Toast.makeText(ViewBookingActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> name = bdBookings.searchTeacherName(keyBooking);
            if(1==name.size()){
                for (int i = 0; i <name.size() ; i++) {
                    nameTeacher= name.get(i);
                }
                bdBookings.dropConnection();
            }else{
                Toast.makeText(ViewBookingActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewBookingActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return nameTeacher;
    }

    private int searchClassroomNumber(int keyBooking) {
        BdBookings bdBookings= new BdBookings();
        int classroom = 0;
        if(bdBookings.getConnection()!=null){
            Toast.makeText(ViewBookingActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> name = bdBookings.searchClassroomNumber(keyBooking);
            if(1==name.size()){
                for (int i = 0; i <name.size() ; i++) {
                    classroom= name.get(i);
                }
                bdBookings.dropConnection();
            }else{
                Toast.makeText(ViewBookingActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewBookingActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return classroom;
    }

    private Time searchHour(int keyBooking) {
        BdBookings bdBookings= new BdBookings();
        Time hour = null;
        if(bdBookings.getConnection()!=null){
            Toast.makeText(ViewBookingActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Time> name = bdBookings.searchHour(keyBooking);
            if(1==name.size()){
                for (int i = 0; i <name.size() ; i++) {
                    hour= name.get(i);
                }
                bdBookings.dropConnection();
            }else{
                Toast.makeText(ViewBookingActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewBookingActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return hour;
    }

    private String searchDay(int keyBooking) {
        BdBookings bdBookings= new BdBookings();
        String day = "";
        if(bdBookings.getConnection()!=null){
            Toast.makeText(ViewBookingActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> name = bdBookings.searchDay(keyBooking);
            if(1==name.size()){
                for (int i = 0; i <name.size() ; i++) {
                    day= name.get(i);
                }
                bdBookings.dropConnection();
            }else{
                Toast.makeText(ViewBookingActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewBookingActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return day;
    }

    private Date searchDate(int keyBooking) {
        BdBookings bdBookings= new BdBookings();
        java.sql.Date date = null;
        if(bdBookings.getConnection()!=null){
            Toast.makeText(ViewBookingActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<java.sql.Date> name = bdBookings.searchSessionDate(keyBooking);
            if(1==name.size()){
                for (int i = 0; i <name.size() ; i++) {
                    date= name.get(i);
                }
                bdBookings.dropConnection();
            }else{
                Toast.makeText(ViewBookingActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewBookingActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return date;
    }

    private String searchLessonName(int keyBooking) {
        BdBookings bdBookings= new BdBookings();
        String lesson = "";
        if(bdBookings.getConnection()!=null){
            Toast.makeText(ViewBookingActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> name = bdBookings.searchBookingLessonName(keyBooking);
            if(1==name.size()){
                for (int i = 0; i <name.size() ; i++) {
                    lesson= name.get(i);
                }
                bdBookings.dropConnection();
            }else{
                Toast.makeText(ViewBookingActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewBookingActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return lesson;
    }
}