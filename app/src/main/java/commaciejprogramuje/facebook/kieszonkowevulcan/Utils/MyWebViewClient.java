package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by 5742ZGPC on 2017-10-07.
 */

public class MyWebViewClient extends WebViewClient {
    private WebView browser;
    private String login;
    private String password;
    private ProgressDialog progressDialog;
    private int loginPageShowIndex;
    private boolean problem;

    //public MyWebViewClient(WebView browser, String login, String password, ProgressDialog progressDialog) {
    public MyWebViewClient(WebView browser, String login, String password) {
        Log.w("UWAGA", "konstruktor MyWebViewClient");
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
            Log.w("UWAGA", "adres 3");
            browser.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
        } else if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie")) {
            gradesParsePage();
        } else {
            if (url.equals("https://uonetplus.vulcan.net.pl/lublin")
                    || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")
                    || url.equals("https://uonetplus.vulcan.net.pl/lublin/?logout=true")) {
                Log.w("UWAGA", "adres 4");
                browser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
            }
        }

        if(loginPageShowIndex > 0) {
            problem = false;

            browser.loadUrl("javascript: {" +
                    "document.getElementById('LoginName').value = '" + login + "';" +
                    "document.getElementById('Password').value = '" + password + "';" +
                    "document.getElementsByTagName('input')[2].click();" +
                    "};");

            Log.w("UWAGA", "adres 2, loginPageShowIndex=" + loginPageShowIndex);
            loginPageShowIndex--;
        } else {
            problem = true;
            //progressDialog.dismiss();
        }
    }

    private void gradesParsePage() {
        Log.w("UWAGA", "adres 5");
        browser.loadUrl("javascript:window.GRADES_HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.w("UWAGA", "LOADING: " + url);
        return false;
    }

    public boolean isProblem() {
        return problem;
    }
}
