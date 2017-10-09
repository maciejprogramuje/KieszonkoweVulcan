package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static commaciejprogramuje.facebook.kieszonkowevulcan.NavMenuButtonsTitle.*;


/**
 * Created by m.szymczyk on 2017-10-06.
 */

class WebNavigation {
    public static final String LOGIN_STRING = "e_szymczyk@orange.pl";
    public static final String PASSWORD_STRING = "Ulka!2002";

    private WebView webView;
    private TextView textView;
    private NavigationView navigationView;
    private Context context;
    private NavMenuButtonsTitle navMenuButtonsTitle;

    WebNavigation(WebView webView, TextView textView, NavigationView navigationView, Context context) {
        this.webView = webView;
        this.textView = textView;
        this.navigationView = navigationView;
        this.context = context;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new MyWebViewClient(this));
    }

    void navToLoginAndDashboard() {
        navMenuButtonsTitle = NEWS;
        webView.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
    }

    void navToPupilPanel(NavMenuButtonsTitle navMenuButtonsTitle) {
        this.navMenuButtonsTitle = navMenuButtonsTitle;
        webView.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index/");
    }

    void navToNews() {
        navMenuButtonsTitle = NEWS;
        webView.loadUrl("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index");
    }




    public NavMenuButtonsTitle getNavMenuButtonsTitle() {
        return navMenuButtonsTitle;
    }

    public void setNavMenuButtonsTitle(NavMenuButtonsTitle navMenuButtonsTitle) {
        this.navMenuButtonsTitle = navMenuButtonsTitle;
    }

    public WebView getWebView() {
        return webView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(String string) {
        textView.setText(string);
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
