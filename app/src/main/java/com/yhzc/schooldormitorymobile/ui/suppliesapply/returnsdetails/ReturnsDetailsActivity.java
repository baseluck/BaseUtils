package com.yhzc.schooldormitorymobile.ui.suppliesapply.returnsdetails;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityReturnsDetailsBinding;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.SuppliesApplyViewModel;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/28 14:59
 * @描述: 退库详情
 */
public class ReturnsDetailsActivity extends BaseActivity<SuppliesApplyViewModel, ActivityReturnsDetailsBinding> {

    private ReturnsWzAdapter mWzAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_returns_details;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        String id = getIntent().getStringExtra("id");
        mViewModel.initReturnsDetails(id);
        initRv();
        mViewModel.getReturnDetailsModel().observe(this, returnDetailsModel -> {
            if (returnDetailsModel != null) {
                mViewDataBind.tvTitle.setText(returnDetailsModel.getData().getVcTitle());
                mViewDataBind.tvBz.setText(returnDetailsModel.getData().getTextMark());
                mWzAdapter.setList(returnDetailsModel.getData().getDetails());
            }
        });
    }

    private void initRv() {
        mWzAdapter = new ReturnsWzAdapter(R.layout.item_approve_wz);
        mViewDataBind.rvWz.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvWz.setAdapter(mWzAdapter);
    }

    @Override
    protected int initTitle() {
        return R.string.wz_returns_details;
    }
}