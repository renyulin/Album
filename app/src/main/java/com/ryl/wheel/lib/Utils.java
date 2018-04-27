package com.ryl.wheel.lib;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 核心类是别人写的：大概是pickView，核心类WheelView
 */
public class Utils {
    public static void setData(View view, final Context context) {
        List<String> dataList = new ArrayList<>();
        dataList.add("辽宁1辽宁1");
        dataList.add("北京1北京1");
        dataList.add("上海1上海1");
        dataList.add("广州1广州1");
        dataList.add("铁岭1铁岭1");
        dataList.add("沈阳1沈阳1");
        List<String> dataList2 = new ArrayList<>();
        dataList2.add("辽宁2辽宁2");
        dataList2.add("北京2北京2");
        dataList2.add("上海2上海2");
        dataList2.add("广州2广州2");
        dataList2.add("铁岭2铁岭2");
        dataList2.add("沈阳2沈阳2");
        List<String> dataList3 = new ArrayList<>();
        dataList3.add("辽宁3辽宁3");
        dataList3.add("北京3北京3");
        dataList3.add("上海3上海3");
        dataList3.add("广州3广州3");
        dataList3.add("铁岭3铁岭3");
        dataList3.add("沈阳3沈阳3");
        DataPopupWindow dWindow = new DataPopupWindow(context, dataList, dataList2, dataList3);
        dWindow.setOnDataSelectListener(new DataPopupWindow.OnDataSelectListener() {
            @Override
            public void onDataSelect(String data) {
                Toast.makeText(context, data, Toast.LENGTH_LONG).show();
            }
        });
        dWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public static void setTime(View view, final Context context) {
        TimePopupWindow tWindow = new TimePopupWindow(context, TimePopupWindow.Type.YEAR_MONTH_DAY);
        tWindow.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                String time = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date);
                Toast.makeText(context, time, Toast.LENGTH_LONG).show();
            }
        });
        tWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
}
