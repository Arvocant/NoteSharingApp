package com.example.notesharingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchText;
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchText = findViewById(R.id.zoeken);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.listView);

        notes.add("Example note");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);
        //spring naar Notitie
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Notitie.class);
                intent.putExtra("noteId", i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {

                final int itemToDelete = i;

                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Dit item wordt verwijderd")
                        .setMessage("Ben je zeker dat je deze notitie wilt verwijderen?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null).show();
                return true;
            }
        });
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
}