package com.example.misbrincosapp;


import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button logIn = findViewById(R.id.log_in_welcome_button);
        logIn.setOnClickListener(this);

        setContentView(R.layout.activity_main);
        setUpToolBar();
    }
    @SuppressLint("ResourceType")
    private void  setUpToolBar(){
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this,R.style.ToolbarTitleStyle);
        toolbar.setBackgroundResource(R.color.colorRebeccaPurple); //Ver por que no funciona.....
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_in_welcome_button:
                Intent intent= new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
                break;
        }
    }
}