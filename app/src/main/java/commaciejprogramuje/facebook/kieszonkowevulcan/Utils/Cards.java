package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import commaciejprogramuje.facebook.kieszonkowevulcan.R;

/**
 * Created by m.szymczyk on 2017-10-18.
 */

public class Cards {

    public static LinearLayout generateNewsCards(LinearLayout scrollViewFragment) {
        LinearLayout.LayoutParams paramsForLinearLayout1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsForLinearLayout1.setMargins(0, 0, 0, 1);

        LinearLayout linearLayout1 = new LinearLayout(scrollViewFragment.getContext());
        linearLayout1.setLayoutParams(paramsForLinearLayout1);
        linearLayout1.setBackgroundColor(ContextCompat.getColor(scrollViewFragment.getContext(), android.R.color.white));
        linearLayout1.setPadding(16, 32, 16, 16);

        LinearLayout.LayoutParams paramsForLinearLayout2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsForLinearLayout2.setMargins(0, 0, 0, 1);
        paramsForLinearLayout2.weight = 1;

        LinearLayout.LayoutParams paramsForCircle = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsForCircle.setMargins(16, 0, 32, 0);

        ImageView circleImage = new ImageView(linearLayout1.getContext());
        circleImage.setLayoutParams(paramsForCircle);
        circleImage.setImageResource(R.drawable.ic_circle_white);

        LinearLayout linearLayout2 = new LinearLayout(linearLayout1.getContext());
        linearLayout2.setLayoutParams(paramsForLinearLayout2);
        linearLayout2.setOrientation(LinearLayout.VERTICAL);

        TextView subjectTextView = new TextView(linearLayout2.getContext());
        subjectTextView.setTypeface(subjectTextView.getTypeface(), Typeface.BOLD);
        subjectTextView.setTextSize(16);

        TextView textTextView = new TextView(linearLayout2.getContext());
        textTextView.setTextSize(13);

        linearLayout2.addView(subjectTextView);
        linearLayout2.addView(textTextView);

        linearLayout1.addView(circleImage);
        linearLayout1.addView(linearLayout2);

        return linearLayout1;
    }
}
