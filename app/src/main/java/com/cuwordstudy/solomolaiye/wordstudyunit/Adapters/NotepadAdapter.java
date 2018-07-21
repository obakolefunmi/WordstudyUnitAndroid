package com.cuwordstudy.solomolaiye.wordstudyunit.Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Notes;
import com.cuwordstudy.solomolaiye.wordstudyunit.R;

import java.util.List;

public class NotepadAdapter extends BaseAdapter {
    private Context context;
    private List<Notes> notes;

    public NotepadAdapter(Context context, List<Notes> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewreq = inflater.inflate(R.layout.notepad_item, null);
            TextView notepadtext = viewreq.findViewById(R.id.note_text);
            TextView notetime = viewreq.findViewById(R.id.note_time);
            notepadtext.setText (notes.get(i).note.toString());
            notetime.setText (notes.get(i).note_date.toString());

            return viewreq;
        }
        catch (Exception ex){}
        return view;
    }
}
