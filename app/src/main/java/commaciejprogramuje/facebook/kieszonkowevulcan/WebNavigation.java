package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static commaciejprogramuje.facebook.kieszonkowevulcan.NavMenuButtonsTitle.*;


/**
 * Created by m.szymczyk on 2017-10-06.
 */

class WebNavigation {
    public static final String LOGIN_STRING = "e_szymczyk@orange.pl";
    public static final String PASSWORD_STRING = "Ulka!2002";

    private MainActivity mainActivity;

    WebNavigation(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mainActivity.getWebView().getSettings().setJavaScriptEnabled(true);
        mainActivity.getWebView().getSettings().setDomStorageEnabled(true);
        mainActivity.getWebView().getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mainActivity.getWebView().setWebViewClient(new MyWebViewClient(mainActivity));
    }

    void navToLoginAndDashboard() {
        mainActivity.getWebView().loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
    }

    void navToPupilPanel() {
        mainActivity.getWebView().loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index/");
    }

    void navToNews() {
        mainActivity.getWebView().loadUrl("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index");
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }
}
