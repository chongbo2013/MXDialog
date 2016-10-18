package com.moxiu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * 通用对话框
 * Created by xff on 2016/10/18.
 */

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
            mBuilder = new Builder();
        }
    }

    /**
     * 显示菜单
     */
    public void show() {
        mAlertDialog.show();
    }

    /**
     * 给菜单设置背景资源
     *
     * @param drawable
     * @return
     */
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

    /**
     * 给菜单设置背景资源
     *
     * @param resId
     * @return
     */
    public MXDialog setBackgroundResource(int resId) {
        mBackgroundResId = resId;
        if (mBuilder != null) {
            mBuilder.setBackgroundResource(mBackgroundResId);
        }
        return this;
    }

    /**
     * 添加菜单
     *
     * @param menuId 菜单id
     * @param title  菜单标题
     * @return
     */
    public MXDialog addMenu(int menuId, String title) {

        if (mBuilder != null) {
            mBuilder.addMenu(menuId, title);
        }
        return this;
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        mAlertDialog.dismiss();
    }

    private MXDialogMenuClickListem mListem;

    /**
     * @param mListem
     * @return
     */
    public MXDialog setMXDialogMenuClickListem(MXDialogMenuClickListem mListem) {
        this.mListem = mListem;
        return this;
    }


    private class Builder implements View.OnClickListener {
        private Window mAlertDialogWindow;

        private Builder() {
            //创建对话框
            mAlertDialog = new Dialog(mContext, R.style.TMShowDialog);
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

        public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mAlertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            mAlertDialog.setCancelable(canceledOnTouchOutside);
        }


        public void addMenu(int menuId, String title) {
            if (menus == null) {
                menus = new ArrayList<>();
            }
            Menu menu = new Menu();
            menu.setId(menuId);
            menu.setTitle(title);
            menus.add(menu);
            //添加菜单
            addView(createMenu(menu));
        }

        private MXDialogItemView createMenu(Menu menu) {
            MXDialogItemView menuView = MXDialogItemView.getXml(mContext);
            menuView.setMenu(menu);
            menuView.setTag(menu);
            ViewGroup.LayoutParams lp = menuView.getLayoutParams();
            if (lp != null && itemWidth > 0) {
                lp.width = (int) itemWidth;
                menuView.setLayoutParams(lp);
            } else {
                menuView.setLayoutParams(getGeneralParams(itemWidth));
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


    }

    //菜单
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
        /**
         * 点击返回菜单
         *
         * @param menu
         * @return true 关闭对话框，否则不关闭，或者手动关闭
         */
        boolean onClick(Menu menu);
    }

}
