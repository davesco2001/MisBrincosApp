package com.example.misbrincosapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.misbrincosapp.BD.BdTeacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class EditTeachersActivity extends AppCompatActivity {

    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button editButton;
    ImageButton searchButton;
    TextView nameTeacher;
    TextView cc;
    TextView email;
    AutoCompleteTextView ccTeacher;
    EditText emailT;
    BdTeacher bdTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teachers);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        editButton = (Button) findViewById(R.id.button_edit_teachers);
        onClickEdit(editButton);
        searchButton = findViewById(R.id.iconCardEditTeacherCc);
        onSearchcc(searchButton);
    }

    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_edit_teachers);
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
                setIdTeacherOptions();
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

    private void onClickEdit(Button createButton) {
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correctInputs = validationInputs();
                if (correctInputs) {
                    Toast.makeText(EditTeachersActivity.this, R.string.create_teacher, Toast.LENGTH_SHORT).show();
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        dbInteraction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(EditTeachersActivity.this, R.string.bad_inputs, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void dbInteraction() {
        ccTeacher = findViewById(R.id.inputTeachersCc);
        emailT = findViewById(R.id.inputEditTeachersEmail);

        String ccS = cc.getText().toString();
        String emailTeachers = email.getText().toString();

        bdTeacher= new BdTeacher();
        if (bdTeacher.getConnection() != null) {
            Toast.makeText(EditTeachersActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            bdTeacher.updateTeacher(ccS, emailTeachers);
            bdTeacher.dropConnection();
            finish();

        } else {
            Toast.makeText(EditTeachersActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validationInputs() {
        ccTeacher = findViewById(R.id.inputTeachersCc);
        emailT = findViewById(R.id.inputEditTeachersEmail);
        if ((ccTeacher.getText().toString().equals(""))&&(ccTeacher.getText().toString().length()>10) && (emailT.getText().toString().equals(""))&& (emailT.getText().toString().length()>30)) {
            return false;
        } else {
            return true;
        }

    }

    private void setIdTeacherOptions() {

        //Change options
        int size = 0;
        ArrayList<String> ccS = new ArrayList<String>();
        BdTeacher bdTeacher = new BdTeacher();
        if (bdTeacher.getConnection() != null) {
            Toast.makeText(EditTeachersActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ccS = bdTeacher.searchCc();
            size = ccS.size();
            bdTeacher.dropConnection();
        } else {
            Toast.makeText(EditTeachersActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
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
                findViewById(R.id.inputTeachersCc);
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
    private void setCcTeacher() {
        //Change options
        int size=0;
        ArrayList<String> ccS = new ArrayList<String>();
        BdTeacher bdTeacher = new BdTeacher();
        if(bdTeacher.getConnection()!=null){
            Toast.makeText(EditTeachersActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ccS= bdTeacher.searchCc();
            size=ccS.size();
            bdTeacher.dropConnection();
        }else{
            Toast.makeText(EditTeachersActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] TEACHERCC = new String[size];
        for (int i = 0; i <size ; i++) {
            String cc = ccS.get(i);
            TEACHERCC[i]=cc;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, TEACHERCC);
        AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                findViewById(R.id.inputTeachersCc);
        ccStudent.setAdapter(adapter);
        //Change options
    }
    private void onSearchcc(ImageButton searchButton){
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AutoCompleteTextView ccTeacher = (AutoCompleteTextView)
                        findViewById(R.id.inputTeachersCc);
                //Search IN bd
                String keyTeacher = (String.valueOf(ccTeacher.getText()));
                emailT = findViewById(R.id.inputEditTeachersEmail);

                String name = searchTeachername(keyTeacher);
                nameTeacher.setText(name);
                String emailT = searchTeacheremail(keyTeacher);
                email.setText(emailT);


            }
        });
    }
    private String searchTeachername(String id) {
        BdTeacher bdTeacher= new BdTeacher();
        String name = null;
        if(bdTeacher.getConnection()!=null){
            Toast.makeText(EditTeachersActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> nameT = bdTeacher.searchTeacherName(id);
            if(1==nameT.size()){
                for (int i = 0; i <nameT.size() ; i++) {
                    name= nameT.get(i);
                }
                bdTeacher.dropConnection();
            }else{
                Toast.makeText(EditTeachersActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(EditTeachersActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return name;
    }

    private String searchTeacheremail(String id) {
        BdTeacher bdTeacher= new BdTeacher();
        String email = null;
        if(bdTeacher.getConnection()!=null){
            Toast.makeText(EditTeachersActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> emailT = bdTeacher.searchTeacherEmail(id);
            if(1==emailT.size()){
                for (int i = 0; i <emailT.size() ; i++) {
                    email= emailT.get(i);
                }
                bdTeacher.dropConnection();
            }else{
                Toast.makeText(EditTeachersActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(EditTeachersActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return email;
    }


}