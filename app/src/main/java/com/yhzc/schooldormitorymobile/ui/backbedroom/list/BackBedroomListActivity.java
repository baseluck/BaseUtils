package com.yhzc.schooldormitorymobile.ui.backbedroom.list;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityLeaveListBinding;
import com.yhzc.schooldormitorymobile.ui.backbedroom.BackBedroomViewModel;
import com.luck.basemodule.utils.Utils;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/28 18:06
 * @描述: 回寝提交列表
 */
public class BackBedroomListActivity extends BaseActivity<BackBedroomViewModel, ActivityLeaveListBinding> {

    private BackBedroomListAdapter mAdapter;
    private int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leave_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        mViewModel.initList(currentPage);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBind.tabLayout.setVisibility(View.GONE);

        mViewDataBind.refresh.setOnRefreshListener(() -> mViewDataBind.refresh.setRefreshing(false));

        mAdapter = new BackBedroomListAdapter(R.layout.item_back_bedroom_list);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mAdapter);

        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initList(currentPage);
        });

        Utils.initLoadMore(mAdapter, () -> {
            currentPage++;
            mViewModel.initList(currentPage);
        });

        mViewModel.getBackBedroomModel().observe(this, backBedroomModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (backBedroomModel != null) {
                if (backBedroomModel.getData().getPagination().getCurrentPage() == 1) {
                    mAdapter.setList(backBedroomModel.getData().getList());
                } else {
                    mAdapter.addData(backBedroomModel.getData().getList());
                }

                if (backBedroomModel.getData().getList() != null && backBedroomModel.getData().getList().size() != 0) {
                    mAdapter.getLoadMoreModule().loadMoreComplete();
                } else {
                    mAdapter.getLoadMoreModule().loadMoreEnd();
                }
            }
        });


    }

    @Override
    protected int initTitle() {
        return R.string.back_bedroom_list;
    }
}