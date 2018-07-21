package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.cuwordstudy.solomolaiye.wordstudyunit.Adapters.BooksAdapter;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.books;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.DbHelper;

import java.util.ArrayList;
import java.util.List;


public class BooksActivity extends AppCompatActivity {
    DbHelper db;
    SQLiteDatabase sqliteDB = null;
    List<String> items = new ArrayList<String>();
    List<String> itemnum = new ArrayList<String>();
    List<books> booklst = new ArrayList<books>();
    books bookselected = null;
    SearchView booksearch;
    ListView booklstvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);


        booksearch = findViewById(R.id.searchBooks);

        booksearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                   Intent gotosearch = new Intent(BooksActivity.this, Search.class);
                 gotosearch.putExtra("searchtxt", s);
                 startActivity(gotosearch);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        booklstvw = findViewById(R.id.bookslst);
        db = new DbHelper(getApplicationContext());
        db.createdatabase();
        sqliteDB = db.getWritableDatabase();
        addData();
        BooksAdapter bookadpt = new BooksAdapter(BooksActivity.this, booklst);
        booklstvw.setAdapter(bookadpt);
        booklstvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bookselected = booklst.get(i);
                String t = bookselected.book_number.toString();
                String j = bookselected.long_name.toString();
                Intent gotochap = new Intent(BooksActivity.this, ChapterActivity.class);
                gotochap.putExtra("book_num", t);
                gotochap.putExtra("book_name", j);
                startActivity(gotochap);
              Toast.makeText(BooksActivity.this, j,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addData()
    {Cursor selectData;
        selectData = sqliteDB.rawQuery("SELECT book_number,long_name FROM books ORDER BY book_number",  new String[]{});
        if (selectData== null)
        {return;}

        selectData.moveToFirst();
        do
        {
            books vals = new books( selectData.getString(selectData.getColumnIndex("book_number")),selectData.getString(selectData.getColumnIndex("long_name")));
            booklst.add(vals);
        }
        while (selectData.moveToNext());
        selectData.close();
        /*
            foreach(var item in booklst)
            {
                LayoutInflater layoutInflater = (LayoutInflater)BaseContext.GetSystemService(Context.LayoutInflaterService);

                items.Add(item.long_name.ToString());
                itemnum.Add(item.book_number.ToString());
            }*/


    }
}
