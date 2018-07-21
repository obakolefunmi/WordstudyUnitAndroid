package com.cuwordstudy.solomolaiye.wordstudyunit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.questions;
import com.cuwordstudy.solomolaiye.wordstudyunit.R;

import java.util.List;

public class QuestionAdapter extends BaseAdapter {
    private Context mContext;
    private List<questions> quest;

    public QuestionAdapter(Context mContext, List<questions> quest)
    {
        this.mContext = mContext;
        this.quest = quest;
    }
    @Override
    public int getCount() {
        return quest.size();
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
        View view = inflater.inflate(R.layout.questitem, null);
        TextView questtopic = view.findViewById(R.id.questtopic);
        TextView questtext = view.findViewById(R.id.questtext);
        TextView questperson = view.findViewById(R.id.questperson);
        questtopic.setText ( quest.get(i).title.toString());
        questtext.setText ( quest.get(i).question.toString());
        questperson.setText ( "by: "+ quest.get(i).user.toString());
        return view;    }
}
