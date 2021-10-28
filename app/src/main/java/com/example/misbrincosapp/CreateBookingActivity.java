package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.misbrincosapp.model.Session;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CreateBookingActivity extends AppCompatActivity implements BookingSessionsAdapter.ListItemClick {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    ArrayList<Session> sessions;
    String studentId;

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
            getSessionsToActivity();
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
    private void getStudentIdWithEmail(){

    }
    private void getSessionsToActivity() {
        sessions = new ArrayList<Session>();
        //Loop that brings the sessions from db

        setSessionsBookingApater(sessions);
    }

    private void setSessionsBookingApater(ArrayList<Session> sessions) {
        BookingSessionsAdapter bookingSessionsAdapter = new BookingSessionsAdapter(sessions, this);
        recyclerView.setAdapter(bookingSessionsAdapter);
    }

    @Override
    public void buttonOnClick(View v, int clickedItem) {
        int size = sessions.size();
        for (int i =0; i<size;i++){
            Session bookingSessionClicked= sessions.get(i);
            if(i==clickedItem){
                //Something
            }
        }
    }
}