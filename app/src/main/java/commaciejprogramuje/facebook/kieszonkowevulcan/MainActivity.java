package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.DataFile;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.DeleteCredentials;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.InternetUtils;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.NewGradeNotification;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.WaitMessages;

import static commaciejprogramuje.facebook.kieszonkowevulcan.Utils.GradesJavaScriptInterface.KIESZONKOWE_FILE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoginFragment.OnFragmentInteractionListener {
    public static final String LOGIN_DATA_KEY = "loginData";
    public static final String PASSWORD_DATA_KEY = "passwordData";

    public static final int ALARM_INTERVAL = 1000 * 120; // co 1 minutę
    public static final String NOT_HIDE_MAIN_ACTIVITY_KEY = "notHideMainActivity";

    private final DeleteCredentials deleteCredentials = new DeleteCredentials(this);
    private final ShowNewsFrag showNewsFrag = new ShowNewsFrag(this);
    private final ShowGradesFrag showGradesFrag = new ShowGradesFrag(this);
    private final ShowMoneyFrag showMoneyFrag = new ShowMoneyFrag(this);
    public final ShowLoginFrag showLoginFrag = new ShowLoginFrag(this);
    private final ShowHelloFrag showHelloFrag = new ShowHelloFrag(this);
    private final ShowTeachersFrag showTeachersFrag = new ShowTeachersFrag(this);
    public final ReplaceFrag replaceFrag = new ReplaceFrag();

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    Subjects subjects;
    NavigationView navigationView;
    ProgressDialog progressDialog;
    private WaitMessages waitMessages;
    public static String login = "";
    public static String password = "";
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
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

        waitMessages = new WaitMessages();
        progressDialog = createProgressDialog();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        login = sharedPref.getString(LOGIN_DATA_KEY, "");
        password = sharedPref.getString(PASSWORD_DATA_KEY, "");

        /*Intent alarmIntent = new Intent(MainActivity.this, MyAlarm.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_INTERVAL, pendingIntent);
*/

        //showHelloFrag.show();

        Intent intent = getIntent();
        if (!InternetUtils.isConnection(this)) {
            InternetUtils.noConnectionReaction(MainActivity.this);
        } else {
            if (login.isEmpty() || password.isEmpty()) {
                showLoginFrag.show();
            } else if ((intent.hasExtra(NewGradeNotification.FROM_NOTIFICATION_KEY) || intent.hasExtra(GradesFromPageActivity.NOT_RELOAD_GRADES_KEY))
                    && DataFile.isExists(this, KIESZONKOWE_FILE)) {
                try {
                    subjects = DataFile.read(this, KIESZONKOWE_FILE);
                } catch (IOException | ClassNotFoundException e) {
                    showHelloFrag.show();
                    //progressDialog.setMessage(waitMessages.getRandomText());
                    //progressDialog.show();
                    reloadGradesNotHideMainActivity();
                }
                if (subjects != null) {
                    subjects.setSubjectsArray(DataFile.originOrder(subjects));
                }
                showNewsFrag.show();
             } else {
                showHelloFrag.show();
                subjects = new Subjects();
                reloadGradesNotHideMainActivity();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            deleteCredentials.delete();
            return true;
        } else if (id == R.id.turnoff_alarm_settings) {
            //alarmManager.cancel(pendingIntent);
            return true;
        } else if (id == R.id.turnon_alarm_settings) {
            //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_INTERVAL, pendingIntent);
            return true;
        } else if (id == R.id.exit_settings) {
            this.finishAndRemoveTask();
            System.exit(0);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private ProgressDialog createProgressDialog() {
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setTitle("Czekaj...");
        pd.setMessage(waitMessages.getRandomText());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setIndeterminate(true);
        pd.setCancelable(false);

        pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                /*if (myWebViewClientGrades.isProblem()) {
                    Log.w("UWAGA", "wystąpił problem");
                    Toast.makeText(MainActivity.this, "Mamy problem z logowaniem!\n\n\nZapewne błędny login lub hasło.\nSpróbuj ponownie", Toast.LENGTH_LONG).show();
                    deleteCredentials.delete();
                } else */

                if (navigationView.getMenu().findItem(R.id.nav_news).isChecked()) {
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_news));
                } else if (navigationView.getMenu().findItem(R.id.nav_grades).isChecked()) {
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_grades));
                } else if (navigationView.getMenu().findItem(R.id.nav_money).isChecked()) {
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_money));
                } else if (navigationView.getMenu().findItem(R.id.nav_teachers).isChecked()) {
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_teachers));
                }
            }
        });

        return pd;
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        if (!InternetUtils.isConnection(this)) {
            InternetUtils.noConnectionReaction(MainActivity.this);
        } else {
            reloadGradesNotHideMainActivity();
        }
    }

    public void reloadGradesNotHideMainActivity() {
        Intent getGradesIntentNotHide = new Intent(this, GradesFromPageActivity.class);
        getGradesIntentNotHide.putExtra(NOT_HIDE_MAIN_ACTIVITY_KEY, true);
        getGradesIntentNotHide.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(getGradesIntentNotHide);
    }

    @Override
    public void onFragmentInteraction(String login, String password) {
        Log.w("UWAGA", login + ", " + password);

        if (login.isEmpty() || password.isEmpty()) {
            login = "a";
            password = "a";
        }

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LOGIN_DATA_KEY, login);
        editor.putString(PASSWORD_DATA_KEY, password);
        editor.apply();

        showHelloFrag.show();
        reloadGradesNotHideMainActivity();
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Subjects getSubjects() {
        return subjects;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void setActionBarSubtitle(String subtitle) {
        getSupportActionBar().setSubtitle(subtitle);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
