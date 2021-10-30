package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdBookings;
import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdSessions;
import com.example.misbrincosapp.BD.BdStudent;
import com.example.misbrincosapp.model.Session;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CreateBookingActivity extends AppCompatActivity implements BookingSessionsAdapter.ListItemClick {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    ArrayList<Session> sessions;
    String studentId;
    private Calendar dateOfEvent;
    BdBookings bdBookings;
    BdSessions bdSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        recyclerView = findViewById(R.id.recycle_create_bookings);
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
                studentId = getStudentIdWithEmail(currentUser.getEmail());
                getSessionsToActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_create_bookings);
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
            Toast.makeText(CreateBookingActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> ccs = bdStudent.searchStudentCcWithEmail(email);
            if(1==ccs.size()){
                for (int i = 0; i <ccs.size() ; i++) {
                    cc= ccs.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(CreateBookingActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(CreateBookingActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return cc;
    }
    private void getSessionsToActivity() {
        sessions = new ArrayList<Session>();
        //Loop that brings the sessions from db
        bdSessions = new BdSessions();
        if(bdBookings.getConnection()!=null){
            Toast.makeText(CreateBookingActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> ids= bdSessions.searchSessionId(studentId);
            ArrayList<String> namesLessons = bdSessions.searchSessionLessonsName(studentId);
            ArrayList<java.sql.Date> dates = bdSessions.searchSessionDate(studentId);
            if((ids.size()==namesLessons.size())&&(ids.size()==dates.size())){
                for (int i = 0; i <ids.size() ; i++) {
                    int id = ids.get(i);
                    String nameLesson = namesLessons.get(i);
                    Date date = new Date(dates.get(i).getTime());
                    Session session = new Session(id, nameLesson, date);
                    sessions.add(session);
                }
                bdSessions.dropConnection();
                setSessionsBookingApater(sessions);
            }else{
                Toast.makeText(CreateBookingActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(CreateBookingActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }

    }

    private void setSessionsBookingApater(ArrayList<Session> sessions) {
        BookingSessionsAdapter bookingSessionsAdapter = new BookingSessionsAdapter(sessions, this);
        recyclerView.setAdapter(bookingSessionsAdapter);
    }
    @NotNull
    private String getDateOfToday() {
        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        setDate(today);
        String dateString = (String) DateFormat.format("yyyy-MM-dd", today);
        return dateString;
    }
    public void setDate(Calendar dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }
    @Override
    public void buttonOnClick(View v, int clickedItem) {
        int size = sessions.size();
        for (int i =0; i<size;i++){
            Session bookingSessionClicked= sessions.get(i);
            if(i==clickedItem){
                //Something
                int id = (int)((Math.random() * (300 - 1)) + 1);
                String asistencia = "false";
                Session sessionClicked = sessions.get(i);
                int sessionClickedId=sessionClicked.getId();
                String now = getDateOfToday();
                //use studentId
                //Intent with the key of the table
                //Bd create bookings
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    bdBookings = new BdBookings();
                    if(bdBookings.getConnection()!=null){
                        Toast.makeText(CreateBookingActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                        bdBookings.addBooking(id, asistencia, now, sessionClickedId, studentId);
                        bdBookings.dropConnection();
                        finish();
                    }else{
                        Toast.makeText(CreateBookingActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}