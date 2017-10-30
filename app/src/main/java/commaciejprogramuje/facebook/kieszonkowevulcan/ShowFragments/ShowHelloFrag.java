package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import commaciejprogramuje.facebook.kieszonkowevulcan.HelloFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.MyAlarm;

import static android.content.Context.ALARM_SERVICE;

public class ShowHelloFrag {
    private final MainActivity mainActivity;

    public ShowHelloFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        HelloFragment helloFragment = HelloFragment.newInstance();
        mainActivity.replaceFrag.replace(mainActivity, helloFragment);
    }
}