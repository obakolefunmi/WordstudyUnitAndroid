package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cuwordstudy.solomolaiye.wordstudyunit.Adapters.ChapterAdapter;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Chapter;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class ChapterActivity extends AppCompatActivity {

    DbHelper db;
    SQLiteDatabase sqliteDB = null;
    List<String> chaps = new ArrayList<String>();
    List<String> chapnum = new ArrayList<>();
    List<Chapter> chaplst = new ArrayList<Chapter>();
    String book_num, book_name, book_chap;
    ListView chapvwlst;
    Chapter chapselected = null;
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
                Intent intent = new Intent(ChapterActivity.this, Notepadactivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        if (savedInstanceState == null) {
            Bundle intent = getIntent().getExtras();
            if(intent == null) {
                book_num = null;
                book_name = null;
            } else {
                book_num = intent.getString("book_num");
                book_name = intent.getString("book_name");            }
        } else {
            book_num = (String) savedInstanceState.getSerializable("book_num");
            book_name = (String) savedInstanceState.getSerializable("book_name");
        }
        Intent intent = new Intent();

        chapvwlst = findViewById(R.id.chaplst);
        setTitle(book_name);

        db = new DbHelper(getApplicationContext());
        db.createdatabase();
        sqliteDB = db.getWritableDatabase();
        addData();
        ChapterAdapter chapadapt = new ChapterAdapter(this, chaplst);
        chapvwlst.setAdapter(chapadapt);
        chapvwlst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chapselected = chaplst.get(i);
                String j = chapselected.chapter.toString();
                book_chap = book_name + " Chapter " + j;
                Intent gotobible = new Intent(ChapterActivity.this, BibleActivity.class);
                gotobible.putExtra("chapter", j);
                gotobible.putExtra("book_num", book_num);
                gotobible.putExtra("title", book_chap);
                startActivity(gotobible);
                Toast.makeText(ChapterActivity.this, book_chap, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addData()
    {
        Cursor selectData = sqliteDB.rawQuery("SELECT DISTINCT chapter FROM verses WHERE book_number LIKE "+ book_num+" ORDER BY chapter", new String[] { });
        if (selectData.getCount() > 0)
        {
            selectData.moveToFirst();
            do
            {
                Chapter vals = new Chapter();
                //vals.book_number = selectData.GetString(selectData.GetColumnIndex("book_number"));
                vals.chapter = selectData.getString(selectData.getColumnIndex("chapter"));
                chaplst.add(vals);
            }
            while (selectData.moveToNext());
            selectData.close();
        }/*
            foreach (var item in chaplst)
            {
                LayoutInflater layoutInflater = (LayoutInflater)BaseContext.GetSystemService(Context.LayoutInflaterService);

                chaps.Add(item.chapter.ToString());
                //itemnum.Add(item.book_number.ToString());
            }*/


    }
}
