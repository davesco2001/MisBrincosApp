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
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdSessions;
import com.example.misbrincosapp.BD.BdStudent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DeleteSessionsActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button deleteButton;
    AutoCompleteTextView autoCompleteTextViewIdSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_sessions);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        deleteButton = (Button) findViewById(R.id.button_delete_sessions);
        onClickDelete(deleteButton);
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_delete_sessions);
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
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                setIdSessionsOptions();
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
    private void onClickDelete(Button deleteButton) {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextViewIdSession = findViewById(R.id.inputDeleteLessonName);
                //Use this to do something with the key of the db when you have a autoCompleteTextView
                String idSessionString=autoCompleteTextViewIdSession .getText().toString();
                int idSession = Integer.parseInt(idSessionString);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    BdSessions bdSessions = new BdSessions();
                    if(bdSessions.getConnection()!=null){
                        Toast.makeText(DeleteSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                        bdSessions.deleteSession(idSession);
                        bdSessions.deleteRealiza(idSession);
                        bdSessions.dropConnection();
                        finish();

                    }else{
                        Toast.makeText(DeleteSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void setIdSessionsOptions() {
        int size=0;
        ArrayList<Integer> ids = new ArrayList<Integer>();
        BdSessions bdSessions = new BdSessions();
        if(bdSessions.getConnection()!=null){
            Toast.makeText(DeleteSessionsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ids= bdSessions.searchId();
            size=ids.size();
        }else{
            Toast.makeText(DeleteSessionsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
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
                findViewById(R.id.inputDeleteSessionsId);
        idSession.setAdapter(adapter);
        //Change options
    }
}