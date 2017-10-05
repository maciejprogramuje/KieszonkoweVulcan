package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @InjectView(R.id.login_button)
    Button loginButton;
    @InjectView(R.id.webView)
    WebView webView;

    public static final String LINK_1 = "https://cufs.vulcan.net.pl/lublin/Account/LogOn?ReturnUrl=%2Flublin%2FFS%2FLS%3Fwa%3Dwsignin1.0%26wtrealm%3Dhttps%253a%252f%252fuonetplus.vulcan.net.pl%252flublin%252fLoginEndpoint.aspx%26wctx%3Dhttps%253a%252f%252fuonetplus.vulcan.net.pl%252flublin%252fLoginEndpoint.aspx";
    public static final String LINK_1_A = "https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx";
    public static final String LINK_1_B = "https://uonetplus.vulcan.net.pl/lublin/";
    public static final String LINK_2 = "https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index";
    public static final String LOGIN_STRING = "e_szymczyk@orange.pl";
    public static final String PASSWORD_STRING = "Ulka!2002";
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36";

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(LINK_1);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //super.onPageFinished(view, url);

                webView.loadUrl("javascript: {" +
                        "document.getElementById('LoginName').value = '" + LOGIN_STRING + "';" +
                        "document.getElementById('Password').value = '" + PASSWORD_STRING + "';" +
                        "document.getElementsByTagName('input')[2].click();" +
                        "};");
            }
        });

    }



    /*================= 1 ======================
<body>
    <form method="POST" name="hiddenform" action="https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx">
        <input type="hidden" name="wa" value="wsignin1.0">
        <input type="hidden" name="wresult" value="<trust:RequestSecurityTokenResponseCollection xmlns:trust=&quot;http://docs.oasis-open.org/ws-sx/ws-trust/200512&quot;><trust:RequestSecurityTokenResponse Context=&quot;https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx&quot;><trust:Lifetime><wsu:Created xmlns:wsu=&quot;http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd&quot;>2017-10-04T17:28:07.167Z</wsu:Created><wsu:Expires xmlns:wsu=&quot;http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd&quot;>2017-10-04T18:13:07.167Z</wsu:Expires></trust:Lifetime><wsp:AppliesTo xmlns:wsp=&quot;http://schemas.xmlsoap.org/ws/2004/09/policy&quot;><wsa:EndpointReference xmlns:wsa=&quot;http://www.w3.org/2005/08/addressing&quot;><wsa:Address>https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx</wsa:Address></wsa:EndpointReference></wsp:AppliesTo><trust:RequestedSecurityToken><saml:Assertion MajorVersion=&quot;1&quot; MinorVersion=&quot;1&quot; AssertionID=&quot;_4e22dd2e-1790-44ff-ac6e-8d2513c806b5&quot; Issuer=&quot;CUFSTokenService&quot; IssueInstant=&quot;2017-10-04T17:28:07.167Z&quot; xmlns:saml=&quot;urn:oasis:names:tc:SAML:1.0:assertion&quot;><saml:Conditions NotBefore=&quot;2017-10-04T17:28:07.167Z&quot; NotOnOrAfter=&quot;2017-10-04T18:13:07.167Z&quot;><saml:AudienceRestrictionCondition><saml:Audience>https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx</saml:Audience></saml:AudienceRestrictionCondition></saml:Conditions><saml:AttributeStatement><saml:Subject><saml:SubjectConfirmation><saml:ConfirmationMethod>urn:oasis:names:tc:SAML:1.0:cm:bearer</saml:ConfirmationMethod></saml:SubjectConfirmation></saml:Subject><saml:Attribute AttributeName=&quot;name&quot; AttributeNamespace=&quot;http://schemas.xmlsoap.org/ws/2005/05/identity/claims&quot;><saml:AttributeValue>e_szymczyk@orange.pl</saml:AttributeValue></saml:Attribute><saml:Attribute AttributeName=&quot;emailaddress&quot; AttributeNamespace=&quot;http://schemas.xmlsoap.org/ws/2005/05/identity/claims&quot;><saml:AttributeValue>e_szymczyk@orange.pl</saml:AttributeValue></saml:Attribute><saml:Attribute AttributeName=&quot;SessionID&quot; AttributeNamespace=&quot;http://schemas.vulcan.edu.pl/ws/identity/claims&quot;><saml:AttributeValue>2Bn1Ztt5EMq9wdOrDwtYEFZfrXc=</saml:AttributeValue></saml:Attribute><saml:Attribute AttributeName=&quot;authtype&quot; AttributeNamespace=&quot;http://schemas.vulcan.edu.pl/ws/identity/claims&quot;><saml:AttributeValue>internal</saml:AttributeValue></saml:Attribute><saml:Attribute AttributeName=&quot;pwdvalid&quot; AttributeNamespace=&quot;http://schemas.vulcan.edu.pl/ws/identity/claims&quot;><saml:AttributeValue>True</saml:AttributeValue></saml:Attribute><saml:Attribute AttributeName=&quot;daystochange&quot; AttributeNamespace=&quot;http://schemas.vulcan.edu.pl/ws/identity/claims&quot;><saml:AttributeValue>28</saml:AttributeValue></saml:Attribute><saml:Attribute AttributeName=&quot;UserInstance&quot; AttributeNamespace=&quot;http://schemas.vulcan.edu.pl/ws/identity/claims&quot;><saml:AttributeValue>lublin</saml:AttributeValue></saml:Attribute></saml:AttributeStatement><Signature xmlns=&quot;http://www.w3.org/2000/09/xmldsig#&quot;><SignedInfo><CanonicalizationMethod Algorithm=&quot;http://www.w3.org/2001/10/xml-exc-c14n#&quot; /><SignatureMethod Algorithm=&quot;http://www.w3.org/2001/04/xmldsig-more#rsa-sha256&quot; /><Reference URI=&quot;#_4e22dd2e-1790-44ff-ac6e-8d2513c806b5&quot;><Transforms><Transform Algorithm=&quot;http://www.w3.org/2000/09/xmldsig#enveloped-signature&quot; /><Transform Algorithm=&quot;http://www.w3.org/2001/10/xml-exc-c14n#&quot; /></Transforms><DigestMethod Algorithm=&quot;http://www.w3.org/2001/04/xmlenc#sha256&quot; /><DigestValue>WWledbRPpTFkg6I0XzNts50f0wRBxoArlqomX9juGg0=</DigestValue></Reference></SignedInfo><SignatureValue>k9f/sH/f5nHpw8L3stEWKYkubsSMP6gGzWdAlZxH3+ejR4KtP0oBZDDRT1HCoPmvxyXlZugO8SgGjrj5hMJNQBevpRwQWEKfZbEC1hYhGd4NWFLfWj++YvNgA0M3TGm6UezEjwhgGOef0XY7BzzKnpiewc7ytIY+PrOZ3Xz5XcwTtJ1iHwyFYNPVi9CpSkZg
        <input type="hidden" name="wctx" value="https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx">
        <noscript>
            <p>Script is disabled. Click Submit to continue.</p>
            <input type="submit" value="Submit">
        </noscript>
    </form>
    <script language="javascript">window.setTimeout('document.forms[0].submit()', 0);</script>
</body>*/

}
