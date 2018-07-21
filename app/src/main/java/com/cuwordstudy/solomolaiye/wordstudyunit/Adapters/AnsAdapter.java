package com.cuwordstudy.solomolaiye.wordstudyunit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.answers;
import com.cuwordstudy.solomolaiye.wordstudyunit.R;

import java.util.List;

public class AnsAdapter extends BaseAdapter {
    private Context mContext;
    private List<answers> anses;


    public AnsAdapter(Context mContext, List<answers> anses)
    {
        this.mContext = mContext;
        this.anses = anses;
    }
    @Override
    public int getCount() {
        return anses.size();
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
        View viewreq = inflater.inflate(R.layout.answeritem, null);
        TextView anstext = viewreq.findViewById(R.id.anstext);
        TextView ansuser = viewreq.findViewById(R.id.ansuser);
        if (anses.size() == 0)
            return view;
        else{

        try {

        anstext.setText  (anses.get(i).answer.toString());
        ansuser.setText  (anses.get(i).user.toString());
        return viewreq;   }
        catch (Exception ex){}
        return view;}
    }
}
