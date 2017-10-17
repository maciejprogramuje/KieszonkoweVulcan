package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsFragment extends Fragment {
    public static final String NEWS_KEY = "news";
    @InjectView(R.id.scroll_view_fragment)
    LinearLayout scrollViewFragment;


    private ArrayList<TextView> textViewArray;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.inject(this, view);

        textViewArray = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            TextView textView = new TextView(scrollViewFragment.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 1);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(16, 16, 16, 16);
            textView.setText("textView nr " + i);
            textView.setBackgroundColor(ContextCompat.getColor(scrollViewFragment.getContext(), android.R.color.white));

            textViewArray.add(textView);

            scrollViewFragment.addView(textView);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            ArrayList<String> gradesArray = getArguments().getStringArrayList(NEWS_KEY);

            for (int i = 0; i < 16; i++) {
                textViewArray.get(i).setText(gradesArray.get(i));
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
