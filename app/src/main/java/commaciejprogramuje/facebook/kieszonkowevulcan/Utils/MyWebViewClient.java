package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

/**
 * Created by 5742ZGPC on 2017-10-07.
 */

public class MyWebViewClient extends WebViewClient {
    private WebView browser;

    public MyWebViewClient(WebView browser) {
        this.browser = browser;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.w("UWAGA", "FINISHED: " + url);

        if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index")
                || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")) {
            browser.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
        } else if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie")) {
            gradesParsePage();
        } else {
            if (url.equals("https://uonetplus.vulcan.net.pl/lublin")
                    || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")
                    || url.equals("https://uonetplus.vulcan.net.pl/lublin/?logout=true")) {
                browser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
            }
        }

        browser.loadUrl("javascript: {" +
                "document.getElementById('LoginName').value = '" + MainActivity.getLogin() + "';" +
                "document.getElementById('Password').value = '" + MainActivity.getPassword() + "';" +
                "document.getElementsByTagName('input')[2].click();" +
                "};");

    }

    private void gradesParsePage() {
        browser.loadUrl("javascript:window.GRADES_HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.w("UWAGA", "LOADING: " + url);
        return false;
    }
}
