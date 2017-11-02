package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.JsInterfaceGrades;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.InternetUtils;

public class GradesForMainActivity extends AppCompatActivity implements JsInterfaceGrades.OnGradesMainInteractionListener {
    public static final String NOT_RELOAD_GRADES_KEY = "notReloadGrades";

    @InjectView(R.id.browser)
    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_grades_from_page);
        ButterKnife.inject(this);

        this.moveTaskToBack(true);

        //Toast.makeText(MainActivity.getMainActivity(), "wezwanie z MainActivity", Toast.LENGTH_LONG).show();

        if (InternetUtils.isConnection(this)) {
            browser.getSettings().setJavaScriptEnabled(true);
            browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            browser.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.w("UWAGA", "FINISHED: " + url);

                    if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index")
                            || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")) {
                        browser.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
                    } else if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie")) {
                        browser.loadUrl("javascript:window.MAIN_HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
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

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.w("UWAGA", "LOADING: " + url);
                    return false;
                }
            });
            browser.addJavascriptInterface(new JsInterfaceGrades(GradesForMainActivity.this), "MAIN_HTMLOUT");
            browser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
        } else {
            this.finishAndRemoveTask();
        }
    }

    @Override
    public void onGradesMainInteraction(boolean fileFlag) {
        if (fileFlag) {
            Log.w("UWAGA", "plik zapisany, kończę i usuwam zadanie");
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            mainActivityIntent.putExtra(NOT_RELOAD_GRADES_KEY, true);
            //mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainActivityIntent);
        }
    }
}
