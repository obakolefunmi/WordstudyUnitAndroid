package com.cuwordstudy.solomolaiye.wordstudyunit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.ChapterActivity;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Chapter;
import com.cuwordstudy.solomolaiye.wordstudyunit.R;

import java.util.List;

public class ChapterAdapter extends BaseAdapter {

    Context chapterActivity;
    List<Chapter> chaplst;
    public ChapterAdapter(ChapterActivity chapterActivity, List<Chapter> chaplst) {
        this.chapterActivity = chapterActivity;
        this.chaplst = chaplst;
    }

    @Override
    public int getCount() {
        return chaplst.size();
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
        LayoutInflater inflater = (LayoutInflater)chapterActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View chapvw = inflater.inflate(R.layout.chapitem, null);
        TextView chaptext = chapvw.findViewById(R.id.chaptext);
        chaptext.setText(chaplst.get(i).chapter.toString());
        return chapvw;    }
}
