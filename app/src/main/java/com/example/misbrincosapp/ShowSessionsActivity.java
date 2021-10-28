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

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdSessions;
import com.example.misbrincosapp.model.Lesson;
import com.example.misbrincosapp.model.Session;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.ArrayList;

public class ShowSessionsActivity extends AppCompatActivity implements SessionsAdapter.ListItemClick{

    private static final int showValue = 7;
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    ArrayList<Session> sessions;
    BdSessions bdSessions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sessions);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        recyclerView = findViewById(R.id.recycle_show_sessions);
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
        toolbar = findViewById(R.id.toolbar_show_sessions);
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
    private void getSessionsToActivity() {
        sessions = new ArrayList<Session>();
        //Loop that brings the sessions from db
        bdSessions = new BdSessions();
        if(bdSessions.getConnection()!=null){
            Toast.makeText(ShowSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> ids= bdSessions.searchId();
            ArrayList<String> namesLessons = bdSessions.searchLessonsName();
            ArrayList<java.sql.Date> dates = bdSessions.searchDate();
            if((ids.size()==namesLessons.size())&&(ids.size()==dates.size())){
                for (int i = 0; i <ids.size() ; i++) {
                    int id = ids.get(i);
                    String nameLesson = namesLessons.get(i);
                    Date date = new Date(dates.get(i).getTime());
                    Session session = new Session(id, nameLesson, date);
                    sessions.add(session);
                }
                bdSessions.dropConnection();
                setSessionsAdapter(sessions);
            }else{
                Toast.makeText(ShowSessionsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ShowSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
    }

    private void setSessionsAdapter(ArrayList<Session> sessions) {
        SessionsAdapter sessionsAdapter = new SessionsAdapter(sessions, this);
        recyclerView.setAdapter(sessionsAdapter);
    }

    @Override
    public void onListItemClick(int clickedItem) {
        int size = sessions.size();
        for (int i = 0; i < size; i++) {
            if (i == clickedItem) {
                Session sessionClicked = sessions.get(i);
                int sessionClickedId=sessionClicked.getId();
                //Intent with the key of the table
                Intent intent = new Intent(ShowSessionsActivity.this, ViewSessionAcitvity.class);
                intent.putExtra("ID", sessionClickedId);
                startActivity(intent);
            }

        }
    }
}