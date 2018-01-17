package commaciejprogramuje.facebook.kieszonkowevulcan.show_fragments;

import java.io.IOException;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.DataFile;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.MultiUtils;

import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.KIESZONKOWE_FILE_SEM_1;
import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.KIESZONKOWE_FILE_SEM_2;

public class ShowGradesFrag {
    private final MainActivity mainActivity;

    public ShowGradesFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        if (MultiUtils.isInternetConnection(mainActivity)) {
            try {
                GradesFragment gradesFragment;
                if(!MainActivity.isSemestrFlag()) {
                    gradesFragment = GradesFragment.newInstance(DataFile.read(mainActivity.getApplicationContext(), KIESZONKOWE_FILE_SEM_1));
                } else {
                    gradesFragment = GradesFragment.newInstance(DataFile.read(mainActivity.getApplicationContext(), KIESZONKOWE_FILE_SEM_2));
                }
                mainActivity.replaceFrag.replace(mainActivity, gradesFragment);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            MultiUtils.noInternetConnectionReaction(mainActivity);
        }
    }
}