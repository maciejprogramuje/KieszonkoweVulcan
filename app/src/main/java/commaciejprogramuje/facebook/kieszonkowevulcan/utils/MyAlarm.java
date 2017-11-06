package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

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
        getGradesIntent.putExtra("login", intent.getStringExtra("loginMyAlarm"));
        getGradesIntent.putExtra("password", intent.getStringExtra("passwordMyAlarm"));

        if (Build.VERSION.SDK_INT >= 23) {
            getGradesIntent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
        }

        context.getApplicationContext().startActivity(getGradesIntent);
    }
}
