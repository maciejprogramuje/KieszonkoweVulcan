package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesForAlarmActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

import static commaciejprogramuje.facebook.kieszonkowevulcan.GradesForAlarmActivity.MY_ALARM_LOGIN_KEY;
import static commaciejprogramuje.facebook.kieszonkowevulcan.GradesForAlarmActivity.MY_ALARM_PASSWORD_KEY;

/**
 * Created by m.szymczyk on 2017-10-24.
 */

public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("UWAGA", "ALARM -> onReceive");

        Intent getGradesIntent = new Intent();
        getGradesIntent.setClassName("commaciejprogramuje.facebook.kieszonkowevulcan", "commaciejprogramuje.facebook.kieszonkowevulcan.GradesForAlarmActivity");
        getGradesIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getGradesIntent.putExtra(MY_ALARM_LOGIN_KEY, intent.getStringExtra(MY_ALARM_LOGIN_KEY));
        getGradesIntent.putExtra(MY_ALARM_PASSWORD_KEY, intent.getStringExtra(MY_ALARM_PASSWORD_KEY));
        context.getApplicationContext().startActivity(getGradesIntent);

        /*if(!MainActivity.isAlarmInProgress) {
            MainActivity.isAlarmInProgress = true;
            Intent getGradesIntent = new Intent(context, GradesForAlarmActivity.class);
            getGradesIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getGradesIntent.putExtra(MY_ALARM_LOGIN_KEY, intent.getStringExtra(CallMyAlarm.ALARM_LOGIN_KEY));
            getGradesIntent.putExtra(MY_ALARM_PASSWORD_KEY, intent.getStringExtra(CallMyAlarm.ALARM_PASSWORD_KEY));
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
        };*/
    }
}
