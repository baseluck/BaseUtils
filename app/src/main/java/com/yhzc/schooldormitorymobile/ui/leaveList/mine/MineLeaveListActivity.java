package com.yhzc.schooldormitorymobile.ui.leaveList.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.android.material.tabs.TabLayout;
import com.lzy.okgo.OkGo;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityLeaveListBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.yhzc.schooldormitorymobile.ui.askLeaveDetails.AskLeaveDetailsActivity;
import com.yhzc.schooldormitorymobile.ui.leaveList.LeaveListAdapter;
import com.yhzc.schooldormitorymobile.ui.leaveList.LeaveListModel;
import com.yhzc.schooldormitorymobile.ui.leaveList.LeaveListViewModel;
import com.luck.basemodule.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/23 16:46
 * @描述: 我的请假列表
 */
public class MineLeaveListActivity extends BaseActivity<LeaveListViewModel, ActivityLeaveListBinding> {
    private final String[] tabs = new String[]{"待处理", "已处理"};
    private int status = 1;
    private LeaveListAdapter mLeaveListAdapter;
    private String workType = "leaveProcess";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leave_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.initLeaveList(status + "", workType);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        mViewDataBind.tabLayout.setVisibility(View.VISIBLE);

        for (int i = 0; i < tabs.length; i++) {
            mViewDataBind.tabLayout.addTab(mViewDataBind.tabLayout.newTab());
            Objects.requireNonNull(mViewDataBind.tabLayout.getTabAt(i)).setText(tabs[i]);
        }
        mLeaveListAdapter = new LeaveListAdapter(R.layout.item_leave_list, null);
        mViewDataBind.refresh.setOnRefreshListener(() -> mViewModel.initLeaveList(status + "", workType));
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mLeaveListAdapter);
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
        showRightTextAndOnClickListener("学生\n请假", v -> showLeaveTypeSelect());
        mLeaveListAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<LeaveListModel.DataBean> data = (List<LeaveListModel.DataBean>) adapter.getData();

            Intent intent = new Intent(this, AskLeaveDetailsActivity.class);
            intent.putExtra("vcTaskId", data.get(position).getVcId());
            intent.putExtra("workType", workType);
            ActivityUtils.startActivity(intent);
        });
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
    }

    private void showLeaveTypeSelect() {
        List<String> leaveType = new ArrayList<>();
        leaveType.add("学生请假");
        leaveType.add("老师请假");
        leaveType.add("后勤请假");
        leaveType.add("通用请假");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String fullName = leaveType.get(options1);
            if (StringUtils.equals(fullName, "学生请假")) {
                showRightText("学生\n请假");
                workType = "leaveProcess";
            } else if (StringUtils.equals(fullName, "老师请假")) {
                showRightText("老师\n请假");
                workType = "teacherLeaveProcess";
            } else if (StringUtils.equals(fullName, "通用请假")) {
                showRightText("通用\n请假");
                workType = "commLeaveProcess";
            } else {
                showRightText("后勤\n请假");
                workType = "logisticsLeaveProcess";
            }
            mViewModel.initLeaveList(status + "", workType);
        })
                .setTitleText("选择请假类型")
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(leaveType);
        pvOptions.show();
    }

    @Override
    protected int initTitle() {
        return R.string.leave_mine_list;
    }
}