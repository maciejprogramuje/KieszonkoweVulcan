package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator.Generator;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.TeacherFragment;

/**
 * Created by m.szymczyk on 2017-10-16.
 */

public class ShowTeachersFrag {
    private final MainActivity mainActivity;

    public ShowTeachersFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        TeacherFragment teacherFragment;
        if (mainActivity.checkInternetConn.checkInternetConnection(mainActivity)) {
            teacherFragment = TeacherFragment.newInstance(Generator.dataForTeachersFragment(mainActivity));
        } else {
            mainActivity.noInternetReaction.noInternetReaction(mainActivity);
            teacherFragment = TeacherFragment.newInstance("Włącz internet i odśwież przyciskiem!");
        }
        mainActivity.replaceFrag.replace(mainActivity, teacherFragment);
    }
}
