package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAlarm extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("UWAGA", "ALARM -> onReceive");

        Intent getGradesIntent = new Intent();
        getGradesIntent.setClassName("commaciejprogramuje.facebook.kieszonkowevulcan", "commaciejprogramuje.facebook.kieszonkowevulcan.GradesForAlarmActivity");
        getGradesIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getGradesIntent.putExtra("login", intent.getStringExtra("loginMyAlarm"));
        getGradesIntent.putExtra("password", intent.getStringExtra("passwordMyAlarm"));

        context.startActivity(getGradesIntent);
    }
}
