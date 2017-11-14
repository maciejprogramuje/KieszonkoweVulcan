package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.InternetUtils;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.JsInterfaceAlarm;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.MyAlarm;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.NewGradeNotification;

import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.alarmInretvalInGradesForAlarmActivity;

public class GradesForAlarmActivity extends AppCompatActivity implements JsInterfaceAlarm.OnAlarmInteractionListener {
    @InjectView(R.id.alarm_browser)
    WebView alarmBrowser;

    String login = "";
    String password = "";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_for_alarm);
        ButterKnife.inject(this);
        this.moveTaskToBack(true); // ewentualnie na czas testów wyłączyć, ale raczej ok

        Log.w("UWAGA", "context: GradesForAlarmActivity");

        login = getIntent().getStringExtra("login");
        password = getIntent().getStringExtra("password");

        if (login != null || password != null) {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("loginGrades", login);
            editor.putString("passwordGrades", password);
            editor.apply();
        } else {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            login = sharedPref.getString("loginGrades", "");
            password = sharedPref.getString("passwordGrades", "");
        }

        Log.w("UWAGA", "ALARM -> 2. " + login + ", " + password);
        //NewGradeNotification.show(this,"ALARM -> 2. " + login + ", " + password);

        if (InternetUtils.isConnection(this)) {
            alarmBrowser.getSettings().setJavaScriptEnabled(true);
            alarmBrowser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            alarmBrowser.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.w("UWAGA", "ALARM -> " + url);

                    switch (url) {
                        case "https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index":
                        case "https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index":
                            alarmBrowser.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
                            break;
                        case "https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie":
                            alarmBrowser.loadUrl("javascript:window.ALARM_HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                            break;
                        default:
                            if (url.equals("https://uonetplus.vulcan.net.pl/lublin")
                                    || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")
                                    || url.equals("https://uonetplus.vulcan.net.pl/lublin/?logout=true")) {
                                alarmBrowser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
                            }
                            break;
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
            callAlarm();
            finish();
        } else {
            //NewGradeNotification.show(this, "ALARM -> brak internetu");
            Log.w("UWAGA", "ALARM -> brak internetu");
            callAlarm();
            finish();
        }
    }

    // problemem jest niezamykanie okna przeglądarki, po nieudanym logowaniu? pobieraniu danych?


    @Override
    protected void onPause() {
        Log.w("UWAGA", "ALARM -> onPause");
        callAlarm();
        finish();
        super.onPause();
    }

    @Override
    public void onAlarmInteraction(boolean alarmFlag) {
        if (alarmFlag) {
            Log.w("UWAGA", "ALARM -> plik zapisany, kończę i usuwam zadanie");
            callAlarm();
            finish();
        }
    }

    private void callAlarm() {
        Intent alarmIntent = new Intent();
        alarmIntent.setClassName("commaciejprogramuje.facebook.kieszonkowevulcan", "commaciejprogramuje.facebook.kieszonkowevulcan.utils.MyAlarm");
        alarmIntent.putExtra("loginMyAlarm", login);
        alarmIntent.putExtra("passwordMyAlarm", password);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + alarmInretvalInGradesForAlarmActivity, pendingIntent);

        // call alarm jest wywoływany 2x
        Log.w("UWAGA", "ALARM -> stworzyłem nowy alarm");
    }

}
