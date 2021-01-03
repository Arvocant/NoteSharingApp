package com.example.notesharingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

public class Saving extends AppCompatActivity {
    public static void Save(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.notesharingapp", Context.MODE_PRIVATE);
        String stringTitles = TextUtils.join(";", MainActivity.Titles);
        String stringBodies = TextUtils.join(";", MainActivity.Bodies);
        String stringDates = TextUtils.join(";", MainActivity.Dates);
        sharedPreferences.edit().putString("title", stringTitles).apply();
        sharedPreferences.edit().putString("body", stringBodies).apply();
        sharedPreferences.edit().putString("date", stringDates).apply();
    }
}
