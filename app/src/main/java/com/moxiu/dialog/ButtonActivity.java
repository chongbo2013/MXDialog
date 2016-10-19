package com.moxiu.dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.moxiu.dialog.button.FollowButton;

/**
 * test dialog
 * ferris.xu 2016-10-18 18:00:09
 */
public class ButtonActivity extends AppCompatActivity {
    FollowButton btn_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_main);
        btn_dialog = (FollowButton) findViewById(R.id.btn_dialog);
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_dialog.targetSelect();
            }
        });
    }
}
