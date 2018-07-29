package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.cuwordstudy.solomolaiye.wordstudyunit.Adapters.SearchAdapters;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.search;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    DbHelper db;
    SQLiteDatabase sqliteDB = null;
    List<search> searchlst = new ArrayList<>();
    ListView searchlstvw;
    String searchtext, book_num, book_chap;
  //  EditText searchedit;
 //   ImageView searchbtn;
    ProgressBar searchpgb;
    search searchselected = null;
    SearchView biblesearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (savedInstanceState == null) {
            Bundle intent = getIntent().getExtras();
            if (intent == null) {
                searchtext = null;
            } else {
                searchtext = intent.getString("searchtxt");
            }
        }else {
            searchtext = (String) savedInstanceState.getSerializable("searchtxt");
        }
        biblesearch = findViewById(R.id.bible_search_search);

        biblesearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(Search.this, s, Toast.LENGTH_SHORT).show();

                searchpgb.setVisibility(View.VISIBLE);
                searchlst.clear();
                adddata(s);
                biblesearch.setQueryHint(s);
                searchpgb.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

      //  searchedit = findViewById(R.id.searchedit);
        //searchbtn = findViewById(R.id.searchbtn);
        searchpgb = findViewById(R.id.searchpgb);
        searchlstvw = findViewById(R.id.searchList);
        biblesearch.setQueryHint(searchtext);
        db = new DbHelper(getApplicationContext());
        db.createdatabase();
        sqliteDB = db.getWritableDatabase();
        searchpgb.setVisibility(View.VISIBLE);
        adddata(searchtext);
        searchpgb.setVisibility(View.GONE);


        searchlstvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                searchselected = searchlst.get(position);
                String j = searchselected.chapter.toString();
                String i = searchselected.long_name.toString();

                book_chap = i + " Chapter " + j;
                Intent gotobible = new Intent(Search.this, BibleActivity.class);
                gotobible.putExtra("chapter", j);
                gotobible.putExtra("book_num", searchselected.book_number);
                gotobible.putExtra("title", book_chap);
                startActivity(gotobible);
                Toast.makeText(Search.this, book_chap, Toast.LENGTH_SHORT).show();

            }
        });
     }
    private void adddata(String info)
    {
        Cursor selectData = sqliteDB.rawQuery("SELECT book_number,chapter, verse , text FROM verses WHERE text LIKE\"% " + info + " %\"" + "ORDER BY book_number, chapter,verse", new String[] { });
        if (selectData.getCount() > 0)
        {
            selectData.moveToFirst();
            do
            {
                search vals = new search();
                vals.book_number = selectData.getString(selectData.getColumnIndex("book_number"));
                book_num = selectData.getString(selectData.getColumnIndex("book_number"));
                Cursor selectDatatwo = sqliteDB.rawQuery("SELECT long_name FROM books WHERE book_number LIKE " + book_num, new String[] { });
                if (selectDatatwo.getCount() > 0)
                {
                    selectDatatwo.moveToFirst();
                    do
                    {
                        vals.long_name = selectDatatwo.getString(selectDatatwo.getColumnIndex("long_name"));
                    }
                    while (selectDatatwo.moveToNext());
                    selectDatatwo.close();
                }
                vals.chapter = selectData.getString(selectData.getColumnIndex("chapter"));
                vals.verse = selectData.getString(selectData.getColumnIndex("verse"));
                vals.text = selectData.getString(selectData.getColumnIndex("text"));
                searchlst.add(vals);
            }
            while (selectData.moveToNext());
            selectData.close();
            SearchAdapters chapadapt = new SearchAdapters(Search.this, searchlst);
            searchlstvw.setAdapter(chapadapt);
        }


    }
}
