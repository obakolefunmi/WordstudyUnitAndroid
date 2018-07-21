package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.cuwordstudy.solomolaiye.wordstudyunit.Adapters.AnsAdapter;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.answers;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.questions;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.HttpDataHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AnswerActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener , AdapterView.OnItemLongClickListener {
    private TextView ansQuest, ansSend, anspull, ansAuthor;
    private EditText ansEdit;



    private SwipeRefreshLayout ansresher;
    private ListView ansList;
    private ProgressBar anspgb, ansclickpgb;
    static String topic, question,questionid, user, questionss;
    private List<answers> anss;
    private answers anselected = null;

    protected void onPause() {
        super.onPause();
        finish();
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ansSend)
        {
            if (!TextUtils.isEmpty(ansEdit.getText().toString().trim()))
            {
                new SendAns(question,questionid, ansEdit.getText().toString(), AnswerActivity.this).execute(Common.getAddresApiAns());
            }
            else
            {
                ansEdit.setError("Required");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        anselected = anss.get(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ansQuest = findViewById(R.id.ansQuest);
        ansSend = findViewById(R.id.ansSend);
        anspull = findViewById(R.id.anspull);
        ansAuthor = findViewById(R.id.ansquestAuthor);
        ansEdit = findViewById(R.id.ansEdit);
        ansresher = findViewById(R.id.ansrefresher);
        ansList = findViewById(R.id.anstlist);
        anspgb = findViewById(R.id.anspgb);
        ansclickpgb = findViewById(R.id.ansclickpgb);
        ansList.setOnItemLongClickListener(this);
        if (savedInstanceState == null) {
            Bundle intent = getIntent().getExtras();
            if(intent == null) {
                topic = null;
                question = null;
                questionid = null;

                user = null;
            } else {
                topic = intent.getString("topic");
                question = intent.getString("question");
                questionid= intent.getString("questionid");

                user = intent.getString("user");
                          }
        } else {
            topic =(String) savedInstanceState.getSerializable("topic");
            question =(String) savedInstanceState.getSerializable("question");
            questionid =(String) savedInstanceState.getSerializable("questionid");

            user = (String) savedInstanceState.getSerializable("user");

        }

        ansSend.setOnClickListener(this);
        ansAuthor.setText(user);
        ansQuest.setText(question);
        questionss = "\"" + questionid + "\"";
        setTitle(topic);


        ansresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetAnsSpecifictData(AnswerActivity.this).execute(Common.getAddresApiAnsSpecificAns(questionss));
                ansresher.setRefreshing(false);
            }
        });
        new GetAnsSpecifictData(AnswerActivity.this).execute(Common.getAddresApiAnsSpecificAns(questionss));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        //pop up code here
        //var announsel = view.Id;
        android.widget.PopupMenu menu = new android.widget.PopupMenu(this, view);

        // with Android 3 need to use MenuInfater to inflate the menu
        //  menu.MenuInflater.Inflate(Resource.Menu.popup, menu.Menu);
        anselected = anss.get(i);
        // with Android 4 Inflate can be called directly on the menu
        menu.inflate(R.menu.popup);

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                if (menuItem.getItemId() == R.id.delete) {
                    if (MainActivity.currmail.equals("wordstudycu@gmail.com") ||  MainActivity.currname.equals(anselected.user.toString())) {
                        new DelAnswerData(anselected, AnswerActivity.this).execute(Common.getAddressSingleAns(anselected));
                    } else {
                        Toast.makeText(AnswerActivity.this, "You can't delete this item", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

        // Android 4 now has the DismissEvent
        menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
                new GetAnsSpecifictData(AnswerActivity.this).execute(Common.getAddresApiAnsSpecificAns(questionss));
            }
        });
        menu.show();


        return true;
    }

    private class GetAnsSpecifictData extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.anspgb.setVisibility(View.VISIBLE);
            activity.ansList.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {
                try
                {

                Type listtype = new TypeToken<List<answers>>(){}.getType();
                activity.anss = new Gson().fromJson(s, listtype);
                AnsAdapter adapter = new AnsAdapter(getApplicationContext(), activity.anss);
                activity.ansList.setAdapter(adapter);
                activity.anspgb.setVisibility(View.GONE);
                activity.anspull.setVisibility(View.GONE);
                activity.ansList.setVisibility(View.VISIBLE);
                }
                 catch (Exception ex)
                 {
                activity.anspull.setVisibility(View.VISIBLE);
                }
            }
            else
                {
                    activity.anspull.setVisibility(View.VISIBLE);

                }


        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlstring = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            stream = http.GetHttpData(urlstring);
            return stream;
        }

        private AnswerActivity activity;
            public GetAnsSpecifictData(AnswerActivity activity)
        {
            this.activity = activity;
        }



    }
    private class SendAns extends AsyncTask<String, java.lang.Void, String>
    {
        String question = "";
        String questionid = "";

        String ans = "";
        String user = MainActivity.currname;
        AnswerActivity activity = new AnswerActivity();
            public SendAns(String question,String questionid, String ans, AnswerActivity activity)
        {
            this.question = question;
            this.questionid = questionid;

            this.ans = ans;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.ansclickpgb.setVisibility(View.VISIBLE);
            activity.ansEdit.setVisibility(View.GONE);
            activity.ansSend.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.ansclickpgb.setVisibility(View.GONE);
            activity.ansEdit.setVisibility(View.VISIBLE);
            activity.ansSend.setVisibility(View.VISIBLE);
            activity.ansEdit.setText("");
            new GetAnsSpecifictData(activity).execute(Common.getAddresApiAnsSpecificAns(questionss));
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            answers anses = new answers();
            anses.question = question;
            anses.questionid = questionid;
            anses.user = user;
            anses.answer = ans;
            String json = new Gson().toJson(anses);
            http.PostHttpData(url, json);
            return "";        }




    }
    public class DelAnswerData extends AsyncTask<String, String, String> {
        String user = MainActivity.currname;
        AnswerActivity activity;
        answers announselected = null;


        public DelAnswerData(answers announselected, AnswerActivity activity) {
            this.activity = activity;
            this.announselected = announselected;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.anspgb.setVisibility(View.VISIBLE);
            activity.ansList.setVisibility(View.GONE) ;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.anspgb.setVisibility(View.GONE) ;
            activity.ansList.setVisibility(View.VISIBLE) ;
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
