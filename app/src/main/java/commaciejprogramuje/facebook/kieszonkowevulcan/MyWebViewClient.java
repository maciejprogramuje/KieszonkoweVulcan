package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import static commaciejprogramuje.facebook.kieszonkowevulcan.WebNavigation.URL_LOGINENDPOINT;
import static commaciejprogramuje.facebook.kieszonkowevulcan.WebNavigation.LOGIN_STRING;
import static commaciejprogramuje.facebook.kieszonkowevulcan.WebNavigation.PASSWORD_STRING;

/**
 * Created by 5742ZGPC on 2017-10-07.
 */

public class MyWebViewClient extends WebViewClient {
    WebView webView;
    private TextView textView;

    MyWebViewClient(WebView webView, TextView textView) {
        this.webView = webView;
        this.textView = textView;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        webView.loadUrl("javascript: {" +
                "document.getElementById('LoginName').value = '" + LOGIN_STRING + "';" +
                "document.getElementById('Password').value = '" + PASSWORD_STRING + "';" +
                "document.getElementsByTagName('input')[2].click();" +
                "};");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        String accurateUrl = Uri.parse(url).getHost() + Uri.parse(url).getEncodedPath() + Uri.parse(url).getEncodedQuery();

        Log.w("UWAGA", accurateUrl);
        textView.setText(accurateUrl);

        if(accurateUrl.equals("uonetplus.vulcan.net.pl/lublin/logout=true")) {
            Log.w("UWAGA", "próbuję");
            webView.loadUrl(URL_LOGINENDPOINT);
        }


        return false;

        // Returning true means that you need to handle what to do with the url, e.g. open web page in a Browser
        // Returning false means that you are going to load this url in the webView itself
    }



}
