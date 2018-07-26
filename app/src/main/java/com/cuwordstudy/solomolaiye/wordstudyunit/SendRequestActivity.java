package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.MyResponse;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Notification;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Sender;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.requests;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.HttpDataHandler;
import com.cuwordstudy.solomolaiye.wordstudyunit.Remote.ApiService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;


import static com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common.baseURL;

public class SendRequestActivity extends AppCompatActivity {
    FirebaseAuth auth;
    public static String curruser;
    ProgressBar sendprayedpgb;
    EditText sendprayerrequestedit;
    ApiService mService;


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_item_send: {
                if (TextUtils.isEmpty(sendprayerrequestedit.getText().toString().trim())) {
                    sendprayerrequestedit.setError("Required");
                } else {
                    new SendPrayerRequest(sendprayerrequestedit.getText().toString(), SendRequestActivity.this).execute(Common.getAddresApiReq());
                    //send the prayer.
                }
            }
        }
        return super.onOptionsItemSelected(item);

    }

                @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);
        auth = FirebaseAuth.getInstance();
        curruser = auth.getCurrentUser().getDisplayName();
        Common.currentToken = FirebaseInstanceId.getInstance().getToken();

        FirebaseMessaging.getInstance().subscribeToTopic("Meeting");



        sendprayedpgb = findViewById(R.id.sendprayerpgb);
        sendprayerrequestedit = findViewById(R.id.sendprayerrequestedit);
        sendprayedpgb.setVisibility(View.GONE);
    }
    private class SendPrayerRequest extends AsyncTask<String, Void, String>
    {
        String prayerreq = "";
        String user = SendRequestActivity.curruser;
        SendRequestActivity activity = new SendRequestActivity();
            public SendPrayerRequest(String prayerreq, SendRequestActivity activity)
        {
            this.prayerreq = prayerreq;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.sendprayedpgb.setVisibility(View.VISIBLE);
            activity.sendprayerrequestedit.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.sendprayedpgb.setVisibility(View.GONE);
            activity.sendprayerrequestedit.setVisibility(View.VISIBLE);
            Notification notification = new Notification(user+" needs our prayers","Word Study Prayers","default");
            Sender sender =  new Sender("/topics/Prayer",notification);
            new ApiService.sendNotification(sender).execute(baseURL);
            Intent intent = new Intent(activity,MainActivity.class);
            activity.startActivity(intent);             }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            requests prayerreqs = new requests();
            prayerreqs.prayer = prayerreq;
            prayerreqs.user = user;
            String json = new Gson().toJson(prayerreqs);
            http.PostHttpData(url, json);
            return "";        }




    }
}


