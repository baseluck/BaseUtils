package com.yhzc.schooldormitorymobile.ui.backbedroom.details;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityBackBedroomDetailsBinding;
import com.yhzc.schooldormitorymobile.ui.backbedroom.BackBedroomViewModel;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/29 18:28
 * @描述:
 */
public class BackBedroomDetailsActivity extends BaseActivity<BackBedroomViewModel, ActivityBackBedroomDetailsBinding> {

    private BackBedroomDetailsAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_back_bedroom_details;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        mAdapter = new BackBedroomDetailsAdapter(R.layout.item_bedroom_details, null);
        mViewDataBind.rvContent.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvContent.setAdapter(mAdapter);

        mViewModel.initDetails();
        mViewModel.getBackBedroomDetailsModel().observe(this, backBedroomDetailsModel -> {
            mViewDataBind.tvTitle.setText(backBedroomDetailsModel.getData().getTitle());
            mAdapter.setList(backBedroomDetailsModel.getData().getContent());
        });
    }

    @Override
    protected int initTitle() {
        return R.string.details;
    }
}