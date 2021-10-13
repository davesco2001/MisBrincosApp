package com.example.misbrincosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText email;
    private EditText password;
    private String emailInput;
    private String passwordInput;
    private FirebaseAuth mAuth;
    private Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logInButton = (Button) findViewById(R.id.buttonLogIn);
        onClickLogIn(logInButton);
        setUpToolBar();
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextPersonName);
        password =findViewById(R.id.editTextTextPassword);
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_log_in);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
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

    /*Firebase Auth*/
    private void logIn(){
        mAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LogInActivity.this, R.string.login_sucess_msg, Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(LogInActivity.this,MisBrincosAppHomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LogInActivity.this, R.string.login_fail_msg, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void onClickLogIn(Button logInButton){
        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                emailInput = email.getText().toString();
                passwordInput= password.getText().toString();
                if(!emailInput.isEmpty() && !passwordInput.isEmpty()){
                    logIn();
                }else{
                    Toast.makeText(LogInActivity.this, R.string.lack_msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}