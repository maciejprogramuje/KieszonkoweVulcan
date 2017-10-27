package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
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

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import commaciejprogramuje.facebook.kieszonkowevulcan.School.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ReplaceFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ShowGradesFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ShowHelloFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ShowLoginFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ShowMoneyFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ShowNewsFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ShowTeachersFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.Credentials;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.DataFile;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.InternetUtils;

import static commaciejprogramuje.facebook.kieszonkowevulcan.Utils.GradesJavaScriptInterface.KIESZONKOWE_FILE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoginFragment.OnFragmentInteractionListener, Credentials.OnCredentialsCheckedListener {
    public static final String LOGIN_DATA_KEY = "loginData";
    public static final String PASSWORD_DATA_KEY = "passwordData";

    public static final int ALARM_INTERVAL = 1000 * 120; // co 1 minutę
    public static final String NOT_HIDE_MAIN_ACTIVITY_KEY = "notHideMainActivity";

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
    @InjectView(R.id.login_browser)
    WebView loginBrowser;
    @InjectView(R.id.progressCircle)
    ProgressBar progressCircle;

    static Subjects subjects;
    NavigationView navigationView;
    private static String login = "";
    private static String password = "";
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static MainActivity mainActivity;
    private int loginIndex = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setMainActivity(this);

        progressCircle.setVisibility(View.INVISIBLE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

        if (!InternetUtils.isConnection(this)) {
            InternetUtils.noConnectionReaction(MainActivity.this);
        } else {
            if (login.isEmpty() || password.isEmpty()) {
                showLoginFrag.show();
            } else {
                credentials.checkCredentials();


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
            return true;
        } else if (id == R.id.turnoff_alarm_settings) {
            alarmManager.cancel(pendingIntent);
            return true;
        } else if (id == R.id.turnon_alarm_settings) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_INTERVAL, pendingIntent);
            return true;
        } else if (id == R.id.exit_settings) {
            this.finishAndRemoveTask();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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
            Intent intent = new Intent(this, GradesFromPageActivity.class);
            intent.putExtra(NOT_HIDE_MAIN_ACTIVITY_KEY, true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onFragmentInteraction(String mLogin, String mPassword) {
        login = mLogin;
        password = mPassword;
        Log.w("UWAGA", "sprawdzam: " + login + ", " + password);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressCircle.setVisibility(View.VISIBLE);
            }
        });

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


            try {
                setSubjects(DataFile.read(MainActivity.this, KIESZONKOWE_FILE));
            } catch (IOException | ClassNotFoundException e) {
                MainActivity.subjects = new Subjects();
                //reloadGrades();
            }
            if (MainActivity.getSubjects() != null) {
                MainActivity.getSubjects().setSubjectsArray(DataFile.originOrder(MainActivity.getSubjects()));
            }

                /*Intent alarmIntent = new Intent(MainActivity.this, MyAlarm.class);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 1000, ALARM_INTERVAL, pendingIntent);*/

            showNewsFrag.show();


        } else {
            Toast.makeText(MainActivity.this, "Błąd logowania!\n\nZły login lub hasło.\n\nSpróbuj ponownie", Toast.LENGTH_LONG).show();
            credentials.delete();
            showLoginFrag.show();
        }
    }

    public void reloadGrades() {
        Intent intent = new Intent(this, GradesFromPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public static Subjects getSubjects() {
        return subjects;
    }

    public static void setSubjects(Subjects subjects) {
        MainActivity.subjects = subjects;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void setActionBarSubtitle(String subtitle) {
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

    public WebView getLoginBrowser() {
        return loginBrowser;
    }

    public int getLoginIndex() {
        return loginIndex;
    }

    public void setLoginIndex(int loginIndex) {
        this.loginIndex = loginIndex;
    }

    public ProgressBar getProgressCircle() {
        return progressCircle;
    }
}
