package com.ryl.wheel.lib;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.ryl.R;

import java.util.List;

/**
 * 时间选择器
 */
public class DataPopupWindow extends PopupWindow implements OnClickListener {
    private View rootView; // 总的布局
    protected WheelData wheelTime;
    private View btnSubmit, btnCancel;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private OnDataSelectListener dataSelectListener;

    public DataPopupWindow(Context context, @Size(min = 1, max = 2) List<String>... lists) {
        super(context);
        this.setWidth(LayoutParams.FILL_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());// 这样设置才能点击屏幕外dismiss窗口
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.timepopwindow_anim_style);

        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        rootView = mLayoutInflater.inflate(R.layout.pw_time, null);
        // -----确定和取消按钮
        btnSubmit = rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = rootView.findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        // ----时间转轮
        final View timepickerview = rootView.findViewById(R.id.timepicker);
        wheelTime = new WheelData(timepickerview);
        wheelTime.setPicker(lists);
        setContentView(rootView);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (dataSelectListener != null) {
                dataSelectListener.onDataSelect(wheelTime.getData());
            }
            dismiss();
            return;
        }
    }

    public interface OnDataSelectListener {
        void onDataSelect(String data);
    }

    public void setOnDataSelectListener(OnDataSelectListener dataSelectListener) {
        this.dataSelectListener = dataSelectListener;
    }
}
