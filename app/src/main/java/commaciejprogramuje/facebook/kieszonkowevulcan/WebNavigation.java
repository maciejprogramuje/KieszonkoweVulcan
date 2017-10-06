package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.LINK_1;
import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.LINK_1_A;
import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.LOGIN_STRING;
import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.PASSWORD_STRING;

/**
 * Created by m.szymczyk on 2017-10-06.
 */

class WebNavigation {
    private WebView webView;

    WebNavigation(WebView webView) {
        this.webView = webView;
        webView.getSettings().setJavaScriptEnabled(true);
    }

    void navToPupilGrades() {
        Log.w("UWAGA", "navToPupilGrades");
        webView.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
    }

    void navToPupilPanel() {
        Log.w("UWAGA", "navToPupilPanel");
        webView.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index/");
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

    void navToLoginPage() {
        Log.w("UWAGA", "navToLoginPage");
        webView.loadUrl(LINK_1);
    }
}
