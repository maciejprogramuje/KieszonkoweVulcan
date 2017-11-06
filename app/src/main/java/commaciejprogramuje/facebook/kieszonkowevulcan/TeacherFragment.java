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

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.fragments_adapters.TeachersAdapter;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Teacher;

public class TeacherFragment extends Fragment {
    public static final String TEACHERS_KEY = "teachers";
    public static final String SUBSTITUTE_DATE_KEY = "substituteDate";

    @InjectView(R.id.teacher_recycler_view)
    RecyclerView teacherRecyclerView;

    String tempSubstituteDate;

    public TeacherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);
        ButterKnife.inject(this, view);

        MainActivity.hideFab();

        teacherRecyclerView.setHasFixedSize(true);
        teacherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        teacherRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            ArrayList<Teacher> teachersArray = (ArrayList<Teacher>) getArguments().getSerializable(TEACHERS_KEY);
            tempSubstituteDate = getArguments().getString(SUBSTITUTE_DATE_KEY);

            TeachersAdapter teachersAdapter = new TeachersAdapter(teachersArray);
            teacherRecyclerView.setAdapter(teachersAdapter);
        }
    }

    public static TeacherFragment newInstance(ArrayList<Teacher> teachersArray, String substituteDate) {
        TeacherFragment fragment = new TeacherFragment();
        Bundle args = new Bundle();
        args.putSerializable(TEACHERS_KEY, teachersArray);
        args.putString(SUBSTITUTE_DATE_KEY, substituteDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) getActivity()).setActionBarSubtitle("");
        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Zastępstwa");
        ((MainActivity) getActivity()).setActionBarSubtitle(tempSubstituteDate);
    }
}
