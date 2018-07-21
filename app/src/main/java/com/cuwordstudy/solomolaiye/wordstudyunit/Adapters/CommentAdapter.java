package com.cuwordstudy.solomolaiye.wordstudyunit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Coment;
import com.cuwordstudy.solomolaiye.wordstudyunit.R;

import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<Coment> coments;


    public CommentAdapter(Context mContext, List<Coment> coments)
    {
        this.mContext = mContext;
        this.coments = coments;
    }
    @Override
    public int getCount() {
        return coments.size();
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
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewreq = inflater.inflate(R.layout.comentitem, null);
            TextView comenttext = viewreq.findViewById(R.id.comenttext);
            TextView comentuser = viewreq.findViewById(R.id.comentusr);
            comentuser.setText (coments.get(i).user.toString());
            comenttext.setText (coments.get(i).contribution.toString());

            return viewreq;
        }
        catch (Exception ex){}
        return view;
    }
}
