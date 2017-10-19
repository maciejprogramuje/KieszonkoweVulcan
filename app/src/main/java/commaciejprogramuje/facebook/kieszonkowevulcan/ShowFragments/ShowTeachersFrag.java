package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator.Generator;
import commaciejprogramuje.facebook.kieszonkowevulcan.HelloFragment;
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
        if (mainActivity.checkInternetConn.checkInternetConnection(mainActivity)) {
            TeacherFragment teacherFragment = TeacherFragment.newInstance(Generator.dataForTeachersFragment());
            mainActivity.replaceFrag.replace(mainActivity, teacherFragment);
        } else {
            mainActivity.noInternetReaction.noInternetReaction(mainActivity);
        }
    }
}
