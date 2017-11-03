package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import java.net.InetAddress;

import commaciejprogramuje.facebook.kieszonkowevulcan.HelloFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

/**
 * Created by m.szymczyk on 2017-10-24.
 */

public class InternetUtils {
    public static void noConnectionReaction(MainActivity mainActivity) {
        Toast.makeText(mainActivity, "Włącz internet i odśwież aplikację!", Toast.LENGTH_LONG).show();
        HelloFragment helloFragment = HelloFragment.newInstance(false);
        mainActivity.replaceFrag.replace(mainActivity, helloFragment);
    }

    public static boolean isConnection(Context context) {
        ConnectivityManager con_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return con_manager.getActiveNetworkInfo() != null && isInternetAvailable();
    }

    private static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }
}
