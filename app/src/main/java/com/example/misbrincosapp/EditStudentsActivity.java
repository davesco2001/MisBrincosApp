package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
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

import com.example.misbrincosapp.BD.BdStudent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class EditStudentsActivity extends AppCompatActivity {

    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button editButton;
    ImageButton searchButton;
    TextView nameStudent;
    TextView cc;
    TextView phoneNumber;
    TextView email;
    AutoCompleteTextView ccStudent;
    EditText telS;
    BdStudent bdStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_students);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        editButton = (Button) findViewById(R.id.button_edit_students);
        onClickEdit(editButton);
        searchButton = findViewById(R.id.iconCardEditStudentCc);
        onSearchcc(searchButton);
    }

    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_edit_students);
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
                setIdStudentOptions();
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
                    Toast.makeText(EditStudentsActivity.this, R.string.create_student, Toast.LENGTH_SHORT).show();
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        dbInteraction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(EditStudentsActivity.this, R.string.bad_inputs, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void dbInteraction() {
        ccStudent = findViewById(R.id.inputStudentsCc);
        telS = findViewById(R.id.inputEditStudentsPhone);

        String ccS = cc.getText().toString();
        String telS = phoneNumber.getText().toString();

        bdStudent = new BdStudent();
        if (bdStudent.getConnection() != null) {
            Toast.makeText(EditStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            bdStudent.updateStudent(ccS, telS);
            bdStudent.dropConnection();
            finish();

        } else {
            Toast.makeText(EditStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validationInputs() {
        ccStudent = findViewById(R.id.inputStudentsCc);
        telS = findViewById(R.id.inputEditStudentsPhone);
        if ((ccStudent.getText().toString().equals(""))&&(ccStudent.getText().toString().length()>10) && (telS.getText().toString().equals(""))&& (telS.getText().toString().length()>10)) {
            return false;
        } else {
            return true;
        }

    }

    private void setIdStudentOptions() {

        //Change options
        int size = 0;
        ArrayList<String> ccS = new ArrayList<String>();
        BdStudent bdStudent = new BdStudent();
        if (bdStudent.getConnection() != null) {
            Toast.makeText(EditStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ccS = bdStudent.searchCc();
            size = ccS.size();
            bdStudent.dropConnection();
        } else {
            Toast.makeText(EditStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] STUDENTCC = new String[size];
        for (int i = 0; i < size; i++) {
            String cc = ccS.get(i);
            STUDENTCC[i] = cc;
        }

        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STUDENTCC);
        AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                findViewById(R.id.inputStudentsCc);
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
    private void setCcStudent() {
        //Change options
        int size=0;
        ArrayList<String> ccS = new ArrayList<String>();
        BdStudent bdStudent = new BdStudent();
        if(bdStudent.getConnection()!=null){
            Toast.makeText(EditStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ccS= bdStudent.searchCc();
            size=ccS.size();
            bdStudent.dropConnection();
        }else{
            Toast.makeText(EditStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] STUDENTCC = new String[size];
        for (int i = 0; i <size ; i++) {
            String cc = ccS.get(i);
            STUDENTCC[i]=cc;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STUDENTCC);
        AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                findViewById(R.id.inputStudentsCc);
        ccStudent.setAdapter(adapter);
        //Change options
    }
    private void onSearchcc(ImageButton searchButton){
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AutoCompleteTextView ccStudent = (AutoCompleteTextView)
                        findViewById(R.id.inputStudentsCc);
                //Search IN bd
                String keyStudent = (String.valueOf(ccStudent.getText()));
                telS = findViewById(R.id.inputEditStudentsPhone);

                String name = searchStudentname(keyStudent);
                nameStudent.setText(name);
                String tel = searchStudentPhoneNumber(keyStudent);
                phoneNumber.setText(tel);
                String emailS = searchStudentemail(keyStudent);
                email.setText(emailS);
            }
        });
    }
    private String searchStudentname(String id) {
        BdStudent bdStudent= new BdStudent();
        String name = null;
        if(bdStudent.getConnection()!=null){
            Toast.makeText(EditStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> nameS = bdStudent.searchStudentName(id);
            if(1==nameS.size()){
                for (int i = 0; i <nameS.size() ; i++) {
                    name= nameS.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(EditStudentsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(EditStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return name;
    }
    private String searchStudentPhoneNumber(String id) {
        BdStudent bdStudent= new BdStudent();
        String telefono = null;
        if(bdStudent.getConnection()!=null){
            Toast.makeText(EditStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> tel = bdStudent.searchPhoneNumber();
            if(1==tel.size()){
                for (int i = 0; i <tel.size() ; i++) {
                    telefono= tel.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(EditStudentsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(EditStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return telefono;
    }
    private String searchStudentemail(String id) {
        BdStudent bdStudent= new BdStudent();
        String email = null;
        if(bdStudent.getConnection()!=null){
            Toast.makeText(EditStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> emailS = bdStudent.searchStudentEmail(id);
            if(1==emailS.size()){
                for (int i = 0; i <emailS.size() ; i++) {
                    email= emailS.get(i);
                }
                bdStudent.dropConnection();
            }else{
                Toast.makeText(EditStudentsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(EditStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return email;
    }


}
