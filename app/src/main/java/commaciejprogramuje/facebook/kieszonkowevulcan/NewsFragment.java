package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.FragmentsAdapters.NewsAdapter;
import commaciejprogramuje.facebook.kieszonkowevulcan.School.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.MyAlarm;

import static android.content.Context.ALARM_SERVICE;


public class NewsFragment extends Fragment {
    public static final String NEWS_KEY = "news";

    @InjectView(R.id.news_recycler_view)
    RecyclerView newsRecyclerView;

    private NewsAdapter newsAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.inject(this, view);

        MainActivity.showFab();

        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            Subjects subjects = (Subjects) getArguments().getSerializable(NEWS_KEY);
            newsAdapter = new NewsAdapter(subjects);
            newsRecyclerView.setAdapter(newsAdapter);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        newsAdapter.notifyDataSetChanged();
    }

    public static NewsFragment newInstance(Subjects subjects) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putSerializable(NEWS_KEY, subjects);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Nowe oceny");
    }
}
