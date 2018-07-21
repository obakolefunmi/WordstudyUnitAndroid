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

import com.cuwordstudy.solomolaiye.wordstudyunit.Adapters.ReqAdapter;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.requests;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.HttpDataHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class PrayerRequest extends AppCompatActivity implements AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener {
    SwipeRefreshLayout reqrefresher;
    private requests reqselected = null;
    private List<requests> requests;
    TextView reqpull;
    ListView reqlist;
    ProgressBar reqpgb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prfrag);
        reqlist = findViewById(R.id.reqlist);
        reqlist.setOnItemClickListener  (this);
        reqlist.setOnItemLongClickListener  (this);
        reqpgb = findViewById(R.id.reqpgb);

        reqpull = findViewById(R.id.reqpull);
        new GetReqData(PrayerRequest.this).execute(Common.getAddresApiReq());

        reqrefresher = findViewById(R.id.reqrefresher);


        reqrefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetReqData(PrayerRequest.this).execute(Common.getAddresApiReq());
                reqrefresher.setRefreshing(false) ;
            }
        });

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        //var announsel = view.Id;
        android.widget.PopupMenu menu = new android.widget.PopupMenu(this, view);

        // with Android 3 need to use MenuInfater to inflate the menu
        //  menu.MenuInflater.Inflate(Resource.Menu.popup, menu.Menu);
        reqselected = requests.get(i);

        // with Android 4 Inflate can be called directly on the menu
        menu.inflate(R.menu.popup);

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.delete) {
                    if (MainActivity.currmail.equals("wordstudycu@gmail.com")) {
                        new DelRequestData(reqselected, PrayerRequest.this).execute(Common.getAddressSinglereq(reqselected));
                    } else {
                        Toast.makeText(PrayerRequest.this, "You can't delete this item", Toast.LENGTH_SHORT).show();
                    }
                }return true;
            }
        });

        // Android 4 now has the DismissEvent
        menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
                new GetReqData(PrayerRequest.this).execute(Common.getAddresApiReq());
            }
        });

        menu.show();

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        reqselected = requests.get(i);

    }

    public class GetReqData extends AsyncTask<String, Void, String> {
        private PrayerRequest activity;

        public GetReqData(PrayerRequest activity) {
            this.activity = activity;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.reqpgb.setVisibility(View.VISIBLE);
            activity.reqlist.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                try {
                    Type listtype = new TypeToken<List<requests>>(){}.getType();
                    activity.requests = new Gson().fromJson(s, listtype);
                    Collections.reverse(activity.requests);
                    ReqAdapter adapter = new ReqAdapter(PrayerRequest.this, activity.requests);
                    activity.reqlist.setAdapter(adapter);
                    activity.reqpgb.setVisibility(View.GONE) ;
                    activity.reqpull.setVisibility(View.GONE) ;
                    activity.reqlist.setVisibility(View.VISIBLE) ;

                } catch (Exception ex) {
                    activity.reqpull.setVisibility(View.VISIBLE) ;
                }}else
                activity.reqpull.setVisibility(View.VISIBLE) ;


        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            stream = http.GetHttpData(urlString);
            return stream;        }

    }

    public class DelRequestData extends AsyncTask<String, String, String> {
        String user = MainActivity.currname;
        PrayerRequest activity;
        requests requestselected = null;

        public DelRequestData(requests requestselected, PrayerRequest activity) {
            this.activity = activity;
            this.requestselected = requestselected;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.reqpgb.setVisibility(View.VISIBLE) ;
            activity.reqlist.setVisibility(View.GONE) ;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.reqpgb.setVisibility(View.GONE) ;
            activity.reqlist.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            String json = new Gson().toJson(requestselected);
            http.DeleteHttpData(url, json);
            return "";
        }


    }
}
