package com.tecsol.batua.user.module.common.service;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.batua.android.user.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.module.common.receiver.GcmBroadcastReceiver;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.dashboard.view.activity.HomeActivity;
import com.tecsol.batua.user.module.onboard.view.activity.OnBoardActivity;
import com.tecsol.batua.user.module.onboard.view.activity.PinLoginActivity;

public class GcmIntentService extends IntentService {

    private NotificationManager notificationManager;
    private PendingIntent contentIntent = null;


    public GcmIntentService() {
        super("GcmIntentService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                receivedNotification(extras);
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                receivedNotification(extras);
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                receivedNotification(extras);
                Log.i("INTENT SERVICE", "gcm: " + extras.toString());
            }
        }

        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }


    //push notification is receiving here.
    private void receivedNotification(Bundle msg) {

        Log.v("PushMessage", msg.toString());

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PreferenceUtil sharedPreferences = new PreferenceUtil();

        if (sharedPreferences.readBoolean(sharedPreferences.IS_LOGGED_IN, false)) {
            User user = (User) sharedPreferences.read(sharedPreferences.USER, User.class);
            if (user.isPinSet() && user.isPinActivated()) {
                contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, PinLoginActivity.class), 0);
            } else {
                contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), 0);
            }
        } else {
            contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, OnBoardActivity.class), 0);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.img_batua_logo_small)
                .setContentTitle(msg.getString("title"))
                .setShowWhen(true)
                .setContentText(msg.getString("message"))
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg.getString("message")))
                .setAutoCancel(true);

        notificationBuilder.setContentIntent(contentIntent);

        Notification note = notificationBuilder.build();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        note.defaults |= Notification.DEFAULT_SOUND;
        notificationManager.notify((int) (System.currentTimeMillis() / 1000), notificationBuilder.build());//time stamp for making different bw notification.
    }

}
