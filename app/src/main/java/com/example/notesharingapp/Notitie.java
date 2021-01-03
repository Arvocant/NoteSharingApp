package com.example.notesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        if (noteId == -1){
            MainActivity.Titles.add("New note");
            MainActivity.Bodies.add("Notes");
            MainActivity.Dates.add("");
            noteId = MainActivity.Titles.size() -1;
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesharingapp", Context.MODE_PRIVATE);
            HashSet<String> set = new HashSet<>(MainActivity.Titles);
            HashSet<String> set2 = new HashSet<>(MainActivity.Bodies);
            HashSet<String> set3 = new HashSet<>(MainActivity.Dates);
            sharedPreferences.edit().putStringSet("title", set).apply();
            sharedPreferences.edit().putStringSet("body", set2).apply();
            sharedPreferences.edit().putStringSet("date", set3).apply();
            MainActivity.mAdapter.notifyDataSetChanged();
       }
        editText.setText(MainActivity.Titles.get(noteId));
        editText2.setText(MainActivity.Bodies.get(noteId));
        etDate.setText(MainActivity.Dates.get(noteId));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesharingapp", Context.MODE_PRIVATE);
                MainActivity.Titles.set(noteId, String.valueOf(s));
                HashSet<String> set = new HashSet<>(MainActivity.Titles);
                sharedPreferences.edit().putStringSet("title", set).apply();
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
                HashSet<String> set2 = new HashSet<>(MainActivity.Bodies);
                sharedPreferences.edit().putStringSet("body", set2).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesharingapp", Context.MODE_PRIVATE);
                MainActivity.Dates.set(noteId, String.valueOf(s));
                HashSet<String> set3 = new HashSet<>(MainActivity.Dates);
                sharedPreferences.edit().putStringSet("date", set3).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void doMySearch(String query) {

    }
}
