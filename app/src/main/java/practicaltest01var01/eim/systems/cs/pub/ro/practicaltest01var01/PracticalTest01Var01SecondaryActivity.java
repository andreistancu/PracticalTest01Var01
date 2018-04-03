package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var01SecondaryActivity extends AppCompatActivity {

    private TextView text = null;
    private Button registerButton = null;
    private Button cancelButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var01_secondary);

        text = (TextView) findViewById(R.id.text_recpt);
        registerButton = (Button) findViewById(R.id.button_register);
        cancelButton = (Button) findViewById(R.id.button_cancel);

        SecondListener listener = new SecondListener();
        registerButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);

        Intent intent = getIntent();
        if (intent != null) {
            String indications = intent.getStringExtra(Constants.GIVEN_ORIENTATIONS);
            text.setText(indications);
        }
    }

    private class SecondListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_register:
                    setResult(RESULT_OK);
                    break;
                case R.id.button_cancel:
                    setResult(RESULT_CANCELED);
                    break;
            }
            finish();
        }
    }
}
