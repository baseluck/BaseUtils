package com.yhzc.schooldormitorymobile.ui.suppliesapply.rkqrlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.android.material.tabs.TabLayout;
import com.lzy.okgo.OkGo;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityTaskListBinding;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.SuppliesApplyViewModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.confirm.ConfirmDetailsActivity;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.confirm.ConfirmOverDetailsActivity;
import com.luck.basemodule.utils.Utils;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/19 15:49
 * @描述: 物资申请列表
 */
public class RkqrApplyListActivity extends BaseActivity<SuppliesApplyViewModel, ActivityTaskListBinding> {

    private final String[] tabs = new String[]{"待处理", "已处理"};
    private RkqrApplyAdapter mLeaveListAdapter;
    private String blQr = "0";
    private int currentPage = 1;
    private final String workType = "receiveApplyLeaveProcess";

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        mViewModel.initRkqrList(currentPage, blQr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        for (String tab : tabs) {
            mViewDataBind.tabLayout.addTab(mViewDataBind.tabLayout.newTab().setText(tab));
        }
        initRv();
        mViewDataBind.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showLoading("正在加载");
                currentPage = 1;
                if (tab.getPosition() == 0) {
                    blQr = "0";
                } else {
                    blQr = "1";
                }
                OkGo.getInstance().cancelAll();
                mViewModel.initRkqrList(currentPage, blQr);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initRv() {
        mLeaveListAdapter = new RkqrApplyAdapter(R.layout.item_leave_list);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mLeaveListAdapter);
        mLeaveListAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<QrListModel.DataBean.ListBean> data = (List<QrListModel.DataBean.ListBean>) adapter.getData();
            Intent intent = new Intent();
            if (StringUtils.equals("1", blQr)) {
                intent.setClass(this, ConfirmOverDetailsActivity.class);
            } else {
                intent.setClass(this, ConfirmDetailsActivity.class);
            }
            intent.putExtra("title", "入库确认");
            intent.putExtra("id", data.get(position).getVcId());
            ActivityUtils.startActivity(intent);
        });
        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initRkqrList(currentPage, blQr);
        });
        Utils.initLoadMore(mLeaveListAdapter, () -> {
            currentPage++;
            mViewModel.initRkqrList(currentPage, blQr);
        });
        mViewModel.getQrListModel().observe(this, approveListModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (approveListModel != null) {
                if (approveListModel.getData().getPagination().getCurrentPage() == 1) {
                    mLeaveListAdapter.setList(approveListModel.getData().getList());
                } else {
                    mLeaveListAdapter.addData(approveListModel.getData().getList());
                }
                if (approveListModel.getData().getList() != null && approveListModel.getData().getList().size() != 0) {
                    mLeaveListAdapter.getLoadMoreModule().loadMoreComplete();
                } else {
                    mLeaveListAdapter.getLoadMoreModule().loadMoreEnd();
                }
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.supplies_apply_list;
    }
}
