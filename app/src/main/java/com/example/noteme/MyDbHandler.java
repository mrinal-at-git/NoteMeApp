package com.example.noteme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class MyDbHandler extends SQLiteOpenHelper {


    public MyDbHandler(Context context) {
        super(context, parameters.DATABASE_NAME, null, parameters.DATABASE_VERSION);
    }

    @Override // when instance of this class id created
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + parameters.TABLE_NAME + "(" + parameters.KEY_ID + " INTEGER PRIMARY KEY,"
                + parameters.KEY_TITLE + " TEXT," + parameters.KEY_CONTENT + " TEXT," + parameters.KEY_DATE + " TEXT,"
                + parameters.KEY_TIME + " TEXT" + ")";

        Log.d("dbMe", "Executed " + query);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //if(oldVersion >= newVersion) return;
        //db.execSQL("DROP TABLE IF EXISTS " + parameters.TABLE_NAME);
        //onCreate(db);

    }

    public long addNote(Note note) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(parameters.KEY_TITLE, note.getTitle());
        value.put(parameters.KEY_CONTENT, note.getContent());
        value.put(parameters.KEY_DATE, note.getDate());
        value.put(parameters.KEY_TIME, note.getTime());

        long ID = db.insert(parameters.TABLE_NAME, null, value);
        db.close();

        return ID;


    }



    public int editNote(Note note){


        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(parameters.KEY_TITLE,note.getTitle());
        values.put(parameters.KEY_CONTENT,note.getContent());
        values.put(parameters.KEY_DATE,note.getDate());
        values.put(parameters.KEY_TIME,note.getTime());
        return db.update(parameters.TABLE_NAME,values,parameters.KEY_ID+"=?",new String[]{String.valueOf(note.getId())});

    }





    public Note getNote(long id) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(parameters.TABLE_NAME, new String[]{parameters.KEY_ID, parameters.KEY_TITLE, parameters.KEY_CONTENT, parameters.KEY_DATE, parameters.KEY_TIME},
                parameters.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);


        Note note = new Note();

        if (cursor.moveToFirst()) {

            note.setId(cursor.getLong(0));
            note.setTitle(cursor.getString(1));
            note.setContent(cursor.getString(2));
            note.setDate(cursor.getString(3));
            note.setTime(cursor.getString(4));
        }

        cursor.close();
        return note;
    }


    public List<Note> getNotes(){

        SQLiteDatabase db = getReadableDatabase();

        List<Note> allNotes = new ArrayList<>();

        String query = "SELECT * FROM "+ parameters.TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){


            do{
                Note note = new Note();
                note.setId(cursor.getLong(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));
                allNotes.add(note);

            }while(cursor.moveToNext());

        }
        cursor.close();
        return allNotes;

    }

    void deleteNote(long id){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(parameters.TABLE_NAME, parameters.KEY_ID + "=?",new String[]{String.valueOf(id)});
        db.close();

    }

}