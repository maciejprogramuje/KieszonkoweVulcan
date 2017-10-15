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

public class HelloFragment extends Fragment {


    public static final String HELLO_KEY = "hello";
    @InjectView(R.id.hello_fragment_textview)
    TextView helloFragmentTextview;

    //private String mParam1;

    public HelloFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hello, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            helloFragmentTextview.setText(getArguments().getString(HELLO_KEY));
        }
    }

    public static HelloFragment newInstance(String helloText) {
        HelloFragment fragment = new HelloFragment();
        Bundle args = new Bundle();
        args.putString(HELLO_KEY, helloText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
