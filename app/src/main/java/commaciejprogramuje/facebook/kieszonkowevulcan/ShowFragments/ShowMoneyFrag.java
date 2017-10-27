package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.MoneyFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.InternetUtils;

public class ShowMoneyFrag {
    private final MainActivity mainActivity;

    public ShowMoneyFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        if (InternetUtils.isConnection(mainActivity)) {
            MoneyFragment moneyFragment = MoneyFragment.newInstance(MainActivity.getSubjects());
            mainActivity.replaceFrag.replace(mainActivity, moneyFragment);
        } else {
            InternetUtils.noConnectionReaction(mainActivity);
        }
    }
}