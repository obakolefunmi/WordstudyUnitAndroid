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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cuwordstudy.solomolaiye.wordstudyunit.Adapters.NotepadAdapter;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.MyResponse;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Notes;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Notification;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Sender;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.books;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.DbHelper2;

import java.util.ArrayList;
import java.util.List;


public class Notepadactivity extends AppCompatActivity implements AdapterView.OnItemClickListener , AdapterView.OnItemLongClickListener{
    LinearLayout note_holder,no_note;
    ImageView add_button;
    ListView note_list_view;
    SQLiteDatabase sqliteDB = null;
    List<Notes> notelist = new ArrayList<Notes>();
    Notes noteselected = null;
    DbHelper2 db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepadactivity);
        note_list_view = findViewById(R.id.notes_list_view);
        note_holder = findViewById(R.id.note_list_holder);
        no_note = findViewById(R.id.no_note_item);
        add_button = findViewById(R.id.new_note);
        db = new DbHelper2(getApplicationContext());
        db.createdatabase2();
        sqliteDB = db.getWritableDatabase();
        addData();
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notepadactivity.this,EditNoteActivity.class);
                intent.putExtra("status","new");
                startActivity(intent);
            }
        });
        note_list_view.setOnItemLongClickListener(this);
        note_list_view.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(Notepadactivity.this,EditNoteActivity.class);
        intent.putExtra("thenote",notelist.get(i).note);
        intent.putExtra("thenote_id",notelist.get(i).note_id);
        intent.putExtra("the_date",notelist.get(i).note_date);


        startActivity(intent);

    }
    private void addData()
    {Cursor selectData;
    notelist.clear();
        selectData = sqliteDB.rawQuery("SELECT * FROM notes",  new String[]{});
        if (selectData.getCount() > 0)
        {
            note_holder.setVisibility(View.VISIBLE);
            no_note.setVisibility(View.GONE);
            selectData.moveToFirst();
            do
            {
                Notes vals = new Notes( selectData.getInt(selectData.getColumnIndex("note_id")),selectData.getString(selectData.getColumnIndex("text")),selectData.getString(selectData.getColumnIndex("date")));
                notelist.add(vals);
            }
            while (selectData.moveToNext());

            selectData.close();
        NotepadAdapter bookadpt = new NotepadAdapter(Notepadactivity.this, notelist);
        note_list_view.setAdapter(bookadpt);}
        else
            {
                note_holder.setVisibility(View.GONE);
                no_note.setVisibility(View.VISIBLE);
            }





        /*
            foreach(var item in booklst)
            {
                LayoutInflater layoutInflater = (LayoutInflater)BaseContext.GetSystemService(Context.LayoutInflaterService);

                items.Add(item.long_name.ToString());
                itemnum.Add(item.book_number.ToString());
            }*/


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        //pop up code here
        //var announsel = view.Id;
        android.widget.PopupMenu menu = new android.widget.PopupMenu(this, view);

        // with Android 3 need to use MenuInfater to inflate the menu
        //  menu.MenuInflater.Inflate(Resource.Menu.popup, menu.Menu);
        noteselected = notelist.get(i);
        // with Android 4 Inflate can be called directly on the menu
        menu.inflate(R.menu.popup);

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                if (menuItem.getItemId() == R.id.delete) {
                    deletedata(noteselected.note_id);
                }
                return true;
            }
        });

        // Android 4 now has the DismissEvent
        menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
                addData();
            }
        });
        menu.show();


        return true;

    }

    private void deletedata(int note_id) {

        sqliteDB.delete("notes","note_id" + "=" + note_id,null);
        addData();
                //.execSQL("DELETE FROM notes WHERE note_id LIKE"+note_id);

    }
}
