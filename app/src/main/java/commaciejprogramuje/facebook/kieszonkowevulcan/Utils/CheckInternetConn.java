package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class CheckInternetConn {
    public CheckInternetConn() {
    }

    public boolean checkInternetConnection(Context context) {
        ConnectivityManager con_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected();
    }
}