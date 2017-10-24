package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.NewsFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.InternetUtils;

public class ShowNewsFrag {
    private final MainActivity mainActivity;

    public ShowNewsFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        if (InternetUtils.isConnection(mainActivity)) {
            NewsFragment newsFragment = NewsFragment.newInstance(mainActivity.getSubjects());
            mainActivity.replaceFrag.replace(mainActivity, newsFragment);
        } else {
            InternetUtils.noConnectionReaction(mainActivity);
        }
    }
}