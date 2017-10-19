package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator.Generator;
import commaciejprogramuje.facebook.kieszonkowevulcan.GradesFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.HelloFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

public class ShowGradesFrag {
    private final MainActivity mainActivity;

    public ShowGradesFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        if (mainActivity.checkInternetConn.checkInternetConnection(mainActivity)) {
            GradesFragment gradesFragment = GradesFragment.newInstance(mainActivity.getSubjects());
            mainActivity.replaceFrag.replace(mainActivity, gradesFragment);
        } else {
            mainActivity.noInternetReaction.noInternetReaction(mainActivity);
        }
    }
}