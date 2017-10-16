package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by 5742ZGPC on 2017-10-07.
 */

class MyWebViewClientGrades extends WebViewClient {
    private WebView browser;
    private String login;
    private String password;
    private ProgressDialog progressDialog;
    private int loginPageShowIndex;
    private boolean problem;

    MyWebViewClientGrades(WebView browser, String login, String password, ProgressDialog progressDialog) {
        Log.w("UWAGA", "konstruktor MyWebViewClientGrades");
        this.browser = browser;
        this.login = login;
        this.password = password;
        this.progressDialog = progressDialog;
        loginPageShowIndex = 10;
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

        if(loginPageShowIndex > 0) {
            Log.w("UWAGA", "adres 2, loginPageShowIndex=" + loginPageShowIndex);

            problem = false;
            browser.loadUrl("javascript: {" +
                    "document.getElementById('LoginName').value = '" + login + "';" +
                    "document.getElementById('Password').value = '" + password + "';" +
                    "document.getElementsByTagName('input')[2].click();" +
                    "};"
            );
            loginPageShowIndex--;
        } else {
            problem = true;
            progressDialog.dismiss();
        }
    }

    private void gradesParsePage() {
        browser.loadUrl("javascript:window.GRADES_HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.w("UWAGA", "LOADING: " + url);
        //mainActivity.setTextViewByString(String.format("LOADING: %s", url));

        return false;
        // Returning true means that you need to handle what to do with the url, e.g. open web page in a Browser
        // Returning false means that you are going to load this url in the webView itself
    }

    public boolean isProblem() {
        return problem;
    }
}
