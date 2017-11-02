package commaciejprogramuje.facebook.kieszonkowevulcan;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.JsInterfaceGrades;

public class HelloFragment extends Fragment {
    WebView firstFileBrowser;

    static boolean isInternet;

    public HelloFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hello, container, false);
        ButterKnife.inject(this, view);

        MainActivity.hideFab();
        firstFileBrowser = MainActivity.getMainActivity().browser.findViewById(R.id.browser);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(isInternet) {
            MainActivity.showProgressCircle();

            firstFileBrowser.getSettings().setJavaScriptEnabled(true);
            firstFileBrowser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            firstFileBrowser.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.w("UWAGA", "FINISHED: " + url);

                    if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Start/Index")
                            || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")) {
                        firstFileBrowser.loadUrl("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie");
                    } else if (url.equals("https://uonetplus-opiekun.vulcan.net.pl/lublin/001959/Oceny.mvc/Wszystkie")) {
                        firstFileBrowser.loadUrl("javascript:window.HELLO_HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                    } else {
                        if (url.equals("https://uonetplus.vulcan.net.pl/lublin")
                                || url.equals("https://uonetplus.vulcan.net.pl/lublin/Start.mvc/Index")
                                || url.equals("https://uonetplus.vulcan.net.pl/lublin/?logout=true")) {
                            firstFileBrowser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
                        }
                    }

                    firstFileBrowser.loadUrl("javascript: {" +
                            "document.getElementById('LoginName').value = '" + MainActivity.getLogin() + "';" +
                            "document.getElementById('Password').value = '" + MainActivity.getPassword() + "';" +
                            "document.getElementsByTagName('input')[2].click();" +
                            "};");
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.w("UWAGA", "LOADING: " + url);
                    return false;
                }
            });
            firstFileBrowser.addJavascriptInterface(new JsInterfaceGrades(getContext()), "HELLO_HTMLOUT");
            firstFileBrowser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");

            Log.w("UWAGA", "ładuję stronę z HelloFragment");
        } else {
            MainActivity.showFab();
        }
    }

    public static HelloFragment newInstance(boolean mIsInternet) {
        isInternet = mIsInternet;
        HelloFragment fragment = new HelloFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
