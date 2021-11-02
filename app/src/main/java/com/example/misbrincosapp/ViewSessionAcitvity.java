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

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdSessions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ViewSessionAcitvity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    TextView lessonNameTextView;
    TextView idTextView;
    TextView dateTextView;
    TextView dayOfWeekTextView;
    TextView hourTextView;
    TextView classroomNumberTextView;
    TextView assistanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_session_acitvity);
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
                getSessionToActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            finish();
        }
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_view_session);
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
    private void getSessionToActivity() {

        //The empty text textview to fill with the information get by db
        lessonNameTextView= findViewById(R.id.viewTextSessionLessonName);
        idTextView = findViewById(R.id.viewTextSessionId);
        dateTextView = findViewById(R.id.viewTextSessionDate);
        dayOfWeekTextView = findViewById(R.id.viewTextSessionDay);
        hourTextView = findViewById(R.id.viewTextSessionHour);
        classroomNumberTextView = findViewById(R.id.viewTextSessionClassroom);
        assistanceTextView = findViewById(R.id.viewTextSessionAssistance);
        //Get the key from ShowLessonsActivity
        Intent key= getIntent();
        int keySession = key.getIntExtra("ID_S", 0);
        //Create 1 method for each search
        String nameLesson = searchSessionLessonName(keySession);
        int id= searchSessionId(keySession);
        String date = (String) DateFormat.format("yyyy-MM-dd", searchSessionDate(keySession));
        String day = searchSessionDay(keySession);
        String hour=(String) DateFormat.format("hh:mm:ss", searchSessionHour(keySession));
        int classRoomNumber= searchSessionClassRoom(keySession);
        int assit = searchAssist(keySession);
        lessonNameTextView.setText(nameLesson);
        idTextView.setText(""+id);
        dateTextView.setText(date);
        dayOfWeekTextView.setText(day);
        hourTextView.setText(hour);
        classroomNumberTextView.setText(""+classRoomNumber);
        assistanceTextView.setText(""+assit);
    }

    private int searchAssist(int keySession) {
        BdSessions bdSessions= new BdSessions();
        int total = 0;
        if(bdSessions.getConnection()!=null){
            Toast.makeText(ViewSessionAcitvity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> totals = bdSessions.searchAssistance(keySession);
            if(1==totals.size()){
                for (int i = 0; i <totals.size() ; i++) {
                    total= totals.get(i);
                }
                bdSessions.dropConnection();
            }else{
                Toast.makeText(ViewSessionAcitvity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewSessionAcitvity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return total;
    }

    private int searchSessionClassRoom(int id) {
        BdSessions bdSessions= new BdSessions();
        int numberClassroom = 0;
        if(bdSessions.getConnection()!=null){
            Toast.makeText(ViewSessionAcitvity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> classroom = bdSessions.searchSessionClassroom(id);
            if(1==classroom.size()){
                for (int i = 0; i <classroom.size() ; i++) {
                    numberClassroom= classroom.get(i);
                }
                bdSessions.dropConnection();
            }else{
                Toast.makeText(ViewSessionAcitvity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewSessionAcitvity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return numberClassroom;
    }

    private Time searchSessionHour(int id) {
        BdSessions bdSessions= new BdSessions();
        Time hour = null;
        if(bdSessions.getConnection()!=null){
            Toast.makeText(ViewSessionAcitvity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Time> times = bdSessions.searchSessionHour(id);
            if(1==times.size()){
                for (int i = 0; i <times.size() ; i++) {
                    hour= times.get(i);
                }
                bdSessions.dropConnection();
            }else{
                Toast.makeText(ViewSessionAcitvity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewSessionAcitvity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return hour;
    }

    private String searchSessionDay(int id) {
        BdSessions bdSessions= new BdSessions();
        String day = null;
        if(bdSessions.getConnection()!=null){
            Toast.makeText(ViewSessionAcitvity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> days = bdSessions.searchSessionDayOfWeek(id);
            if(1==days.size()){
                for (int i = 0; i <days.size() ; i++) {
                    day= days.get(i);
                }
                bdSessions.dropConnection();
            }else{
                Toast.makeText(ViewSessionAcitvity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewSessionAcitvity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return day;
    }

    private Date searchSessionDate(int id) {
        BdSessions bdSessions= new BdSessions();
        java.sql.Date date = null;
        if(bdSessions.getConnection()!=null){
            Toast.makeText(ViewSessionAcitvity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<java.sql.Date> dates = bdSessions.searchSessionDate(id);
            if(1==dates.size()){
                for (int i = 0; i <dates.size() ; i++) {
                    date= dates.get(i);
                }
                bdSessions.dropConnection();
            }else{
                Toast.makeText(ViewSessionAcitvity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewSessionAcitvity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return date;
    }

    private int searchSessionId(int idKey) {
        BdSessions bdSessions= new BdSessions();
        int id = 0;
        if(bdSessions.getConnection()!=null){
            Toast.makeText(ViewSessionAcitvity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> ids = bdSessions.searchSessionId(idKey);
            if(1==ids.size()){
                for (int i = 0; i <ids.size() ; i++) {
                    id= ids.get(i);
                }
                bdSessions.dropConnection();
            }else{
                Toast.makeText(ViewSessionAcitvity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewSessionAcitvity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return id;
    }

    private String searchSessionLessonName(int id) {
        BdSessions bdSessions= new BdSessions();
        String lesson = null;
        if(bdSessions.getConnection()!=null){
            Toast.makeText(ViewSessionAcitvity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> lessons = bdSessions.searchSessionLessonName(id);
            if(1==lessons.size()){
                for (int i = 0; i <lessons.size() ; i++) {
                    lesson= lessons.get(i);
                }
                bdSessions.dropConnection();
            }else{
                Toast.makeText(ViewSessionAcitvity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewSessionAcitvity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return lesson;
    }
}