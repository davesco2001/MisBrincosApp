package com.example.misbrincosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdStudent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;

public class CreateStudentsActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button createButton;
    EditText nameStudent;
    EditText cc;
    EditText phoneNumber;
    EditText email;
    BdStudent bdStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_students);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        createButton = (Button) findViewById(R.id.button_create_students);
        onClickCreate(createButton);
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

        } else {
            finish();
        }
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_create_students);
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
    private void onClickCreate(Button createButton) {
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correctInputs = validationInputs();
                if(correctInputs) { if(correctInputs){
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        dbInteraction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(CreateStudentsActivity.this, R.string.bad_inputs, Toast.LENGTH_SHORT).show();
                }
                }
            }
        })
        ;}
    private void dbInteraction() {
        nameStudent = (EditText) findViewById(R.id.inputStudentsName);
        cc = (EditText) findViewById(R.id.inputStudentsCc);
        phoneNumber = (EditText) findViewById(R.id.inputStudentsPhone);
        email = (EditText) findViewById(R.id.inputStudentsEmail);
        String name = nameStudent.getText().toString();
        String emailS = email.getText().toString();
        String  cedula = cc.getText().toString();
        String  tel = phoneNumber.getText().toString();
        bdStudent = new BdStudent();
        if(bdStudent.getConnection()!=null){
            Toast.makeText(CreateStudentsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            bdStudent.addStudent(cedula, name ,tel, emailS);
            singUp(emailS, cedula);
            bdStudent.dropConnection();
            finish();

        }else{
            Toast.makeText(CreateStudentsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validationInputs() {
        nameStudent = (EditText) findViewById(R.id.inputStudentsName);
        cc = (EditText) findViewById(R.id.inputStudentsCc);
        phoneNumber = (EditText) findViewById(R.id.inputStudentsPhone);
        email = (EditText) findViewById(R.id.inputStudentsEmail);
        String nameInput=nameStudent.getText().toString();
        //Validate that inputs are empty
        if((nameStudent.getText().toString().equals(""))&&(nameInput.length()>60)&&(cc.getText().toString().equals(""))&&(cc.getText().toString().length()>10)&&(phoneNumber.getText().toString().equals(""))&&(phoneNumber.getText().toString().length()>10)&&(email.getText().toString().equals("")&&(email.getText().toString().length()>30))){
            return false;
        }else{
            return true;
        }
    }
    private void singUp(String emailInput, String passwordInput){
        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user= mAuth.getCurrentUser();
                    String uId= user.getUid();
                    Toast.makeText(CreateStudentsActivity.this, R.string.sign_up_succes, Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthRecentLoginRequiredException e){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(emailInput, passwordInput);
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d("TAG", "User re-authenticated.");
                                    }
                                });
                    } catch (Exception e) {
                        Toast.makeText(CreateStudentsActivity.this, R.string.sign_up_failed, Toast.LENGTH_SHORT).show();
                        Log.e("TAG", e.getMessage());
                    }
                }
            }
        });
    }

}