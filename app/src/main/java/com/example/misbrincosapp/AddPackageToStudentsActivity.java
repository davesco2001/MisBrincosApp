package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdPackages;
import com.example.misbrincosapp.BD.BdStudent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddPackageToStudentsActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button addPackageToStudentButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_package_to_students);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        addPackageToStudentButton = (Button) findViewById(R.id.button_add_package_to_student);
        onClickAdd(addPackageToStudentButton);
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_add_package_to_student);
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

                setCcStudentOptions();
                setIdPackageOptions();
                setNameLessonOptions();
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
    private void onClickAdd(Button createButton) {
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                            findViewById(R.id.inputPakcageToStudentsCc);
                    AutoCompleteTextView idPackage = (AutoCompleteTextView)
                            findViewById(R.id.inputPackageToStudentId);
                    MultiAutoCompleteTextView lessonsNames = findViewById(R.id.inputPackageToStudentName);
                    if ((!ccStudent.getText().toString().equals("")) && (!idPackage.getText().toString().equals("")) && (!lessonsNames.getText().toString().equals(""))){
                        int totalP = totalOfPackages(ccStudent.getText().toString());
                        int totalL = totalOfLessons(Integer.parseInt(idPackage.getText().toString()));
                        if(totalP>0){
                            Toast.makeText(AddPackageToStudentsActivity.this, "You already have 1 active package", Toast.LENGTH_SHORT).show();
                        }else{
                            String lessonsName = lessonsNames.getText().toString();
                            lessonsName = lessonsName.substring(0, lessonsName.length()-1);
                            String lessons[] = lessonsName.split(",");
                            if(lessons.length>totalL){
                                Toast.makeText(AddPackageToStudentsActivity.this, "You have exceed teh limit of lessons = "+totalL, Toast.LENGTH_SHORT).show();
                            }else{
                                int days= getTotalOfDays(Integer.parseInt(idPackage.getText().toString()));
                                Date currentDate = new Date();
                                Calendar today = Calendar.getInstance(TimeZone.getDefault());
                                today.setTime(currentDate);
                                String dateTodayString = (String) DateFormat.format("yyyy-MM-dd", today);
                                today.add(Calendar.DATE, days);
                                String dateFinalString = (String) DateFormat.format("yyyy-MM-dd", today);
                                String ccS = ccStudent.getText().toString();
                                int idP = Integer.parseInt(idPackage.getText().toString());
                                BdPackages bdPackages = new BdPackages();
                                if(bdPackages.getConnection()!=null){
                                    Toast.makeText(AddPackageToStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                                    bdPackages.addCompra(idP, ccS, dateTodayString, dateFinalString);
                                    for (String lessonName: lessons) {
                                        bdPackages.addContiene(idP,lessonName);
                                    }
                                    bdPackages.dropConnection();
                                    finish();

                                }else{
                                    Toast.makeText(AddPackageToStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private int getTotalOfDays(int idPackage) {
        BdPackages bdPackages = new BdPackages();
        int total = 0;
        if(bdPackages.getConnection()!=null){
            Toast.makeText(AddPackageToStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> totals = bdPackages.searchPackageTotalOfDays(idPackage);
            if(1==totals.size()){
                for (int i = 0; i <totals.size() ; i++) {
                    total= totals.get(i);
                }
                bdPackages.dropConnection();
            }else{
                Toast.makeText(AddPackageToStudentsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(AddPackageToStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return total;
    }

    private int totalOfPackages(String studentCc) {
        BdPackages bdPackages = new BdPackages();
        int total = 0;
        if(bdPackages.getConnection()!=null){
            Toast.makeText(AddPackageToStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> totals = bdPackages.searchStudentPackages(studentCc);
            if(1==totals.size()){
                for (int i = 0; i <totals.size() ; i++) {
                    total= totals.get(i);
                }
                bdPackages.dropConnection();
            }else{
                Toast.makeText(AddPackageToStudentsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(AddPackageToStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return total;
    }

    private int totalOfLessons(int idPackage) {
        BdPackages bdPackages = new BdPackages();
        int total = 0;
        if(bdPackages.getConnection()!=null){
            Toast.makeText(AddPackageToStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> totals = bdPackages.searchLessonsPackages(idPackage);
            if(1==totals.size()){
                for (int i = 0; i <totals.size() ; i++) {
                    total= totals.get(i);
                }
                bdPackages.dropConnection();
            }else{
                Toast.makeText(AddPackageToStudentsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(AddPackageToStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return total;
    }

    private void setCcStudentOptions() {
        int size=0;
        ArrayList<String> ccs = new ArrayList<String>();
        BdStudent bdStudent = new BdStudent();
        if(bdStudent.getConnection()!=null){
            Toast.makeText(AddPackageToStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ccs= bdStudent.searchCc();
            size=ccs.size();
            bdStudent.dropConnection();
        }else{
            Toast.makeText(AddPackageToStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] STUDENTSCCS = new String[size];
        for (int i = 0; i <size ; i++) {
            String id = ""+ccs.get(i);
            STUDENTSCCS[i]=id;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STUDENTSCCS);
        AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                findViewById(R.id.inputPakcageToStudentsCc);
        ccStudent.setAdapter(adapter);
    }
    private void setIdPackageOptions() {
        int size=0;
        ArrayList<Integer> ids = new ArrayList<Integer>();
        BdPackages bdPackages = new BdPackages();
        if(bdPackages.getConnection()!=null){
            Toast.makeText(AddPackageToStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ids= bdPackages.searchId();
            size=ids.size();
            bdPackages.dropConnection();
        }else{
            Toast.makeText(AddPackageToStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] PACKAGESID = new String[size];
        for (int i = 0; i <size ; i++) {
            String id = ""+ids.get(i);
            PACKAGESID[i]=id;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PACKAGESID);
        AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                findViewById(R.id.inputPackageToStudentId);
        ccStudent.setAdapter(adapter);
    }
    private void setNameLessonOptions() {
        int size=0;
        ArrayList<String> names = new ArrayList<String>();
        BdLessons bdLessons = new BdLessons();
        if(bdLessons.getConnection()!=null){
            Toast.makeText(AddPackageToStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            names= bdLessons.searchName();
            size=names.size();
            bdLessons.dropConnection();
        }else{
            Toast.makeText(AddPackageToStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] LESSONSNAME = new String[size];
        for (int i = 0; i <size ; i++) {
            String name = names.get(i);
            LESSONSNAME[i]=name;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LESSONSNAME);
        MultiAutoCompleteTextView ccStudent = findViewById(R.id.inputPackageToStudentName);
        ccStudent.setAdapter(adapter);
        ccStudent.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}