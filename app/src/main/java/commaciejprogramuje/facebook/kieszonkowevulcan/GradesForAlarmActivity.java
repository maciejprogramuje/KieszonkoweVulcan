package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.JsInterfaceAlarm;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.MultiUtils;

public class GradesForAlarmActivity extends Activity implements JsInterfaceAlarm.OnAlarmInteractionListener {
    @InjectView(R.id.alarm_browser)
    WebView alarmBrowser;

    String login = "";
    String password = "";
    boolean semestrFlag = false;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_grades_for_alarm);
        ButterKnife.inject(this);
        this.moveTaskToBack(true); // ewentualnie na czas testów wyłączyć, ale raczej ok

        Log.w("UWAGA", "context: GradesForAlarmActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();

        login = getIntent().getStringExtra("login");
        password = getIntent().getStringExtra("password");
        semestrFlag = getIntent().getBooleanExtra("semestrFlag", true);

        if (login != null || password != null) {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("loginGrades", login);
            editor.putString("passwordGrades", password);
            editor.putBoolean("semestrFlag", semestrFlag);
            editor.apply();
        } else {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            login = sharedPref.getString("loginGrades", "");
            password = sharedPref.getString("passwordGrades", "");
            semestrFlag = sharedPref.getBoolean("semestrFlag", true);
        }

        Log.w("UWAGA", "ALARM -> 2. " + login + ", " + password);
        //NewGradeNotification.showNotification(this,"ALARM -> 2. " + login + ", " + password);

        if (MultiUtils.isInternetConnection(this)) {
            alarmBrowser.getSettings().setJavaScriptEnabled(true);
            alarmBrowser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            alarmBrowser.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.w("UWAGA", "ALARM -> " + url);

                    switch (url) {
                        case "https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index":
                        case "https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index":
                            if(!semestrFlag) {
                                alarmBrowser.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie?details=1&okres=42781");
                            } else {
                                alarmBrowser.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie?details=1&okres=42782");
                            }
                            break;
                        case "https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie?details=1&okres=42781":
                        case "https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie?details=1&okres=42782":
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
            alarmBrowser.addJavascriptInterface(new JsInterfaceAlarm(this, semestrFlag), "ALARM_HTMLOUT");
            alarmBrowser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");

            MultiUtils.callAlarm(GradesForAlarmActivity.this, login, password, semestrFlag);
            finishAndRemoveTask();
        } else {
            //NewGradeNotification.showNotification(this, "ALARM -> brak internetu");
            Log.w("UWAGA", "ALARM -> brak internetu");
            MultiUtils.callAlarm(GradesForAlarmActivity.this, login, password, semestrFlag);
            finishAndRemoveTask();
        }
    }

    // problemem jest niezamykanie okna przeglądarki, po nieudanym logowaniu? pobieraniu danych?


    @Override
    protected void onPause() {
        Log.w("UWAGA", "ALARM -> onPause");
        finishAndRemoveTask();
        super.onPause();
    }

    @Override
    public void onAlarmInteraction(boolean alarmFlag) {
        if (alarmFlag) {
            Log.w("UWAGA", "ALARM -> plik zapisany, kończę i usuwam zadanie");
            MultiUtils.callAlarm(GradesForAlarmActivity.this, login, password, semestrFlag);
            finishAndRemoveTask();
        }
    }
}
