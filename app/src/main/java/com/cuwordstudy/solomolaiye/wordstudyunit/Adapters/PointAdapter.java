package com.cuwordstudy.solomolaiye.wordstudyunit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.*;
import com.cuwordstudy.solomolaiye.wordstudyunit.R;

import java.util.List;

public class PointAdapter extends BaseAdapter {
    private Context mContext;
    private List<prayerpoints> points;

    public PointAdapter(Context mContext, List<prayerpoints> points)
    {
        this.mContext = mContext;
        this.points = points;
    }
    @Override
    public int getCount() {
        return points.size();
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
    public View getView(int i, View cview, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ppitem, null);
        TextView pointtext = view.findViewById(R.id.pointttext);
        TextView pbible = view.findViewById(R.id.pointbible);
        pointtext.setText(points.get(i).point.toString());
        pbible.setText(points.get(i).bibletext.toString());
        return view;    }
}
