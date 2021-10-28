package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdSessions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class EditSessionsActivity extends AppCompatActivity {

    ImageButton calendarButton;
    TextView calendarText;
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button editButton;
    ImageButton searchButton;
    TextView dayOfWeek;
    TextView hour;
    TextView timeText;
    private Calendar timeOfEvent;
    AutoCompleteTextView classRoomNumber;
    AutoCompleteTextView ccTeacher;
    AutoCompleteTextView id;
    Calendar dateOfSession;
    BdSessions bdSessions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sessions);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        editButton = (Button) findViewById(R.id.button_edit_sessions);
        onClickEdit(editButton);
        searchButton = (ImageButton) findViewById(R.id.iconCardEditIdSession);
        onSearchId(searchButton);
        setCalendarButton();
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_edit_sessions);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.Toolbar_TitleText);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //Get info of student selected
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                setIdSessionsOptions();
                setCcTeacheroptions();
                setIdClassRoomNumberptions();
            } catch (Exception e) {
                e.printStackTrace();
            }

            setIdClassRoomNumberptions();
        } else {
            finish();
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
    private void onClickEdit(Button createButton) {
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correctInputs = validationInputs();
                if(correctInputs){
                    Toast.makeText(EditSessionsActivity.this, R.string.creating_lesson, Toast.LENGTH_SHORT).show();
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        dbInteraction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(EditSessionsActivity.this, R.string.bad_inputs, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void dbInteraction() {
        id = findViewById(R.id.inputEditSessionId);
        timeText = findViewById(R.id.dateSessionEditText);
        classRoomNumber = findViewById(R.id.inputEditSessionsClassRoomNumber);
        ccTeacher = findViewById(R.id.inputEditSessionsTeacher);

        int idSes= Integer.parseInt(id.getText().toString());
        String timeTextS=timeText.getText().toString();
        int classR=Integer.parseInt(classRoomNumber.getText().toString());
        String ccT= ccTeacher.getText().toString();

        bdSessions = new BdSessions();
        if(bdSessions.getConnection()!=null){
            Toast.makeText(EditSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            bdSessions.updateSesion(idSes, timeTextS, ccT, classR);
            bdSessions.dropConnection();
            finish();

        }else{
            Toast.makeText(EditSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validationInputs() {
        timeText = findViewById(R.id.dateSessionEditText);
        classRoomNumber = findViewById(R.id.inputEditSessionsClassRoomNumber);
        ccTeacher = findViewById(R.id.inputEditSessionsTeacher);
        if((timeText.getText().toString().equals(""))&&(classRoomNumber.getText().toString().equals(""))&&(ccTeacher.getText().toString().equals(""))){
            return false;
        }else{
            return true;
        }

    }
    private void onSearchId(ImageButton searchButton){
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AutoCompleteTextView idSession = (AutoCompleteTextView)
                        findViewById(R.id.inputEditSessionId);
                //Search IN bd
                int keySession = Integer.parseInt(String.valueOf(idSession.getText()));
                dayOfWeek = findViewById(R.id.textEditSessionDay);
                hour = findViewById(R.id.textEditSessionHour);
                String day = searchSessionDay(keySession);
                dayOfWeek.setText(day);
                String hourString=(String) DateFormat.format("hh:mm:ss", searchSessionHour(keySession));
                hour.setText(hourString);

            }
        });
    }

    private Time searchSessionHour(int id) {
        BdSessions bdSessions= new BdSessions();
        Time hour = null;
        if(bdSessions.getConnection()!=null){
            Toast.makeText(EditSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Time> times = bdSessions.searchSessionHour(id);
            if(1==times.size()){
                for (int i = 0; i <times.size() ; i++) {
                    hour= times.get(i);
                }
                bdSessions.dropConnection();
            }else{
                Toast.makeText(EditSessionsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(EditSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return hour;
    }

    private String searchSessionDay(int id) {
        BdSessions bdSessions= new BdSessions();
        String day = null;
        if(bdSessions.getConnection()!=null){
            Toast.makeText(EditSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> days = bdSessions.searchSessionDayOfWeek(id);
            if(1==days.size()){
                for (int i = 0; i <days.size() ; i++) {
                    day= days.get(i);
                }
                bdSessions.dropConnection();
            }else{
                Toast.makeText(EditSessionsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(EditSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return day;
    }

    private void setCalendarButton() {
        calendarButton = findViewById(R.id.dateSessionButtonEdit);
        calendarText = findViewById(R.id.dateSessionEditText);
        //Trae el día actual
        String dateOfToday = getDateOfToday();
        calendarText.setText(dateOfToday);
        setCalendarOnClickButton();
    }
    @NotNull
    private String getDateOfToday() {
        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        setDate(today);
        String dateString = (String) DateFormat.format("YYYY-MM-DD", today);
        return dateString;
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
                String dateString = (String) DateFormat.format("YYYY-MM-DD", date);
                calendarText.setText(dateString);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
    public Calendar getDate() {
        return dateOfSession;
    }

    public void setDate(Calendar dateOfEvent) {
        this.dateOfSession = dateOfEvent;
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

    private void setIdSessionsOptions() {
        //Change options
        int size=0;
        ArrayList<Integer> ids = new ArrayList<Integer>();
        BdSessions bdSessions = new BdSessions();
        if(bdSessions.getConnection()!=null){
            Toast.makeText(EditSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ids= bdSessions.searchId();
            size=ids.size();
        }else{
            Toast.makeText(EditSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] SESSIONSID = new String[size];
        for (int i = 0; i <size ; i++) {
            String id = ""+ids.get(i);
            SESSIONSID[i]=id;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SESSIONSID);
        AutoCompleteTextView idSession = (AutoCompleteTextView)
                findViewById(R.id.inputEditSessionId);
        idSession.setAdapter(adapter);
        //Change options
    }
    private void setIdClassRoomNumberptions() {
        //Change options
        int size=0;
        ArrayList<Integer> classroomNumbers = new ArrayList<Integer>();
        BdSessions bdSessions = new BdSessions();
        if(bdSessions.getConnection()!=null){
            Toast.makeText(EditSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            classroomNumbers= bdSessions.searchClassroomNumber();
            size=classroomNumbers.size();
        }else{
            Toast.makeText(EditSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] CLASSROOMNUMBERS = new String[size];
        for (int i = 0; i <size ; i++) {
            String classroomNumber = ""+classroomNumbers.get(i);
            CLASSROOMNUMBERS[i]=classroomNumber;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CLASSROOMNUMBERS);
        AutoCompleteTextView idSession = (AutoCompleteTextView)
                findViewById(R.id.inputEditSessionsClassRoomNumber);
        idSession.setAdapter(adapter);
    }
    private void setCcTeacheroptions() {
        //Change options
        int size=0;
        ArrayList<String> ccsTeachers = new ArrayList<String>();
        BdSessions bdSessions = new BdSessions();
        if(bdSessions.getConnection()!=null){
            Toast.makeText(EditSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ccsTeachers= bdSessions.searchCcTeacher();
            size=ccsTeachers.size();
        }else{
            Toast.makeText(EditSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] CCTEACHERS = new String[size];
        for (int i = 0; i <size ; i++) {
            String ccsTeacher = ""+ccsTeachers.get(i);
            CCTEACHERS[i]=ccsTeacher;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CCTEACHERS);
        AutoCompleteTextView idSession = (AutoCompleteTextView)
                findViewById(R.id.inputEditSessionsTeacher);
        idSession.setAdapter(adapter);
    }

}