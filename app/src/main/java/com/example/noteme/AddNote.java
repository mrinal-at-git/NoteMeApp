package com.example.noteme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.Toast;


import java.util.Calendar;


public class AddNote extends AppCompatActivity {

    Toolbar toolbar;
    EditText title, details;
    Calendar c;
    String todayDate, todayTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        toolbar.setTitle("New Note"); // OR getSupportActionBar().setTitle("New Note");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        title = findViewById(R.id.noteTitle);
        details = findViewById(R.id.noteDetails);


        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() != 0) toolbar.setTitle(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        c = Calendar.getInstance();

        todayDate = c.get(Calendar.YEAR) + "/" + c.get(Calendar.MONTH + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
        todayTime = Pad(c.get(Calendar.HOUR)) + ":" + Pad(c.get(Calendar.MINUTE));


    }

    private String Pad(int i) {

        if (i < 10) return "0" + i;
        return String.valueOf(i);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.delete){

         onBackPressed();
        }

        if(item.getItemId() == R.id.save) {

            MyDbHandler db = new MyDbHandler(AddNote.this);

            Note note = new Note(title.getText().toString(), details.getText().toString(), todayDate, todayTime);
            if (title.getText().length() != 0) {
                db.addNote(note);

                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();

                goToMain();   // or startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }else {
                title.setError("Title can not  be empty");
            }
        }

        return super.onOptionsItemSelected(item);

    }

    private void goToMain() {

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public  void onBackPressed(){
        super.onBackPressed();
    }
}








