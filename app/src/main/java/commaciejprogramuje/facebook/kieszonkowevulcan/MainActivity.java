package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.show_fragments.ReplaceFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.show_fragments.ShowGradesFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.show_fragments.ShowHelloFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.show_fragments.ShowLoginFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.show_fragments.ShowMoneyFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.show_fragments.ShowNewsFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.show_fragments.ShowTeachersFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.Credentials;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.InternetUtils;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.JsInterfaceGrades;

import static commaciejprogramuje.facebook.kieszonkowevulcan.GradesForMainActivity.NOT_RELOAD_GRADES_KEY;
import static commaciejprogramuje.facebook.kieszonkowevulcan.utils.NewGradeNotification.FROM_NOTIFICATION_KEY;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        LoginFragment.OnFragmentInteractionListener,
        Credentials.OnCredentialsCheckedListener,
        JsInterfaceGrades.OnGradesMainInteractionListener {

    //public static final long ALARM_INRETVAL_IN_GRADES_FOR_ALARM_ACTIVITY = 1000 * 60 * 30;
    public static final long ALARM_INRETVAL_IN_GRADES_FOR_ALARM_ACTIVITY = 1000 * 60 * 5;

    public static final String LOGIN_DATA_KEY = "loginData";
    public static final String PASSWORD_DATA_KEY = "passwordData";
    public static final String KIESZONKOWE_FILE = "kieszonkoweVulcanGrades.dat";

    public final Credentials credentials = new Credentials(this);
    public final ShowNewsFrag showNewsFrag = new ShowNewsFrag(this);
    public final ShowGradesFrag showGradesFrag = new ShowGradesFrag(this);
    public final ShowMoneyFrag showMoneyFrag = new ShowMoneyFrag(this);
    public final ShowLoginFrag showLoginFrag = new ShowLoginFrag(this);
    public final ShowHelloFrag showHelloFrag = new ShowHelloFrag(this);
    public final ShowTeachersFrag showTeachersFrag = new ShowTeachersFrag(this);
    public final ReplaceFrag replaceFrag = new ReplaceFrag();

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.progressCircle)
    ProgressBar progressCircle;

    static Subjects subjects;
    private static String login = "";
    private static String password = "";
    private int loginIndex = 10;
    private static MainActivity mainActivity;
    NavigationView navigationView;
    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        browser = findViewById(R.id.browser);

        setMainActivity(this);

        progressCircle.setVisibility(View.INVISIBLE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //noinspection deprecation
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // czyli szuflada
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        login = sharedPref.getString(LOGIN_DATA_KEY, "");
        password = sharedPref.getString(PASSWORD_DATA_KEY, "");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!InternetUtils.isConnection(this)) {
            InternetUtils.noConnectionReaction(MainActivity.this);
        } else {
            if (login.isEmpty() || password.isEmpty()) {
                showLoginFrag.show();
            } else if (getIntent().hasExtra(NOT_RELOAD_GRADES_KEY) || getIntent().hasExtra(FROM_NOTIFICATION_KEY)) {
                showNewsFrag.show();
            } else {
                showHelloFrag.show();
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout_settings) {
            credentials.delete();
            showLoginFrag.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle bottom_navigation_money_activity view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_news) {
            showNewsFrag.show();
        } else if (id == R.id.nav_grades) {
            showGradesFrag.show();
        } else if (id == R.id.nav_money) {
            showMoneyFrag.show();
        } else if (id == R.id.nav_teachers) {
            showTeachersFrag.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        if (!InternetUtils.isConnection(this)) {
            InternetUtils.noConnectionReaction(MainActivity.this);
        } else {
            if (login.equals("") || password.equals("")) {
                showLoginFrag.show();
            } else {
                showHelloFrag.show();
            }
        }
    }

    @Override
    public void onFragmentInteraction(String mLogin, String mPassword) {
        login = mLogin;
        password = mPassword;
        Log.w("UWAGA", "Main Activity, sprawdzam: " + login + ", " + password);
        credentials.checkCredentials();
    }

    @Override
    public void OnCredentialsCheckedInteraction(boolean loginOk) {
        if (loginOk) {
            // zapisanie danych logowania, jeżeli poprawne
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(LOGIN_DATA_KEY, login);
            editor.putString(PASSWORD_DATA_KEY, password);
            editor.apply();

            Log.w("UWAGA", "MainActivity, zapisano login:" + login + ", hasło: " + password);

            showHelloFrag.show();
        } else {
            Toast.makeText(MainActivity.this, "Błąd logowania! Zły login lub hasło.", Toast.LENGTH_LONG).show();
            credentials.delete();
            showLoginFrag.show();
        }
    }

    @Override
    protected void onStop() {
        Toast.makeText(this, "onStop", Toast.LENGTH_LONG).show();

        /*Intent intent = new Intent();
        intent.setClassName("commaciejprogramuje.facebook.kieszonkowevulcan", "commaciejprogramuje.facebook.kieszonkowevulcan.GradesForAlarmActivity");
        intent.putExtra("login", login);
        intent.putExtra("password", password);
        startActivity(intent);*/

        callAlarm();

        super.onStop();

        supportFinishAfterTransition();
    }

    private void callAlarm() {
        Intent alarmIntent = new Intent();
        alarmIntent.setClassName("commaciejprogramuje.facebook.kieszonkowevulcan", "commaciejprogramuje.facebook.kieszonkowevulcan.utils.MyAlarm");
        alarmIntent.putExtra("loginMyAlarm", login);
        alarmIntent.putExtra("passwordMyAlarm", password);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_INRETVAL_IN_GRADES_FOR_ALARM_ACTIVITY, pendingIntent);

        // call alarm jest wywoływany 2x
        Log.w("UWAGA", "ALARM -> stworzyłem nowy alarm");
    }

    @Override
    public void onGradesMainInteraction(boolean fileFlag) {
        if (fileFlag) {
            Log.w("UWAGA", "MainActivity, plik zapisany - pokazuję showNewsFrag");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressCircle.setVisibility(View.INVISIBLE);
                }
            });
            showNewsFrag.show();
        }
    }

    public static Subjects getSubjects() {
        return subjects;
    }

    public static void setSubjects(Subjects subjects) {
        MainActivity.subjects = subjects;
    }

    public void setActionBarTitle(String title) {
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(title);
    }

    public void setActionBarSubtitle(String subtitle) {
        //noinspection ConstantConditions
        getSupportActionBar().setSubtitle(subtitle);
    }

    public static void setLogin(String mLogin) {
        login = mLogin;
    }

    public static void setPassword(String mPassword) {
        password = mPassword;
    }

    public static String getLogin() {
        return login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setMainActivity(MainActivity mMainActivity) {
        mainActivity = mMainActivity;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public WebView getBrowser() {
        return browser;
    }

    public int getLoginIndex() {
        return loginIndex;
    }

    public void setLoginIndex(int loginIndex) {
        this.loginIndex = loginIndex;
    }

    public static void showProgressCircle() {
        mainActivity.progressCircle.bringToFront();
        mainActivity.progressCircle.setVisibility(View.VISIBLE);
    }

    public static void hideProgressCircle() {
        mainActivity.progressCircle.setVisibility(View.INVISIBLE);
    }

    public static void showFab() {
        mainActivity.fab.show();
    }

    public static void hideFab() {
        mainActivity.fab.hide();
    }
}
