package com.cuwordstudy.solomolaiye.wordstudyunit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.search;

import com.cuwordstudy.solomolaiye.wordstudyunit.R;

import java.util.List;

public class SearchAdapters extends BaseAdapter {
    private Context mContext;
    private List<search> search;
    public SearchAdapters(Context mContext, List<search> search)
    {
        this.mContext = mContext;
        this.search = search;
    }
    @Override
    public int getCount() {
        return search.size();
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
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View chapvw = inflater.inflate(R.layout.searchitem, null);
        TextView searchres = chapvw.findViewById(R.id.searchres);
        TextView searchresbible = chapvw.findViewById(R.id.searchresbible);

        searchres.setText(search.get(i).text.toString());
        searchresbible.setText(search.get(i).long_name.toString() +" "+ search.get(i).chapter + ": " + search.get(i).verse.toString());

        return chapvw;
    }
}
