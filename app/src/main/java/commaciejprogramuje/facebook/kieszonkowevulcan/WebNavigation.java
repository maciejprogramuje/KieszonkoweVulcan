package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


/**
 * Created by m.szymczyk on 2017-10-06.
 */

class WebNavigation {
    private static final String LINK_LOGINENDPOINT = "https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx";
    private static final String LOGIN_STRING = "e_szymczyk@orange.pl";
    private static final String PASSWORD_STRING = "Ulka!2002";

    private WebView webView;
    private TextView textView;

    WebNavigation(WebView webView, TextView textView) {
        this.webView = webView;
        this.textView = textView;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
    }

    void navToLoginPage() {
        webView.loadUrl(LINK_LOGINENDPOINT);
    }

    void navToDashboard() {
        Log.w("UWAGA", "navToDashboard");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript: {" +
                        "document.getElementById('LoginName').value = '" + LOGIN_STRING + "';" +
                        "document.getElementById('Password').value = '" + PASSWORD_STRING + "';" +
                        "document.getElementsByTagName('input')[2].click();" +
                        "};");
            }
        });
    }

    void navToPupilPanel() {
        Log.w("UWAGA", "navToPupilPanel");
        webView.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index/");
    }

    void navToPupilGrades() {
        Log.w("UWAGA", "navToPupilGrades");
        webView.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
    }
}
