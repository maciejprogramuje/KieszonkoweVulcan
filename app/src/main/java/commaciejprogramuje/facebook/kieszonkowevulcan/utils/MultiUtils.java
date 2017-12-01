package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import commaciejprogramuje.facebook.kieszonkowevulcan.HelloFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.R;

import static android.content.Context.ALARM_SERVICE;
import static android.support.v4.app.NotificationCompat.BADGE_ICON_SMALL;
import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.ALARM_INRETVAL_IN_GRADES_FOR_ALARM_ACTIVITY;
import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.getLogin;

/**
 * Created by 5742ZGPC on 2017-12-01.
 */

public class MultiUtils {
    public static final String FROM_NOTIFICATION_KEY = "fromNotification";

    public static void noInternetConnectionReaction(MainActivity mainActivity) {
        Toast.makeText(mainActivity, "Włącz internet i odśwież aplikację!", Toast.LENGTH_LONG).show();
        HelloFragment helloFragment = HelloFragment.newInstance(false);
        mainActivity.replaceFrag.replace(mainActivity, helloFragment);
    }

    public static boolean isInternetConnection(Context context) {
        ConnectivityManager con_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert con_manager != null;
        return con_manager.getActiveNetworkInfo() != null;
    }

    public static void showNotification(Context context, String message) {
        @SuppressWarnings("deprecation")
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);

        Intent intent = new Intent();
        intent.setClassName("commaciejprogramuje.facebook.kieszonkowevulcan", "commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity");
        intent.putExtra(FROM_NOTIFICATION_KEY, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        notification.setContentIntent(pendingIntent);
        notification.setSmallIcon(R.drawable.ic_notification_grade_badge);
        notification.setBadgeIconType(BADGE_ICON_SMALL);
        notification.setContentTitle("Dzienniczek Vulcan G16");
        notification.setContentText(message);
        notification.setAutoCancel(true);

        //Sound
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification.setSound(alarmSound);

        //Vibration
        //notification.setVibrate(new long[] { 500, 500, 500, 500, 500 });

        notification.setLights(Color.WHITE, 3000, 3000);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        //int index = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        int index = 1;
        notificationManager.notify(index, notification.build());
    }

    public static void callAlarm(Context context, String log, String pass) {
        Intent alarmIntent = new Intent();
        alarmIntent.setClassName("commaciejprogramuje.facebook.kieszonkowevulcan", "commaciejprogramuje.facebook.kieszonkowevulcan.utils.MyAlarm");
        alarmIntent.putExtra("loginMyAlarm", log);
        alarmIntent.putExtra("passwordMyAlarm", pass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_INRETVAL_IN_GRADES_FOR_ALARM_ACTIVITY, pendingIntent);

        // call alarm jest wywoływany 2x
        Log.w("UWAGA", "ALARM -> stworzyłem nowy alarm");
    }
}
