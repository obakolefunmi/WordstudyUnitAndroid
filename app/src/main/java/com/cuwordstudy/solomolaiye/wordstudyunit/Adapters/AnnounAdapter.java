package com.cuwordstudy.solomolaiye.wordstudyunit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.announcements;
import com.cuwordstudy.solomolaiye.wordstudyunit.R;

import java.util.List;

public class AnnounAdapter extends BaseAdapter {
    private Context mContext;
    private List<announcements> announ;


    public AnnounAdapter(Context mContext, List<announcements> announ)
    {
        this.mContext = mContext;
        this.announ = announ;
    }

    @Override
    public int getCount() {
        return announ.size();
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
        View viewann = inflater.inflate(R.layout.annunitem, null);
        TextView annountext = viewann.findViewById(R.id.announttext);
        annountext.setText(announ.get(i).announcement.toString());
        return viewann;    }
}
