package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesFromPageActivity;

/**
 * Created by m.szymczyk on 2017-10-24.
 */

public class MyAlarm extends BroadcastReceiver {
    public static final String BROADCAST_FROM_ALARM_KEY = "broadcastFromAlarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent getGradesIntent = new Intent(context, GradesFromPageActivity.class);
        getGradesIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BROADCAST_FROM_ALARM_KEY, true);
        context.startActivity(getGradesIntent);
    }
}
