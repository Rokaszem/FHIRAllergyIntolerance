package com.example.fhir_allergyintolerance;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {

    private static final String CHANNEL_ID = "fhir_allergyintolerance_channel";
    private final int PATIENT_ADDED_ID = 0;

    private NotificationManager mManager;
    private Context mContext;

    public NotificationHandler(Context context) {
        this.mContext = context;
        this.mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Patient noti test", NotificationManager.IMPORTANCE_DEFAULT);

        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("New patient added!");
        this.mManager.createNotificationChannel(notificationChannel);
    }

    public void send(String sTitle, String sText) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID).setContentTitle(sTitle).setContentText(sText).setSmallIcon(R.drawable.common_full_open_on_phone);
        this.mManager.notify(PATIENT_ADDED_ID, builder.build());
    }
}
