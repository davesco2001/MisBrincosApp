package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MisBrincosAppHomeActivity extends AppCompatActivity implements FunctionAdapter.ListItemClick{
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    //Entities
    private static final int classValue = 1;
    private static final int sessionValue = 2;
    private static final int studentValue = 3;
    private static final int packageValue = 4;
    private static final int bookingValue = 5;
    //CRUD
    private static final int createValue = 6;
    private static final int showValue = 7;
    private static final int editValue = 8;
    private static final int deleteValue = 9;
    ArrayList<Function> functionsAdmin;
    ArrayList<Function> functionsUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_brincos_app_home);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        recyclerView = findViewById(R.id.recycle_functions_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getUid().equals("XIEfOnMpi7dsmtV6mT7JEGpJCiC3")) {
                getAdminFunctions();
            } else {
                getNormalUserFunctions();
            }
        } else {
            finish();
        }
    }

    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_mis_brincos_home);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.Toolbar_TitleText);
        if (getSupportActionBar() != null) {
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setHomeButtonEnabled(true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.action_user_favorite:
                openUserInfoActivity();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
    private void openUserInfoActivity() {
        Intent intent= new Intent(MisBrincosAppHomeActivity.this,UserInfoActivity.class);
        startActivity(intent);
    }
    private void getAdminFunctions() {
        upDateUIAdmin();
    }

    private void getNormalUserFunctions() {
        upDateUIUser();
        //setLogoIconToolBar(); Not working produce error, check later when you have time ;)
    }

    private void setLogoIconToolBar() {
        toolbar = findViewById(R.id.toolbar_mis_brincos_home);
        toolbar.setLogo(R.drawable.ic_book_black_24dp);
    }

    private void upDateUIAdmin() {

        functionsAdmin = new ArrayList<Function>();
        functionsAdmin.add(new Function(getString(R.string.classes_function), classValue));
        functionsAdmin.add(new Function(getString(R.string.sessions_functions), sessionValue));
        functionsAdmin.add(new Function(getString(R.string.students_function), studentValue));
        functionsAdmin.add(new Function(getString(R.string.packages_function), packageValue));
        FunctionAdapter functionAdapter = new FunctionAdapter(functionsAdmin, this);
        recyclerView.setAdapter(functionAdapter);
    }

    private void upDateUIUser() {
        functionsUser = new ArrayList<Function>();
        functionsUser.add(new Function(getString(R.string.create_booking), createValue));
        functionsUser.add(new Function(getString(R.string.delete_booking), deleteValue));
        functionsUser.add(new Function(getString(R.string.show_bookings), showValue));
        FunctionAdapter functionAdapter = new FunctionAdapter(functionsUser, this);
        recyclerView.setAdapter(functionAdapter);
    }

    @Override
    public void onListItemClick(int clickedItem) {
        if (currentUser.getUid().equals("XIEfOnMpi7dsmtV6mT7JEGpJCiC3")) {
            int size = functionsAdmin.size();
            for (int i = 0; i < size; i++) {
                if (i == clickedItem) {
                    Function functionSelected = functionsAdmin.get(i);
                    String name = functionSelected.getName();
                    if (name.equals(getString(R.string.classes_function))) {
                        Intent intent = new Intent(MisBrincosAppHomeActivity.this, LessonsActivity.class);
                        startActivity(intent);
                    }
                    if (name.equals(getString(R.string.sessions_functions))) {
                        Intent intent = new Intent(MisBrincosAppHomeActivity.this, SessionsActivity.class);
                        startActivity(intent);
                    }
                    if (name.equals(getString(R.string.students_function))) {
                        Intent intent = new Intent(MisBrincosAppHomeActivity.this, StudentsActivity.class);
                        startActivity(intent);
                    }
                    if (name.equals(getString(R.string.packages_function))) {
                        Intent intent = new Intent(MisBrincosAppHomeActivity.this, PackagesActivity.class);
                        startActivity(intent);
                    }
                }
            }
        } else {
            int size = functionsUser.size();
            for (int i = 0; i < size; i++) {
                if (i == clickedItem) {
                    Function functionSelected = functionsUser.get(i);
                    String name = functionSelected.getName();
                    if (name.equals(getString(R.string.create_booking))) {
                        Intent intent = new Intent(MisBrincosAppHomeActivity.this, CreateBookingActivity.class);
                        startActivity(intent);
                    }
                    if (name.equals(getString(R.string.delete_booking))) {
                        Intent intent = new Intent(MisBrincosAppHomeActivity.this, DeleteBookingActivity.class);
                        startActivity(intent);
                    }
                    if (name.equals(getString(R.string.show_bookings))) {
                        Intent intent = new Intent(MisBrincosAppHomeActivity.this, ShowBookingsActivity.class);
                        startActivity(intent);
                    }
                }

            }
        }
    }
}