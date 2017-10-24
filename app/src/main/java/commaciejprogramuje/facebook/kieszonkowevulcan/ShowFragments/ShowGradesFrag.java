package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.InternetUtils;

public class ShowGradesFrag {
    private final MainActivity mainActivity;

    public ShowGradesFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        if (InternetUtils.isConnection(mainActivity)) {
            GradesFragment gradesFragment = GradesFragment.newInstance(mainActivity.getSubjects());
            mainActivity.replaceFrag.replace(mainActivity, gradesFragment);
        } else {
            InternetUtils.noConnectionReaction(mainActivity);
        }
    }
}