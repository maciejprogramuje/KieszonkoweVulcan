package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.net.URL;

import static commaciejprogramuje.facebook.kieszonkowevulcan.WebNavigation.LOGIN_STRING;
import static commaciejprogramuje.facebook.kieszonkowevulcan.WebNavigation.PASSWORD_STRING;

/**
 * Created by 5742ZGPC on 2017-10-07.
 */

public class MyWebViewClient extends WebViewClient {
    private WebView webView;
    private TextView textView;
    private NavigationView navigationView;

    MyWebViewClient(WebView webView, TextView textView, NavigationView navigationView) {
        this.webView = webView;
        this.textView = textView;
        this.navigationView = navigationView;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        textView.setText(String.format("FINISHED: %s", url));
        // do Oceny
        if(url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index/")) {
            webView.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
        } else {
            tryLoginToPage(url);
        }

        webView.loadUrl("javascript: {" +
                "document.getElementById('LoginName').value = '" + LOGIN_STRING + "';" +
                "document.getElementById('Password').value = '" + PASSWORD_STRING + "';" +
                "document.getElementsByTagName('input')[2].click();" +
                "};");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.w("UWAGA", url);
        textView.setText(String.format("LOADING: %s", url));

        return false;
        // Returning true means that you need to handle what to do with the url, e.g. open web page in a Browser
        // Returning false means that you are going to load this url in the webView itself
    }

    private void tryLoginToPage(String  url) {
        if (url.equals("https://uonetplus.vulcan.net.pl/lublin/")
                || url.equals("https://uonetplus.vulcan.net.pl/lublin/?logout=true")) {
            Log.w("UWAGA", "================== LOGOWANIE ==================");
            webView.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
            navigationView.setCheckedItem(R.id.nav_news);
        }
    }
}
