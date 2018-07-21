package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendRequestActivity extends AppCompatActivity {
    FirebaseAuth auth;
    public static String curruser;
    ProgressBar sendprayedpgb;
    EditText sendprayerrequestedit;
    TextView prayerrequestsend;
    ApiService mService;


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);
        auth = FirebaseAuth.getInstance();
        curruser = auth.getCurrentUser().getDisplayName();
        Common.currentToken = FirebaseInstanceId.getInstance().getToken();

        FirebaseMessaging.getInstance().subscribeToTopic("Meeting");

        mService = Common.getFCMClient();


        sendprayedpgb = findViewById(R.id.sendprayerpgb);
        sendprayerrequestedit = findViewById(R.id.sendprayerrequestedit);
        prayerrequestsend = findViewById(R.id.prayerrequestsend);
        prayerrequestsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(sendprayerrequestedit.getText().toString().trim()))
                {
                    sendprayerrequestedit.setError("Required");
                }
                else
                {
                    new SendPrayerRequest(sendprayerrequestedit.getText().toString(), SendRequestActivity.this).execute(Common.getAddresApiReq());
                    //send the prayer.
                }
            }
        });
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
            activity.prayerrequestsend.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.sendprayedpgb.setVisibility(View.GONE);
            activity.sendprayerrequestedit.setVisibility(View.VISIBLE);
            activity.prayerrequestsend.setVisibility(View.VISIBLE);
            Notification notification = new Notification(user+" needs our prayers","Word Study Prayers");
            Sender sender =  new Sender("/topics/Prayer",notification);
            mService.sendNotification(sender)
                    .enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {
                            Toast.makeText(SendRequestActivity.this, "connect to the internet and re-post the prayer",Toast.LENGTH_SHORT).show();
                        }
                    });
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


