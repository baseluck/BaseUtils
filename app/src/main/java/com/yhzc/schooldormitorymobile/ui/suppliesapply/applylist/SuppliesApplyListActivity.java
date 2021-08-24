package com.yhzc.schooldormitorymobile.ui.suppliesapply.applylist;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.android.material.tabs.TabLayout;
import com.lzy.okgo.OkGo;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityTaskListBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.yhzc.schooldormitorymobile.ui.leaveList.LeaveListModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.SuppliesApplyViewModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.apply.SuppliesApplyActivity;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.applydetails.WzApplyDetailsActivity;
import com.luck.basemodule.utils.Utils;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/19 15:49
 * @描述: 物资申请列表
 */
public class SuppliesApplyListActivity extends BaseActivity<SuppliesApplyViewModel, ActivityTaskListBinding> {

    private final String[] tabs = new String[]{"待处理", "已处理"};
    private WzApplyAdapter mLeaveListAdapter;
    private int status = 1;
    private final String workType = "receiveApplyLeaveProcess";

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.initLeaveList(status + "", workType);
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
                if (tab.getPosition() == 0) {
                    status = 1;
                } else {
                    status = 2;
                }
                OkGo.getInstance().cancelAll();
                mViewModel.initLeaveList(status + "", workType);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        showRightTextAndOnClickListener("申请", v -> ActivityUtils.startActivity(SuppliesApplyActivity.class));
    }

    private void initRv() {
        mLeaveListAdapter = new WzApplyAdapter(R.layout.item_leave_list, null);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mLeaveListAdapter);
        mLeaveListAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<LeaveListModel.DataBean> data = (List<LeaveListModel.DataBean>) adapter.getData();
            Intent intent = new Intent(this, WzApplyDetailsActivity.class);
            intent.putExtra("vcTaskId", data.get(position).getVcProcId());
            intent.putExtra("mainId", data.get(position).getVcId());
            intent.putExtra("workType", "receiveApplyLeaveProcess");
            ActivityUtils.startActivity(intent);
        });
        mViewModel.getLeaveListModel().observe(this, leaveListModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (leaveListModel.getCode() == ApiUrl.SUCCESS) {
                if (leaveListModel.getData().size() == 0) {
                    mLeaveListAdapter.setEmptyView(Utils.getEmptyView(this, 0, "无数据\n点击刷新", null, v -> {
                        showLoading("正在加载");
                        mViewModel.initLeaveList(status + "", workType);
                    }));
                }
                mLeaveListAdapter.setList(leaveListModel.getData());
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.supplies_apply_list;
    }
}
