package com.example.noteme;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    TextView details;
    MyDbHandler db;
    Note note;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        details = findViewById(R.id.details_of_note);

        Intent i = getIntent();
        id =i.getLongExtra("ID",0);

         db = new MyDbHandler(this);
         note =db.getNote(id);

        getSupportActionBar().setTitle(note.getTitle());

        details.setText(note.getContent());












        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteNote(id);

                Toast.makeText(Details.this, "Note is  Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.edit_note){

            Intent intent =  new Intent(this, Edit.class);
            intent.putExtra("ID",note.getId());

            startActivity(intent);

        }




        return super.onOptionsItemSelected(item);

    }




}
