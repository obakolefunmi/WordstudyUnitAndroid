package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cuwordstudy.solomolaiye.wordstudyunit.Adapters.MembersAdapter;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Profile;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.HttpDataHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class MembersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SwipeRefreshLayout memlistrefresher;
    private Profile memlistselected = null;
    private List<Profile> profiles;
    TextView memlistpull;
    ListView memlistlist;
    ProgressBar memlistpgb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        memlistlist = findViewById(R.id.memlist);
        memlistlist.setOnItemClickListener  (this);
        memlistpgb = findViewById(R.id.mempgb);

        memlistpull = findViewById(R.id.mempull);
        new GetmemData(MembersActivity.this).execute(Common.getAddresApiProfile());

        memlistrefresher = findViewById(R.id.memrefresher);


        memlistrefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetmemData(MembersActivity.this).execute(Common.getAddresApiProfile());
                memlistrefresher.setRefreshing(false) ;
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        memlistselected = profiles.get(i);

    }

    public class GetmemData extends AsyncTask<String, Void, String> {
        private MembersActivity activity;

        public GetmemData(MembersActivity activity) {
            this.activity = activity;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.memlistpgb.setVisibility(View.VISIBLE);
            activity.memlistlist.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                try {
                    Type listtype = new TypeToken<List<Profile>>(){}.getType();
                    activity.profiles = new Gson().fromJson(s, listtype);
                    Collections.reverse(activity.profiles);
                    MembersAdapter adapter = new MembersAdapter(MembersActivity.this, activity.profiles);
                    activity.memlistlist.setAdapter(adapter);
                    activity.memlistpgb.setVisibility(View.GONE) ;
                    activity.memlistpull.setVisibility(View.GONE) ;
                    activity.memlistlist.setVisibility(View.VISIBLE) ;

                } catch (Exception ex) {
                    activity.memlistpull.setVisibility(View.VISIBLE) ;
                }}else
                activity.memlistpull.setVisibility(View.VISIBLE) ;


        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            stream = http.GetHttpData(urlString);
            return stream;        }

    }

}
