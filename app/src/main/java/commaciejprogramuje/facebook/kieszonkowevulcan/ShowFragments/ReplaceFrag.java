package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.R;

public class ReplaceFrag {
    public ReplaceFrag() {
    }

    public void replace(MainActivity mainActivity, Fragment fragment) {
        FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }
}