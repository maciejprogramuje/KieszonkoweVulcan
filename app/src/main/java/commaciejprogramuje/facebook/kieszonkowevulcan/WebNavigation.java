package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by m.szymczyk on 2017-10-06.
 */

class WebNavigation {
    public static final String URL_LOGINENDPOINT = "https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx";
    public static final String LOGIN_STRING = "e_szymczyk@orange.pl";
    public static final String PASSWORD_STRING = "Ulka!2002";

    private WebView webView;
    private TextView textView;

    WebNavigation(WebView webView, TextView textView) {
        this.webView = webView;
        this.textView = textView;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    void navToLoginPage() {
        webView.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
    }

    void navToDashboard() {
        Log.w("UWAGA", "navToDashboard");
        webView.setWebViewClient(new MyWebViewClient(webView, textView));
    }

    void navToPupilPanel() {
        Log.w("UWAGA", "navToPupilPanel");
        webView.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index/");
    }

    void navToPupilGrades() {
        Log.w("UWAGA", "navToPupilGrades");

        //webView.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
        //webView.loadUrl("javascript:document.getElementsByClassName('a')[14].onclick();");

    }

    void navToNews() {
        Log.w("UWAGA", "navToNews");
        webView.loadUrl("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index");
    }
}
