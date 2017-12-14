package madcourse.neu.edu.allot;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import madcourse.neu.edu.allot.group.GroupActivity;

/**
 * Created by zeko on 12/14/17.
 */

public class AllotFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        super.onMessageReceived(remoteMessage);

        String TAG = "AllotFirebase";

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        // Create Notification
        Intent intent = new Intent(this, RegisterActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410,
                intent, PendingIntent.FLAG_ONE_SHOT);

//        Notification notification = new NotificationCompat.Builder(this)
//                .setContentTitle("Word Game")
//                .setContentText(remoteMessage.getNotification().getBody())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .build();
//        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
//        manager.notify(1410, notification);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Allot")
                .setContentText(remoteMessage.getNotification().getBody())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(1410, notification);
    }
}
