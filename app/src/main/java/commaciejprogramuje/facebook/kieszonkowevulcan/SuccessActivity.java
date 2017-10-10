package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.RESULTS_KEY;

public class SuccessActivity extends AppCompatActivity {

    @InjectView(R.id.resultTextView)
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if(savedInstanceState != null) {
            resultTextView.setText(savedInstanceState.getString(RESULTS_KEY));
        }*/

        setContentView(R.layout.activity_success);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        String string = intent.getStringExtra(RESULTS_KEY);
        resultTextView.setText(string);
    }
}
