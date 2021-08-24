package com.yhzc.schooldormitorymobile.ui.performance.rank;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.StringUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityPerformanceRankDetailsBinding;
import com.yhzc.schooldormitorymobile.ui.performance.PerformanceViewModel;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/29 16:00
 * @描述: 考核成绩列表
 */
public class PerformanceRankDetailsActivity extends BaseActivity<PerformanceViewModel, ActivityPerformanceRankDetailsBinding> {

    private PerformanceRankAdapter mRankAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_performance_rank_details;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        String id = getIntent().getStringExtra("id");
        mViewModel.initRankDetails(id);

        mRankAdapter = new PerformanceRankAdapter(R.layout.item_performance_rank_details);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mRankAdapter);

        mViewModel.getPerformanceRankModel().observe(this, performanceRankModel -> {
            dismissLoading();
            if (performanceRankModel != null) {
                mViewDataBind.tvTitle.setText(performanceRankModel.getData().getTitle());
                mViewDataBind.tvTime.setText(String.format(StringUtils.getString(R.string.kh_date), performanceRankModel.getData().getExamineTime()));
                mRankAdapter.setList(performanceRankModel.getData().getRank());
            }
        });

    }

    @Override
    protected int initTitle() {
        return R.string.performance_cj;
    }
}