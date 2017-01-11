package com.moxiu.mxdialoglib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MXDialog {
    private Context mContext;
    private MXDialog.Builder mBuilder;
    private Dialog mAlertDialog;
    private Drawable mBackgroundDrawable;
    private int mBackgroundResId = -1;
    //菜单的ID 和菜单的标题
    private ArrayList<Menu> menus;
    private float itemWidth = 100;


    public MXDialog(Context context) {
        this.mContext = context;
        if (mBuilder == null) {
            mBuilder = new Builder(R.style.TMShowDialog);
        }
    }
    public MXDialog(Context context,int style) {
        this.mContext = context;
        if (mBuilder == null) {
            mBuilder = new Builder(style);
        }
    }


    public void show() {
        if (mBuilder == null) {
            mBuilder.commit();
        }
        mAlertDialog.show();
    }


    public MXDialog setBackground(Drawable drawable) {
        mBackgroundDrawable = drawable;
        if (mBuilder != null) {
            mBuilder.setBackground(mBackgroundDrawable);
        }
        return this;
    }

    public MXDialog setItemWidth(float width) {
        itemWidth = width;
        return this;
    }


    public MXDialog setBackgroundResource(int resId) {
        mBackgroundResId = resId;
        if (mBuilder != null) {
            mBuilder.setBackgroundResource(mBackgroundResId);
        }
        return this;
    }


    public MXDialog addMenu(int menuId, String title,int grarys) {

        if (mBuilder != null) {
            mBuilder.addMenu(menuId, title,grarys);
        }
        return this;
    }
    public MXDialog addMenu(int menuId, String title) {

        if (mBuilder != null) {
            mBuilder.addMenu(menuId, title,Gravity.CENTER);
        }
        return this;
    }
    public MXDialog addLine(int hight, int color) {

        if (mBuilder != null) {
            mBuilder.addLine(hight, color);
        }
        return this;
    }


    public void dismiss() {
        mAlertDialog.dismiss();
    }

    private MXDialogMenuClickListem mListem;


    public MXDialog setMXDialogMenuClickListem(MXDialogMenuClickListem mListem) {
        this.mListem = mListem;
        return this;
    }

    public MXDialog setItemLineEnable(boolean bool) {
        if (mBuilder != null) {
            mBuilder.setItemLineEnable(bool);
        }
        return this;
    }

    public MXDialog setItemLineColor(int gray) {
        if (mBuilder != null) {
            mBuilder.setItemLineColor(gray);
        }
        return this;
    }


    private class Builder implements View.OnClickListener {
        private Window mAlertDialogWindow;

        int style=R.style.TMShowDialog;
        boolean enableLine=false;

        int itemLineColor= Color.GRAY;
        public Builder(int style) {
            this.style=style;
            mAlertDialog = new Dialog(mContext, style);
            mAlertDialog.getWindow()
                    .clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            mAlertDialog.getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_STATE);

            mAlertDialogWindow = mAlertDialog.getWindow();
            mAlertDialogWindow.setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));

            View contentView = LayoutInflater.from(mContext)
                    .inflate(R.layout.tm_common_dialog, null);
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);
            mAlertDialogWindow.setContentView(contentView);

            if(style==R.style.TMShowDialog_Animation){
            mAlertDialogWindow.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = mAlertDialogWindow.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.BOTTOM;
            mAlertDialogWindow.setAttributes(lp);
            }
        }

        public void setBackground(Drawable drawable) {
            LinearLayout linearLayout = (LinearLayout) mAlertDialogWindow.findViewById(
                    R.id.ll_mxdiaolog_root);
            linearLayout.setBackground(drawable);
        }

        public void setBackgroundResource(int resId) {
            LinearLayout linearLayout = (LinearLayout) mAlertDialogWindow.findViewById(
                    R.id.ll_mxdiaolog_root);
            linearLayout.setBackgroundResource(resId);
        }

        private void addView(View mView) {
            LinearLayout linearLayout = (LinearLayout) mAlertDialogWindow.findViewById(
                    R.id.ll_mxdiaolog_root);
            linearLayout.addView(mView);
        }

        private LinearLayout getViewGroup(){
            return (LinearLayout) mAlertDialogWindow.findViewById(
                    R.id.ll_mxdiaolog_root);
        }

        public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mAlertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            mAlertDialog.setCancelable(canceledOnTouchOutside);
        }


        public void addLine(int hight, int color) {
            View line=new View(mContext);
            line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,hight));
            line.setBackgroundColor(color);
            addView(line);
        }

        public void addMenu(int menuId, String title,int gravity) {
            if (menus == null) {
                menus = new ArrayList<>();
            }
            Menu menu = new Menu();
            menu.setId(menuId);
            menu.setTitle(title);
            menus.add(menu);
            View menuView=createMenu(menu);
            addView(menuView);
            if(menuView.getLayoutParams()!=null&&menuView.getLayoutParams() instanceof LinearLayout.LayoutParams){
                LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) menuView.getLayoutParams();
                lp.gravity=gravity;
                menuView.setLayoutParams(lp);
            }
            if(enableLine)
            addView(addLineView());
        }


        private View addLineView() {
            View line=new View(mContext);
            line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
            line.setBackgroundColor(itemLineColor);
            return line;
        }

        private MXDialogItemView createMenu(Menu menu) {
            MXDialogItemView menuView = MXDialogItemView.getXml(mContext);
            menuView.setMenu(menu);
            menuView.setTag(menu);
            ViewGroup.LayoutParams lp = menuView.getLayoutParams();
            if (style != R.style.TMShowDialog_Animation) {
                if (lp != null && itemWidth > 0) {
                    lp.width = (int) itemWidth;
                    menuView.setLayoutParams(lp);
                } else {
                    menuView.setLayoutParams(getGeneralParams(itemWidth));
                }
            } else {

                if (lp != null ) {
                    lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    menuView.setLayoutParams(lp);
                } else {
                    menuView.setLayoutParams(getGeneralParams(ViewGroup.LayoutParams.MATCH_PARENT));
                }
            }
            menuView.setOnClickListener(this);
            return menuView;
        }

        public ViewGroup.LayoutParams getGeneralParams(float width) {
            return new ViewGroup.LayoutParams((int) width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        @Override
        public void onClick(View v) {
            Object obj = v.getTag();
            if (obj != null && obj instanceof Menu) {
                Menu menu = (Menu) obj;
                if (mListem != null && mListem instanceof MXDialogMenuClickListem) {
                    if (mListem.onClick(menu)) {
                        dismiss();
                    }
                }
            }
        }


        public void setItemLineEnable(boolean bool) {
            this.enableLine=bool;
        }

        public void setItemLineColor(int gray) {
            this.itemLineColor=gray;
        }


        public void commit() {
            if(enableLine) {
                LinearLayout root = getViewGroup();
                if (root != null) {
                    if (root.getChildCount() > 1) {
                        root.removeViewAt(root.getChildCount()-1);
                    }
                }
            }
        }


    }


    public class Menu {
        int id = 0;
        String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public interface MXDialogMenuClickListem {
        boolean onClick(Menu menu);
    }

}
