package com.francotte.go4lunch_opc.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.repositories.user_repository.FirestoreCall;
import com.francotte.go4lunch_opc.models.User;
import com.francotte.go4lunch_opc.repositories.user_repository.UserHelper;
import com.francotte.go4lunch_opc.ui.activities.LogActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService implements FirestoreCall.CallbackGetAllInformationToConstructNotification {


    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        Log.d("FirebaseMessageService", "Message received");
        FirestoreCall.getAllInformationToConstructNotification(this);
    }
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserHelper.updateToken(uid, s);
    }

    private void showNotification(String messageBody) {
        // 1 - Create an Intent that will be shown when user will click on the Notification
        Intent intent = new Intent(this, LogActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // 2 - Create a Style for the Notification
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(getString(R.string.notification_title));
        inboxStyle.setSummaryText(messageBody);
        //inboxStyle.addLine(messageBody);

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(messageBody);

        // 3 - Create a Channel (Android 8)
        String channelId = getString(R.string.default_notification_channel_id);

        // 4 - Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_logo_go4lunch_blank)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setSubText(messageBody)
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .setStyle(bigTextStyle);

        // 5 - Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 6 - Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Message provenant de Firebase";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        // 7 - Show notification
        String NOTIFICATION_TAG = "FIREBASEOC";
        int NOTIFICATION_ID = 7;
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());

    }

    @Override
    public void onSuccessGetAllInformationToConstructNotification(String name, String nameRestaurant, List<User> usersLunchByUser) {
        String msg;
        if(nameRestaurant != null) {
            StringBuilder allUsers = new StringBuilder();
            if (usersLunchByUser != null) {
                for (int i = 0; i < usersLunchByUser.size(); i++) {
                    if (!usersLunchByUser.get(i).getUserName().equals(name)) {
                        allUsers.append(usersLunchByUser.get(i).getUserName());
                        if (usersLunchByUser.size() < i - 1) {
                            allUsers.append(", ");
                        }
                    }
                }
            }

            if (!allUsers.toString().equals("")) {
                msg = getString(R.string.notification_part_1_hey) + name + getString(R.string.notification_part_2_eating) + nameRestaurant + getString(R.string.notification_part_3_with) + allUsers;
            } else {
                msg = getString(R.string.notification_part_1_hey) + name + getString(R.string.notification_part_2_eating) + nameRestaurant;
            }
            Log.d("FirebaseMessaging", msg);
        }else{
            msg = getString(R.string.notification_not_deceixded);
        }
        showNotification(msg);
    }
    @Override
    public void onFailureGetAllInformationToConstructNotification() {
        String msg;
        msg = getString(R.string.notification_not_deceixded);
        showNotification(msg);
    }
}
