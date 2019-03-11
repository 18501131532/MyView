package com.example.duanxiaqingqq.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends ViewGroup {
    public CustomView(MainActivity context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //存储每一行的高度
    List<Integer> listHeight = new ArrayList<>();
    //存储每一行View的个数
    List<List<View>> allList = new ArrayList<>();

    //测量自定义 View 的大小
    //如果在自定义控件的时候，不重写 onMeasure 方法的话，
    // 就只能使用 EXACTLY，也就是 layout_width 和 layout_height 只能使用具体数值或者 match_parent。
    @Override
    //MeasureSpec封装了父布局传递给子View的布局要求
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //当测量模式为 EXACTLY 时，直接使用 specSize 即可；
        //当测量模式为 AT_MOST 时，去除我们制定的大小与 specSize 中的最小是来作为最后的测量值；
        //当测量模式为 UNSPECIFIED 时，直接使用我们制定的大小。
        //获取父View的宽高以及模式
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);

        //记录当前自定义View的宽高
        int measurewidth = 0;
        int measureheight = 0;

        //记录每一行宽和行高
        int containe_width = 0;
        int containe_height = 0;

        //记录子View的宽高
        int childwidth = 0;
        int childheight = 0;

        //match_parent 具体值
        if (widthmode == MeasureSpec.EXACTLY && heightmode == MeasureSpec.EXACTLY) {
            measurewidth = widthsize;
            measureheight = heightsize;
        }
        //存储每一行View的集合
        List<View> itemList = new ArrayList<>();

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            //measureChild     用来测量子View的宽高
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
            //getMeasuredWidth()是在onMeasure()中设置得到的
            childwidth = params.leftMargin + params.rightMargin + view.getMeasuredWidth();
            childheight = params.topMargin + params.bottomMargin + view.getMeasuredHeight();
            if (containe_width + childwidth > widthsize) {
                measureheight += containe_height;
                //记录上一行的信息
                measurewidth = Math.max(measurewidth, containe_width);
                //将行高存储
                listHeight.add(containe_height);
                allList.add(itemList);

                containe_height = childheight;
                //另起一行的宽度
                containe_width = childwidth;

                itemList = new ArrayList<>();
                itemList.add(view);

            } else {
                //宽度叠加
                containe_width += childwidth;
                //取当前View的高度和行高  取最大
                containe_height = Math.max(containe_height, childheight);
                itemList.add(view);

                if(getChildCount()-1 == i){
                    measureheight += containe_height ;
                    measurewidth = Math.max(measurewidth, containe_width);
                    listHeight.add(containe_height);
                    allList.add(itemList);
                }
            }

        }
        //设置控件需要绘制的大小
        setMeasuredDimension(measurewidth, measureheight);

    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, right, top, bottom;
        int cutLeft = 0;
        int cutTop = 0;
        for (int i = 0; i < allList.size(); i++) {
            List<View> views = allList.get(i);
            for (int j = 0; j < views.size(); j++) {
                View childview = views.get(j);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childview.getLayoutParams();
//                    Top：子View左上角距父View顶部的距离；
//                    Left：子View左上角距父View左侧的距离；
//                    Bottom：子View右下角距父View顶部的距离
//                    Right：子View右下角距父View左侧的距离
                left = cutLeft + layoutParams.leftMargin;
                right = left + childview.getMeasuredWidth();
                top = cutTop + layoutParams.topMargin;
                bottom = top + childview.getMeasuredHeight();
                childview.layout(left, top, right, bottom);
                cutLeft += childview.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
            cutLeft = 0;
            cutTop += listHeight.get(i);
        }
        listHeight.clear();
        allList.clear();
    }
}
