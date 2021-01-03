package com.example.notesharingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;


import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    static NoteAdapter mAdapter;
    private EditText mSearchText;
    public static ArrayList<String> Titles = new ArrayList<>();
    public static ArrayList<String> Bodies = new ArrayList<>();
    public static ArrayList<String> Dates = new ArrayList<>();
    //static ArrayAdapter arrayAdapter; //deleten

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchText = findViewById(R.id.zoeken);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recyclerview);

        final Button button = findViewById(R.id.button_first);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, splashscreen.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesharingapp", Context.MODE_PRIVATE);
        String stringTitles = sharedPreferences.getString("title", null);
        String stringBodies = sharedPreferences.getString("body", null);
        String stringDates = sharedPreferences.getString("date", null);
        if (stringTitles == null || stringBodies == null || stringDates == null){
            Titles.add("Title here");
            Bodies.add("Notes here");
            Dates.add("1/1/2021");
            Saving.Save(getApplicationContext());
            stringTitles = sharedPreferences.getString("title", null);
            stringBodies = sharedPreferences.getString("body", null);
            stringDates = sharedPreferences.getString("date", null);
        }

        if (!stringTitles.isEmpty()){
            Titles = new ArrayList<>(Arrays.asList(stringTitles.split(";")));
        }
        if (!stringBodies.isEmpty()){
            Bodies = new ArrayList<>(Arrays.asList(stringBodies.split(";")));
        }
        if (!stringDates.isEmpty()){
            Dates = new ArrayList<>(Arrays.asList(stringDates.split(";")));
        }

        if (Titles == null || Bodies == null || Dates == null){ //Als app voor het eerst wordt geopend moet deze een voorbeeld hebben
            Titles.add("Title here");
            Bodies.add("Notes here");
            Dates.add("1/1/2021");
            Saving.Save(getApplicationContext());
        } else{
            if (Titles.size() == 0){ //Als er geen notes (meer) zijn wordt een voorbeeld toegevoegd
                Titles.add("Title here");
                Bodies.add("Notes here");
                Dates.add("1/1/2021");
                Saving.Save(getApplicationContext());
            }
        }

        mAdapter = new NoteAdapter(this, Titles);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_note) {
            Intent intent = new Intent(getApplicationContext(), Notitie.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void zoekElement(View view) {
        String url = mSearchText.getText().toString();

        Uri element = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, element);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
        else {
            Log.d("EersteFragment", "Niet gevonden element");
        }
    }

    /*new Handler().postDelayed({
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }, SPLASH_SCREEN);*/

}