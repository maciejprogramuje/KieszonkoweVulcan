package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator.Generator;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.NewsFragment;

public class ShowNewsFrag {
    private final MainActivity mainActivity;

    public ShowNewsFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void showNewsFragment() {
        NewsFragment newsFragment;
        if (mainActivity.checkInternetConn.checkInternetConnection(mainActivity)) {
            newsFragment = NewsFragment.newInstance(Generator.dataForNewsFragment(mainActivity.getSubjects()));
        } else {
            mainActivity.noInternetReaction.noInternetReaction(mainActivity);
            newsFragment = NewsFragment.newInstance("Włącz internet i odśwież przyciskiem!");
        }
        mainActivity.replaceFrag.replaceFragment(mainActivity, newsFragment);
    }
}