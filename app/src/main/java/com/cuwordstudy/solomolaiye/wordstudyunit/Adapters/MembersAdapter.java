package com.cuwordstudy.solomolaiye.wordstudyunit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Profile;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.requests;
import com.cuwordstudy.solomolaiye.wordstudyunit.R;

import java.util.List;

public class MembersAdapter extends BaseAdapter {
    private Context mContext;
    private List<Profile> profiles;

    public MembersAdapter(Context mContext, List<Profile> profiles) {
        this.mContext = mContext;
        this.profiles = profiles;
    }

    @Override
    public int getCount() {
        return profiles.size();
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
        View viewprofile = inflater.inflate(R.layout.members_item, null);

        TextView first_name,last_name,hall,room,mail;
        first_name = viewprofile.findViewById(R.id.mem_firstname);
        last_name = viewprofile.findViewById(R.id.mem_lastname);
        hall = viewprofile.findViewById(R.id.mem_hall);
        room = viewprofile.findViewById(R.id.mem_room);
        mail = viewprofile.findViewById(R.id.mem_mail);

        first_name.setText(profiles.get(i).fitst_name);
        last_name.setText(profiles.get(i).last_name);
        hall.setText(profiles.get(i).hall_of_residence);
        room.setText(profiles.get(i).room_number);
        mail.setText(profiles.get(i).user_email);

        return viewprofile;      }
}
