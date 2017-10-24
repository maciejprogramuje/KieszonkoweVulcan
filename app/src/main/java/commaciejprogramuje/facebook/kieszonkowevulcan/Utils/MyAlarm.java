package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by m.szymczyk on 2017-10-24.
 */

public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NewGradeNotification.show(context, "\n\n\nMyAlarm\n\n\n");
    }
}
