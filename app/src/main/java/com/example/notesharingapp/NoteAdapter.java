/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.notesharingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Shows how to implement a simple Adapter for a RecyclerView.
 * Demonstrates how to add a click handler for each item in the ViewHolder.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.WordViewHolder>  {

    private final LinkedList<String> mWordList;
    private LayoutInflater mInflater;
    private Context _context;
    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final TextView wordItemView;
        final NoteAdapter mAdapter;
        public WordViewHolder(View itemView, NoteAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setLongClickable(true);
            itemView.setClickable(true);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            String element = mWordList.get(mPosition);
            mAdapter.notifyDataSetChanged();
            Intent intent = new Intent(_context, Notitie.class);
            intent.putExtra("noteId", mPosition);
            _context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            final int itemToDelete = getLayoutPosition();
            new AlertDialog.Builder(_context).setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Dit item wordt verwijderd")
                    .setMessage("Ben je zeker dat je deze notitie wilt verwijderen?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.Bodies.remove(itemToDelete);
                            MainActivity.Titles.remove(itemToDelete);
                            MainActivity.Dates.remove(itemToDelete);
                            mAdapter.notifyDataSetChanged();

                            SharedPreferences sharedPreferences = _context.getApplicationContext().getSharedPreferences("com.example.notesharingapp", Context.MODE_PRIVATE);

                            HashSet<String> set = new HashSet<>(MainActivity.Titles);
                            HashSet<String> set2 = new HashSet<>(MainActivity.Bodies);
                            HashSet<String> set3 = new HashSet<>(MainActivity.Dates);
                            sharedPreferences.edit().putStringSet("title", set).apply();
                            sharedPreferences.edit().putStringSet("body", set2).apply();
                            sharedPreferences.edit().putStringSet("date", set3).apply();
                        }
                    })
                    .setNegativeButton("No", null).show();
            return true;
        }
    }
    public NoteAdapter(Context context, LinkedList<String> wordList) {
        mInflater = LayoutInflater.from(context);
        _context = context;
        /*Gson gson = new Gson();
        String json = gson.fromJson();*/
        this.mWordList = wordList;
    }

    @NonNull
    @Override
    public NoteAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.note_item,
                parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}

