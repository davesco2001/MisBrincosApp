package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdBookings;
import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdSessions;
import com.example.misbrincosapp.BD.BdStudent;
import com.example.misbrincosapp.model.Booking;
import com.example.misbrincosapp.model.Session;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;

public class ShowBookingsActivity extends AppCompatActivity implements BookingsAdapter.ListItemClick {

    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    ArrayList<Booking> bookings;
    BdBookings bdBookings;
    String studentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bookings);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        recyclerView = findViewById(R.id.recycle_show_bookings);
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
                getBookingsToActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }

    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_show_bookings);
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
    private void getBookingsToActivity() {
        bookings = new ArrayList<Booking>();
        //Loop that brings the sessions from db
        bdBookings = new BdBookings();
        if(bdBookings.getConnection()!=null){
            Toast.makeText(ShowBookingsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> ids= bdBookings.searchBookingId(studentId);
            ArrayList<String> namesLessons = bdBookings.searchBookingLessonsName(studentId);
            ArrayList<java.sql.Date> dates = bdBookings.searchBookingDate(studentId);
            if((ids.size()==namesLessons.size())&&(ids.size()==dates.size())){
                for (int i = 0; i <ids.size() ; i++) {
                    int id = ids.get(i);
                    String nameLesson = namesLessons.get(i);
                    Date date = new Date(dates.get(i).getTime());
                    Booking booking = new Booking(id, nameLesson, date);
                    bookings.add(booking);
                }
                bdBookings.dropConnection();
                setBookingAdapter(bookings);
            }else{
                Toast.makeText(ShowBookingsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ShowBookingsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        setBookingAdapter(bookings);
    }

    private void setBookingAdapter(ArrayList<Booking> bookings) {
        BookingsAdapter bookingsAdapter = new BookingsAdapter(bookings, this);
        recyclerView.setAdapter(bookingsAdapter);
    }
    private String getStudentIdWithEmail(String email){
        BdStudent bdStudent= new BdStudent();
        String cc = "";
        if(bdStudent.getConnection()!=null){
            Toast.makeText(ShowBookingsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> ccs = bdStudent.searchStudentCcWithEmail(email);
            if(1==ccs.size()){
                for (int i = 0; i <ccs.size() ; i++) {
                    cc= ccs.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(ShowBookingsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ShowBookingsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return cc;
    }
    @Override
    public void onListItemClick(int clickedItem) {
        int size = bookings.size();
        for (int i = 0; i < size; i++) {
            if (i == clickedItem) {
                //Session sessionsClicked = sessions.getSessions().get(i);
                Booking bookingClicked = bookings.get(i);
                int bookingClickedId=bookingClicked.getBookingId();
                //Intent with the key of the table
                Intent intent = new Intent(ShowBookingsActivity.this, ViewBookingActivity.class);
                intent.putExtra("ID", bookingClickedId);
                startActivity(intent);
            }

        }
    }
}