package com.moxiu.dialog;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.moxiu.mxdialoglib.MXDialog;

/**
 * test dialog
 * ferris.xu 2016-10-18 18:00:09
 */
public class DialogActivity extends AppCompatActivity {
    Button btn_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_main);
        btn_dialog = (Button) findViewById(R.id.btn_dialog);
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MXDialog(DialogActivity.this,R.style.TMShowDialog_Animation).
                        setBackgroundResource(R.drawable.tm_common_dialog_style_one_bg).
                        addMenu(0, "拍摄视频").//添加菜单
                        addLine(1,Color.GRAY).
                        addMenu(1, "导入视频").
                        addLine(2,Color.GRAY).
                        addMenu(2, "制作图片电影").
                        addLine(12,Color.GRAY).
                        addMenu(3, "取消").
                        setMXDialogMenuClickListem(new MXDialog.MXDialogMenuClickListem() {
                            @Override
                            public boolean onClick(MXDialog.Menu menu) {
                                Toast.makeText(DialogActivity.this, menu.getTitle(), Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        }).show();
            }
        });
    }
}
