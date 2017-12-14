package commaciejprogramuje.facebook.kieszonkowevulcan.show_fragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.TeacherFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.html_parsers.Teachers;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.MultiUtils;

public class ShowTeachersFrag {
    private final MainActivity mainActivity;

    public ShowTeachersFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        if (MultiUtils.isInternetConnection(mainActivity)) {
            TeacherFragment teacherFragment = TeacherFragment.newInstance();
            mainActivity.replaceFrag.replace(mainActivity, teacherFragment);
        } else {
            MultiUtils.noInternetConnectionReaction(mainActivity);
        }
    }
}
