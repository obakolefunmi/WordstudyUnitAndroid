package com.cuwordstudy.solomolaiye.wordstudyunit.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.books;
import com.cuwordstudy.solomolaiye.wordstudyunit.R;

import java.util.List;

public class BooksAdapter  extends BaseAdapter {

    private Context mContext;
    private List<com.cuwordstudy.solomolaiye.wordstudyunit.Class.books> books;
    public BooksAdapter(Context mContext, List<books> books) {
        this.mContext = mContext;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View booksvw = inflater.inflate(R.layout.booksitm, null);
        TextView booktext = booksvw.findViewById(R.id.bookstext);
        booktext.setText(books.get(position).long_name.toString());
        return booksvw;
    }
}