package com.example.misbrincosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misbrincosapp.BD.BdPackages;
import com.example.misbrincosapp.BD.BdPurchase;
import com.example.misbrincosapp.model.Lesson;
import com.example.misbrincosapp.model.Package;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ShoppingsActivity extends AppCompatActivity implements ShoppingsAdapter.ListItemClick{
    FirebaseUser currentUser;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    ArrayList<Shoppings> shoppings;
    BdPackages bdPackages;
    Calendar date;
    Calendar today;
    ImageButton calendarButton;
    TextView calendarText;
    ImageButton searchId;
    ImageButton searchDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppings);
        mAuth = FirebaseAuth.getInstance();
        setUpToolBar();
        currentUser = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.recycle_show_shoppings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setCalendarButton();
        searchId = findViewById(R.id.iconCardShoppingsPakcageId);
        onSearchIdPackage(searchId);
        getDateOfToday();
        setDate(today);
        searchDates = findViewById(R.id.iconCardShoppingsDate);
        onSearchDates(searchDates);
    }
    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
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
    @SuppressLint("ResourceType")
    private void setUpToolBar() {
        toolbar = findViewById(R.id.toolbar_view_shoppings);
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

    private void onSearchDates(ImageButton searchButton){
        String dateInit = "";
        String dateFinal = "";
        if(today.after(date)){
            dateInit = (String) DateFormat.format("yyyy-MM-dd", date);
            dateFinal = getDateOfToday();
        }
        if(today.before(date)){
            dateInit = getDateOfToday();
            dateFinal = (String) DateFormat.format("yyyy-MM-dd", date);
        }
        if(today.equals(date)){
            dateInit = (String) DateFormat.format("yyyy-MM-dd", date);
            dateFinal = getDateOfToday();
        }
        String finalDateInit = dateInit;
        String finalDateFinal = dateFinal;
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AutoCompleteTextView idPackage = (AutoCompleteTextView)
                        findViewById(R.id.inputShoppingsPackageId);
                //Search IN bd
                int keyShoppings = Integer.parseInt(String.valueOf(idPackage.getText()));
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    shoppings = new ArrayList<Shoppings>();
                    bdPackages = new BdPackages();
                    if(bdPackages.getConnection()!=null){
                        Toast.makeText(ShoppingsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                        ArrayList<Integer> ids = bdPackages.searchShoppingIdPackagesDates(finalDateInit, finalDateFinal);
                        ArrayList<String> ccs= bdPackages.searchShoppingCcStudentDates(finalDateInit, finalDateFinal);
                        if((ids.size()==ccs.size())){
                            for (int i = 0; i <ids.size() ; i++) {
                                int id = ids.get(i);
                                String cc = ccs.get(i);
                                Shoppings shopping = new Shoppings(cc, id);
                                shoppings.add(shopping);
                            }
                            bdPackages.dropConnection();
                            setShoppingsAdapter(shoppings);
                        }else{
                            Toast.makeText(ShoppingsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ShoppingsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void onSearchIdPackage(ImageButton searchButton){
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AutoCompleteTextView idPackage = (AutoCompleteTextView)
                        findViewById(R.id.inputShoppingsPackageId);
                //Search IN bd
                int keyShoppings = Integer.parseInt(String.valueOf(idPackage.getText()));
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    shoppings = new ArrayList<Shoppings>();
                    bdPackages = new BdPackages();
                    if(bdPackages.getConnection()!=null){
                        Toast.makeText(ShoppingsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
                        ArrayList<String> ccs= bdPackages.searchShoppingCcStudent(Integer.parseInt(idPackage.getText().toString()));
                        if((ccs.size())>0){
                            for (int i = 0; i <ccs.size() ; i++) {
                                int id = Integer.parseInt(idPackage.getText().toString());
                                String cc = ccs.get(i);
                                Shoppings shopping = new Shoppings(cc, id);
                                shoppings.add(shopping);
                            }
                            bdPackages.dropConnection();
                            setShoppingsAdapter(shoppings);
                        }else{
                            Toast.makeText(ShoppingsActivity.this, R.string.error_in_consult, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ShoppingsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void setIDPackagesOptions() {
        int size=0;
        ArrayList<Integer> ids = new ArrayList<Integer>();
        BdPackages bdPackages = new BdPackages();
        if(bdPackages.getConnection()!=null){
            Toast.makeText(ShoppingsActivity.this, R.string.succes_bd_conection, Toast.LENGTH_SHORT).show();
            ids= bdPackages.searchId();
            size=ids.size();
        }else{
            Toast.makeText(ShoppingsActivity.this, R.string.nosucces_bd_conection, Toast.LENGTH_SHORT).show();
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
                findViewById(R.id.inputShoppingsPackageId);
        idPackage.setAdapter(adapter);
    }
    private void setCalendarButton() {
        calendarButton = findViewById(R.id.dateShoppingsButtonEdit);
        calendarText = findViewById(R.id.dateShoopingsEditText);
        //Trae el día actual
        //String dateOfToday = getDateOfToday();
        //calendarText.setText(dateOfToday);
        setCalendarOnClickButton();
    }

    @NotNull
    private String getDateOfToday() {
        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        setToday(today);
        String dateString = (String) DateFormat.format("yyyy-MM-dd", today);
        return dateString;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
    public Calendar getToday() {
        return today;
    }

    public void setToday(Calendar today) {
        this.today = today;
    }
    private void setCalendarOnClickButton() {
        //Cosntruye y define el estilo del Calendar View
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnDateListen();
            }
        });

    }

    private void setOnDateListen() {
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, month, dayOfMonth, 0, 0);
                setDate(date);
                //Db validation
                String dateInit = "";
                String dateFinal = "";
                if(today.after(date)){
                    dateInit = (String) DateFormat.format("yyyy-MM-dd", date);
                    dateFinal = getDateOfToday();
                }
                if(today.before(date)){
                    dateInit = getDateOfToday();
                    dateFinal = (String) DateFormat.format("yyyy-MM-dd", date);
                }
                if(today.equals(date)){
                    dateInit = (String) DateFormat.format("yyyy-MM-dd", date);
                    dateFinal = getDateOfToday();
                }
                calendarText.setText(dateInit+" - "+dateFinal);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void setShoppingsAdapter(ArrayList<Shoppings> shoppings){
        ShoppingsAdapter shoppingsAdapter = new ShoppingsAdapter(shoppings, this);
        recyclerView.setAdapter(shoppingsAdapter);
    }

    @Override
    public void onListItemClick(int clickedItem) {

    }
}