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

public class Edit extends AppCompatActivity {

    Toolbar toolbar;
    EditText title, details;
    Calendar c;
    String todayDate, todayTime;
    long id;
    Note note;
    MyDbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        Intent i = getIntent();
        id =i.getLongExtra("ID",0);


         db= new MyDbHandler(this);
        note = db.getNote(id);






        toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);


        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        title = findViewById(R.id.noteTitle2);
        details = findViewById(R.id.noteDetails2);


        getSupportActionBar().setTitle(note.getTitle());
        title.setText(note.getTitle());
        details.setText(note.getContent());



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

        if(item.getItemId() == R.id.save){

         if(title.getText().length()!=0) {

             note.setTitle(title.getText().toString());
             note.setContent(details.getText().toString());
             note.setDate(todayDate);
             note.setTime(todayTime);
             int x = db.editNote(note);

             if (x > 0) {
                 Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
             }


             Intent i = new Intent(this, Details.class);
             i.putExtra("ID", note.getId());
             startActivity(i);


         }else {
             title.setError("Title can not  be empty");
         }
         }




        return super.onOptionsItemSelected(item);

    }



    @Override
    public  void onBackPressed(){
        super.onBackPressed();
    }
}



