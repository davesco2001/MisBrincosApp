package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdPackages;
import com.example.misbrincosapp.BD.BdSessions;
import com.example.misbrincosapp.model.Lesson;
import com.example.misbrincosapp.model.Session;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.TimeZone;

public class ShowSessionsActivity extends AppCompatActivity implements SessionsAdapter.ListItemClick{

    private static final int showValue = 7;
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    ArrayList<Session> sessions;
    BdSessions bdSessions;
    ImageButton calendarButton;
    TextView calendarText;
    Calendar date;
    ImageButton searchLesson;
    ImageButton searchDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sessions);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        recyclerView = findViewById(R.id.recycle_show_sessions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentUser = mAuth.getCurrentUser();
        setCalendarButton();
        searchLesson = findViewById(R.id.iconCardSessionsLessonName);
        onSearchLessonNames(searchLesson);
        searchDate = findViewById(R.id.iconCardSessionsSearchDate);
        onSearchDates(searchDate);
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
                setLessonsNameOptions();
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
    private void setLessonsNameOptions() {
        int size=0;
        ArrayList<String> names = new ArrayList<String>();
        BdLessons bdLessons = new BdLessons();
        if(bdLessons.getConnection()!=null){
            Toast.makeText(ShowSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            names= bdLessons.searchName();
            size=names.size();
        }else{
            Toast.makeText(ShowSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] LESSONSNAME = new String[size];
        for (int i = 0; i <size ; i++) {
            String name = names.get(i);
            LESSONSNAME[i]=name;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LESSONSNAME);
        AutoCompleteTextView idPackage = (AutoCompleteTextView)
                findViewById(R.id.inputSessionsLessonName);
        idPackage.setAdapter(adapter);
    }
    private void setCalendarButton() {
        calendarButton = findViewById(R.id.dateButtonSessionsSearchDate);
        calendarText = findViewById(R.id.SessionsSearchDateText);
        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        setDate(today);
        //Trae el día actual
        //String dateOfToday = getDateOfToday();
        calendarText.setText(parseDateSql(getDate().getTime()));
        setCalendarOnClickButton();
    }

    @NotNull
    private String parseDateSql(Date date) {
        String dateString = (String) DateFormat.format("yyyy-MM-dd", date);
        return dateString;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
    private void onSearchLessonNames(ImageButton searchButton){
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AutoCompleteTextView lessonName = (AutoCompleteTextView)
                        findViewById(R.id.inputSessionsLessonName);
                //Search IN bd
                String lesson = lessonName.getText().toString();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    sessions = new ArrayList<Session>();
                    bdSessions = new BdSessions();
                    if(bdSessions.getConnection()!=null){
                    Toast.makeText(ShowSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                    ArrayList<Integer> ids= bdSessions.searchSessionLessonNameId(lesson);
                    //ArrayList<String> namesLessons = bdSessions.searchLessonsName();
                    ArrayList<java.sql.Date> dates = bdSessions.searchSessionLessonNameDate(lesson);
                    if((ids.size()!=0)&&(ids.size()==dates.size())) {
                        for (int i = 0; i < ids.size(); i++) {
                            int id = ids.get(i);
                            String nameLesson = lesson;
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void onSearchDates(ImageButton searchButton){
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String dateFilter = calendarText.getText().toString();
                //Search IN bd
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    sessions = new ArrayList<Session>();
                    bdSessions = new BdSessions();
                    if(bdSessions.getConnection()!=null){
                        Toast.makeText(ShowSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                        ArrayList<Integer> ids= bdSessions.searchSessionDatesId(dateFilter);
                        ArrayList<String> namesLessons = bdSessions.searchSessionLessonsNameDate(dateFilter);
                        //ArrayList<java.sql.Date> dates = bdSessions.searchSessionLessonNameDate(lesson);
                        if((ids.size()!=0)&&(ids.size()==namesLessons.size())) {
                            for (int i = 0; i < ids.size(); i++) {
                                int id = ids.get(i);
                                String nameLesson = namesLessons.get(i);
                                Date date = getDate().getTime();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setCalendarOnClickButton() {
        //Cosntruye y define el estilo del Calendar View
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnDateListen();
            }
        });

    }

    private void setOnDateListen() {
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, month, dayOfMonth, 0, 0);
                setDate(date);
                //Db validation
                calendarText.setText(parseDateSql(date.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
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