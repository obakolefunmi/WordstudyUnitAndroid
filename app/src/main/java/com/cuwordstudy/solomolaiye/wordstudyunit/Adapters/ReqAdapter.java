package com.cuwordstudy.solomolaiye.wordstudyunit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.requests;
import com.cuwordstudy.solomolaiye.wordstudyunit.R;

import java.util.List;

public class ReqAdapter extends BaseAdapter {
    private Context mContext;
    private List<requests> request;


    public ReqAdapter(Context mContext, List<requests> request)
    {
        this.mContext = mContext;
        this.request = request;
    }
    @Override
    public int getCount() {
        return request.size();
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
        View viewreq = inflater.inflate(R.layout.pritem, null);
        TextView reqtext = viewreq.findViewById(R.id.reqttext);
        TextView requser = viewreq.findViewById(R.id.requser);
        reqtext.setText(request.get(i).prayer.toString());
        requser.setText(request.get(i).user.toString());
        return viewreq;    }
}
