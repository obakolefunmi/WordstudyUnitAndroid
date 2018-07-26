package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.MyResponse;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Notification;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Sender;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.announcements;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.HttpDataHandler;
import com.cuwordstudy.solomolaiye.wordstudyunit.Remote.ApiService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonSerializer;



import static com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common.baseURL;

public class NewAnnouncementActivity extends AppCompatActivity {
    ProgressBar newannouncementpgb;
    EditText newannouncementedit;
    LinearLayout newannounHolder;
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
                if (TextUtils.isEmpty(newannouncementedit.getText().toString())) {
                    newannouncementedit.setError("Required");
                } else {
                    new SendAnnouncement(newannouncementedit.getText().toString(), NewAnnouncementActivity.this).execute(Common.getAddresApiAnoun());
                    //send the prayer.
                }
            }
        }
        return super.onOptionsItemSelected(item);

    }


                @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_announcement);
        Common.currentToken = FirebaseInstanceId.getInstance().getToken();

        FirebaseMessaging.getInstance().subscribeToTopic("Meeting");


        newannouncementpgb = findViewById(R.id.newannouncepgb);
        newannouncementedit = findViewById(R.id.newannouncementedit);
        newannounHolder = findViewById(R.id.newannounHolder);

        newannouncementpgb.setVisibility(View.GONE);
    }
    private class SendAnnouncement extends AsyncTask<String, Void, String>
    {
        String announcement;

        NewAnnouncementActivity activity ;
       public SendAnnouncement(String announcement, NewAnnouncementActivity activity)
        {
            this.announcement = announcement;
            this.activity = activity;
        }




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.newannouncementpgb.setVisibility(View.VISIBLE);
            activity.newannounHolder.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.newannouncementpgb.setVisibility(View.GONE);
            activity.newannounHolder.setVisibility(View.VISIBLE);
            Notification notification = new Notification(announcement,"Word Study Announcement","default");
            Sender sender =  new Sender("/topics/Meeting",notification);
            new ApiService.sendNotification(sender).execute(baseURL);

            Intent intent = new Intent(activity,MainActivity.class);
            activity.startActivity(intent);
        }

        @Override
        protected String doInBackground(String... strings) {
           String url = strings[0];
        HttpDataHandler http = new HttpDataHandler();
        announcements announce = new announcements();
            announce.announcement = announcement;
        String json = new Gson().toJson(announce);//"{\"announcement\":\""+announcement+"\"}";
                http.PostHttpData(url, json);
                return "";}
    }

}
