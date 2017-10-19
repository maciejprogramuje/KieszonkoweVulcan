package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import java.util.ArrayList;
import java.util.StringTokenizer;

import commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator.Generator;
import commaciejprogramuje.facebook.kieszonkowevulcan.HelloFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.NewsFragment;

public class ShowNewsFrag {
    private final MainActivity mainActivity;

    public ShowNewsFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        if (mainActivity.checkInternetConn.checkInternetConnection(mainActivity)) {
            NewsFragment newsFragment = NewsFragment.newInstance(mainActivity.getSubjects());
            mainActivity.replaceFrag.replace(mainActivity, newsFragment);
        } else {
            mainActivity.noInternetReaction.noInternetReaction(mainActivity);
        }
    }
}