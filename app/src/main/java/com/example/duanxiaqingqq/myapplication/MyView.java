package com.example.duanxiaqingqq.myapplication;

import android.content.Context;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：MyViewDemo2
 * 包名：  notice.liangxq.com.myapplication
 * 文件名：MyView4
 * 创建者：liangxq
 * 创建时间：2019/2/19  14:05
 * 描述：TODO
 */
public class MyView extends ViewGroup{
    LinearLayout linearLayout;
    RelativeLayout relativeLayout;
    public MyView(MainActivity mainActivity) {
        this(mainActivity,null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //存储每一个的高度
    List<Integer>listHeight=new ArrayList<>();
    //存储每一行view
    List<List<View>>allList=new ArrayList<>();
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取父View的宽高以及模式
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);

        int heightMode= MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        //记录当前自定义View的宽高
        int measureWidth=0;
        int measureHeight=0;
        //match_parent 具体值
        if(widthMode==MeasureSpec.EXACTLY &&heightMode==MeasureSpec.EXACTLY){
            measureWidth=widthSize;
            measureHeight=heightSize;
        }
        //记录子View的宽高
        int childWidth=0;
        int childHeight=0;

        //记录每一行宽和行高
        int lineWidth=0;
        int lineHeight=0;


        //wrap_content
        int iCount=getChildCount();
        //存储一行view的集合
        List<View>itemList=new ArrayList<>();
        for (int i = 0; i < iCount; i++) {
            View childView=getChildAt(i);
            //测量每一个子View
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams layoutParams= (MarginLayoutParams) childView.getLayoutParams();
            childWidth=childView.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
            childHeight=childView.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
            //换行
            if(lineWidth+childWidth>widthSize){
                //记录上一行的信息
                measureWidth=Math.max(measureWidth,lineWidth);
                measureHeight+=lineHeight;
                //将行高存储
                listHeight.add(lineHeight);
                allList.add(itemList);

                lineHeight=childHeight;
                lineWidth=childWidth;

                itemList=new ArrayList<>();
                itemList.add(childView);


            }else{
                //宽度叠加
                lineWidth+=childWidth;
                //取当前View的高度和行高  取最大
                lineHeight=Math.max(lineHeight,childHeight);
                itemList.add(childView);
            }
        }
        //设置View的宽和高
        setMeasuredDimension(measureWidth,measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left,top,right,bottom;
        int cutleft=0;
        int cutTop=0;
        for (int i = 0; i < allList.size(); i++) {
            List<View>views=allList.get(i);
            for (int j = 0; j <views.size() ; j++) {
                View childView=views.get(j);
                MarginLayoutParams layoutParams= (MarginLayoutParams) childView.getLayoutParams();
                left=cutleft+layoutParams.leftMargin;
                right=left+childView.getMeasuredWidth();
                top=cutTop+layoutParams.topMargin;
                bottom=top+childView.getMeasuredHeight();
                childView.layout(left,top,right,bottom);
                cutleft+=childView.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
            }
            cutleft=0;
            cutTop+=listHeight.get(i);
        }
        listHeight.clear();
        allList.clear();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
