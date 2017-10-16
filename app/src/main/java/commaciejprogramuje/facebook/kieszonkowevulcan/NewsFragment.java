package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsFragment extends Fragment {
    public static final String NEWS_KEY = "news";

    @InjectView(R.id.news_fragment_textview)
    TextView newsFragmentTextView;

    private String grades;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            grades = getArguments().getString(NEWS_KEY);
            newsFragmentTextView.setText(grades);
        }
    }

    public static NewsFragment newInstance(String initialGrades) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(NEWS_KEY, initialGrades);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
