package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.R;

/**
 * Created by m.szymczyk on 2017-10-24.
 */

public class NewGradeNotification {
    public static final String FROM_NOTIFICATION_KEY = "fromNotification";

    public static void show(Context context, String message) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(FROM_NOTIFICATION_KEY, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_notification_grade);
        mBuilder.setContentTitle("Dzienniczek Vulcan G16");
        mBuilder.setContentText(message);
        mBuilder.setAutoCancel(true);

        //Vibration
        mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        //LED
        mBuilder.setLights(Color.WHITE, 3000, 3000);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //NotificationManager
        notificationManager.notify(001, mBuilder.build());
    }
}
