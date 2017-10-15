package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator.Generator;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.MoneyFragment;

public class ShowMoneyFrag {
    private final MainActivity mainActivity;

    public ShowMoneyFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void showMoneyFragment() {
        MoneyFragment moneyFragment;
        if (mainActivity.checkInternetConn.checkInternetConnection(mainActivity)) {
            moneyFragment = MoneyFragment.newInstance(Generator.dataForMoneyFragment(mainActivity.getSubjects()));
        } else {
            mainActivity.noInternetReaction.noInternetReaction(mainActivity);
            moneyFragment = MoneyFragment.newInstance("Włącz internet i odśwież przyciskiem!");
        }
        mainActivity.replaceFrag.replaceFragment(mainActivity, moneyFragment);
    }
}