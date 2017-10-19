package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator.Generator;
import commaciejprogramuje.facebook.kieszonkowevulcan.HelloFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.MoneyFragment;

public class ShowMoneyFrag {
    private final MainActivity mainActivity;

    public ShowMoneyFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        if (mainActivity.checkInternetConn.checkInternetConnection(mainActivity)) {
            MoneyFragment moneyFragment = MoneyFragment.newInstance(Generator.dataForMoneyFragment(mainActivity.getSubjects()));
            mainActivity.replaceFrag.replace(mainActivity, moneyFragment);
        } else {
            mainActivity.noInternetReaction.noInternetReaction(mainActivity);
        }
    }
}