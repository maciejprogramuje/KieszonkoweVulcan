package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.Cards;

public class NewsFragment extends Fragment {
    public static final String NEWS_KEY = "news";

    @InjectView(R.id.scroll_view_fragment)
    LinearLayout scrollViewFragment;

    private ArrayList<LinearLayout> linearLayoutArray;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.inject(this, view);

        linearLayoutArray = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            LinearLayout tempLinearLayout = Cards.generateNewsCards(scrollViewFragment);
            linearLayoutArray.add(tempLinearLayout);
            scrollViewFragment.addView(tempLinearLayout);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            ArrayList<String> gradesArray = getArguments().getStringArrayList(NEWS_KEY);

            for (int i = 0; i < 16; i++) {
                String[] input = gradesArray.get(i).split("XXX");

                LinearLayout tempLinearLayout = (LinearLayout) linearLayoutArray.get(i).getChildAt(1);
                TextView subjectTextView = (TextView) tempLinearLayout.getChildAt(0);
                TextView textTextView = (TextView) tempLinearLayout.getChildAt(1);

                subjectTextView.setText(input[0]);

                textTextView.setText(input[1]);
                for (int j = 2; j < input.length; j++) {
                    textTextView.setText(textTextView.getText() + "\n" + input[j]);
                }


                ImageView tempImageView = (ImageView) linearLayoutArray.get(i).getChildAt(0);
                if(input[0].contains("(")) {
                    Double avg = Double.valueOf(input[0].substring(input[0].indexOf("(") + 1, input[0].indexOf(")")).replace(",", "."));
                    if(avg < 3.00) {
                        tempImageView.setImageResource(R.drawable.ic_circle_red);
                    } else if (avg < 4.00) {
                        tempImageView.setImageResource(R.drawable.ic_circle_yellow);
                    } else if(avg < 5.00) {
                        tempImageView.setImageResource(R.drawable.ic_circle_green);
                    } else {
                        tempImageView.setImageResource(R.drawable.ic_circle_blue);
                    }
                }
            }
        }
    }

    public static NewsFragment newInstance(ArrayList<String> initialGrades) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(NEWS_KEY, initialGrades);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
