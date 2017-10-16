package commaciejprogramuje.facebook.kieszonkowevulcan;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TeacherFragment extends Fragment {
    public static final String TEACHERS_KEY = "teachers";

    @InjectView(R.id.teachers_fragment_textview)
    TextView teachersFragmentTextview;

    public TeacherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    public static TeacherFragment newInstance(String teachersData) {
        TeacherFragment fragment = new TeacherFragment();
        Bundle args = new Bundle();
        args.putString(TEACHERS_KEY, teachersData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String teachers = getArguments().getString(TEACHERS_KEY);
            teachersFragmentTextview.setText(teachers);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
