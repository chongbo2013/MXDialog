package com.moxiu.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * test dialog
 * ferris.xu 2016-10-18 18:00:09
 */
public class MainActivity extends AppCompatActivity {
    Button btn_dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_dialog= (Button) findViewById(R.id.btn_dialog);
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MXDialog(MainActivity.this).setBackgroundResource(R.drawable.tm_common_dialog_style_one_bg).setItemWidth(300).addMenu(0,"first").addMenu(1,"2").addMenu(2,"3").setMXDialogMenuClickListem(new MXDialog.MXDialogMenuClickListem() {
                    @Override
                    public boolean onClick(MXDialog.Menu menu) {
                        Toast.makeText(MainActivity.this,menu.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }).show();
            }
        });
    }
}
