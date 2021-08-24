package com.yhzc.schooldormitorymobile.ui.signRecord;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivitySignRecordBinding;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.yhzc.schooldormitorymobile.view.calendarView.IndexWeekView;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/23 16:46
 * @描述: 签到打卡记录
 */
public class SignRecordActivity extends BaseActivity<SignRecordViewModel, ActivitySignRecordBinding>
        implements CalendarView.OnCalendarSelectListener {
    private SignListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_record;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        int year = mViewDataBind.calendarView.getCurYear();
        int month = mViewDataBind.calendarView.getCurMonth();
        int day = mViewDataBind.calendarView.getCurDay();
        Glide.with(this)
                .load(Cache.getHttp() + Cache.getLogin().getData().getVcHeadPic())
                .placeholder(R.mipmap.ic_head)
                .into(mViewDataBind.ivHead);
        mViewDataBind.tvName.setText(Cache.getLogin().getData().getVcRealName());
        mViewDataBind.tvBm.setText(Cache.getLogin().getData().getVcPostion());
        mAdapter = new SignListAdapter(R.layout.item_sign_in_list, null);
        mViewDataBind.rvSignRecord.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvSignRecord.setAdapter(mAdapter);
        String time = year + "年" + month + "月";
        @SuppressLint("DefaultLocale") String date = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
        mViewModel.initList(this, date);
        mViewDataBind.tvMonthTj.setText(String.format(StringUtils.getString(R.string.tj_month), month));
        mViewModel.initTjUser(date);
        mViewModel.getSignListBean().observe(this, signListBean -> {
            if (signListBean.getData() != null) {
                mAdapter.setList(signListBean.getData());
            }
        });
        mViewModel.getCheckStatisticsModel().observe(this, checkStatisticsModel -> {
            mViewDataBind.tvQqNum.setText(String.valueOf(checkStatisticsModel.getData().getMonth_qdk()));
            mViewDataBind.tvDkNum.setText(String.valueOf(checkStatisticsModel.getData().getMonth_sjdk()));
            mViewDataBind.tvAllNum.setText(String.valueOf(checkStatisticsModel.getData().getMonth_ydk()));
        });
        mViewDataBind.tvMonth.setText(time);
        mViewDataBind.tvMonth.setOnClickListener(v -> showTimePicker());
        mViewDataBind.clTj.setOnClickListener(v -> {
            CheckStatisticsModel checkStatisticsModel = mViewModel.getCheckStatisticsModel().getValue();
            if (checkStatisticsModel == null) {
                ToastUtils.showShort("统计数据为空");
            } else {
                Intent intent = new Intent(this, SignInTjActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("content", checkStatisticsModel);
                intent.putExtras(bundle);
                intent.putExtra("title", mViewDataBind.tvMonthTj.getText().toString());
                ActivityUtils.startActivity(intent);
            }
        });
        mViewDataBind.calendarView.setOnCalendarSelectListener(this);
        mViewDataBind.calendarView.setWeekView(IndexWeekView.class);
    }

    @Override
    protected int initTitle() {
        return R.string.sign_record;
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);
        calendar.setScheme("");
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "");
        calendar.addScheme(0xFF008800, "");
        return calendar;
    }

    /**
     * 超出范围越界
     *
     * @param calendar calendar
     */
    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    /**
     * 日期选择事件
     *
     * @param calendar calendar
     * @param isClick  isClick
     */
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        String time = calendar.getYear() + "年" + calendar.getMonth() + "月";
        @SuppressLint("DefaultLocale") String date = calendar.getYear() + "-" + String.format("%02d", calendar.getMonth()) + "-" + String.format("%02d", calendar.getDay());
        mViewDataBind.tvMonthTj.setText(String.format(StringUtils.getString(R.string.tj_month), calendar.getMonth()));
        mViewDataBind.tvMonth.setText(time);
        mViewModel.initList(this, date);
        mViewModel.initTjUser(date);

    }

    private void showTimePicker() {
        java.util.Calendar selectedDate = java.util.Calendar.getInstance();
        java.util.Calendar startDate = java.util.Calendar.getInstance();
        startDate.set(2000, 1, 1);
        selectedDate.setTime(TimeUtils.getNowDate());
        java.util.Calendar endDate = java.util.Calendar.getInstance();
        endDate.set(2050, 12, 30);
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            String year = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy"));
            String month = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("MM"));
            mViewDataBind.calendarView.scrollToCalendar(Integer.parseInt(year), Integer.parseInt(month), mViewDataBind.calendarView.getCurDay());
        }).setType(new boolean[]{true, true, false, false, false, false})
                .setCancelText("取消")
                .setSubmitText("确认")
                .setContentTextSize(16)
                .setTitleSize(18)
                .setTitleText("选择时间")
                .setOutSideCancelable(true)
                .setTitleColor(ColorUtils.getColor(R.color.color333333))
                .setSubmitColor(ColorUtils.getColor(R.color.color549BFF))
                .setCancelColor(ColorUtils.getColor(R.color.color333333))
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .build();
        timePickerView.show();
    }
}