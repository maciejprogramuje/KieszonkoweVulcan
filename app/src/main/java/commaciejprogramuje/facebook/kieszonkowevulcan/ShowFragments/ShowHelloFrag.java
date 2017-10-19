package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.HelloFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

public class ShowHelloFrag {
    private final MainActivity mainActivity;

    public ShowHelloFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void showHelloFragment() {
        HelloFragment helloFragment = HelloFragment.newInstance();
        mainActivity.replaceFrag.replace(mainActivity, helloFragment);
    }
}