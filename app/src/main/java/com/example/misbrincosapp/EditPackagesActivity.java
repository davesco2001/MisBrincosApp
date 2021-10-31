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

import com.example.misbrincosapp.BD.BdPackages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class EditPackagesActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button editButton;
    ImageButton searchButton;
    TextView packageName;
    TextView price;
    AutoCompleteTextView id;
    EditText totalLessons;
    EditText totalDays;
    BdPackages bdPackages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_packages);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        editButton = (Button) findViewById(R.id.button_edit_packages);
        onClickEdit(editButton);
        searchButton = (ImageButton) findViewById(R.id.iconCardEditPakcageId);
        onSearchId(searchButton);
    }
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_edit_packages);
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
                setIDPackagesOptions();
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
                if(correctInputs){
                    Toast.makeText(EditPackagesActivity.this, R.string.create_package, Toast.LENGTH_SHORT).show();
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        dbInteraction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(EditPackagesActivity.this, R.string.bad_inputs, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void dbInteraction() {
        id = findViewById(R.id.inputPackageId);
        totalLessons = findViewById(R.id.inputEditPackageTotalOfLessons);
        totalDays = findViewById(R.id.inputEditPackageTotalOfDays);

        int idP= Integer.parseInt(id.getText().toString());
        int tLessons=Integer.parseInt(totalLessons.getText().toString());
        int tDays=Integer.parseInt(totalDays.getText().toString());

        bdPackages = new BdPackages();
        if(bdPackages.getConnection()!=null){
            Toast.makeText(EditPackagesActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            bdPackages.updatePackage(idP, tLessons, tDays);
            bdPackages.dropConnection();
            finish();

        }else{
            Toast.makeText(EditPackagesActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validationInputs() {
        totalLessons = findViewById(R.id.inputEditPackageTotalOfLessons);
        totalDays = findViewById(R.id.inputEditPackageTotalOfDays);
        if((totalLessons.getText().toString().equals(""))&&(totalDays.getText().toString().equals(""))){
            return false;
        }else{
            return true;
        }

    }

    private void onSearchId(ImageButton searchButton){
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AutoCompleteTextView idPackage = (AutoCompleteTextView)
                        findViewById(R.id.inputPackageId);
                //Search IN bd
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    int keySession = Integer.parseInt(String.valueOf(idPackage.getText()));
                    packageName = findViewById(R.id.textEditPackageName);
                    price = findViewById(R.id.textEditPackagePrice);
                    String name = searchPackageName(keySession);
                    packageName.setText(name);
                    String pPrice = searchPackagePrice(keySession);
                    price.setText(pPrice);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String searchPackageName(int id) {
        BdPackages bdPackages= new BdPackages();
        String name = null;
        if(bdPackages.getConnection()!=null){
            Toast.makeText(EditPackagesActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> names = bdPackages.searchPackagePlan(id);
            if(1==names.size()){
                for (int i = 0; i <names.size() ; i++) {
                    name= names.get(i);
                }
                bdPackages.dropConnection();
            }else{
                Toast.makeText(EditPackagesActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(EditPackagesActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return name;
    }

    private String searchPackagePrice(int id) {
        BdPackages bdPackages= new BdPackages();
        String pPrice = null;
        if(bdPackages.getConnection()!=null){
            Toast.makeText(EditPackagesActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ArrayList<String> prices = bdPackages.searchPackagePrice(id);
            if(1==prices.size()){
                for (int i = 0; i <prices.size() ; i++) {
                    pPrice= prices.get(i);
                }
                bdPackages.dropConnection();
            }else{
                Toast.makeText(EditPackagesActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(EditPackagesActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        return pPrice;
    }

    private void setIDPackagesOptions() {
        int size=0;
        ArrayList<Integer> ids = new ArrayList<Integer>();
        BdPackages bdPackages = new BdPackages();
        if(bdPackages.getConnection()!=null){
            Toast.makeText(EditPackagesActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ids= bdPackages.searchId();
            size=ids.size();
        }else{
            Toast.makeText(EditPackagesActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
        }
        final String[] PACKAGESID = new String[size];
        for (int i = 0; i <size ; i++) {
            String id = ""+ids.get(i);
            PACKAGESID[i]=id;
        }
        //Change options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PACKAGESID);
        AutoCompleteTextView idPackage = (AutoCompleteTextView)
                findViewById(R.id.inputPackageId);
        idPackage.setAdapter(adapter);
    }
}