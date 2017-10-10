package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String LOGIN_STRING = "e_szymczyk@orange.pl";
    public static final String PASSWORD_STRING = "Ulka!2002";
    public static final String SUBJECTS_KEY = "subjects";
    public static final String RESULTS_KEY = "results";

    @InjectView(R.id.tempWebView)
    WebView tempWebView;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    Subjects subjects;
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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressBar.setVisibility(View.GONE);

        if (!checkInternetConnection(this)) {
            Toast.makeText(getApplicationContext(), "Włącz internet!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, NoInternetActivity.class);
            startActivity(intent);
        } else {
            // czyli szuflada
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            tempWebView.setVisibility(View.INVISIBLE);
            subjects = new Subjects(MainActivity.this);

            navigationView.setCheckedItem(R.id.nav_news);
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_news));
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
            NewsFragment newsFragment = NewsFragment.newInstance();
            replaceFragment(newsFragment);
        } else if (id == R.id.nav_grades) {
            loadGrades();
            showGradesFragment();
        } else if (id == R.id.nav_money) {

        } else if (id == R.id.nav_attending) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public WebView getTempWebView() {
        return tempWebView;
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        loadGrades();
        showGradesFragment();
    }

    private void loadGrades() {
        Thread t = new Thread() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //do some serious stuff...
                        subjects = new Subjects(MainActivity.this);
                    }
                });
            }
        };
        t.start();
        //t.join();
    }

    private void showGradesFragment() {
        while (!progressBar.isShown()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < subjects.size(); i++) {
                stringBuilder.append(subjects.getName(i))
                        .append(": ")
                        .append(subjects.getGrades(i))
                        .append("średnia: ")
                        .append(subjects.getAverage(i))
                        .append("\n");
            }
            //Log.w("UWAGA", stringBuilder.toString());

            GradesFragment gradesFragment = GradesFragment.newInstance(stringBuilder.toString());
            replaceFragment(gradesFragment);
        }
    }
}
