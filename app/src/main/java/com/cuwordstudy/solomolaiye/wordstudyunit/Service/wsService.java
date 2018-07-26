package com.cuwordstudy.solomolaiye.wordstudyunit.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.cuwordstudy.solomolaiye.wordstudyunit.MainActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class wsService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Common.currentToken = FirebaseInstanceId.getInstance().getToken();
        com.google.firebase.messaging.FirebaseMessaging.getInstance().subscribeToTopic("Meeting");
        if (MainActivity.auth.getCurrentUser().getEmail().equals("wordstudycu@gmail.com"))
        {
            FirebaseMessaging.getInstance().subscribeToTopic("Prayer");
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
