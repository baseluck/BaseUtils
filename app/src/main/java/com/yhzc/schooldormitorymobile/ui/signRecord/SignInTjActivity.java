package com.yhzc.schooldormitorymobile.ui.signRecord;

import android.os.Bundle;

import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivitySignInTjBinding;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/3 16:44
 * @描述:
 */
public class SignInTjActivity extends BaseActivity<SignRecordViewModel, ActivitySignInTjBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_in_tj;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("title");
        setTabTitle(title);
        CheckStatisticsModel checkStatisticsModel = (CheckStatisticsModel) getIntent().getSerializableExtra("content");
        mViewDataBind.tvYearYdk.setText(String.valueOf(checkStatisticsModel.getData().getYear_ydk()));
        mViewDataBind.tvYearSjdk.setText(String.valueOf(checkStatisticsModel.getData().getYear_sjdk()));
        mViewDataBind.tvYearQq.setText(String.valueOf(checkStatisticsModel.getData().getYear_qdk()));
        mViewDataBind.tvMonthYdk.setText(String.valueOf(checkStatisticsModel.getData().getMonth_ydk()));
        mViewDataBind.tvMonthSjdk.setText(String.valueOf(checkStatisticsModel.getData().getMonth_sjdk()));
        mViewDataBind.tvMonthQq.setText(String.valueOf(checkStatisticsModel.getData().getMonth_qdk()));
        mViewDataBind.tvWeekYdk.setText(String.valueOf(checkStatisticsModel.getData().getWeek_ydk()));
        mViewDataBind.tvWeekSjdk.setText(String.valueOf(checkStatisticsModel.getData().getWeek_sjdk()));
        mViewDataBind.tvWeekZqq.setText(String.valueOf(checkStatisticsModel.getData().getWeek_qdk()));
    }

    @Override
    protected int initTitle() {
        return 0;
    }
}