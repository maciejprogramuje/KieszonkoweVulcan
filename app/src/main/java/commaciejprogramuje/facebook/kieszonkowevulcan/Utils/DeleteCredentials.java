package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

public class DeleteCredentials {
    private final MainActivity mainActivity;

    public DeleteCredentials(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void delete() {
        Log.w("UWAGA", "czyszczenie danych");

        mainActivity.setLogin("");
        mainActivity.setPassword("");

        CookieSyncManager.createInstance(mainActivity);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        SharedPreferences sharedPref = mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(MainActivity.LOGIN_DATA_KEY, "");
        editor.putString(MainActivity.PASSWORD_DATA_KEY, "");
        editor.apply();

        mainActivity.showLoginFrag.showLoginFragment();
    }
}