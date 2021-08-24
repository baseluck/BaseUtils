package com.yhzc.schooldormitorymobile.ui.statistics;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityPersonCheckStatisticsBinding;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/3 15:13
 * @描述: 考勤全统计
 */
public class PersonCheckStatisticsActivity extends BaseActivity<StatisticsViewModel, ActivityPersonCheckStatisticsBinding> {
    private PersonCheckStatisticsAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_check_statistics;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initRv();
        mViewModel.initData();
    }

    private void initRv() {
        mAdapter = new PersonCheckStatisticsAdapter(R.layout.item_check_statistics);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mAdapter);
        mViewModel.getWeekStatisticsModel().observe(this, weekStatisticsModel -> {
            if (weekStatisticsModel != null) {
                mAdapter.setList(weekStatisticsModel.getData());
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.week_kqtj;
    }
}