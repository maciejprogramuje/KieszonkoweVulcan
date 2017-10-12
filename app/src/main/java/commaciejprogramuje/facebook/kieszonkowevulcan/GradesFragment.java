package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class GradesFragment extends Fragment {
    public static final String GRADES_KEY = "grades";

    @InjectView(R.id.grades_fragment_textview)
    TextView gradesFragmentTextview;

    private String grades;

    public GradesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grades, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            grades = getArguments().getString(GRADES_KEY);
            gradesFragmentTextview.setText(grades);
        }
    }

    public static GradesFragment newInstance(String initialGrades) {
        GradesFragment fragment = new GradesFragment();
        Bundle args = new Bundle();
        args.putString(GRADES_KEY, initialGrades);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
