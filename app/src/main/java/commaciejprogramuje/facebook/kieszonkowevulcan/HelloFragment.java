package commaciejprogramuje.facebook.kieszonkowevulcan;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.ButterKnife;
import commaciejprogramuje.facebook.kieszonkowevulcan.School.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.DataFile;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.GradesJavaScriptInterface;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.MyAlarm;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.MyWebViewClient;

import static android.content.Context.ALARM_SERVICE;
import static commaciejprogramuje.facebook.kieszonkowevulcan.Utils.GradesJavaScriptInterface.KIESZONKOWE_FILE;

public class HelloFragment extends Fragment {
    WebView firstFileBrowser;

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

        MainActivity.showProgressCircle();

        firstFileBrowser.getSettings().setJavaScriptEnabled(true);
        firstFileBrowser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        MyWebViewClient myWebViewClient = new MyWebViewClient(firstFileBrowser, MainActivity.getLogin(), MainActivity.getPassword());
        firstFileBrowser.setWebViewClient(myWebViewClient);
        firstFileBrowser.addJavascriptInterface(new GradesJavaScriptInterface(getContext()), "GRADES_HTMLOUT");
        firstFileBrowser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");

        Log.w("UWAGA", "ładuję stronę z HelloFragment");
    }

    public static HelloFragment newInstance() {
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
