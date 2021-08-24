package com.yhzc.schooldormitorymobile.ui.approveList;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.luck.basemodule.base.BaseActivity;
import com.luck.basemodule.utils.Utils;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityLeaveListBinding;
import com.yhzc.schooldormitorymobile.ui.askLeaveDetails.ApproveDetailsActivity;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 17:04
 * @描述: 审批列表
 */
public class ApproveListActivity extends BaseActivity<ApproveListViewModel, ActivityLeaveListBinding> {

    private final String[] tabs = new String[]{"待处理", "已处理"};
    private int currentPage = 1;
    private ApproveListAdapter mApproveListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leave_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading("正在加载");
        currentPage = 1;
        mViewModel.initApproveList(currentPage);

    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mApproveListAdapter = new ApproveListAdapter(R.layout.item_approve_list, null);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mApproveListAdapter);
        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initApproveList(currentPage);
        });
        Utils.initLoadMore(mApproveListAdapter, () -> {
            currentPage++;
            mViewModel.initApproveList(currentPage);
        });
        mViewModel.getApproveListModel().observe(this, approveListModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (approveListModel != null) {
                if (approveListModel.getData().getPagination().getCurrentPage() == 1) {
                    mApproveListAdapter.setList(approveListModel.getData().getList());
                } else {
                    mApproveListAdapter.addData(approveListModel.getData().getList());
                }

                if (approveListModel.getData().getList() != null && approveListModel.getData().getList().size() != 0) {
                    mApproveListAdapter.getLoadMoreModule().loadMoreComplete();
                } else {
                    mApproveListAdapter.getLoadMoreModule().loadMoreEnd();
                }
            }
        });
        mApproveListAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<ApproveListModel.DataBean.ListBean> data = (List<ApproveListModel.DataBean.ListBean>) adapter.getData();
            if (StringUtils.equals("leaveProcess", data.get(position).getProcessInstanceKey())
                    || StringUtils.equals("teacherLeaveProcess", data.get(position).getProcessInstanceKey())
                    || StringUtils.equals("receiveApplyLeaveProcess", data.get(position).getProcessInstanceKey())
                    || StringUtils.equals("logisticsLeaveProcess", data.get(position).getProcessInstanceKey())
                    || StringUtils.equals("commLeaveProcess", data.get(position).getProcessInstanceKey())
                    || StringUtils.equals("enterStockProcess", data.get(position).getProcessInstanceKey())
            ) {
                Intent intent = new Intent(this, ApproveDetailsActivity.class);
                intent.putExtra("vcTaskId", data.get(position).getTaskId());
                intent.putExtra("mainId", data.get(position).getMainId());
                intent.putExtra("workType", data.get(position).getProcessInstanceKey());
                ActivityUtils.startActivity(intent);
            }
        });

    }

    @Override
    protected int initTitle() {
        return R.string.sprw;
    }
}