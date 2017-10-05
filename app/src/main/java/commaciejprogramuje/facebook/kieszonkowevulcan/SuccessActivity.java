package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SuccessActivity extends AppCompatActivity {

    @InjectView(R.id.resultTextView)
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            resultTextView.setText(savedInstanceState.getString("result"));
        }

        setContentView(R.layout.activity_success);
        ButterKnife.inject(this);
    }
}
