package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.DbHelper;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.*;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.HttpDataHandler;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class NewTopicActivity extends AppCompatActivity {
    Spinner bookSpinner, ChapterSpinner, verseSpinner;
    EditText wordtitle, verse2, message;
    ProgressBar newtopicpgb;
    LinearLayout newtopicHolder;
    DbHelper db;
    LinearLayout container;
    List<Bible> biblelst = new ArrayList<Bible>();

    List<String> books = new ArrayList<>();
    List<String> chapter = new ArrayList<>();
    List<String> verse = new ArrayList<>();
    List<String> book_nums = new ArrayList<>();
    String book, chapter_num, verse_num, book_num, anchor;
    List<books> booklst = new ArrayList<>();
    SQLiteDatabase sqliteDB = null;
    ArrayAdapter bookadapter, chapteradapter, verseadapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_item_send: {
                if (TextUtils.isEmpty(wordtitle.getText().toString().trim())) {
                    wordtitle.setError("Required");
                    wordtitle.requestFocus();
                } else if (TextUtils.isEmpty(message.getText().toString().trim())) {
                    message.setError("Required");
                    message.requestFocus();
                } else {
                    if (TextUtils.isEmpty(verse2.getText().toString().trim())) {
                        // send to mlab
                        new SendNewTopic(wordtitle.getText().toString(), anchor, message.getText().toString(), NewTopicActivity.this).execute(Common.getAddresApitopic());
                    } else {
                        anchor = anchor + "-" + verse2.getText().toString();
                        //send to mlab
                        new SendNewTopic(wordtitle.getText().toString(), anchor, message.getText().toString(), NewTopicActivity.this).execute(Common.getAddresApitopic());
                    }
                }
            }

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_topic);
        db = new DbHelper(getApplicationContext());
        db.createdatabase();
        sqliteDB = db.getWritableDatabase();
        bookSpinner = findViewById(R.id.spinner1);
        ChapterSpinner = findViewById(R.id.spinner2);
        verseSpinner = findViewById(R.id.spinner3);
        wordtitle = findViewById(R.id.newtopicTitle);
        verse2 = findViewById(R.id.newtopicverseExtra);
        message = findViewById(R.id.newtopicWord);
        newtopicpgb = findViewById(R.id.newtopicpgb);
        newtopicHolder = findViewById(R.id.newtopicHolder);
        container = findViewById(R.id.topic_container);


        addBookData();
        bookSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                book_num = book_nums.get(i);
                book = books.get(i);
                addChapterData(book_num);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ChapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chapter_num = chapter.get(i);
                addVersedata(chapter_num, book_num);
                adddata(chapter_num, book_num);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }) ;
        verseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                verse_num = verse.get(i);
                anchor = book + " " + chapter_num +":"+ verse_num;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void addBookData()
    {
        Cursor selectData = sqliteDB.rawQuery("SELECT book_number,long_name FROM books ORDER BY book_number", new String[] { });
        if (selectData.getCount() > 0)
        {
            selectData.moveToFirst();
            do
            {
                //books vals = new books();
                book_nums.add(selectData.getString(selectData.getColumnIndex("book_number")));
                books.add(selectData.getString(selectData.getColumnIndex("long_name")));
                // vals.book_number = selectData.GetString(selectData.GetColumnIndex("book_number"));
                //vals.long_name = selectData.GetString(selectData.GetColumnIndex("long_name"));
                //booklst.Add(vals);
                bookadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, books);
                bookadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bookSpinner.setAdapter(bookadapter);

            }
            while (selectData.moveToNext());
            selectData.close();
        }


    }
    private void addChapterData(String book_num)
    {
        Cursor selectData = sqliteDB.rawQuery("SELECT DISTINCT chapter FROM verses WHERE book_number LIKE " + book_num + " ORDER BY chapter", new String[] { });
        if (selectData.getCount() > 0)
        {
            chapter.clear();

            selectData.moveToFirst();
            do
            {
                chapter.add(selectData.getString(selectData.getColumnIndex("chapter")));
                chapteradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chapter);
                chapteradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ChapterSpinner.setAdapter(chapteradapter);

            }
            while (selectData.moveToNext());
            selectData.close();
        }

    }
    private void addVersedata(String chap_num , String book_num)
    {
        Cursor selectData = sqliteDB.rawQuery("SELECT  verse , text FROM verses WHERE chapter LIKE " + chap_num + " AND book_number LIKE " + book_num + " ORDER BY verse", new String[] { });
        if (selectData.getCount() > 0)
        {
            verse.clear();

            selectData.moveToFirst();
            do
            {
                // Bible vals = new Bible();
                //vals.verse = selectData.GetString(selectData.GetColumnIndex("verse"));
                //vals.text = selectData.GetString(selectData.GetColumnIndex("text"));
                //biblelst.Add(vals);
                verse.add(selectData.getString(selectData.getColumnIndex("verse")));
                verseadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, verse);
                verseadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                verseSpinner.setAdapter (verseadapter);

            }
            while (selectData.moveToNext());
            selectData.close();
        }

    }

    private class SendNewTopic extends AsyncTask<String, Void, String>
    {
        String title = "";
        String bible = "";
        String text = "";

        NewTopicActivity activity = new NewTopicActivity();
            public SendNewTopic(String title, String bible, String text, NewTopicActivity activity)
        {
            this.title = title;
            this.bible = bible;
            this.text = text;

            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.newtopicpgb.setVisibility(View.VISIBLE);
            activity.newtopicHolder.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.newtopicpgb.setVisibility(View.GONE);
            activity.newtopicHolder.setVisibility(View.VISIBLE);
            Intent intent = new Intent(activity,MainActivity.class);
            activity.startActivity(intent);              }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            topic topics = new topic();
            topics.title = title;
            topics.bible = bible;
            topics.text = text;

            String json = new Gson().toJson(topics);
            http.PostHttpData(url, json);
            return "";
        }





    }
    private void adddata(String chap_num , String book_num)
    {
        Cursor selectData = sqliteDB.rawQuery("SELECT  verse , text FROM verses WHERE chapter LIKE "+chap_num+" AND book_number LIKE "+book_num+" ORDER BY verse", new String[] { });
        if (selectData.getCount() > 0)
        {        biblelst.clear();
        container.removeAllViewsInLayout();

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
