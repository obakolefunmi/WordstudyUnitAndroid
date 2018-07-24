package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.cuwordstudy.solomolaiye.wordstudyunit.Adapters.CommentAdapter;
import com.cuwordstudy.solomolaiye.wordstudyunit.Adapters.QuestionAdapter;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Coment;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.questions;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.topic;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.HttpDataHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class TopicActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    private TextView topicacctWord, topicacctpull, topiccontriIndicator,topicquestindicator;
    private ImageView topicacctContri , topicacctAsk;
    private EditText topicacctEdit, topicacctEditcontri;
    private SwipeRefreshLayout topicacctrefresher, topicacctrefreshercontri;
    private ListView topicacctList, topicacctListcontri;
    private ProgressBar topicacttpgb, pgbcliclask, pgbcliclaskcontri;
    private String topic, bible, text, textss, topicss,textid;
    private List<questions> quests;
    private List<Coment> contris;
    private questions questselected = null;
    private Coment contriselected = null;
    LinearLayout topiccontriholder, topicquestholder, holder,
            topiccontricard, topicquestcard;
    CardView topicwordholder;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        topicacctWord = findViewById(R.id.topicacctWord);

        topicacctAsk = findViewById(R.id.topicacctAsk);
        topicacctContri = findViewById(R.id.topicacctContri);

        topicacctpull = findViewById(R.id.topicacctpull);

        topicacctEdit = findViewById(R.id.topicacctEdit);
        topicacctEditcontri = findViewById(R.id.topicacctEditcontri);

        topicacctList = findViewById(R.id.topicacctlist);
        topicacctListcontri = findViewById(R.id.topicacctlistcontri);

        topicacctrefresher = findViewById(R.id.topicacctrefresher);
        topicacctrefreshercontri = findViewById(R.id.topicacctrefreshercontri);

        topicacttpgb = findViewById(R.id.topicacctpgb);

        pgbcliclask = findViewById(R.id.topicclickpgb);
        pgbcliclaskcontri = findViewById(R.id.topicclickpgbcontri);

        topiccontriIndicator = findViewById(R.id.topiccontriIndicator);
        topicquestindicator = findViewById(R.id.topicsquestindicator);

        topiccontriholder = findViewById(R.id.linetopiccontri);
        topicquestholder =findViewById(R.id.linetopicquest);

        topiccontricard =findViewById(R.id.linetopiccontri);
        topicquestcard =findViewById(R.id.linetopicquest);
        topicwordholder = findViewById(R.id.topicwordholder);

        holder = findViewById(R.id.topicaccholder);
        topicquestindicator.setOnClickListener(this);
        topiccontriIndicator.setOnClickListener(this);
        topicacctAsk.setOnClickListener(this);
        topicacctContri.setOnClickListener(this);
        if (savedInstanceState == null) {
            Bundle intent = getIntent().getExtras();
            if(intent == null) {
                topic = null;
                bible = null;
                text = null;
                textid = null;

            } else {
                topic = intent.getString("topic") ;
                bible = intent.getString("bible") ;
                text = intent.getString("text");
                textid = intent.getString("textid");

            }
        } else {
            topic =(String) savedInstanceState.getSerializable("topic");
            topic = (String) savedInstanceState.getSerializable("topic");
            bible = (String) savedInstanceState.getSerializable("bible");
            text = (String) savedInstanceState.getSerializable("text");
            textid = (String) savedInstanceState.getSerializable("textid");


        }

        setTitle(topic + " - " + bible);
        topicss = "\""+topic+"\"";
        topicacctWord.setText(text);
        textss = "\"" + textid + "\"";

        // topicquestholder.Visibility = ViewStates.Visible;
        //topiccontriholder.Visibility = ViewStates.Visible;
        // questrefresher = quest.FindViewById<SwipeRefreshLayout>(R.id.questrefresher);
          topicacctWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ViewGroup.LayoutParams expand = topicwordholder.getLayoutParams();
                ViewGroup.LayoutParams compress = topicwordholder.getLayoutParams();

                if(expand.height == ViewGroup.LayoutParams.WRAP_CONTENT){
                    compress.height = 300;
                    topicwordholder.setLayoutParams(compress);
                }
                else {
                expand.height= ViewGroup.LayoutParams.WRAP_CONTENT;
                    topicwordholder.setLayoutParams(expand);
                }
              //  topicwordholder.setLayoutParams(expand);

            }
        });

        topicacctrefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetQuesSpecifictData(TopicActivity.this).execute(Common.getAddresApiQuestspecifictitle(textss));
                topicacctrefresher.setRefreshing(false);
            }
        });
        topicacctrefreshercontri.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new GetComentSpecifictData(TopicActivity.this).execute(Common.getAddresApiAnsSpecificComment(textss));
                topicacctrefreshercontri.setRefreshing(false);
            }
        });
        new GetComentSpecifictData(TopicActivity.this).execute(Common.getAddresApiAnsSpecificComment(textss));

        new GetQuesSpecifictData(TopicActivity.this).execute(Common.getAddresApiQuestspecifictitle(textss));
        topicacctList.setOnItemClickListener(this);
        topicacctList.setOnItemLongClickListener(this);
        topicacctListcontri.setOnItemLongClickListener(this);
    }






    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.topicacctAsk)
        {
            if (!TextUtils.isEmpty(topicacctEdit.getText().toString().trim()))
            {
                new SendQuest(topicacctEdit.getText().toString(), topic, textid,TopicActivity.this).execute(Common.getAddresApiQuest());
            }
            else
            {
                topicacctEdit.setError("Required");
            }
            //send the prayer.
        }
        if (v.getId() == R.id.topicacctContri)
        {
            if (!TextUtils.isEmpty(topicacctEditcontri.getText().toString().trim()))
            {
                new AddComent(topicacctEditcontri.getText().toString(), textid, TopicActivity.this).execute(Common.getAddresApiComment());
            }
            else
            {
                topicacctEditcontri.setError("Required");
            }
            //send the prayer.
        }
        if (v.getId() == R.id.topicsquestindicator)
        {
            if (topicquestholder.getVisibility() ==View.GONE)
            {
                topicquestholder.setVisibility(View.VISIBLE);
                topiccontriholder.setVisibility(View.GONE);

            }
            else
            {
                topicquestholder.setVisibility(View.GONE);
                topiccontriholder.setVisibility(View.VISIBLE);


            }
            //send the prayer.
        }
        if (v.getId() == R.id.topiccontriIndicator)
        {

            if (topiccontriholder.getVisibility() == View.GONE)
            {
                topiccontriholder.setVisibility(View.VISIBLE);
                topicquestholder.setVisibility(View.GONE);

            }
            else
            {
                topiccontriholder.setVisibility(View.GONE);
                topicquestholder.setVisibility(View.VISIBLE);

                //set the card weight to 0
            }
            //send the prayer.



        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        questselected = quests.get(i);
        String title = questselected.title;

        String quest = questselected.question;
        String questid = questselected._id.getOid();
        String user = questselected.user;
        Intent gotoans = new Intent(TopicActivity.this, AnswerActivity.class);
        gotoans.putExtra("topic", title);
        gotoans.putExtra("question", quest);
        gotoans.putExtra("questionid", questid);

        gotoans.putExtra("user", user);
        startActivity(gotoans);
        finish();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
       int id = adapterView.getId();
        switch (id){
        case R.id.topicacctlist: {
            questselected = quests.get(i);
            //pop up code here
            //var announsel = view.Id;
            android.widget.PopupMenu menu = new android.widget.PopupMenu(TopicActivity.this, view);

            // with Android 3 need to use MenuInfater to inflate the menu
            //  menu.MenuInflater.Inflate(Resource.Menu.popup, menu.Menu);

            // with Android 4 Inflate can be called directly on the menu
            menu.inflate(R.menu.popup);

            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(menuItem.getItemId() == R.id.delete)  {
                        if(MainActivity.currmail.equals("wordstudycu@gmail.com") ||  MainActivity.currname.equals(questselected.user.toString())){
                            new DelQuestionData(questselected, TopicActivity.this).execute(Common.getAddressSingleQuest(questselected));
                        } else {
                            Toast.makeText(TopicActivity.this, "You can't delete this question", Toast.LENGTH_SHORT).show();

                        }
                    }
                    return true;
                }
            });

            // Android 4 now has the DismissEvent
            menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                @Override
                public void onDismiss(PopupMenu popupMenu) {
                    new GetQuesSpecifictData(TopicActivity.this).execute(Common.getAddresApiQuestspecifictitle(textss));

                }
            });
            menu.show();
            break;
        }
            case R.id.topicacctlistcontri: {
            //pop up code here
            //var announsel = view.Id;
                contriselected = contris.get(i);

                android.widget.PopupMenu menu = new android.widget.PopupMenu(TopicActivity.this, view);

            // with Android 3 need to use MenuInfater to inflate the menu
            //  menu.MenuInflater.Inflate(Resource.Menu.popup, menu.Menu);

            // with Android 4 Inflate can be called directly on the menu
            menu.inflate(R.menu.popup);

            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.delete) {
                        if (MainActivity.currmail.equals("wordstudycu@gmail.com") ||  MainActivity.currname.equals(contriselected.user.toString())) {
                            new DelCommment(contriselected, TopicActivity.this).execute(Common.getAddressSingleComment(contriselected));
                        } else {
                            Toast.makeText(TopicActivity.this, "You can't delete this contribution", Toast.LENGTH_SHORT).show();                        }
                    }
                    return true;
                }
            });

            // Android 4 now has the DismissEvent
            menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                @Override
                public void onDismiss(PopupMenu popupMenu) {
                    new GetComentSpecifictData(TopicActivity.this).execute(Common.getAddresApiAnsSpecificComment(textss));

                }
            });

            menu.show();
            break;
            }
        }
        return true;

    }

    private class GetQuesSpecifictData extends AsyncTask<String, java.lang.Void, String>
    {

        private TopicActivity activity;
            public GetQuesSpecifictData(TopicActivity activity)
        {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.topicacttpgb.setVisibility(View.VISIBLE);
            activity.topicacctList.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
            try
            {
                Type listtype = new TypeToken<List<questions>>(){}.getType();
                activity.quests = new Gson().fromJson(s, listtype);
                Collections.reverse(activity.quests);
                QuestionAdapter adapter = new QuestionAdapter(getApplicationContext(), activity.quests);
                activity.topicacctList.setAdapter(adapter);
                activity.topicacttpgb.setVisibility(View.GONE);
                activity.topicacctpull.setVisibility(View.GONE);
                activity.topicacctList.setVisibility(View.VISIBLE);
            }
            catch (Exception ex)
            {
                activity.topicacctpull.setVisibility(View.VISIBLE);
            }
            }else {
                activity.topicacctpull.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            stream = http.GetHttpData(urlString);
            return stream;        }


    }
    private class SendQuest extends AsyncTask<String, java.lang.Void, String>
    {
        String question = "";
        String titleid = "";
        String title = "";
        String user = MainActivity.currname;
        TopicActivity activity = new TopicActivity();
            public SendQuest(String question, String title,String titleid, TopicActivity activity)
        {
            this.question = question;
            this.activity = activity;
            this.title = title;
            this.titleid = titleid;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.pgbcliclask.setVisibility(View.VISIBLE);
            activity.topicacctEdit.setVisibility(View.GONE);
            activity.topicacctAsk.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.pgbcliclask.setVisibility(View.GONE);
            activity.topicacctEdit.setVisibility(View.VISIBLE);
            activity.topicacctAsk.setVisibility(View.VISIBLE);
            activity.topicacctEdit.setText("");
            String titless = "\"" + titleid + "\"";

            new GetQuesSpecifictData(TopicActivity.this).execute(Common.getAddresApiQuestspecifictitle(titless));

        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            questions quests = new questions();
            quests.question = question;
            quests.user = user;
            quests.title = title;
            quests.titleid = titleid;
            String json = new Gson().toJson(quests);
            http.PostHttpData(url, json);
            return "";
        }



    }
    public class AddComent extends AsyncTask<String, java.lang.Void, String>
    {
        String comment = "";
        String textid = "";
        String user = MainActivity.currname;
        TopicActivity activity = new TopicActivity();
            public AddComent(String comment, String textid, TopicActivity activity)
        {
            this.textid = textid;
            this.comment = comment;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.pgbcliclaskcontri.setVisibility(View.VISIBLE);
            activity.topicacctEditcontri.setVisibility(View.GONE);
            activity.topicacctContri.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.pgbcliclaskcontri.setVisibility(View.GONE);
            activity.topicacctEditcontri.setVisibility(View.VISIBLE);
            activity.topicacctContri.setVisibility(View.VISIBLE);
            activity.topicacctEditcontri.setText("");
            String textss = "\"" + textid + "\"";

            new GetComentSpecifictData(activity).execute(Common.getAddresApiAnsSpecificComment(textss));
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            Coment comments = new Coment();
            comments.contribution = comment;
            comments.user = user;
            comments.textid = textid;
            String json = new Gson().toJson(comments);
            http.PostHttpData(url, json);
            return "";
        }




    }
    private class GetComentSpecifictData extends AsyncTask<String, java.lang.Void, String>
    {

        private TopicActivity activity;
            public GetComentSpecifictData(TopicActivity activity)
        {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.pgbcliclaskcontri.setVisibility(View.VISIBLE);
            activity.topicacctListcontri.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){

                try
            { Type listtype = new TypeToken<List<Coment>>(){}.getType();
                activity.contris = new Gson().fromJson(s, listtype);
                Collections.reverse(activity.contris);
                CommentAdapter adapter = new CommentAdapter(getApplicationContext(), activity.contris);
                activity.topicacctListcontri.setAdapter(adapter);
                activity.pgbcliclaskcontri.setVisibility(View.GONE);
                 activity.topicacctpull.setVisibility(View.GONE);
                activity.topicacctListcontri.setVisibility(View.VISIBLE);

            }
            catch (Exception ex)
            {
                //activity.topicacctpull.Visibility = ViewStates.Visible;
            }
            }else
                {
                    activity.topicacctpull.setVisibility(View.VISIBLE);

                }
        }

        @Override
        protected String doInBackground(String... strings) {

            String stream = null;
            String urlString = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            stream = http.GetHttpData(urlString);
            return stream;        }


    }
    public class DelCommment extends AsyncTask<String, Void, String>
    {
        String user = MainActivity.currname;
        TopicActivity activity = new TopicActivity();
        Coment comentselected = null;

            public DelCommment(Coment comentselected, TopicActivity activity)
        {
            this.activity = activity;
            this.comentselected = comentselected;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.pgbcliclaskcontri.setVisibility(View.VISIBLE);
            activity.topicacctListcontri.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            activity.pgbcliclaskcontri.setVisibility(View.GONE);
            activity.topicacctListcontri.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            String json = new Gson().toJson(comentselected);
            http.DeleteHttpData(url, json);
            return "";
        }



    }
    public class DelQuestionData extends AsyncTask<String, java.lang.Void, String>
    {
        String user = MainActivity.currname;
        TopicActivity activity = new TopicActivity();
        questions announselected = null;

            public DelQuestionData(questions announselected, TopicActivity activity)
        {
            this.activity = activity;
            this.announselected = announselected;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.topicacttpgb.setVisibility(View.VISIBLE); ;
            activity.topicacctList.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.topicacttpgb.setVisibility(View.GONE);
            activity.topicacctList.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            String json = new Gson().toJson(announselected);
            http.DeleteHttpData(url, json);
            return "";
        }



    }

}
