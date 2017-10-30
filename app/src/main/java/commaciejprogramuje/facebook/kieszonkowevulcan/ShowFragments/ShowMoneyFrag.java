package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import java.io.IOException;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.MoneyFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.DataFile;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.InternetUtils;

import static commaciejprogramuje.facebook.kieszonkowevulcan.Utils.GradesJavaScriptInterface.KIESZONKOWE_FILE;

public class ShowMoneyFrag {
    private final MainActivity mainActivity;

    public ShowMoneyFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        if (InternetUtils.isConnection(mainActivity)) {
            try {
                MoneyFragment moneyFragment = MoneyFragment.newInstance(DataFile.read(mainActivity.getApplicationContext(), KIESZONKOWE_FILE));
                mainActivity.replaceFrag.replace(mainActivity, moneyFragment);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            InternetUtils.noConnectionReaction(mainActivity);
        }
    }
}