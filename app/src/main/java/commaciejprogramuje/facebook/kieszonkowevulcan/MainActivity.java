package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static commaciejprogramuje.facebook.kieszonkowevulcan.NavMenuButtonsTitle.ATTENDING;
import static commaciejprogramuje.facebook.kieszonkowevulcan.NavMenuButtonsTitle.GRADES;
import static commaciejprogramuje.facebook.kieszonkowevulcan.NavMenuButtonsTitle.MONEY;
import static commaciejprogramuje.facebook.kieszonkowevulcan.NavMenuButtonsTitle.NEWS;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String SUBJECTS_KEY = "subjects";
    public static final String RESULTS_KEY = "results";

    @InjectView(R.id.webView)
    WebView webView;
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.button)
    Button button;

    WebNavigation webNavigation;
    Subjects subjects;
    NavMenuButtonsTitle navMenuButtonsTitle;
    NavigationView navigationView;

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

        if (!checkInternetConnection(this)) {
            Toast.makeText(getApplicationContext(), "Włącz internet!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, NoInternetActivity.class);
            startActivity(intent);
        } else {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            navMenuButtonsTitle = NEWS;
            webNavigation = new WebNavigation(this);

            webNavigation.navToLoginAndDashboard();

            if (savedInstanceState == null) {
                subjects = new Subjects();
            } else {
                subjects = (Subjects) savedInstanceState.getSerializable(SUBJECTS_KEY);
            }

            navigationView.setCheckedItem(R.id.nav_news);
            //onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_planets));
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
            navMenuButtonsTitle = NEWS;
            webNavigation.navToNews();
        } else if (id == R.id.nav_grades) {
            navMenuButtonsTitle = GRADES;
            webNavigation.navToPupilPanel();
        } else if (id == R.id.nav_money) {
            navMenuButtonsTitle = MONEY;
            webNavigation.navToPupilPanel();
        } else if (id == R.id.nav_attending) {
            navMenuButtonsTitle = ATTENDING;
            webNavigation.navToPupilPanel();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
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


    public WebView getWebView() {
        return webView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextViewByString(String s) {
        textView.setText(s);
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public WebNavigation getWebNavigation() {
        return webNavigation;
    }

    public void setWebNavigation(WebNavigation webNavigation) {
        this.webNavigation = webNavigation;
    }

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }

    public NavMenuButtonsTitle getNavMenuButtonsTitle() {
        return navMenuButtonsTitle;
    }

    public void setNavMenuButtonsTitle(NavMenuButtonsTitle navMenuButtonsTitle) {
        this.navMenuButtonsTitle = navMenuButtonsTitle;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
