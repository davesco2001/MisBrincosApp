package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdSessions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CreateSessionsActivity extends AppCompatActivity {
    private ImageButton calendarButton;
    private ImageButton timeButton;
    private Calendar dateOfEvent;
    private Calendar timeOfEvent;
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    TextView timeText;
    TextView calendarText;
    Button createButton;
    EditText lessonName;
    AutoCompleteTextView dayOfWeek;
    AutoCompleteTextView classRoomNumber;
    AutoCompleteTextView ccTeacher;
    BdSessions bdSessions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sessions);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        setCalendarButton();
        setTimeButton();
        createButton = findViewById(R.id.button_create_sessions);
        onClickCreate(createButton);
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_create_sessions);
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
                setIdClassRoomNumberptions();
                setCcTeacheroptions();
                setNameLessonsOptions();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private void onClickCreate(Button createButton) {
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correctInputs = validationInputs();
                if(correctInputs){
                    Toast.makeText(CreateSessionsActivity.this, R.string.creating_lesson, Toast.LENGTH_SHORT).show();
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        dbInteraction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(CreateSessionsActivity.this, R.string.bad_inputs, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void dbInteraction() {
        calendarText = findViewById(R.id.date_text);
        timeText = findViewById(R.id.hour_text);
        lessonName = findViewById(R.id.inputSessionsName);
        dayOfWeek = findViewById(R.id.inputSessionsDayOfWeek);
        classRoomNumber = findViewById(R.id.inputSessionsClassRoomNumber);
        ccTeacher = findViewById(R.id.inputSessionsCcTeacher);

        int id = (int)((Math.random() * (200 - 1)) + 1);
        String fecha = calendarText.getText().toString();
        String ccProfesor = ccTeacher.getText().toString();
        int numeroSalon = Integer.parseInt(classRoomNumber.getText().toString());
        String hora = timeText.getText().toString();
        String dia= dayOfWeek.getText().toString();
        String nombreClase = lessonName.getText().toString();
        bdSessions = new BdSessions();
        if(bdSessions.getConnection()!=null){
            Toast.makeText(CreateSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> dayBd= bdSessions.searchDay(hora, dia);
            ArrayList<Time> hourBd = bdSessions.searchHour(dia, hora);
            ArrayList<Integer> classrooms= bdSessions.searchNumber(nombreClase);
            Toast.makeText(CreateSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            boolean t = false;
            if((dayBd.get(0).equals(dia))&&(hourBd.get(0).equals(hora))) {
                Toast.makeText(CreateSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < classrooms.size(); i++) {
                    if (classrooms.get(i) == numeroSalon) {
                        Toast.makeText(CreateSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                        t = true;
                        ArrayList<Integer> fulls = bdSessions.searchNumberClassRoomOccupied(numeroSalon, fecha, hora);
                        for (int o = 0; o <fulls.size() ; o++) {
                            if(fulls.get(o)!=numeroSalon){
                                Toast.makeText(CreateSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                                t = true;
                            }else  {
                                t = false;
                                Toast.makeText(CreateSessionsActivity.this, R.string.bad_inputs +"lleno", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{Toast.makeText(CreateSessionsActivity.this, R.string.bad_inputs +"salon", Toast.LENGTH_SHORT).show();}
                }
            }else{Toast.makeText(CreateSessionsActivity.this, R.string.bad_inputs, Toast.LENGTH_SHORT).show();}
            if(!t){                bdSessions.addSession(id, fecha, ccProfesor, numeroSalon);
                bdSessions.addRealiza(id, dia, hora, nombreClase);
                finish();
            }
            bdSessions.dropConnection();
        }else{
            Toast.makeText(CreateSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validationInputs() {
        calendarText = findViewById(R.id.date_text);
        timeText = findViewById(R.id.hour_text);
        lessonName = findViewById(R.id.inputSessionsName);
        dayOfWeek = findViewById(R.id.inputSessionsDayOfWeek);
        classRoomNumber = findViewById(R.id.inputSessionsClassRoomNumber);
        ccTeacher = findViewById(R.id.inputSessionsCcTeacher);
        if((lessonName.getText().toString().equals(""))&&(lessonName.length()>10)&&(dayOfWeek.getText().toString().equals(""))&&(dayOfWeek.length()>10)&&(calendarText.getText().toString().equals(""))&&(timeText.getText().toString().equals(""))&&(classRoomNumber.getText().toString().equals(""))&&(ccTeacher.getText().toString().equals(""))&&(ccTeacher.getText().toString().length()>10)){
            return false;
        }else{
            return true;
        }

    }

    private void setTimeButton() {
        timeButton = findViewById(R.id.clockButton);
        timeText = findViewById(R.id.hour_text);
        //Trae la hora actual
        Calendar time = Calendar.getInstance(Locale.getDefault());
        setTime(time);
        String timeDefault = (String) DateFormat.format("hh:mm:ss", time);
        setTimerOnClickButton();
    }

    private void setTimerOnClickButton() {
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnTimeListen();
            }
        });
    }

    private void setOnTimeListen() {
        Calendar time = Calendar.getInstance();
        int HOUR = time.get(Calendar.HOUR);
        int MINUTE = time.get(Calendar.MINUTE);
        int SECONDS = time.get(Calendar.SECOND);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar time = Calendar.getInstance(Locale.getDefault());
                time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                time.set(Calendar.MINUTE, minute);
                time.set(Calendar.SECOND, 0);
                setTime(time);
                //Db validation
                timeText.setText(DateFormat.format("hh:mm:ss", time));
            }
        }, HOUR, MINUTE, false);
        timePickerDialog.show();
    }

    private void setCalendarButton() {
        calendarButton = findViewById(R.id.dateButton);
        calendarText = findViewById(R.id.date_text);
        //Trae el día actual
        String dateOfToday = getDateOfToday();
        calendarText.setText(dateOfToday);
        setCalendarOnClickButton();
    }

    @NotNull
    private String getDateOfToday() {
        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        setDate(today);
        String dateString = (String) DateFormat.format("yyyy-MM-dd", today);
        return dateString;
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
                String dateString = (String) DateFormat.format("yyyy-MM-dd", date);
                calendarText.setText(dateString);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public Calendar getTime() {
        return timeOfEvent;
    }

    public void setTime(Calendar timeOfEvent) {
        this.timeOfEvent = timeOfEvent;
    }
    public Calendar getDate() {
        return dateOfEvent;
    }

    public void setDate(Calendar dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }
    private void setNameLessonsOptions() {
        int size=0;
        ArrayList<String> names = new ArrayList<String>();
        BdLessons bdLessons = new BdLessons();
        if(bdLessons.getConnection()!=null){
            Toast.makeText(CreateSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            names= bdLessons.searchName();
            size=names.size();
            bdLessons.dropConnection();
        }else{
            Toast.makeText(CreateSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] LESSONSNAME = new String[size];
        for (int i = 0; i <size ; i++) {
            String name = names.get(i);
            LESSONSNAME[i]=name;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LESSONSNAME);
        AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                findViewById(R.id.inputSessionsName);
        ccStudent.setAdapter(adapter);
    }
    private void setCcTeacheroptions() {
        //Change options
        int size=0;
        ArrayList<String> ccsTeachers = new ArrayList<String>();
        BdSessions bdSessions = new BdSessions();
        if(bdSessions.getConnection()!=null){
            Toast.makeText(CreateSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ccsTeachers= bdSessions.searchCcTeacher();
            size=ccsTeachers.size();
            bdSessions.dropConnection();
        }else{
            Toast.makeText(CreateSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] CCTEACHERS = new String[size];
        for (int i = 0; i <size ; i++) {
            String ccsTeacher = ccsTeachers.get(i);
            CCTEACHERS[i]=ccsTeacher;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CCTEACHERS);
        AutoCompleteTextView idSession = (AutoCompleteTextView)
                findViewById(R.id.inputSessionsCcTeacher);
        idSession.setAdapter(adapter);
    }
    private void setIdClassRoomNumberptions() {
        //Change options
        int size=0;
        ArrayList<Integer> classroomNumbers = new ArrayList<Integer>();
        BdSessions bdSessions = new BdSessions();
        if(bdSessions.getConnection()!=null){
            Toast.makeText(CreateSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            classroomNumbers= bdSessions.searchClassroomNumber();
            size=classroomNumbers.size();
            bdSessions.dropConnection();
        }else{
            Toast.makeText(CreateSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
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
                findViewById(R.id.inputSessionsClassRoomNumber);
        idSession.setAdapter(adapter);
    }
}