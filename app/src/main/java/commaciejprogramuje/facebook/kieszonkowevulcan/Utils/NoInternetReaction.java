package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.widget.Toast;

import commaciejprogramuje.facebook.kieszonkowevulcan.HelloFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

public class NoInternetReaction {
    public NoInternetReaction() {
    }

    public void noInternetReaction(MainActivity mainActivity) {
        Toast.makeText(mainActivity, "Włącz internet!", Toast.LENGTH_LONG).show();
        HelloFragment helloFragment = HelloFragment.newInstance();
        mainActivity.replaceFrag.replace(mainActivity, helloFragment);
    }
}