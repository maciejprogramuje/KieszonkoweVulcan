package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesFromPageActivity;

/**
 * Created by m.szymczyk on 2017-10-24.
 */

public class MyAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent getGradesIntent = new Intent(context, GradesFromPageActivity.class);
        getGradesIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(getGradesIntent);
    }
}
