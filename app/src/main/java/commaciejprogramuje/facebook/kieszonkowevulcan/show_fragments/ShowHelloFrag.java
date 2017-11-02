package commaciejprogramuje.facebook.kieszonkowevulcan.show_fragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.HelloFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

public class ShowHelloFrag {
    private final MainActivity mainActivity;

    public ShowHelloFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        HelloFragment helloFragment = HelloFragment.newInstance(true);
        mainActivity.replaceFrag.replace(mainActivity, helloFragment);
    }
}