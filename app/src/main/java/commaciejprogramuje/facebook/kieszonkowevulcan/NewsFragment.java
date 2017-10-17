package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText("textView nr " + i);

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
