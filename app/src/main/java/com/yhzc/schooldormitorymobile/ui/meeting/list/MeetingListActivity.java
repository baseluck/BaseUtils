package com.yhzc.schooldormitorymobile.ui.meeting.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lzy.okgo.OkGo;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityTaskListBinding;
import com.yhzc.schooldormitorymobile.ui.meeting.MeetingViewModel;
import com.yhzc.schooldormitorymobile.ui.meeting.initiatedetails.MeetingDetailsActivity;
import com.yhzc.schooldormitorymobile.ui.meeting.signin.MeetingSignInActivity;
import com.luck.basemodule.utils.Utils;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/12 17:48
 * @描述: 我的会议列表
 */
public class MeetingListActivity extends BaseActivity<MeetingViewModel, ActivityTaskListBinding> {

    private final String[] tabs = new String[]{"参与会议", "负责会议"};
    private String type = "0";
    private int currentPage = 1;
    private MeetingListAdapter mMeetingListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        mViewModel.initMeetingList(currentPage, type);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        for (String tab : tabs) {
            mViewDataBind.tabLayout.addTab(mViewDataBind.tabLayout.newTab().setText(tab));
        }

        mViewDataBind.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showLoading("正在加载");
                if (tab.getPosition() == 0) {
                    type = "0";
                } else {
                    type = "1";
                }
                OkGo.getInstance().cancelAll();
                mViewDataBind.rv.scrollToPosition(0);
                currentPage = 1;
                mViewModel.initMeetingList(currentPage, type);
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
        mMeetingListAdapter = new MeetingListAdapter(R.layout.item_meeting_list);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mMeetingListAdapter);
        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initMeetingList(currentPage, type);
        });

        Utils.initLoadMore(mMeetingListAdapter, () -> {
            currentPage++;
            mViewModel.initMeetingList(currentPage, type);
        });

        mViewModel.getMeetingListModel().observe(this, meetingListModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (meetingListModel == null) {
                mMeetingListAdapter.setEmptyView(Utils.getEmptyView(this, R.mipmap.ic_error, "请求失败", null, null));
            } else {
                if (meetingListModel.getData().getPagination().getCurrentPage() == 1) {
                    if (meetingListModel.getData().getList().size() == 0) {
                        mMeetingListAdapter.setEmptyView(Utils.getEmptyView(this, 0, "当前数据列表为空", null, v -> {
                            currentPage = 1;
                            mViewModel.initMeetingList(currentPage, type);
                        }));
                    }
                    mMeetingListAdapter.setList(meetingListModel.getData().getList());
                } else {
                    mMeetingListAdapter.addData(meetingListModel.getData().getList());
                }
                if (meetingListModel.getData().getList() != null && meetingListModel.getData().getList().size() != 0) {
                    mMeetingListAdapter.getLoadMoreModule().loadMoreComplete();
                } else {
                    mMeetingListAdapter.getLoadMoreModule().loadMoreEnd();
                }
            }
        });

        mMeetingListAdapter.setOnItemClickListener((adapter, view, position) -> {
            MeetingListModel.DataBean.ListBean listBean = (MeetingListModel.DataBean.ListBean) adapter.getData().get(position);
            if (StringUtils.equals("1", type)) {
                long nowMills = TimeUtils.getNowMills();
                long signInStart = TimeUtils.string2Millis(listBean.getDtSignInStartTime());
                long signInEnd = TimeUtils.string2Millis(listBean.getDtSignInEndTime());
                long signOutStart = TimeUtils.string2Millis(listBean.getDtSignOutStartTime());
                long signOutEnd = TimeUtils.string2Millis(listBean.getDtSignOutEndTime());

                if (signInStart < nowMills && nowMills < signInEnd) {
                    Intent intent = new Intent(this, MeetingSignInActivity.class);
                    intent.putExtra("id", listBean.getVcId());
                    intent.putExtra("signType", "0");
                    ActivityUtils.startActivity(intent);
                } else if (signOutStart < nowMills && nowMills < signOutEnd) {
                    Intent intent = new Intent(this, MeetingSignInActivity.class);
                    intent.putExtra("id", listBean.getVcId());
                    intent.putExtra("vcSignUser", listBean.getVcId());
                    intent.putExtra("signType", "1");
                    ActivityUtils.startActivity(intent);
                } else {
                    ToastUtils.showShort("不在签到、签退时间范围");
                }
            } else {
                Intent intent = new Intent(this, MeetingDetailsActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("id", listBean.getVcId());
                ActivityUtils.startActivity(intent);
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.meeting_list;
    }
}