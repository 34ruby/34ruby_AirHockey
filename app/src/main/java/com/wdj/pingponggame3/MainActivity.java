package com.wdj.pingponggame3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main).setOnClickListener(OnMyClick);
    }

    Button.OnClickListener OnMyClick = new Button.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main:
                    startActivity(new Intent(MainActivity.this, AirHockeyActivity.class));
                    break;

            }
        }
    };
}