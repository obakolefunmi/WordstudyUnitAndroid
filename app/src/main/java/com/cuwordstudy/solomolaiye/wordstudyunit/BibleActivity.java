package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Bible;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class BibleActivity extends AppCompatActivity {
    DbHelper db;
    SQLiteDatabase sqliteDB = null;
    LinearLayout container;
    List<Bible> biblelst = new ArrayList<Bible>();
    String chap_num, book_num, title;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ife, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menue_goto_note:
//cancel
                Intent intent = new Intent(BibleActivity.this, Notepadactivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible);
        if (savedInstanceState == null) {
            Bundle intent = getIntent().getExtras();
            if(intent == null) {
                book_num = null;
                chap_num = null;
                title = null;
            } else {
                book_num = intent.getString("book_num");
                chap_num = intent.getString("chapter");
                title = intent.getString("title");            }
        } else {
            book_num = (String) savedInstanceState.getSerializable("book_num");
            chap_num =(String) savedInstanceState.getSerializable("chapter");
            title = (String) savedInstanceState.getSerializable("title");
          //  newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }
        //Intent intent = new Intent();

        container = findViewById(R.id.container);
        db = new DbHelper(getApplicationContext());
        db.createdatabase();
        sqliteDB = db.getWritableDatabase();
        adddata();
        setTitle(title);
    }
    private void adddata()
    {
        Cursor selectData = sqliteDB.rawQuery("SELECT  verse , text FROM verses WHERE chapter LIKE "+chap_num+" AND book_number LIKE "+book_num+" ORDER BY verse", new String[] { });
        if (selectData.getCount() > 0)
        {
            selectData.moveToFirst();
            do
            {
                Bible vals = new Bible();
                vals.verse = selectData.getString(selectData.getColumnIndex("verse"));
                vals.text = selectData.getString(selectData.getColumnIndex("text"));
                biblelst.add(vals);
            }
            while (selectData.moveToNext());
            selectData.close();
        }
        for (Bible bible: biblelst)
        {
            LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View addview = layoutInflater.inflate(R.layout.bibletext, null);
            TextView verse_num = addview.findViewById(R.id.verse);
            TextView text = addview.findViewById(R.id.read);

            verse_num.setText(bible.getVerse());
            text.setText(bible.getText());
            container.addView(addview);
        }

    }

}
