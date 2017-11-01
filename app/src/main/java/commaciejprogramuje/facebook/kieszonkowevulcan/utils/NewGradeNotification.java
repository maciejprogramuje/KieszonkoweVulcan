package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.R;

/**
 * Created by m.szymczyk on 2017-10-24.
 */

public class NewGradeNotification {
    private static int index = 1;

    public static final String FROM_NOTIFICATION_KEY = "fromNotification";

    public static void show(Context context, String message) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(FROM_NOTIFICATION_KEY, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setContentIntent(pendingIntent);
        notification.setSmallIcon(R.drawable.ic_notification_grade);
        notification.setContentTitle("Dzienniczek Vulcan G16");
        notification.setContentText(message);
        notification.setAutoCancel(true);

        //Sound
        //Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //notification.setSound(alarmSound);

        //Vibration
        //notification.setVibrate(new long[] { 500, 500, 500, 500, 500 });

        notification.setLights(Color.WHITE, 3000, 3000);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(index, notification.build());
        //index++;
    }
}
