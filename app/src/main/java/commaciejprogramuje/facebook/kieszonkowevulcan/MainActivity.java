package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import commaciejprogramuje.facebook.kieszonkowevulcan.SchoolUtils.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ReplaceFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ShowGradesFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ShowHelloFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ShowLoginFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ShowMoneyFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ShowNewsFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments.ShowTeachersFrag;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.CheckInternetConn;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.DeleteCredentials;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.NoInternetReaction;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.WaitMessages;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoginFragment.OnFragmentInteractionListener {
    public static final String LOGIN_DATA_KEY = "loginData";
    public static final String PASSWORD_DATA_KEY = "passwordData";

    private final DeleteCredentials deleteCredentials = new DeleteCredentials(this);
    private final ShowNewsFrag ShowNewsFrag = new ShowNewsFrag(this);
    private final ShowGradesFrag showGradesFrag = new ShowGradesFrag(this);
    private final ShowMoneyFrag showMoneyFrag = new ShowMoneyFrag(this);
    public final ShowLoginFrag showLoginFrag = new ShowLoginFrag(this);
    private final ShowHelloFrag showHelloFrag = new ShowHelloFrag(this);
    private final ShowTeachersFrag showTeachersFrag = new ShowTeachersFrag(this);
    public final CheckInternetConn checkInternetConn = new CheckInternetConn();
    public final NoInternetReaction noInternetReaction = new NoInternetReaction();
    public final ReplaceFrag replaceFrag = new ReplaceFrag();

    @InjectView(R.id.tempWebView)
    WebView browser;
    @InjectView(R.id.fab)
    FloatingActionButton fab;

    Subjects subjects;
    NavigationView navigationView;
    ProgressDialog progressDialog;
    private WaitMessages waitMessages;
    private String login = "";
    private String password = "";
    private MyWebViewClientGrades myWebViewClientGrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // czyli szuflada
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        browser.setVisibility(View.INVISIBLE);
        waitMessages = new WaitMessages();
        progressDialog = createProgressDialog();
        subjects = new Subjects();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        login = sharedPref.getString(LOGIN_DATA_KEY, "");
        password = sharedPref.getString(PASSWORD_DATA_KEY, "");

        if (!checkInternetConn.checkInternetConnection(this)) {
            noInternetReaction.noInternetReaction(MainActivity.this);
        } else {
            if(login.isEmpty() || password.isEmpty()) {
                showLoginFrag.showLoginFragment();
            } else {
                showHelloFrag.showHelloFragment();
                loadGrades(login, password);
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
        } else if(id == R.id.exit_settings) {
            this.finishAndRemoveTask();
            System.exit(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle bottom_navigation_money_activity view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_news) {
            ShowNewsFrag.show();
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
                if(myWebViewClientGrades.isProblem()) {
                    Log.w("UWAGA", "wystąpił problem");
                    Toast.makeText(MainActivity.this, "Mamy problem z logowaniem!\n\n\nZapewne błędny login lub hasło.\nSpróbuj ponownie", Toast.LENGTH_LONG).show();
                    deleteCredentials.delete();
                } else if (navigationView.getMenu().findItem(R.id.nav_news).isChecked()) {
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_news));
                } else if (navigationView.getMenu().findItem(R.id.nav_grades).isChecked()) {
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_grades));
                } else if (navigationView.getMenu().findItem(R.id.nav_money).isChecked()) {
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_money));
                } else if(navigationView.getMenu().findItem(R.id.nav_teachers).isChecked()) {
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_teachers));
                }
            }
        });

        return pd;
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        if (!checkInternetConn.checkInternetConnection(this)) {
            noInternetReaction.noInternetReaction(MainActivity.this);
        } else {
            loadGrades(login, password);
        }
    }

    private void loadGrades(String login, String password) {
        progressDialog.setMessage(waitMessages.getRandomText());
        progressDialog.show();

        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setDomStorageEnabled(true);
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        myWebViewClientGrades = new MyWebViewClientGrades(browser, login, password, progressDialog);

        browser.setWebViewClient(myWebViewClientGrades);
        browser.addJavascriptInterface(new GradesJavaScriptInterface(this), "GRADES_HTMLOUT");

        browser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
    }

    @Override
    public void onFragmentInteraction(String login, String password) {
        Log.w("UWAGA", login + ", " + password);

        if(login.isEmpty() || password.isEmpty()) {
            login = "a";
            password = "a";
        }

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LOGIN_DATA_KEY, login);
        editor.putString(PASSWORD_DATA_KEY, password);
        editor.apply();

        showHelloFrag.showHelloFragment();
        loadGrades(login, password);
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
}
