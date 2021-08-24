package com.yhzc.schooldormitorymobile.ui.meeting.initiatelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityTaskListBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.meeting.MeetingViewModel;
import com.yhzc.schooldormitorymobile.ui.meeting.initiate.MeetingInitiateActivity;
import com.yhzc.schooldormitorymobile.ui.meeting.initiate.MeetingInitiateChangeActivity;
import com.yhzc.schooldormitorymobile.ui.meeting.initiatedetails.MeetingDetailsActivity;
import com.yhzc.schooldormitorymobile.ui.meeting.list.MeetingListModel;
import com.luck.basemodule.utils.OkGoUtils;
import com.luck.basemodule.utils.Utils;

import java.util.HashMap;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/14 17:05
 * @描述: 我发布的会议列表
 */
public class MeetingInitiateListActivity extends BaseActivity<MeetingViewModel, ActivityTaskListBinding> {

    private final String type = "3";
    private int currentPage = 1;
    private MeetingInitiateListAdapter mMeetingInitiateListAdapter;

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
        mViewDataBind.tabLayout.setVisibility(View.GONE);

        initRv();

        showRightTextAndOnClickListener("发布", v -> ActivityUtils.startActivity(MeetingInitiateActivity.class));
    }

    private void initRv() {
        mMeetingInitiateListAdapter = new MeetingInitiateListAdapter(R.layout.item_meeting_initiate_list);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mMeetingInitiateListAdapter);
        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initMeetingList(currentPage, type);
        });

        Utils.initLoadMore(mMeetingInitiateListAdapter, () -> {
            currentPage++;
            mViewModel.initMeetingList(currentPage, type);
        });

        mViewModel.getMeetingListModel().observe(this, meetingListModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (meetingListModel == null) {
                mMeetingInitiateListAdapter.setEmptyView(Utils.getEmptyView(this, R.mipmap.ic_error, "请求失败", null, null));
            } else {
                if (meetingListModel.getData().getPagination().getCurrentPage() == 1) {
                    if (meetingListModel.getData().getList().size() == 0) {
                        mMeetingInitiateListAdapter.setEmptyView(Utils.getEmptyView(this, 0, "当前数据列表为空", null, v -> {
                            currentPage = 1;
                            mViewModel.initMeetingList(currentPage, type);
                        }));
                    }
                    mMeetingInitiateListAdapter.setList(meetingListModel.getData().getList());
                } else {
                    mMeetingInitiateListAdapter.addData(meetingListModel.getData().getList());
                }
                if (meetingListModel.getData().getList() != null && meetingListModel.getData().getList().size() != 0) {
                    mMeetingInitiateListAdapter.getLoadMoreModule().loadMoreComplete();
                } else {
                    mMeetingInitiateListAdapter.getLoadMoreModule().loadMoreEnd();
                }
            }
        });

        mMeetingInitiateListAdapter.addChildClickViewIds(R.id.tv_publish, R.id.tv_delete);
        mMeetingInitiateListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            MeetingListModel.DataBean.ListBean listBean = (MeetingListModel.DataBean.ListBean) adapter.getData().get(position);
            if (view.getId() == R.id.tv_publish) {
                OkGoUtils.put(ApiUrl.MEETINGPUBLISH + listBean.getVcId(), new HashMap(), new BaseCallback() {
                    @Override
                    protected void onSuccess(String callback) {
                        BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                        ToastUtils.showShort(baseBean.getMsg());
                        listBean.setBlSend(1);
                        mMeetingInitiateListAdapter.setData(position, listBean);
                    }

                    @Override
                    protected void onError(String error) {
                        ToastUtils.showShort(error);
                    }
                });
            } else if (view.getId() == R.id.tv_delete) {
                OkGoUtils.delete(ApiUrl.MEETINGDELETE + listBean.getVcId(), new HashMap(), new BaseCallback() {
                    @Override
                    protected void onSuccess(String callback) {
                        BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                        ToastUtils.showShort(baseBean.getMsg());
                        mMeetingInitiateListAdapter.removeAt(position);
                    }

                    @Override
                    protected void onError(String error) {
                        ToastUtils.showShort(error);
                    }
                });
            }
        });

        mMeetingInitiateListAdapter.setOnItemClickListener((adapter, view, position) -> {
            MeetingListModel.DataBean.ListBean listBean = (MeetingListModel.DataBean.ListBean) adapter.getData().get(position);
            Intent intent;
            if (listBean.getBlSend() == 0) {
                intent = new Intent(this, MeetingInitiateChangeActivity.class);
            } else {
                intent = new Intent(this, MeetingDetailsActivity.class);
                intent.putExtra("type", "1");
            }
            intent.putExtra("id", listBean.getVcId());
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected int initTitle() {
        return R.string.meeting_initiate_list;
    }
}