package com.cuwordstudy.solomolaiye.wordstudyunit.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import com.cuwordstudy.solomolaiye.wordstudyunit.Discussion;
import com.cuwordstudy.solomolaiye.wordstudyunit.MainActivity;
import com.cuwordstudy.solomolaiye.wordstudyunit.PrayerRequest;
import com.cuwordstudy.solomolaiye.wordstudyunit.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification());

    }



    private void showNotification(RemoteMessage.Notification notification) {
        if (notification.getTitle().equals("Word Study Prayers"))
        {
            Intent intent = new Intent(this, PrayerRequest.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_ws)
                    .setContentTitle(notification.getTitle())
                    .setContentText(notification.getBody())
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);



            NotificationManager notificationManager  = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0,builder.build());
        }
        else if(notification.getTitle().equals("Word Study Unit"))
        {
            Intent intent = new Intent(this, Discussion.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_ws)
                    .setContentTitle(notification.getTitle())
                    .setContentText(notification.getBody())

                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager  = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0,builder.build());
        }
        else
            {


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_ws)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager  = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
            }
    }

}
