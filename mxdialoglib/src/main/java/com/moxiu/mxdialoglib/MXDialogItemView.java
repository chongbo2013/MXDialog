package com.moxiu.mxdialoglib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by xff on 2016/10/18.
 */

public class MXDialogItemView extends LinearLayout {

    TextView tv_mxdialog_common_item;


    public MXDialogItemView(Context context) {
        super(context);
    }

    public MXDialogItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MXDialogItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static MXDialogItemView getXml(Context mContext){
        return (MXDialogItemView) LayoutInflater.from(mContext).inflate(R.layout.tm_common_dialog_item,null);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_mxdialog_common_item=(TextView)findViewById(R.id.tv_mxdialog_common_item);
    }

    public void setMenuTitle(String title){
        tv_mxdialog_common_item.setText(title);
    }

    public void setMenu(MXDialog.Menu menu) {
        setMenuTitle(menu.getTitle());
    }
}
