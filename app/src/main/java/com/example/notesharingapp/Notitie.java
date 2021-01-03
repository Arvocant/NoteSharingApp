package com.example.notesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.HashSet;

public class Notitie extends AppCompatActivity {


    EditText etDate;
    DatePickerDialog.OnDateSetListener setListener;
    int noteId;
    @Override
    protected void onStop(){
        super.onStop();
        Saving.Save(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notitie);
        etDate = findViewById(R.id.et_date);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Notitie.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                }, year,month,day);
                datePickerDialog.show();
            }
        });

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
        EditText editText = findViewById(R.id.title);
        EditText editText2 = findViewById(R.id.editTextTextMultiLine);
        EditText editText3 = etDate;
        if (noteId == -1){
            MainActivity.Titles.add("Title here");
            MainActivity.Bodies.add("Notes here");
            MainActivity.Dates.add("1/1/2021");
            noteId = MainActivity.Titles.size() -1;
            Saving.Save(this);
            MainActivity.mAdapter.notifyDataSetChanged();
       }/*
        Log.d("MainActivity", String.valueOf(noteId));
        for (int i = 0; i <= MainActivity.Dates.size(); i++) {
            Log.d("MainActivity", String.valueOf(MainActivity.Titles.get(i)));
        }*/
        editText.setText(MainActivity.Titles.get(noteId));
        editText2.setText(MainActivity.Bodies.get(noteId));
        editText3.setText(MainActivity.Dates.get(noteId));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesharingapp", Context.MODE_PRIVATE);
                MainActivity.Titles.set(noteId, String.valueOf(s));
                Saving.Save(getApplicationContext());
                MainActivity.mAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesharingapp", Context.MODE_PRIVATE);
                MainActivity.Bodies.set(noteId, String.valueOf(s));
                Saving.Save(getApplicationContext());
                MainActivity.mAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesharingapp", Context.MODE_PRIVATE);
                MainActivity.Dates.set(noteId, String.valueOf(s));
                Saving.Save(getApplicationContext());
                MainActivity.mAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void doMySearch(String query) {

    }
}
