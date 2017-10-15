package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator.Generator;
import commaciejprogramuje.facebook.kieszonkowevulcan.GradesFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

public class ShowGradesFrag {
    private final MainActivity mainActivity;

    public ShowGradesFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void showGradesFragment() {
        GradesFragment gradesFragment;
        if (mainActivity.checkInternetConn.checkInternetConnection(mainActivity)) {
            gradesFragment = GradesFragment.newInstance(Generator.dataForGradesFragment(mainActivity.getSubjects()));
        } else {
            mainActivity.noInternetReaction.noInternetReaction(mainActivity);
            gradesFragment = GradesFragment.newInstance("Włącz internet i odśwież przyciskiem!");
        }
        mainActivity.replaceFrag.replaceFragment(mainActivity, gradesFragment);
    }
}