package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdPackages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreatePackagesActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button createButton;
    EditText namePackage;
    EditText totalLessons;
    EditText price;
    EditText totalDays;
    BdPackages bdPackages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_packages);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        createButton = (Button) findViewById(R.id.button_create_packages);
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
        toolbar = findViewById(R.id.toolbar_create_packages);
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
                if(correctInputs){
                    Toast.makeText(CreatePackagesActivity.this, R.string.creating_student, Toast.LENGTH_SHORT).show();
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        dbInteraction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(CreatePackagesActivity.this, R.string.bad_inputs, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void dbInteraction() {
        namePackage = (EditText) findViewById(R.id.inputPackageName);
        totalLessons = (EditText) findViewById(R.id.inputPackageTotalOfLessons);
        price = (EditText) findViewById(R.id.inputPackagePrice);
        totalDays = (EditText) findViewById(R.id.inputPackageTotalOfDays);
        int id = (int) (Math.random() * (200-1)) + 1;
        String plan = namePackage.getText().toString();
        int tLessons = Integer.parseInt(totalLessons.getText().toString());
        String pPrice = price.getText().toString();
        int tDays = Integer.parseInt(totalDays.getText().toString());
        bdPackages = new BdPackages();
        if(bdPackages.getConnection()!=null){
            Toast.makeText(CreatePackagesActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            bdPackages.addPackage(id, plan, tDays, tLessons, pPrice);
            bdPackages.dropConnection();
            finish();

        }else{
            Toast.makeText(CreatePackagesActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validationInputs() {
        namePackage = (EditText) findViewById(R.id.inputPackageName);
        totalLessons = (EditText) findViewById(R.id.inputPackageTotalOfLessons);
        price = (EditText) findViewById(R.id.inputPackagePrice);
        totalDays = (EditText) findViewById(R.id.inputPackageTotalOfDays);
        String nameInput=namePackage.getText().toString();
        //Validate that inputs are empty
        if((namePackage.getText().toString().equals(""))&&(nameInput.length()>60)&&(totalLessons.getText().toString().equals(""))&&(price.getText().toString().equals(""))&&(price.getText().toString().length()>20)&&(totalDays.getText().toString().equals(""))){
            return false;
        }else{
            return true;
        }
    }
}