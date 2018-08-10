package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.DbHelper2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {
    String status, thenote,the_olddate;
    int thenote_id;
    MultiAutoCompleteTextView newtext;
    DbHelper2 db;
    SQLiteDatabase sqliteDB = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        newtext = findViewById(R.id.new_note);

        db = new DbHelper2(getApplicationContext());
        db.createdatabase2();
        sqliteDB = db.getReadableDatabase();
        if (savedInstanceState == null) {
            Bundle intent = getIntent().getExtras();
            if(intent == null) {
                status = null;
                thenote = null;
                the_olddate = null;

            }
            else
                {
                status = intent.getString("status");
                thenote = intent.getString("thenote");
                    thenote_id = intent.getInt("thenote_id");
                    the_olddate=intent.getString("the_date");

                }
        }
        else
            {
            status = (String) savedInstanceState.getSerializable("status");
            thenote =(String) savedInstanceState.getSerializable("thenote");
                thenote_id =(int) savedInstanceState.getSerializable("thenote_id");
                the_olddate=(String) savedInstanceState.getSerializable("the_date");

            }


        if(!TextUtils.isEmpty(thenote)){
            newtext.setText(thenote);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_discard:
//cancel
                Intent intent = new Intent(EditNoteActivity.this, Notepadactivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_save:
                //save
                if(!TextUtils.isEmpty(status))
                {
                    addnewnote(newtext.getText().toString());

                }
                else
                    {
                        updatenote(newtext.getText().toString());


                    }
                Toast.makeText(EditNoteActivity.this,"Saved",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menue_goto_bible:
                //goto the bible
                if(!TextUtils.isEmpty(status))
                {
                    addnewnote(newtext.getText().toString());
                    Intent intent0 = new Intent(EditNoteActivity.this, BooksActivity.class);
                    startActivity(intent0);
                }
                else
                {
                    updatenote(newtext.getText().toString());
                    Intent intent0 = new Intent(EditNoteActivity.this, BooksActivity.class);
                    startActivity(intent0);

                }
                Toast.makeText(EditNoteActivity.this,"Saved",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void addnewnote(String newnote) {
        DateFormat dateFormat = new SimpleDateFormat("EEE,dd/MM/yyyy ' at '(hh:mm aaa)");
        Date date = new Date();
        //String add = "INSERT INTO notes (text, date) VALUES (\""+newnote+"\",\""+dateFormat.format(date)+"\")";
      //  sqliteDB.execSQL(add);
        ContentValues values = new ContentValues();
        values.put("text", newnote);
        values.put("date", dateFormat.format(date));

// Inserting Row
        sqliteDB.insert("notes", null, values);


    }
    private void updatenote(String the_new_note){
        ContentValues values = new ContentValues();
       values.put("text", the_new_note);
    //   values.put("date",the_olddate);
       sqliteDB.update("notes",values,"note_id = " + thenote_id,null);
      // String update =  "UPDATE notes SET text=\""+the_new_note+" \"WHERE note_id LIKE " + thenote_id;
      //  sqliteDB.execSQL(update);


    }


}
