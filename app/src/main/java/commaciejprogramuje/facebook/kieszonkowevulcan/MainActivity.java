package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator.Generator;
import commaciejprogramuje.facebook.kieszonkowevulcan.GradesUtils.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.WaitMessages;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoginFragment.OnFragmentInteractionListener {
    public static final String SUBJECTS_KEY = "subjects";
    public static final String LOGIN_DATA_KEY = "loginData";
    public static final String PASSWORD_DATA_KEY = "passwordData";

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
    private MyWebViewClient myWebViewClient;
    //boolean problem = false;

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

        if (!checkInternetConnection(this)) {
            noInternetReaction();
        } else {
            if(login.isEmpty() || password.isEmpty()) {
                showLoginFragment();
            } else {
                showHelloFragment("Logowanie...");
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
            deleteLoginAndPassword();
            return true;
        } else if(id == R.id.exit_settings) {
            this.finishAndRemoveTask();
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void deleteLoginAndPassword() {
        Log.w("UWAGA", "czyszczenie danych");

        login = "";
        password = "";

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LOGIN_DATA_KEY, "");
        editor.putString(PASSWORD_DATA_KEY, "");
        editor.apply();

        showLoginFragment();
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle bottom_navigation_money_activity view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_news) {
            showNewsFragment();
        } else if (id == R.id.nav_grades) {
            showGradesFragment();
        } else if (id == R.id.nav_money) {
            showMoneyFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showNewsFragment() {
        NewsFragment newsFragment;
        if (checkInternetConnection(this)) {
            newsFragment = NewsFragment.newInstance(Generator.dataForNewsFragment(subjects));
        } else {
            noInternetReaction();
            newsFragment = NewsFragment.newInstance("Włącz internet i odśwież przyciskiem!");
        }
        replaceFragment(newsFragment);
    }

    public void showGradesFragment() {
        GradesFragment gradesFragment;
        if (checkInternetConnection(this)) {
            gradesFragment = GradesFragment.newInstance(Generator.dataForGradesFragment(subjects));
        } else {
            noInternetReaction();
            gradesFragment = GradesFragment.newInstance("Włącz internet i odśwież przyciskiem!");
        }
        replaceFragment(gradesFragment);
    }

    private void showMoneyFragment() {
        MoneyFragment moneyFragment;
        if (checkInternetConnection(this)) {
            moneyFragment = MoneyFragment.newInstance(Generator.dataForMoneyFragment(subjects));
        } else {
            noInternetReaction();
            moneyFragment = MoneyFragment.newInstance("Włącz internet i odśwież przyciskiem!");
        }
        replaceFragment(moneyFragment);
    }

    private void showLoginFragment() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        replaceFragment(loginFragment);
    }

    private void showHelloFragment(String helloText) {
        HelloFragment helloFragment = HelloFragment.newInstance(helloText);
        replaceFragment(helloFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SUBJECTS_KEY, subjects);
    }

    public boolean checkInternetConnection(Context context) {
        ConnectivityManager con_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected();
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
                if(myWebViewClient.isProblem()) {
                    Log.w("UWAGA", "wystąpił problem");
                    Toast.makeText(MainActivity.this, "Mamy problem z logowaniem!\n\n\nZapewne błędny login lub hasło.\nSpróbuj ponownie", Toast.LENGTH_LONG).show();
                    deleteLoginAndPassword();
                } else if (navigationView.getMenu().findItem(R.id.nav_news).isChecked()) {
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_news));
                } else if (navigationView.getMenu().findItem(R.id.nav_grades).isChecked()) {
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_grades));
                } else if (navigationView.getMenu().findItem(R.id.nav_money).isChecked()) {
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_money));
                }
            }
        });

        return pd;
    }

    private void noInternetReaction() {
        Toast.makeText(getApplicationContext(), "Włącz internet!", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        if (!checkInternetConnection(this)) {
            noInternetReaction();
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

        myWebViewClient = new MyWebViewClient(browser, login, password, progressDialog);

        browser.setWebViewClient(myWebViewClient);
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

        showHelloFragment("Trwa łącznie z bazą danych...");
        loadGrades(login, password);
    }
}
