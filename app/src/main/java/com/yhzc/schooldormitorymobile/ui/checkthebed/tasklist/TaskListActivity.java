package com.yhzc.schooldormitorymobile.ui.checkthebed.tasklist;

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
import com.yhzc.schooldormitorymobile.ui.checkthebed.CheckTheBedViewModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.locationList.LocationListActivity;
import com.luck.basemodule.utils.Utils;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/9 9:44
 * @描述: 查寝任务列表
 */
public class TaskListActivity extends BaseActivity<CheckTheBedViewModel, ActivityTaskListBinding> {

    private final String[] tabs = new String[]{"待处理", "已结束"};
    private TaskListAdapter mTaskListAdapter;

    private int currentPage = 1;
    private String type;
    private String status = "2";

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(type)) {
            currentPage = 1;
            mViewModel.initTaskList(currentPage, status, type);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        type = getIntent().getStringExtra("type");

        for (String tab : tabs) {
            mViewDataBind.tabLayout.addTab(mViewDataBind.tabLayout.newTab().setText(tab));
        }

        mViewDataBind.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    status = "2";
                } else {
                    status = "3";
                }
                currentPage = 1;
                OkGo.getInstance().cancelAll();
                mViewModel.initTaskList(currentPage, status, type);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        initRv();
    }

    private void initRv() {
        mTaskListAdapter = new TaskListAdapter(R.layout.item_task_list);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mTaskListAdapter);
        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initTaskList(currentPage, status, type);
        });
        Utils.initLoadMore(mTaskListAdapter, () -> {
            currentPage++;
            mViewModel.initTaskList(currentPage, status, type);
        });
        mViewModel.getTaskListModel().observe(this, taskListModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (taskListModel == null) {
                mTaskListAdapter.setEmptyView(Utils.getEmptyView(this, R.mipmap.ic_error, "请求失败", null, null));
            } else {
                if (taskListModel.getData().getPagination().getCurrentPage() == 1) {
                    if (taskListModel.getData().getList().size() == 0) {
                        mTaskListAdapter.setEmptyView(Utils.getEmptyView(this, 0, "当前数据列表为空", null, v -> {
                            currentPage = 1;
                            mViewModel.initTaskList(currentPage, status, type);
                        }));
                    }
                    mTaskListAdapter.setList(taskListModel.getData().getList());
                } else {
                    mTaskListAdapter.addData(taskListModel.getData().getList());
                }
                if (taskListModel.getData().getList() != null && taskListModel.getData().getList().size() != 0) {
                    mTaskListAdapter.getLoadMoreModule().loadMoreComplete();
                } else {
                    mTaskListAdapter.getLoadMoreModule().loadMoreEnd();
                }
            }
        });
        mTaskListAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<TaskListModel.DataBean.ListBean> data = (List<TaskListModel.DataBean.ListBean>) adapter.getData();
            Intent intent = new Intent(this, LocationListActivity.class);
            intent.putExtra("id", data.get(position).getVcId());
            intent.putExtra("status", status);
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected int initTitle() {
        return R.string.check_bed_task_list;
    }
}