package commaciejprogramuje.facebook.kieszonkowevulcan;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import commaciejprogramuje.facebook.kieszonkowevulcan.fragments_adapters.TeachersAdapter;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Teacher;
import commaciejprogramuje.facebook.kieszonkowevulcan.html_parsers.Teachers;

public class TeacherFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Teacher>> {
    public static final String TEACHERS_KEY = "teachers";
    public static final String SUBSTITUTE_DATE_KEY = "substituteDate";

    @InjectView(R.id.teacher_recycler_view)
    RecyclerView teacherRecyclerView;

    String tempSubstituteDate;
    private TeachersAdapter teachersAdapter;

    public TeacherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);
        ButterKnife.inject(this, view);

        MainActivity.hideFab();

        getLoaderManager().initLoader(1, null, this);

        teacherRecyclerView.setHasFixedSize(true);
        teacherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        teacherRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        teachersAdapter = new TeachersAdapter();
        teacherRecyclerView.setAdapter(teachersAdapter);
    }

    public static TeacherFragment newInstance() {
        return new TeacherFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        assert getActivity() != null;
        ((MainActivity) getActivity()).setActionBarSubtitle("");
        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        assert getActivity() != null;
        ((MainActivity) getActivity()).setActionBarTitle("Zastępstwa");
        ((MainActivity) getActivity()).setActionBarSubtitle(tempSubstituteDate);
    }

    @Override
    public Loader<ArrayList<Teacher>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Teacher>>(MainActivity.getMainActivity().getBaseContext()) {
            ArrayList<Teacher> teacherArrayList = null;

            @Override
            protected void onStartLoading() {
                if (teacherArrayList != null) {
                    deliverResult(teacherArrayList);
                } else {
                    MainActivity.showProgressCircle();
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public ArrayList<Teacher> loadInBackground() {
                return Teachers.getArray();
            }

            public void deliverResult(ArrayList<Teacher> data) {
                teacherArrayList = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Teacher>> loader, ArrayList<Teacher> data) {
        MainActivity.hideProgressCircle();
        teachersAdapter.setmTeachers(data);
        teachersAdapter.notifyDataSetChanged();

        if (data == null) {
            Log.w("UWAGA", "problem w TeacherFragment onLoadFinished");
        } else {
            teacherRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Teacher>> loader) {

    }
}
