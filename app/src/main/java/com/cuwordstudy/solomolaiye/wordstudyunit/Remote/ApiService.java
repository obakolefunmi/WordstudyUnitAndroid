package com.cuwordstudy.solomolaiye.wordstudyunit.Remote;

import android.os.AsyncTask;
import android.support.constraint.solver.SolverVariable;
import android.view.View;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Coment;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.MyResponse;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Sender;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.HttpDataHandler;
import com.cuwordstudy.solomolaiye.wordstudyunit.MainActivity;
import com.cuwordstudy.solomolaiye.wordstudyunit.PlaceholderFragment;
import com.google.gson.Gson;


public interface ApiService {

    public class sendNotification extends AsyncTask<String, Void, String> {
       Sender body;


        public sendNotification(Sender body) {
            this.body = body;
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            RetrofitClient http = new RetrofitClient();
            String json = new Gson().toJson(body);
            http.PostHttpData(url, json);
            return "";
        }
    }

}
