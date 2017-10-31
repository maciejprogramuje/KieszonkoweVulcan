package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.JsInterfaceAlarm;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.InternetUtils;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.MyAlarm;

import static commaciejprogramuje.facebook.kieszonkowevulcan.utils.MyAlarm.MY_ALARM_LOGIN_KEY;

public class GradesForAlarmActivity extends AppCompatActivity implements JsInterfaceAlarm.OnAlarmInteractionListener {
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

        Toast.makeText(MainActivity.getMainActivity(), "wezwanie z GradesForAlarmActivity", Toast.LENGTH_LONG).show();

        if (InternetUtils.isConnection(this)) {
            alarmBrowser.getSettings().setJavaScriptEnabled(true);
            alarmBrowser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            alarmBrowser.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    Toast.makeText(getBaseContext(), "ALARM -> page finished", Toast.LENGTH_LONG).show();
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
            alarmBrowser.addJavascriptInterface(new JsInterfaceAlarm(GradesForAlarmActivity.this), "ALARM_HTMLOUT");
            alarmBrowser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
        } else {
            Toast.makeText(getBaseContext(), "ALARM -> usuwam 1", Toast.LENGTH_LONG).show();
            this.finishAndRemoveTask();
        }
    }

    @Override
    public void onAlarmInteraction(boolean alarmFlag) {
        if (alarmFlag) {
            Log.w("UWAGA", "ALARM -> plik zapisany, kończę i usuwam zadanie");
            Toast.makeText(getBaseContext(), "ALARM -> plik zapisany, kończę i usuwam zadanie", Toast.LENGTH_LONG).show();
            this.finishAndRemoveTask();
        }
    }
}
