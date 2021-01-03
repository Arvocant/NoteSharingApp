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

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    static NoteAdapter mAdapter;
    private EditText mSearchText;
    public static LinkedList<String> Titles = new LinkedList<>();
    public static LinkedList<String> Bodies = new LinkedList<>();
    public static LinkedList<String> Dates = new LinkedList<>();
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
        HashSet<String> setTitle = (HashSet<String>)sharedPreferences.getStringSet("title", null);
        HashSet<String> setBody = (HashSet<String>)sharedPreferences.getStringSet("body", null);
        HashSet<String> setDate = (HashSet<String>)sharedPreferences.getStringSet("date", null);
        if (setTitle == null || setBody == null || setDate == null){
            Titles.add("Title here");
            Bodies.add("Notes here");
            Dates.add("1/1/2021");
        } else{
            Titles = new LinkedList<>(setTitle);
            Bodies = new LinkedList<>(setBody);
            Dates = new LinkedList<>(setDate);
            if (Titles.size() == 0){
                Titles.add("Title here");
                Bodies.add("Notes here");
                Dates.add("1/1/2021");
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