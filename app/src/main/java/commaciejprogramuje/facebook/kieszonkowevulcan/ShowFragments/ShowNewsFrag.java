package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import java.util.ArrayList;
import java.util.StringTokenizer;

import commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator.Generator;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.NewsFragment;

public class ShowNewsFrag {
    private final MainActivity mainActivity;

    public ShowNewsFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        NewsFragment newsFragment;
        if (mainActivity.checkInternetConn.checkInternetConnection(mainActivity)) {
            newsFragment = NewsFragment.newInstance(Generator.dataForNewsFragment(mainActivity.getSubjects()));
        } else {
            mainActivity.noInternetReaction.noInternetReaction(mainActivity);
            ArrayList<String> tempArray = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                tempArray.add("Włącz internet i odśwież przyciskiem!");
            }
            newsFragment = NewsFragment.newInstance(tempArray);
        }
        mainActivity.replaceFrag.replace(mainActivity, newsFragment);
    }
}