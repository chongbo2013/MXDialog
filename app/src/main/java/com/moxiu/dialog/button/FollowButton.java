package com.moxiu.dialog.button;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.moxiu.dialog.R;
/**
 * 关注和收藏的按钮
 * Created by xff on 2016/10/19.
 */

public class FollowButton extends View {
    private Paint mPaint;
    private RectF targetRect;
    //字体大小
    private float textSize;
    //按钮弧度
    private float radius;
    //未选中的颜色
    private int unselectedColor;
    //选中的颜色
    private int selectColor;
    //选中显示的文字
    private String followText;
    //未选中显示的文字
    private String unfollowText;
    //是否选中
    private boolean isSelect;

    private float followStroke;
    public FollowButton(Context context) {
        super(context);
        init(null, 0);
    }

    public FollowButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public FollowButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    /**
     * 选中显示文字
     * @param followText
     */
    public void setFollowText(String followText){
        this.followText=followText;
    }

    /**
     * 未选中显示文字
     * @param unFollowText
     */
    public void setUnfollowText(String unFollowText){
        this.unfollowText=unFollowText;
    }

    /**
     * 设置是否选中
     * @param isSelect
     */
    public void setSelect(boolean isSelect){
        this.isSelect=isSelect;
        postInvalidate();
    }

    public void targetSelect(){
        isSelect=!isSelect;
        setSelect(isSelect);
    }

    /**
     * 是否选中
     * @return
     */
    public boolean isSelect(){
        return isSelect;
    }
    public void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MXFollowButton, defStyle, 0);
        textSize = a.getDimension(R.styleable.MXFollowButton_followTextSize, 16);
        unselectedColor = a.getColor(R.styleable.MXFollowButton_followUnselectedColor, 0x4DCCEEFF);
        selectColor = a.getColor(R.styleable.MXFollowButton_followSelectColor, 0x00e5d2);
        followText = a.getString(R.styleable.MXFollowButton_followText);
        unfollowText = a.getString(R.styleable.MXFollowButton_unfollowText);
        radius = a.getDimension(R.styleable.MXFollowButton_followRadius, 26);
        isSelect = a.getBoolean(R.styleable.MXFollowButton_followSelect, false);
        followStroke= a.getDimension(R.styleable.MXFollowButton_followStroke, 1);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        targetRect = new RectF();
    }


    /**
     * 初始化背景
     */
    public void initBgColor() {
        mPaint.setColor(isSelect ? selectColor : unselectedColor);
        mPaint.setStyle(isSelect ? Paint.Style.FILL : Paint.Style.STROKE);
//        mPaint.setStrokeWidth(followStroke);
    }

    /**
     * 初始化字体颜色
     */
    public void initFontColor() {
        mPaint.setColor(isSelect ? Color.WHITE : unselectedColor);
        mPaint.setTextSize(textSize);
        mPaint.setStyle(Paint.Style.FILL );
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        //画背景
        targetRect.set(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + width, getPaddingBottom() + height);
        initBgColor();
        canvas.drawRoundRect(targetRect, radius, radius, mPaint);
        //画文字
        initFontColor();
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (int) ((targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.drawText(isSelect ? followText : unfollowText, targetRect.centerX(), baseline, mPaint);
    }
}
