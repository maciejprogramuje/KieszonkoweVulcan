package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static commaciejprogramuje.facebook.kieszonkowevulcan.NavMenuButtonsTitle.ATTENDING;
import static commaciejprogramuje.facebook.kieszonkowevulcan.NavMenuButtonsTitle.GRADES;
import static commaciejprogramuje.facebook.kieszonkowevulcan.NavMenuButtonsTitle.MONEY;
import static commaciejprogramuje.facebook.kieszonkowevulcan.WebNavigation.LOGIN_STRING;
import static commaciejprogramuje.facebook.kieszonkowevulcan.WebNavigation.PASSWORD_STRING;

/**
 * Created by 5742ZGPC on 2017-10-07.
 */

class MyWebViewClient extends WebViewClient {
    private WebNavigation webNavigation;

    MyWebViewClient(WebNavigation webNavigation) {
        this.webNavigation = webNavigation;

        webNavigation.getWebView().addJavascriptInterface(new GradesJavaScriptInterface(), "GRADES_HTMLOUT");
        webNavigation.getWebView().addJavascriptInterface(new attendingJavaScriptInterface(), "ATTENDING_HTMLOUT");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        webNavigation.setTextView(String.format("FINISHED: %s", url));
        // links to pages
        if (webNavigation.getNavMenuButtonsTitle().equals(GRADES)
                && url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index/")) {
            webNavigation.getWebView().loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
        } else if (webNavigation.getNavMenuButtonsTitle().equals(MONEY)
                && url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index/")) {
            webNavigation.getWebView().loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
        } else if (webNavigation.getNavMenuButtonsTitle().equals(ATTENDING)
                && url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index/")) {
            webNavigation.getWebView().loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Frekwencja.mvc");
        // parse pages
        } else if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie")) {
            System.out.println("===================== PARSING GRADES ======================");
            gradesParsePage();
        } else if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Frekwencja.mvc")) {
            System.out.println("===================== PARSING ATTENDING ===================");
            attendingParsePage();
        } else {
            getLoadingPage(url);
        }

        fillLoginForm();
    }

    private void gradesParsePage() {
        webNavigation.getWebView().loadUrl("javascript:window.GRADES_HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
    }

    private void attendingParsePage() {
        webNavigation.getWebView().loadUrl("javascript:window.ATTENDING_HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
    }


    /* An instance of this class will be registered as a JavaScript interface */
    private class attendingJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            System.out.println(html);
        }
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.w("UWAGA", url);
        webNavigation.setTextView(String.format("LOADING: %s", url));

        return false;
        // Returning true means that you need to handle what to do with the url, e.g. open web page in a Browser
        // Returning false means that you are going to load this url in the webView itself
    }

    private void getLoadingPage(String url) {
        if (url.equals("https://uonetplus.vulcan.net.pl/lublin/")
                || url.equals("https://uonetplus.vulcan.net.pl/lublin/?logout=true")
                || url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Default.aspx")) {
            Log.w("UWAGA", "================== LOGOWANIE ==================");
            webNavigation.getWebView().loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
            webNavigation.getNavigationView().setCheckedItem(R.id.nav_news);
        }
    }

    private void fillLoginForm() {
        webNavigation.getWebView().loadUrl("javascript: {" +
                "document.getElementById('LoginName').value = '" + LOGIN_STRING + "';" +
                "document.getElementById('Password').value = '" + PASSWORD_STRING + "';" +
                "document.getElementsByTagName('input')[2].click();" +
                "};");
    }


}
