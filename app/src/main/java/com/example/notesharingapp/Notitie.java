package com.example.notesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;

public class Notitie extends AppCompatActivity {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notitie);
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
        EditText editText = findViewById(R.id.title);
        EditText editText2 = findViewById(R.id.editTextTextMultiLine);

        Intent intent1 = getIntent();
        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1){
            editText.setText(MainActivity.Titles.get(noteId));
            editText2.setText(MainActivity.Bodies.get(noteId));
        } else {
            MainActivity.Titles.add("");
            MainActivity.Bodies.add("");
            noteId = MainActivity.Titles.size() -1;
            MainActivity.mAdapter.notifyDataSetChanged();

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesharingapp", Context.MODE_PRIVATE);

            HashSet<String> set = new HashSet<>(MainActivity.Titles);
            sharedPreferences.edit().putStringSet("title", set).apply();
            HashSet<String> set2 = new HashSet<>(MainActivity.Bodies);
            sharedPreferences.edit().putStringSet("body", set2).apply();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.Titles.set(noteId, String.valueOf(s));
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
                MainActivity.Bodies.set(noteId, String.valueOf(s));
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
