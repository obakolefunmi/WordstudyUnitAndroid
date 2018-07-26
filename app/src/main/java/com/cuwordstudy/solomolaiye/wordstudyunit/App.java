package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.app.Application;
import android.content.Intent;

import com.cuwordstudy.solomolaiye.wordstudyunit.Service.FirebaseMessaging;
import com.cuwordstudy.solomolaiye.wordstudyunit.Service.wsService;

public class App extends Application {
    @Override
    public void onCreate() {
        startService(new Intent(this, wsService.class));
        startService(new Intent(this, FirebaseMessaging.class));

        super.onCreate();
    }
}
