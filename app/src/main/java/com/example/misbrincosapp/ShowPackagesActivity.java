package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdLessons;
import com.example.misbrincosapp.BD.BdPackages;
import com.example.misbrincosapp.model.Lesson;
import com.example.misbrincosapp.model.Package;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ShowPackagesActivity extends AppCompatActivity implements PackagesAdapter.ListItemClick {

    private static final int showValue = 7;
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    ArrayList<Package> packages;
    BdPackages bdPackages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_packages);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        recyclerView = findViewById(R.id.recycle_show_packages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                getPackagesToActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_show_packages);
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

    private void getPackagesToActivity() {
        packages = new ArrayList<Package>();
        //Loop that brings the packages from db
        bdPackages = new BdPackages();
        if(bdPackages.getConnection()!=null){
            Toast.makeText(ShowPackagesActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<Integer> ids = bdPackages.searchId();
            ArrayList<String> plans= bdPackages.searchPlan();
            ArrayList<Integer> tDays = bdPackages.searchTotalOfDays();
            ArrayList<Integer> tLessons = bdPackages.searchTotalOfLessons();
            ArrayList<String> prices = bdPackages.searchPrice();
            if((ids.size()==plans.size())&&(ids.size()==tDays.size())&&(ids.size()==tLessons.size())&&(ids.size()==prices.size())){
                for (int i = 0; i <ids.size() ; i++) {
                    int id = ids.get(i);
                    String plan = plans.get(i);
                    int days = tDays.get(i);
                    int lessons = tLessons.get(i);
                    String price = prices.get(i);
                    Package aPackage= new Package(id, plan, days, lessons, price);
                    packages.add(aPackage);
                }
                bdPackages.dropConnection();
                setPackagesAdapter(packages);
            }else{
                Toast.makeText(ShowPackagesActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ShowPackagesActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
    }

    private void setPackagesAdapter(ArrayList<Package> packages) {
        PackagesAdapter packagesAdapter = new PackagesAdapter(packages, this);
        recyclerView.setAdapter(packagesAdapter);
    }

    @Override
    public void onListItemClick(int clickedItem) {
        int size = packages.size();
        for (int i = 0; i < size; i++) {
            if (i == clickedItem) {
                Package packageClicked = packages.get(i);
                int packageClickedId=packageClicked.getId();
                //Intent with the key of the table
                Intent intent = new Intent(ShowPackagesActivity.this, ViewPackageActivity.class);
                intent.putExtra("ID", packageClickedId);
                startActivity(intent);
            }

        }
    }
}