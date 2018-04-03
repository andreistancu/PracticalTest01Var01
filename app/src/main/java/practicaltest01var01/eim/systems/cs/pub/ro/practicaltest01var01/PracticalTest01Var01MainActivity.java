package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PracticalTest01Var01MainActivity extends AppCompatActivity {

    private Button northButton = null;
    private Button southButton = null;
    private Button eastButton = null;
    private Button westButton = null;
    private Button switchButton = null;
    private TextView text = null;
    private static int noButtons = 0;
    //private ArrayList arr = new ArrayList();
    private static final int SECONDARY_ACTIVITY_REQUEST_CODE = 1;
    private String serviceStatus = Constants.SERVICE_STOPPED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var01_main);

        //get refs
        northButton = (Button) findViewById(R.id.button_north);
        southButton = (Button) findViewById(R.id.button_south);
        eastButton = (Button) findViewById(R.id.button_east);
        westButton = (Button) findViewById(R.id.button_west);
        switchButton = (Button) findViewById(R.id.button_switch);
        text = (TextView) findViewById(R.id.textView);

        FirstListener listener = new FirstListener();
        northButton.setOnClickListener(listener);
        eastButton.setOnClickListener(listener);
        southButton.setOnClickListener(listener);
        westButton.setOnClickListener(listener);
        switchButton.setOnClickListener(listener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.NO_PUSHED_BUTTONS, "" + noButtons);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            String val = savedInstanceState.getString(Constants.NO_PUSHED_BUTTONS);
            Toast.makeText(getApplicationContext(), "No pushed buttons " + val, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            //Button b;
            String buttonText;
            if (resultCode == RESULT_OK) {
               //b = (Button) findViewById(R.id.button_register);
                buttonText = "REGISTER";
            } else {
                //b = (Button) findViewById(R.id.button_cancel);
                buttonText = "CANCEL";
            }
            //String buttonText = b.getText().toString();
            Toast.makeText(this, "Activity returned with " + buttonText, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    private class FirstListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String buttonText = null;
            switch(view.getId()) {
                case R.id.button_north:
                    buttonText = northButton.getText().toString();
                    break;
                case R.id.button_south:
                    buttonText = southButton.getText().toString();
                    break;
                case R.id.button_west:
                    buttonText = westButton.getText().toString();
                    break;
                case R.id.button_east:
                    buttonText = eastButton.getText().toString();
                    break;
                case R.id.button_switch:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var01SecondaryActivity.class);
                    intent.putExtra(Constants.GIVEN_ORIENTATIONS, text.getText().toString());
                    text.setText("");
                    noButtons = 0;
                    startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }
            if (buttonText != null) {
                String retText = text.getText().toString();
                retText += buttonText + ", ";
                text.setText(retText);
                noButtons++;
            }

            if (noButtons == 4) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var01Service.class);
                intent.putExtra(Constants.GIVEN_ORIENTATIONS, text.getText().toString());
                startService(intent);
                serviceStatus = Constants.SERVCE_STARTED;
            }
        }
    }
}
