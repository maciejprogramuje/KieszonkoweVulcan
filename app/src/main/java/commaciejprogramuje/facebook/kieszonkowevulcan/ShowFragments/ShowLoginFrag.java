package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import commaciejprogramuje.facebook.kieszonkowevulcan.LoginFragment;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;

public class ShowLoginFrag {
    private final MainActivity mainActivity;

    public ShowLoginFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        mainActivity.replaceFrag.replace(mainActivity, loginFragment);
    }
}