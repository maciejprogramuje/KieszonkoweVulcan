package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.Adapters.GradesAdapter;
import commaciejprogramuje.facebook.kieszonkowevulcan.GradesUtils.Subjects;


public class GradesFragment extends Fragment {
    public static final String GRADES_KEY = "grades";

    @InjectView(R.id.grades_recycler_view)
    RecyclerView gradesRecyclerView;

    private GradesAdapter gradesAdapter;

    public GradesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grades, container, false);
        ButterKnife.inject(this, view);

        gradesRecyclerView.setHasFixedSize(true);
        gradesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        gradesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Subjects subjects = (Subjects) getArguments().getSerializable(GRADES_KEY);

            gradesAdapter = new GradesAdapter(subjects);
            gradesRecyclerView.setAdapter(gradesAdapter);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        gradesAdapter.notifyDataSetChanged();
    }

    public static GradesFragment newInstance(Subjects subjects) {
        GradesFragment fragment = new GradesFragment();
        Bundle args = new Bundle();
        args.putSerializable(GRADES_KEY, subjects);
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
        ((MainActivity) getActivity()).setActionBarTitle("Oceny");
    }
}
