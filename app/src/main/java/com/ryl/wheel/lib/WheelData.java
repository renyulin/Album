package com.ryl.wheel.lib;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.View;

import com.ryl.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

public class WheelData {
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;
    private int type;
    public final static int SINGLE = 1;
    public final static int DOUBLE = 2;
    public final static int MORE = 3;

    @IntDef({SINGLE, DOUBLE, MORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ReqType {
    }

    private static int START_YEAR = 1990, END_YEAR = 2100;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public static int getSTART_YEAR() {
        return START_YEAR;
    }

    public static void setSTART_YEAR(int sTART_YEAR) {
        START_YEAR = sTART_YEAR;
    }

    public static int getEND_YEAR() {
        return END_YEAR;
    }

    public static void setEND_YEAR(int eND_YEAR) {
        END_YEAR = eND_YEAR;
    }

    public WheelData(View view) {
        super();
        this.view = view;
        setView(view);
    }

    private List<List<String>> dateList;

    public void setPicker(List<String>... lists) {
        type = lists.length;
        this.dateList = Arrays.asList(lists);
        Context context = view.getContext();
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_day = (WheelView) view.findViewById(R.id.day);
        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_mins = (WheelView) view.findViewById(R.id.min);
        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = 0;
        switch (type) {
            case SINGLE:
                wv_month.setAdapter(new ArrayWheelAdapter(dateList.get(0)));
                wv_month.setCurrentItem(0);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.VISIBLE);
                wv_day.setVisibility(View.GONE);
                break;
            case DOUBLE:
                wv_year.setAdapter(new ArrayWheelAdapter(dateList.get(0)));// 设置"年"的显示数据
                wv_year.setCurrentItem(0);// 初始化时显示的数据
                wv_day.setAdapter(new ArrayWheelAdapter(dateList.get(1)));// 设置"年"的显示数据
                wv_day.setCurrentItem(0);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                wv_year.setVisibility(View.VISIBLE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.VISIBLE);
                break;
            case MORE:
                wv_year.setAdapter(new ArrayWheelAdapter(dateList.get(0)));// 设置"年"的显示数据
                wv_year.setCurrentItem(0);// 初始化时显示的数据
                wv_month.setAdapter(new ArrayWheelAdapter(dateList.get(1)));
                wv_month.setCurrentItem(0);
                wv_day.setAdapter(new ArrayWheelAdapter(dateList.get(2)));// 设置"年"的显示数据
                wv_day.setCurrentItem(0);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                wv_year.setVisibility(View.VISIBLE);
                wv_month.setVisibility(View.VISIBLE);
                wv_day.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_mins.setCyclic(cyclic);
    }

    public String getData() {
        StringBuffer sb = new StringBuffer();
        switch (type) {
            case SINGLE:
                sb.append(dateList.get(0).get(wv_month.getCurrentItem()));
                break;
            case DOUBLE:
                sb.append(dateList.get(0).get(wv_year.getCurrentItem()) + "-" + dateList.get(1).get(wv_day.getCurrentItem()));
                break;
            case MORE:
                sb.append(dateList.get(0).get(wv_year.getCurrentItem()) + "-" + dateList.get(1).get(wv_month.getCurrentItem())
                        + "-" + dateList.get(2).get(wv_day.getCurrentItem()));
                break;
        }
        return sb.toString();
    }
}
