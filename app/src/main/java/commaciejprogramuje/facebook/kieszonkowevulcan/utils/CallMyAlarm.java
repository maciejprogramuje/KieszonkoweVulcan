package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesForAlarmActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

import static android.content.Context.ALARM_SERVICE;
import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.alarmInterval;

/**
 * Created by 5742ZGPC on 2017-11-04.
 */

public class CallMyAlarm {
    public static final String ALARM_LOGIN_KEY = "alarmLogin";
    public static final String ALARM_PASSWORD_KEY = "alarmPassword";

    public static void generateNew(Context context, String login, String password) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        Log.w("UWAGA", "context: " + context);
        alarmManager.cancel(getStaticPendingIntent(context, login, password));

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            Log.w("UWAGA", "tworzę ALARM dla 6.0");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.MINUTE, alarmInterval);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), getStaticPendingIntent(context, login, password));
        } else {
            Log.w("UWAGA", "tworzę ALARM z interwałem " + alarmInterval);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmInterval, getStaticPendingIntent(context, login, password));
        }
    }

    public static void delete(Context context, String login, String password) {
        Log.w("UWAGA", "kasuję ALARM");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(getStaticPendingIntent(context, login, password));
    }

    private static PendingIntent getStaticPendingIntent(Context context, String login, String password) {
        Intent alarmIntent = new Intent(context, MyAlarm.class);
        alarmIntent.putExtra(ALARM_LOGIN_KEY, login);
        alarmIntent.putExtra(ALARM_PASSWORD_KEY, password);
        //return PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //return PendingIntent.getBroadcast(context, 0, alarmIntent, 0); // było ok chyba
    }
}
