package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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

import com.example.misbrincosapp.BD.BdSessions;
import com.example.misbrincosapp.BD.BdTeacher;
import com.example.misbrincosapp.model.Session;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ShowTeacherSessionsActivity extends AppCompatActivity implements SessionsAdapter.ListItemClick {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    ArrayList<Session> sessions;
    BdSessions bdSessions;
    ImageButton calendarButton;
    TextView calendarText;
    private Calendar date;
    ImageButton searchTeacher;
    ImageButton searchDate;
    private Calendar today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teacher_sessions);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        recyclerView = findViewById(R.id.recycle_show_teachers_sessions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentUser = mAuth.getCurrentUser();
        setCalendarButton();
        getDateOfToday();
        setDate(today);
        searchTeacher = findViewById(R.id.iconCardTeachersSessionsSearchCc);
        onSearchTeacherCc(searchTeacher);
        searchDate = findViewById(R.id.iconCardTeachersSessionsSearchDate);
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
                setIdTeacherOptions();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }

    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_show_teachers_sessions);
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

    private void setSessionsAdapter(ArrayList<Session> sessions) {
        SessionsAdapter sessionsAdapter = new SessionsAdapter(sessions, this);
        recyclerView.setAdapter(sessionsAdapter);
    }

    private void onSearchTeacherCc(ImageButton searchButton) {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView teacherCc = (AutoCompleteTextView)
                        findViewById(R.id.inputTeachersSessionsSearchCc);
                //Search IN bd
                String cc = teacherCc.getText().toString();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    sessions = new ArrayList<Session>();
                    bdSessions = new BdSessions();
                    if (bdSessions.getConnection() != null) {
                        Toast.makeText(ShowTeacherSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                        ArrayList<Integer> ids = bdSessions.searchSessionTeacherId(cc);
                        ArrayList<String> lesson = bdSessions.searchSessionLessonNameTeacherCc(cc);
                        ArrayList<java.sql.Date> dates = bdSessions.searchSessionTeacherNameDate(cc);
                        if ((ids.size() != 0) && (ids.size() == dates.size())) {
                            for (int i = 0; i < ids.size(); i++) {
                                int id = ids.get(i);
                                String nameLesson = lesson.get(i);
                                Date date = new Date(dates.get(i).getTime());
                                Session session = new Session(id, nameLesson, date);
                                sessions.add(session);
                            }
                            bdSessions.dropConnection();
                            setSessionsAdapter(sessions);
                        } else {
                            Toast.makeText(ShowTeacherSessionsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ShowTeacherSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onSearchDates(ImageButton searchButton) {
        String dateInit = "";
        String dateFinal = "";
        if(today.after(date)){
            dateInit = (String) DateFormat.format("yyyy-MM-dd", date);
            dateFinal = getDateOfToday();
        }
        if(today.before(date)){
            dateInit = getDateOfToday();
            dateFinal = (String) DateFormat.format("yyyy-MM-dd", date);
        }
        if(today.equals(date)){
            dateInit = (String) DateFormat.format("yyyy-MM-dd", date);
            dateFinal = getDateOfToday();
        }
        String finalDateInit = dateInit;
        String finalDateFinal = dateFinal;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView ccTeacher = (AutoCompleteTextView)
                        findViewById(R.id.inputTeachersSessionsSearchCc);
                //Search IN bd
                int cc = Integer.parseInt(String.valueOf(ccTeacher.getText()));
                //Search IN bd
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    sessions = new ArrayList<Session>();
                    bdSessions = new BdSessions();
                    if (bdSessions.getConnection() != null) {
                        Toast.makeText(ShowTeacherSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                        ArrayList<Integer> ids = bdSessions.searchSessionTeacherDatesId(finalDateInit, finalDateFinal, cc);
                        ArrayList<String> namesLessons = bdSessions.searchSessionLessonsNameTeacherDate(finalDateInit, finalDateFinal, cc);
                        ArrayList<java.sql.Date> dates = bdSessions.searchSessionTeacherDate(finalDateInit, finalDateFinal, cc);
                        if ((ids.size() != 0) && (ids.size() == namesLessons.size())) {
                            for (int i = 0; i < ids.size(); i++) {
                                int id = ids.get(i);
                                String nameLesson = namesLessons.get(i);
                                Date date = new Date(dates.get(i).getTime());
                                Session session = new Session(id, nameLesson, date);
                                sessions.add(session);
                            }
                            bdSessions.dropConnection();
                            setSessionsAdapter(sessions);
                        } else {
                            Toast.makeText(ShowTeacherSessionsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ShowTeacherSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setCalendarButton() {
        calendarButton = findViewById(R.id.dateButtonSessionsSearchDate);
        calendarText = findViewById(R.id.SessionsSearchDateText);
        //Trae el día actual
        //String dateOfToday = getDateOfToday();
        //calendarText.setText(dateOfToday);
        setCalendarOnClickButton();
    }

    @NotNull
    private String getDateOfToday() {
        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        setToday(today);
        String dateString = (String) DateFormat.format("yyyy-MM-dd", today);
        return dateString;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getToday() {
        return today;
    }

    public void setToday(Calendar today) {
        this.today = today;
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
                String dateInit = "";
                String dateFinal = "";
                if (today.after(date)) {
                    dateInit = (String) DateFormat.format("yyyy-MM-dd", date);
                    dateFinal = getDateOfToday();
                }
                if (today.before(date)) {
                    dateInit = getDateOfToday();
                    dateFinal = (String) DateFormat.format("yyyy-MM-dd", date);
                }
                if (today.equals(date)) {
                    dateInit = (String) DateFormat.format("yyyy-MM-dd", date);
                    dateFinal = getDateOfToday();
                }
                calendarText.setText(dateInit + " - " + dateFinal);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void setIdTeacherOptions() {

        //Change options
        int size = 0;
        ArrayList<String> ccS = new ArrayList<String>();
        BdTeacher bdTeacher = new BdTeacher();
        if (bdTeacher.getConnection() != null) {
            Toast.makeText(ShowTeacherSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ccS = bdTeacher.searchCc();
            size = ccS.size();
        } else {
            Toast.makeText(ShowTeacherSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] TEACHERCC = new String[size];
        for (int i = 0; i < size; i++) {
            String cc = ccS.get(i);
            TEACHERCC[i] = cc;
        }

        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, TEACHERCC);
        AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                findViewById(R.id.inputTeachersSessionsSearchCc);
        ccStudent.setAdapter(adapter);
        //Change options
        //Search for setNameLessonsOptions in DeleteLessons and the structure of the onClickDelete in the same activity but combined with the create use the logic of both javaclass
        //Change options
        /*/final String[] STUDENTSCC= new String[size];
        /ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STUDENTSCC);
        AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                findViewById(R.id.inputStudentsCc);
        ccStudent.setAdapter(adapter);*/
    }

    @Override
    public void onListItemClick(int clickedItem) {

    }
}