package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.KIESZONKOWE_FILE;

public class Credentials {
    private final MainActivity mainActivity;

    private OnCredentialsCheckedListener onCredentialsCheckedListener;

    public Credentials(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        if (mainActivity != null) {
            onCredentialsCheckedListener = mainActivity;
        } else {
            throw new RuntimeException(mainActivity.toString()
                    + " must implement OnFragmentInteractionListener (MACIEJ 1)");
        }

    }

    public void delete() {
        MainActivity.setLogin("");
        MainActivity.setPassword("");

        DataFile.deleteFile(mainActivity.getApplicationContext(), KIESZONKOWE_FILE);

        CookieSyncManager.createInstance(mainActivity);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        SharedPreferences sharedPref = mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(MainActivity.LOGIN_DATA_KEY, "");
        editor.putString(MainActivity.PASSWORD_DATA_KEY, "");
        editor.apply();
    }

    public void checkCredentials() {
        Log.w("UWAGA", "rozpoczynam sprawdzanie");

        MainActivity.showProgressCircle();

        mainActivity.getBrowser().getSettings().setJavaScriptEnabled(true);
        mainActivity.getBrowser().getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mainActivity.getBrowser().setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.w("UWAGA", "FINISHED (login): " + url);

                if (url.equals("https://uonetplus.vulcan.net.pl/lublin")
                        || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")
                        || url.equals("https://uonetplus.vulcan.net.pl/lublin/?logout=true")) {
                    mainActivity.getBrowser().loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
                }

                mainActivity.getBrowser().loadUrl("javascript: {" +
                        "document.getElementById('LoginName').value = '" + MainActivity.getLogin() + "';" +
                        "document.getElementById('Password').value = '" + MainActivity.getPassword() + "';" +
                        "document.getElementsByTagName('input')[2].click();" +
                        "};");

                mainActivity.setLoginIndex(mainActivity.getLoginIndex() - 1);

                if (mainActivity.getLoginIndex() == 0) {
                    Log.w("UWAGA", "logowanie NIEudane!");
                    mainActivity.setLoginIndex(10);

                    onCredentialsCheckedListener.OnCredentialsCheckedInteraction(false);
                    mainActivity.hideProgressCircle();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index")
                        || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")) {
                    // udane logowanie
                    Log.w("UWAGA", "logowanie udane!");

                    mainActivity.hideProgressCircle();
                    mainActivity.getBrowser().stopLoading();
                    onCredentialsCheckedListener.OnCredentialsCheckedInteraction(true);
                }
                return false;
            }
        });
        mainActivity.getBrowser().loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");

    }

    public interface OnCredentialsCheckedListener {
        void OnCredentialsCheckedInteraction(boolean loginOk);
    }
}