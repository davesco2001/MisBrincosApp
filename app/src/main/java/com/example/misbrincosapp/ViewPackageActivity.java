package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdPackages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ViewPackageActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    TextView planTextView;
    TextView daysTextView;
    TextView lessonsTextView;
    TextView priceTextView;
    TextView idTextView;
    TextView totalSold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_package);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
    }
    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                getPackageToActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }

    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_view_package);
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
    private void getPackageToActivity() {
        //The empty text textview to fill with the information get by db
        planTextView= findViewById(R.id.viewTextPackagePlan);
        daysTextView = findViewById(R.id.viewTextPackageDays);
        lessonsTextView = findViewById(R.id.viewTextPackageLessons);
        priceTextView = findViewById(R.id.viewTextPackagePrice);
        idTextView = findViewById(R.id.viewTextPackageId);
        totalSold = findViewById(R.id.viewTextPackageTotalSold);
        //Get the key from ShowPackagesActivity
        Intent key= getIntent();
        int keyPackage = key.getIntExtra("ID",0);
        //Create 1 method for each search
        String plan = searchPackagePlan(keyPackage);
        int totalDays= searchPackageTotalDays(keyPackage);
        int totalLessons =searchPackageTotalLessons(keyPackage);
        String price = searchPackagePrice(keyPackage);
        planTextView.setText(""+plan);
        daysTextView.setText(""+totalDays);
        lessonsTextView.setText(""+totalLessons);
        priceTextView.setText(price);
        idTextView.setText(""+searchPackageId(keyPackage));
        totalSold.setText(""+searchPackageTotalSold(plan));

    }

    private String searchPackagePlan(int keyLesson) {
        BdPackages bdPackages= new BdPackages();
        String packagePlan = "";
        if(bdPackages.getConnection()!=null){
            Toast.makeText(ViewPackageActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> pPlan = bdPackages.searchPackagePlan(keyLesson);
            if(1==pPlan.size()){
                for (int i = 0; i <pPlan.size() ; i++) {
                    packagePlan = pPlan.get(i);
                }
                bdPackages.dropConnection();
            }else{
                Toast.makeText(ViewPackageActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewPackageActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return packagePlan;
    }

    private int searchPackageTotalDays(int keyLesson) {
        BdPackages bdPackages= new BdPackages();
        int totalOfDays = 0;
        if(bdPackages.getConnection()!=null){
            Toast.makeText(ViewPackageActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> tDays = bdPackages.searchPackageTotalOfDays(keyLesson);
            if(1==tDays.size()){
                for (int i = 0; i <tDays.size() ; i++) {
                    totalOfDays= tDays.get(i);
                }
                bdPackages.dropConnection();
            }else{
                Toast.makeText(ViewPackageActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewPackageActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return totalOfDays;
    }
    private int searchPackageTotalSold(String plan) {
        BdPackages bdPackages= new BdPackages();
        int totalSold = 0;
        if(bdPackages.getConnection()!=null){
            Toast.makeText(ViewPackageActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> tDays = bdPackages.searchTotalSold(plan);
            if(1==tDays.size()){
                for (int i = 0; i <tDays.size() ; i++) {
                    totalSold= tDays.get(i);
                }
                bdPackages.dropConnection();
            }else{
                Toast.makeText(ViewPackageActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewPackageActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return totalSold;
    }

    private int searchPackageTotalLessons(int keyLesson) {
        BdPackages bdPackages= new BdPackages();
        int totalOfLessons = 0;
        if(bdPackages.getConnection()!=null){
            Toast.makeText(ViewPackageActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> tLessons = bdPackages.searchPackageTotalOfLessons(keyLesson);
            if(1==tLessons.size()){
                for (int i = 0; i <tLessons.size() ; i++) {
                    totalOfLessons= tLessons.get(i);
                }
                bdPackages.dropConnection();
            }else{
                Toast.makeText(ViewPackageActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewPackageActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return totalOfLessons;
    }
    private int searchPackageId(int keyLesson) {
        BdPackages bdPackages= new BdPackages();
        int id = 0;
        if(bdPackages.getConnection()!=null){
            Toast.makeText(ViewPackageActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> ids = bdPackages.searchPackageId(keyLesson);
            if(1==ids.size()){
                for (int i = 0; i <ids.size() ; i++) {
                    id= ids.get(i);
                }
                bdPackages.dropConnection();
            }else{
                Toast.makeText(ViewPackageActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewPackageActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return id;
    }

    private String searchPackagePrice(int keyLesson) {
        BdPackages bdPackages= new BdPackages();
        String price = "";
        if(bdPackages.getConnection()!=null){
            Toast.makeText(ViewPackageActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> pPrice = bdPackages.searchPackagePrice(keyLesson);
            if(1==pPrice.size()){
                for (int i = 0; i <pPrice.size() ; i++) {
                    price= pPrice.get(i);
                }
                bdPackages.dropConnection();
            }else{
                Toast.makeText(ViewPackageActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ViewPackageActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return price;
    }
}