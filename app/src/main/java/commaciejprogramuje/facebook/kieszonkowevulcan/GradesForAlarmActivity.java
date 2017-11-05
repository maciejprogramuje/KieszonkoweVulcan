package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.NewGradeNotification;

import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.LOGIN_GRADES_ALARM_KEY;
import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.PASSWORD_GRADES_ALARM_KEY;

public class GradesForAlarmActivity extends AppCompatActivity implements JsInterfaceAlarm.OnAlarmInteractionListener {
    public static final String MY_ALARM_LOGIN_KEY = "myAlarmLogin";
    public static final String MY_ALARM_PASSWORD_KEY = "myAlarmPassword";
    public static final String LOGIN_ALARM_KEY = "loginAlarm";
    public static final String PASSWORD_ALARM_KEY = "passwordAlarm";
    public static final String LOGIN_GRADES_KEY = "loginGrades";
    public static final String PASSWORD_GRADES_KEY = "passwordGrades";
    @InjectView(R.id.alarm_browser)
    WebView alarmBrowser;

    String login = "";
    String password = "";
    long alarmInretvalInGradesForAlarmActivity = 1000 * 60 * 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_for_alarm);
        ButterKnife.inject(this);
        this.moveTaskToBack(true); // ewentualnie na czas testów wyłączyć, ale raczej ok

        Log.w("UWAGA", "context: GradesForAlarmActivity");

        setResult(222);

        login = getIntent().getStringExtra(LOGIN_GRADES_ALARM_KEY);
        password = getIntent().getStringExtra(PASSWORD_GRADES_ALARM_KEY);

        Log.w("UWAGA", "ALARM -> 1. " + login + ", " + password);

        /*if (login.equals("") || password.equals("")) {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            login = sharedPref.getString(LOGIN_GRADES_KEY, "");
            password = sharedPref.getString(PASSWORD_GRADES_KEY, "");
        }*/

        login = "e_szymczyk@orange.pl";
        password = "Ulka!2002";

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LOGIN_GRADES_KEY, login);
        editor.putString(PASSWORD_GRADES_KEY, password);
        editor.apply();

        //Toast.makeText(this, "ALARM -> " + login + ", " + password, Toast.LENGTH_LONG).show();
        Log.w("UWAGA", "ALARM -> 2. " + login + ", " + password);

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
            NewGradeNotification.show(this, "ALARM -> brak internetu");

            if (android.os.Build.VERSION.SDK_INT >= 23) {
                Log.w("UWAGA", "wykonanie ALARM dla 6.0, brak internetu");

                Intent alarmIntent = new Intent();
                alarmIntent.setClassName("commaciejprogramuje.facebook.kieszonkowevulcan", "commaciejprogramuje.facebook.kieszonkowevulcan.utils.MyAlarm");
                alarmIntent.putExtra(MY_ALARM_LOGIN_KEY, login);
                alarmIntent.putExtra(MY_ALARM_PASSWORD_KEY, password);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 222, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    Log.w("UWAGA", "tworzę ALARM dla 6.0");
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + alarmInretvalInGradesForAlarmActivity, pendingIntent);
                } else {
                    Log.w("UWAGA", "tworzę ALARM z interwałem " + alarmInretvalInGradesForAlarmActivity);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmInretvalInGradesForAlarmActivity, pendingIntent);
                }
            }
            finishAndRemoveTask();
        }
    }


    @Override
    public void onAlarmInteraction(boolean alarmFlag) {
        if (alarmFlag) {
            Log.w("UWAGA", "ALARM -> plik zapisany, kończę i usuwam zadanie");
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                Log.w("UWAGA", "wykonanie ALARM dla 6.0, jest interent");

                Intent alarmIntent = new Intent(this, MyAlarm.class);
                alarmIntent.putExtra(MY_ALARM_LOGIN_KEY, login);
                alarmIntent.putExtra(MY_ALARM_PASSWORD_KEY, password);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 222, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    Log.w("UWAGA", "tworzę ALARM dla 6.0");
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + alarmInretvalInGradesForAlarmActivity, pendingIntent);
                } else {
                    Log.w("UWAGA", "tworzę ALARM z interwałem " + alarmInretvalInGradesForAlarmActivity);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmInretvalInGradesForAlarmActivity, pendingIntent);
                }
            }
            finishAndRemoveTask();
        }
    }

}
