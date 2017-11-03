package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesForAlarmActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

/**
 * Created by m.szymczyk on 2017-10-24.
 */

public class MyAlarm extends BroadcastReceiver {

    public static final String MY_ALARM_LOGIN_KEY = "myAlarmLogin";
    public static final String MY_ALARM_PASSWORD_KEY = "myAlarmPassword";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "ALARM -> onReceive", Toast.LENGTH_LONG).show();

        if(!MainActivity.isAlarmInProgress) {
            MainActivity.isAlarmInProgress = true;
            Intent getGradesIntent = new Intent(context, GradesForAlarmActivity.class);
            getGradesIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getGradesIntent.putExtra(MY_ALARM_LOGIN_KEY, intent.getStringExtra(MainActivity.ALARM_LOGIN_KEY));
            getGradesIntent.putExtra(MY_ALARM_PASSWORD_KEY, intent.getStringExtra(MainActivity.ALARM_PASSWORD_KEY));
            context.getApplicationContext().startActivity(getGradesIntent);
        }

        new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MainActivity.isAlarmInProgress = false;
            }
        };
    }
}
