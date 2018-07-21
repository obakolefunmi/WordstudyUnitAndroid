package com.cuwordstudy.solomolaiye.wordstudyunit.Remote;

import android.support.constraint.solver.SolverVariable;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.MyResponse;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({
          "Content-Type:application/json",
            "Authorization:key=AAAAJg9f1GA:APA91bFrCxlaYUnqgTL3CNs-SHsDwoioCtrJTzzLoMpUgYW-F3XOlHSe9zsLl2bqJxOpIkppGHQeagwUzX5GxN70WNq2FbCTZO9XUOOlLkk6DRRrWJj61xSiCfkEP65qTS6Gu7cZ6sHk"

    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
