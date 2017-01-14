
package com.abtotest.voiptest.Ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.abtotest.voiptest.R;

public class CheckActivity extends Activity {
    CheckBox wifiCheck, awakeChek, bgCheck;
    Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        wifiCheck = (CheckBox) findViewById(R.id.wifiCHeck);
        awakeChek = (CheckBox) findViewById(R.id.stayAwayCHeck);
        bgCheck = (CheckBox) findViewById(R.id.bgCHeck);
        save = (Button) findViewById(R.id.save_button);
        cancel = (Button) findViewById(R.id.cancel_button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bgCheck.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("app", MODE_PRIVATE).edit();
                    editor.putBoolean("check", true);
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("app", MODE_PRIVATE).edit();
                    editor.putBoolean("check", false);
                }
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
