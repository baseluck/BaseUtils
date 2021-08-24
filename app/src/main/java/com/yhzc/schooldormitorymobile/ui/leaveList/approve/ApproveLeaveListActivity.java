package com.yhzc.schooldormitorymobile.ui.leaveList.approve;

import android.os.Bundle;
import android.view.View;

import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityLeaveListBinding;
import com.yhzc.schooldormitorymobile.ui.leaveList.LeaveListViewModel;

import java.util.Objects;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/23 16:46
 * @描述: 待审批请假列表
 */
public class ApproveLeaveListActivity extends BaseActivity<LeaveListViewModel, ActivityLeaveListBinding> {

    private final String[] tabs = new String[]{"待处理", "已处理"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leave_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBind.tabLayout.setVisibility(View.VISIBLE);

        for (int i = 0; i < tabs.length; i++) {
            mViewDataBind.tabLayout.addTab(mViewDataBind.tabLayout.newTab());
            Objects.requireNonNull(mViewDataBind.tabLayout.getTabAt(i)).setText(tabs[i]);
        }
    }

    @Override
    protected int initTitle() {
        return R.string.leave_approve_list;
    }
}