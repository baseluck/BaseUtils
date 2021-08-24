package com.yhzc.schooldormitorymobile.ui.performance;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityPerformanceBinding;
import com.yhzc.schooldormitorymobile.ui.performance.rank.PerformanceRankDetailsActivity;
import com.yhzc.schooldormitorymobile.ui.performanceDetails.PerformanceDetailsActivity;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 17:52
 * @描述: 绩效打分列表
 */
public class PerformanceRankListActivity extends BaseActivity<PerformanceViewModel, ActivityPerformanceBinding> {

    private PerformanceAdapter mPerformanceAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_performance;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.initDataAll();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        mPerformanceAdapter = new PerformanceAdapter(R.layout.item_performance, null);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mPerformanceAdapter);
        mPerformanceAdapter.setItemClick((data, postion) -> ActivityUtils.startActivity(PerformanceDetailsActivity.class));
        mViewModel.getPerforManceModel().observe(this, perforManceModel -> {
            mViewDataBind.refresh.setRefreshing(false);
            mPerformanceAdapter.setList(perforManceModel.getData());
        });

        mViewDataBind.refresh.setOnRefreshListener(() -> mViewModel.initDataAll());

        mPerformanceAdapter.setItemClick((data, postion) -> {
            Intent intent = new Intent(this, PerformanceRankDetailsActivity.class);
            intent.putExtra("id", data.get(postion).getId());
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected int initTitle() {
        return R.string.performance_rank;
    }

}