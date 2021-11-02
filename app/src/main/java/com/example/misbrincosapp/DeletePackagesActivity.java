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
import com.example.misbrincosapp.BD.BdPackages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DeletePackagesActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button deleteButton;
    AutoCompleteTextView autoCompleteTextViewIdPackage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_packages);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        deleteButton = (Button) findViewById(R.id.button_delete_package);
        onClickDelete(deleteButton);
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_delete_packages);
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
                setIdPackageOptions();;
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
                autoCompleteTextViewIdPackage = findViewById(R.id.inputDeletePakcageId);
                //Search for setNameLessonsOptions in DeleteLessons and the structure of the onClickDelete in the same activity
                int packageId= Integer.parseInt(autoCompleteTextViewIdPackage.getText().toString());
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    BdPackages bdPackages = new BdPackages();
                    if(bdPackages.getConnection()!=null){
                        Toast.makeText(DeletePackagesActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                        bdPackages.deletePackage(packageId);
                        bdPackages.dropConnection();
                        finish();

                    }else{
                        Toast.makeText(DeletePackagesActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void setIdPackageOptions() {
        int size=0;
        ArrayList<Integer> ids = new ArrayList<Integer>();
        BdPackages bdPackages = new BdPackages();
        if(bdPackages.getConnection()!=null){
            Toast.makeText(DeletePackagesActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ids= bdPackages.searchId();
            size=ids.size();
            bdPackages.dropConnection();
        }else{
            Toast.makeText(DeletePackagesActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] PACKAGEID= new String[size];
        for (int i = 0; i <size ; i++) {
            String id = ids.get(i).toString();
            PACKAGEID[i]=id;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PACKAGEID);
        AutoCompleteTextView idPackage = (AutoCompleteTextView)
                findViewById(R.id.inputDeletePakcageId);
        idPackage.setAdapter(adapter);
    }
}