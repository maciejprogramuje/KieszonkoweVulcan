package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.InternetUtils;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.JsInterfaceAlarm;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.MyAlarm;

import static commaciejprogramuje.facebook.kieszonkowevulcan.utils.MyAlarm.MY_ALARM_LOGIN_KEY;

public class GradesForAlarmActivity extends AppCompatActivity implements JsInterfaceAlarm.OnAlarmInteractionListener {
    public static final String LOGIN_ALARM_KEY = "loginAlarm";
    public static final String PASSWORD_ALARM_KEY = "passwordAlarm";
    @InjectView(R.id.alarm_browser)
    WebView alarmBrowser;

    String login = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_for_alarm);
        ButterKnife.inject(this);
        this.moveTaskToBack(true); // ewentualnie na czas testów wyłączyć, ale raczej ok


        login = getIntent().getStringExtra(MY_ALARM_LOGIN_KEY);
        password = getIntent().getStringExtra(MyAlarm.MY_ALARM_PASSWORD_KEY);

        if (login.equals("") || password.equals("")) {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            login = sharedPref.getString(LOGIN_ALARM_KEY, "");
            password = sharedPref.getString(PASSWORD_ALARM_KEY, "");
        }

        //Toast.makeText(this, "ALARM -> " + login + ", " + password, Toast.LENGTH_LONG).show();
        Log.w("UWAGA", "ALARM -> " + login + ", " + password);

        if (InternetUtils.isConnection(this)) {
            Toast.makeText(this, "ALARM -> Internet OK", Toast.LENGTH_LONG).show();

            alarmBrowser.getSettings().setJavaScriptEnabled(true);
            alarmBrowser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            alarmBrowser.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.w("UWAGA", "ALARM -> " + url);

                    if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index")
                            || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")) {
                        alarmBrowser.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
                    } else if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie")) {
                        alarmBrowser.loadUrl("javascript:window.ALARM_HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                    } else {
                        if (url.equals("https://uonetplus.vulcan.net.pl/lublin")
                                || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")
                                || url.equals("https://uonetplus.vulcan.net.pl/lublin/?logout=true")) {
                            alarmBrowser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
                        }
                    }

                    alarmBrowser.loadUrl("javascript: {" +
                            "document.getElementById('LoginName').value = '" + login + "';" +
                            "document.getElementById('Password').value = '" + password + "';" +
                            "document.getElementsByTagName('input')[2].click();" +
                            "};");
                }
            });


            // context ma być this, nie kombinuj...
            alarmBrowser.addJavascriptInterface(new JsInterfaceAlarm(this), "ALARM_HTMLOUT");
            alarmBrowser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
        } else {
            Toast.makeText(this, "ALARM -> BRAK Internetu", Toast.LENGTH_LONG).show();
            finishAndRemoveTask();
        }
    }


    @Override
    public void onAlarmInteraction(boolean alarmFlag) {
        if (alarmFlag) {
            Log.w("UWAGA", "ALARM -> plik zapisany, kończę i usuwam zadanie");
            finishAndRemoveTask();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        //Toast.makeText(getBaseContext(), "ALARM -> zapisuję " + login + ", " + password, Toast.LENGTH_LONG).show();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LOGIN_ALARM_KEY, login);
        editor.putString(PASSWORD_ALARM_KEY, password);
        editor.apply();
    }
}
