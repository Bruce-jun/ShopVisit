package com.my.shopvisit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.shopvisit.R;

/**
 *
 * Created by admin on 2017/7/26.
 */

public class MeItemView extends RelativeLayout {
    private RelativeLayout mRootLayout;
    private TextView mTvLeftText;
    private ImageView mIvLeftIcon;
    private ImageView mIvRightIcon;
    private View mUnderLine;
    private String mText;
    private Drawable mLeftIcon;
    private Drawable mRightIcon;
    private int mTextColor;
    private OnItemViewClick mOnItemViewClick;

    public MeItemView(Context context) {
        this(context,null);
    }

    public MeItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MeItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        getCustomStyle(context, attrs);

        mRootLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemViewClick!=null){
                    mOnItemViewClick.onClick();
                }
            }
        });
    }


    /**
     * @param context
     * 初始化控件对象
     */
    private void initView(Context context) {
        View mView = View.inflate(context, R.layout.me_item, this);
        mRootLayout = (RelativeLayout) mView.findViewById(R.id.rootLayout);
        mTvLeftText = (TextView) mView.findViewById(R.id.tv_lefttext);
        mIvLeftIcon = (ImageView) mView.findViewById(R.id.iv_lefticon);
        mIvRightIcon = (ImageView) mView.findViewById(R.id.iv_righticon);
        mUnderLine = mView.findViewById(R.id.underline);
    }

    /**
     * @param context
     * @param attrs
     * 初始化自定义属性
     */
    private void getCustomStyle(Context context, AttributeSet attrs) {
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ItemView);
        int num=typedArray.getIndexCount();
        for (int i = 0; i < num; i++) {
            int arr = typedArray.getIndex(i);
            if (arr == R.styleable.ItemView_leftText) {
                mText = typedArray.getString(arr);
                mTvLeftText.setText(mText);
            } else if (arr == R.styleable.ItemView_leftIcon) {
                mLeftIcon = typedArray.getDrawable(arr);
                mIvLeftIcon.setImageDrawable(mLeftIcon);
            } else if (arr == R.styleable.ItemView_rightIcon) {
                mRightIcon = typedArray.getDrawable(arr);
                mIvRightIcon.setImageDrawable(mRightIcon);
            } else if (arr == R.styleable.ItemView_textSize) {
                // 默认设置为16sp
                float textSize = typedArray.getFloat(arr, 16);
                mTvLeftText.setTextSize(textSize);
            } else if (arr == R.styleable.ItemView_textColor) {
                //文字默认灰色
                mTextColor = typedArray.getColor(arr, Color.GRAY);
                mTvLeftText.setTextColor(mTextColor);
            } else if (arr == R.styleable.ItemView_isShowUnderLine) {
                boolean flag = typedArray.getBoolean(arr, true);
                if (!flag) {
                    mUnderLine.setVisibility(View.GONE);
                }
            }
        }
        typedArray.recycle();
    }

    public void setmOnSetItemClick(OnItemViewClick mOnSetItemClick) {
        this.mOnItemViewClick = mOnSetItemClick;
    }
    public interface OnItemViewClick{
        void  onClick();
    }
}
