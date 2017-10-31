package commaciejprogramuje.facebook.kieszonkowevulcan.show_fragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.html_parsers.Teachers;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.TeacherFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.InternetUtils;

/**
 * Created by m.szymczyk on 2017-10-16.
 */

public class ShowTeachersFrag {
    private final MainActivity mainActivity;

    public ShowTeachersFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        if (InternetUtils.isConnection(mainActivity)) {
            TeacherFragment teacherFragment = TeacherFragment.newInstance(Teachers.getArray(), Teachers.getDate());
            mainActivity.replaceFrag.replace(mainActivity, teacherFragment);
        } else {
            InternetUtils.noConnectionReaction(mainActivity);
        }
    }
}
