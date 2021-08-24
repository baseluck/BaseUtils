package com.yhzc.schooldormitorymobile.ui.suppliesapply.returnslist;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityReturnsListBinding;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.SuppliesApplyViewModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.returns.SuppliesReturnsActivity;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.returnsdetails.ReturnsDetailsActivity;
import com.luck.basemodule.utils.Utils;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/28 11:43
 * @描述: 退库列表
 */
public class ReturnsListActivity extends BaseActivity<SuppliesApplyViewModel, ActivityReturnsListBinding> {
    private WzReturnsListAdapter mWzReturnsListAdapter;
    private int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_returns_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        mViewModel.initReturnsList(currentPage);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initRv();
        showRightTextAndOnClickListener("申请", v -> ActivityUtils.startActivity(SuppliesReturnsActivity.class));
    }

    private void initRv() {
        mWzReturnsListAdapter = new WzReturnsListAdapter(R.layout.item_leave_list);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mWzReturnsListAdapter);
        mWzReturnsListAdapter.setOnItemClickListener((adapter, view, position) -> {
            ReturnsListModel.DataBean.ListBean listBean = (ReturnsListModel.DataBean.ListBean) adapter.getData().get(position);
            Intent intent = new Intent(this, ReturnsDetailsActivity.class);
            intent.putExtra("id", listBean.getVcId());
            ActivityUtils.startActivity(intent);
        });
        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initReturnsList(currentPage);
        });
        Utils.initLoadMore(mWzReturnsListAdapter, () -> {
            currentPage++;
            mViewModel.initReturnsList(currentPage);
        });
        mViewModel.getReturnsListModel().observe(this, returnsListModel -> {
            mViewDataBind.refresh.setRefreshing(false);
            mViewDataBind.refresh.setRefreshing(false);
            if (returnsListModel == null) {
                mWzReturnsListAdapter.setEmptyView(Utils.getEmptyView(this, R.mipmap.ic_error, "请求失败", null, null));
            } else {
                if (returnsListModel.getData().getPagination().getCurrentPage() == 1) {
                    if (returnsListModel.getData().getList().size() == 0) {
                        mWzReturnsListAdapter.setEmptyView(Utils.getEmptyView(this, 0, "当前数据列表为空", null, null));
                    }
                    mWzReturnsListAdapter.setList(returnsListModel.getData().getList());
                } else {
                    mWzReturnsListAdapter.addData(returnsListModel.getData().getList());
                }
                if (returnsListModel.getData().getList() != null && returnsListModel.getData().getList().size() != 0) {
                    mWzReturnsListAdapter.getLoadMoreModule().loadMoreComplete();
                } else {
                    mWzReturnsListAdapter.getLoadMoreModule().loadMoreEnd();
                }
            }
        });
    }


    @Override
    protected int initTitle() {
        return R.string.wz_returns_list;
    }
}