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

public class TopicAdapter extends BaseAdapter {
    private Context mContext;
    private List<topic> topic;

    public TopicAdapter(Context mContext, List<topic> topic)
    {
        this.mContext = mContext;
        this.topic = topic;
    }
    @Override
    public int getCount() {
        return topic.size();
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
        View view = inflater.inflate(R.layout.topicitem, null);
        TextView topictext = view.findViewById(R.id.topictext);
        TextView topicbible = view.findViewById(R.id.topicbible);
        topictext.setText  (topic.get(i).title.toString());
        topicbible.setText (topic.get(i).bible.toString());
        return view;    }
}
