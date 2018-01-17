package commaciejprogramuje.facebook.kieszonkowevulcan.show_fragments;

import java.io.IOException;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.MoneyFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.DataFile;
import commaciejprogramuje.facebook.kieszonkowevulcan.utils.MultiUtils;

import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.KIESZONKOWE_FILE_SEM_1;
import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.KIESZONKOWE_FILE_SEM_2;

public class ShowMoneyFrag {
    private final MainActivity mainActivity;

    public ShowMoneyFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        if (MultiUtils.isInternetConnection(mainActivity)) {
            try {
                MoneyFragment moneyFragment;
                if(!MainActivity.isSemestrFlag()) {
                    moneyFragment = MoneyFragment.newInstance(DataFile.read(mainActivity.getApplicationContext(), KIESZONKOWE_FILE_SEM_1));
                } else {
                    moneyFragment = MoneyFragment.newInstance(DataFile.read(mainActivity.getApplicationContext(), KIESZONKOWE_FILE_SEM_2));
                }
                mainActivity.replaceFrag.replace(mainActivity, moneyFragment);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            MultiUtils.noInternetConnectionReaction(mainActivity);
        }
    }
}