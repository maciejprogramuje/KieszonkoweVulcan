package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @InjectView(R.id.info_text_view)
    TextView infoTextView;

    //String link = "https://cufs.vulcan.net.pl/lublin/Account/LogOn";
    String link = "https://cufs.vulcan.net.pl/lublin/Account/LogOn?ReturnUrl=%2Flublin%2FFS%2FLS%3Fwa%3Dwsignin1.0%26wtrealm%3Dhttps%3a%2f%2fuonetplus.vulcan.net.pl%2flublin%2fLoginEndpoint.aspx%26wctx%3Dhttps%3a%2f%2fuonetplus.vulcan.net.pl%2flublin%2fLoginEndpoint.aspx";
    String loginString = "e_szymczyk@orange.pl";
    String passwordString = "Ulka!2002";
    String targetLink = "https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index";
    String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36";

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loginOnClick(View view) throws IOException {
        infoTextView.setText("Login in progress...");

        new JsoupAsyncIserv().execute();

    }

    private class JsoupAsyncIserv extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected Void doInBackground(Void... params) {
            try {
                Connection.Response login = Jsoup.connect(link)
                        .userAgent(userAgent)
                        .data("LoginName", loginString)
                        .data("Password", passwordString)
                        .method(Connection.Method.POST)
                        .followRedirects(true)
                        .execute();

                //Document document = login.parse();
                System.out.println(login.body());

                /*
                <html>
                <head>
                <title>Working...</title>
                </head>

                <body>
                <form method="POST" name="hiddenform" action="https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx">
                    <input type="hidden" name="wa" value="wsignin1.0" />
                    <input type="hidden" name="wresult" value="&lt;trust:RequestSecurityTokenResponseCollection xmlns:trust=&quot;http://docs.oasis-open.org/ws-sx/ws-trust/200512&quot;>&lt;trust:RequestSecurityTokenResponse Context=&quot;https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx&quot;>&lt;trust:Lifetime>&lt;wsu:Created xmlns:wsu=&quot;http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd&quot;>2017-10-04T16:09:34.548Z&lt;/wsu:Created>&lt;wsu:Expires xmlns:wsu=&quot;http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd&quot;>2017-10-04T16:54:34.548Z&lt;/wsu:Expires>&lt;/trust:Lifetime>&lt;wsp:AppliesTo xmlns:wsp=&quot;http://schemas.xmlsoap.org/ws/2004/09/policy&quot;>&lt;wsa:EndpointReference xmlns:wsa=&quot;http://www.w3.org/2005/08/addressing&quot;>&lt;wsa:Address>https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx&lt;/wsa:Address>&lt;/wsa:EndpointReference>&lt;/wsp:AppliesTo>&lt;trust:RequestedSecurityToken>&lt;saml:Assertion MajorVersion=&quot;1&quot; MinorVersion=&quot;1&quot; AssertionID=&quot;_bfdcef45-8a03-43ad-adfc-e935cfbbdf83&quot; Issuer=&quot;CUFSTokenService&quot; IssueInstant=&quot;2017-10-04T16:09:34.548Z&quot; xmlns:saml=&quot;urn:oasis:names:tc:SAML:1.0:assertion&quot;>&lt;saml:Conditions NotBefore=&quot;2017-10-04T16:09:34.548Z&quot; NotOnOrAfter=&quot;2017-10-04T16:54:34.548Z&quot;>&lt;saml:AudienceRestrictionCondition>&lt;saml:Audience>https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx&lt;/saml:Audience>&lt;/saml:AudienceRestrictionCondition>&lt;/saml:Conditions>&lt;saml:AttributeStatement>&lt;saml:Subject>&lt;saml:SubjectConfirmation>&lt;saml:ConfirmationMethod>urn:oasis:names:tc:SAML:1.0:cm:bearer&lt;/saml:ConfirmationMethod>&lt;/saml:SubjectConfirmation>&lt;/saml:Subject>&lt;saml:Attribute AttributeName=&quot;name&quot; AttributeNamespace=&quot;http://schemas.xmlsoap.org/ws/2005/05/identity/claims&quot;>&lt;saml:AttributeValue>e_szymczyk@orange.pl&lt;/saml:AttributeValue>&lt;/saml:Attribute>&lt;saml:Attribute AttributeName=&quot;emailaddress&quot; AttributeNamespace=&quot;http://schemas.xmlsoap.org/ws/2005/05/identity/claims&quot;>&lt;saml:AttributeValue>e_szymczyk@orange.pl&lt;/saml:AttributeValue>&lt;/saml:Attribute>&lt;saml:Attribute AttributeName=&quot;SessionID&quot; AttributeNamespace=&quot;http://schemas.vulcan.edu.pl/ws/identity/claims&quot;>&lt;saml:AttributeValue>ipHPubySZuWUdOum4m5S+K28LBw=&lt;/saml:AttributeValue>&lt;/saml:Attribute>&lt;saml:Attribute AttributeName=&quot;authtype&quot; AttributeNamespace=&quot;http://schemas.vulcan.edu.pl/ws/identity/claims&quot;>&lt;saml:AttributeValue>internal&lt;/saml:AttributeValue>&lt;/saml:Attribute>&lt;saml:Attribute AttributeName=&quot;pwdvalid&quot; AttributeNamespace=&quot;http://schemas.vulcan.edu.pl/ws/identity/claims&quot;>&lt;saml:AttributeValue>True&lt;/saml:AttributeValue>&lt;/saml:Attribute>&lt;saml:Attribute AttributeName=&quot;daystochange&quot; AttributeNamespace=&quot;http://schemas.vulcan.edu.pl/ws/identity/claims&quot;>&lt;saml:AttributeValue>28&lt;/saml:AttributeValue>&lt;/saml:Attribute>&lt;saml:Attribute AttributeName=&quot;UserInstance&quot; AttributeNamespace=&quot;http://schemas.vulcan.edu.pl/ws/identity/claims&quot;>&lt;saml:AttributeValue>lublin&lt;/saml:AttributeValue>&lt;/saml:Attribute>&lt;/saml:AttributeStatement>&lt;Signature xmlns=&quot;http://www.w3.org/2000/09/xmldsig#&quot;>&lt;SignedInfo>&lt;CanonicalizationMethod Algorithm=&quot;http://www.w3.org/2001/10/xml-exc-c14n#&quot; />&lt;SignatureMethod Algorithm=&quot;http://www.w3.org/2001/04/xmldsig-more#rsa-sha256&quot; />&lt;Reference URI=&quot;#_bfdcef45-8a03-43ad-adfc-e935cfbbdf83&quot;>&lt;Transforms>&lt;Transform Algorithm=&quot;http://www.w3.org/2000/09/xmldsig#enveloped-signature&quot; />&lt;Transform Algorithm=&quot;http://www.w3.org/2001/10/xml-exc-c14n#&quot;
                 */


                Document document1 = Jsoup.connect(targetLink)
                        .cookies(login.cookies())
                        .post();

                Log.w("UWAGA", document1.text());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }
}
