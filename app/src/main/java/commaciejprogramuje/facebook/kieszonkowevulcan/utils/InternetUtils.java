package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import commaciejprogramuje.facebook.kieszonkowevulcan.HelloFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

/**
 * Created by m.szymczyk on 2017-10-24.
 */

public class InternetUtils {
    public static void noConnectionReaction(MainActivity mainActivity) {
        Toast.makeText(mainActivity, "Włącz internet!", Toast.LENGTH_LONG).show();
        HelloFragment helloFragment = HelloFragment.newInstance();
        mainActivity.replaceFrag.replace(mainActivity, helloFragment);
    }

    public static boolean isConnection(Context context) {
        ConnectivityManager con_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected();
    }
}
