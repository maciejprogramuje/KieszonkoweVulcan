package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.GradesJavaScriptInterface;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.InternetUtils;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.MyAlarm;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.MyWebViewClient;

public class GradesFromPageActivity extends AppCompatActivity implements GradesJavaScriptInterface.OnFileSavedInteractionListener {
    public static final String NOT_RELOAD_GRADES_KEY = "notReloadGrades";

    @InjectView(R.id.browser)
    WebView browser;

    String gradesLogin = "";
    String gradesPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.w("UWAGA", "kreator GradesFromPageActivity");

        setContentView(R.layout.activity_get_grades_from_page);
        ButterKnife.inject(this);

        this.moveTaskToBack(true);

        gradesLogin = MainActivity.getLogin();
        gradesPassword = MainActivity.getPassword();
        if (gradesLogin == "" || gradesPassword == "") {
            gradesLogin = getIntent().getStringExtra(MyAlarm.MY_ALARM_LOGIN_KEY);
            gradesPassword = getIntent().getStringExtra(MyAlarm.MY_ALARM_PASSWORD_KEY);
        }

        if (InternetUtils.isConnection(this)) {
            browser.getSettings().setJavaScriptEnabled(true);
            browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            MyWebViewClient myWebViewClient = new MyWebViewClient(browser, gradesLogin, gradesPassword);
            browser.setWebViewClient(myWebViewClient);
            browser.addJavascriptInterface(new GradesJavaScriptInterface(GradesFromPageActivity.this), "GRADES_HTMLOUT");
            browser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
        } else {
            this.finishAndRemoveTask();
        }
    }

    @Override
    public void onFileSavedInteraction(boolean fileFlag) {
        if (fileFlag) {
            Log.w("UWAGA", "plik zapisany, kończę i usuwam zadanie");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    browser.stopLoading();
                }
            });

            if (getIntent().hasExtra(MainActivity.BROADCAST_FROM_MAIN_ACTIVITY_KEY)) {
                Intent mainActivityIntent = new Intent(this, MainActivity.class);
                mainActivityIntent.putExtra(NOT_RELOAD_GRADES_KEY, true);
                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainActivityIntent);
            } else {
                this.finishAndRemoveTask();
            }
        }
    }
}
