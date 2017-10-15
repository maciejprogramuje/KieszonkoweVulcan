package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.content.Context;
import android.widget.Toast;

public class NoInternetReaction {
    public NoInternetReaction() {
    }

    public void noInternetReaction(Context context) {
        Toast.makeText(context, "Włącz internet!", Toast.LENGTH_LONG).show();
    }
}