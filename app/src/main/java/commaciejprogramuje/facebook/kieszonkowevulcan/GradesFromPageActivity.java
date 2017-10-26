package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.GradesJavaScriptInterface;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.MyWebViewClient;

public class GradesFromPageActivity extends AppCompatActivity {

    public static final String NOT_RELOAD_GRADES_KEY = "notReloadGrades";
    @InjectView(R.id.browser)
    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_grades_from_page);
        ButterKnife.inject(this);

        this.moveTaskToBack(true);

        Intent intent = getIntent();

        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        //MyWebViewClient myWebViewClient = new MyWebViewClient(browser, MainActivity.login, MainActivity.password);
        MyWebViewClient myWebViewClient = new MyWebViewClient(browser, "e_szymczyk@orange.pl", "Ulka!2002");

        browser.setWebViewClient(myWebViewClient);
        browser.addJavascriptInterface(new GradesJavaScriptInterface(GradesFromPageActivity.this), "GRADES_HTMLOUT");

        browser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
        Log.w("UWAGA", "adres 1");

        if(intent.hasExtra(MainActivity.NOT_HIDE_MAIN_ACTIVITY_KEY)) {
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            mainActivityIntent.putExtra(NOT_RELOAD_GRADES_KEY, true);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainActivityIntent);
        }
    }
}
