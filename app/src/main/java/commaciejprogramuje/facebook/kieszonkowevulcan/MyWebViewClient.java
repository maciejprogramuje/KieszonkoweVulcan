package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.LOGIN_STRING;
import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.PASSWORD_STRING;

/**
 * Created by 5742ZGPC on 2017-10-07.
 */

class MyWebViewClient extends WebViewClient {
    private WebView webView;

    MyWebViewClient(WebView webView) {
        Log.w("UWAGA", "konstruktor MyWebViewClient");
        this.webView = webView;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.w("UWAGA", "FINISHED: " + url);
        if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index/")
                || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")) {
            webView.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
        } else if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie")) {
            gradesParsePage();
        } else {
            if (url.equals("https://uonetplus.vulcan.net.pl/lublin/")
                    || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")
                    || url.equals("https://uonetplus.vulcan.net.pl/lublin/?logout=true")) {
                webView.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
            }
        }
        webView.loadUrl("javascript: {" +
                "document.getElementById('LoginName').value = '" + LOGIN_STRING + "';" +
                "document.getElementById('Password').value = '" + PASSWORD_STRING + "';" +
                "document.getElementsByTagName('input')[2].click();" +
                "};");

    }

    private void gradesParsePage() {
        webView.loadUrl("javascript:window.GRADES_HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.w("UWAGA", "LOADING: " + url);
        //mainActivity.setTextViewByString(String.format("LOADING: %s", url));

        return false;
        // Returning true means that you need to handle what to do with the url, e.g. open web page in a Browser
        // Returning false means that you are going to load this url in the webView itself
    }
}
