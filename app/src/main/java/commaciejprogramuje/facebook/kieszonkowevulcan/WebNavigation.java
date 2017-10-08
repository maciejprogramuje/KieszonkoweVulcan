package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.support.design.widget.NavigationView;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by m.szymczyk on 2017-10-06.
 */

class WebNavigation {
    public static final String LOGIN_STRING = "e_szymczyk@orange.pl";
    public static final String PASSWORD_STRING = "Ulka!2002";

    private WebView webView;
    private TextView textView;
    private NavigationView navigationView;

    WebNavigation(WebView webView, TextView textView, NavigationView navigationView) {
        this.webView = webView;
        this.textView = textView;
        this.navigationView = navigationView;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    void navLoginAndDashboard() {
        webView.setWebViewClient(new MyWebViewClient(webView, textView, navigationView));
        webView.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
    }

    void navToPupilPanel() {
        Log.w("UWAGA", "navToPupilPanel");
        webView.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index/");
    }

    void navToNews() {
        Log.w("UWAGA", "navToNews");
        webView.loadUrl("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index");
    }
}
