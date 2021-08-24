package com.yhzc.schooldormitorymobile.ui.meeting.initiatedetails;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.previewlibrary.ZoomMediaLoader;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityMeetingDetailsBinding;
import com.yhzc.schooldormitorymobile.ui.askLeaveDetails.ImgShowAdapter;
import com.yhzc.schooldormitorymobile.ui.meeting.MeetingViewModel;
import com.yhzc.schooldormitorymobile.ui.meeting.signin.MeetingUserAdapter;
import com.luck.basemodule.utils.UrlImageLoader;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/13 15:41
 * @描述: 会议签到
 */
public class MeetingDetailsActivity extends BaseActivity<MeetingViewModel, ActivityMeetingDetailsBinding> {

    private String id;
    private MeetingUserAdapter mMeetingUserAdapter;
    private ImgShowAdapter mImgShowAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting_details;
    }


    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        ZoomMediaLoader.getInstance().init(new UrlImageLoader(Cache.getHttp()));
        id = getIntent().getStringExtra("id");
        String type = getIntent().getStringExtra("type");
        mViewModel.initMeetingSignInUser(id);
        mViewModel.initMeetingDetails(id);
        initImgRv();

        mViewDataBind.refresh.setOnRefreshListener(() -> {
            mViewModel.initMeetingSignInUser(id);
            mViewModel.initMeetingDetails(id);
        });

        mViewModel.getMeetingDetailsModel().observe(this, meetingDetailsModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (meetingDetailsModel != null) {
                mViewDataBind.tvTitle.setText(meetingDetailsModel.getData().getVcTitle());
                mViewDataBind.tvUser.setText(String.format(StringUtils.getString(R.string.meeting_list_charge_user), meetingDetailsModel.getData().getVcSignUserName()));
                mViewDataBind.tvLocation.setText(String.format(StringUtils.getString(R.string.meeting_list_time), TimeUtils.millis2String(meetingDetailsModel.getData().getDtStartTime()), TimeUtils.millis2String(meetingDetailsModel.getData().getDtEndTime())));
                mViewDataBind.tvDate.setText(String.format(StringUtils.getString(R.string.meeting_list_check_in_time), TimeUtils.millis2String(meetingDetailsModel.getData().getDtSignInStartTime()), TimeUtils.millis2String(meetingDetailsModel.getData().getDtSignInEndTime())));
                mViewDataBind.tvTime.setText(String.format(StringUtils.getString(R.string.meeting_list_check_out_time), TimeUtils.millis2String(meetingDetailsModel.getData().getDtSignOutStartTime()), TimeUtils.millis2String(meetingDetailsModel.getData().getDtSignOutEndTime())));
                if (!StringUtils.isEmpty(meetingDetailsModel.getData().getVcPath())) {
                    String[] split = meetingDetailsModel.getData().getVcPath().split(",");
                    List<String> images = new ArrayList<>(Arrays.asList(split));
                    mImgShowAdapter.setList(images);
                }
            }
        });
        if (StringUtils.equals("1", type)) {
            initRv();
        } else {
            mViewDataBind.rvUser.setVisibility(View.GONE);
        }
    }

    private void initImgRv() {
        mImgShowAdapter = new ImgShowAdapter(R.layout.item_notice_select_img, null);
        mViewDataBind.rvImg.setLayoutManager(new GridLayoutManager(this, 3));
        mViewDataBind.rvImg.setAdapter(mImgShowAdapter);
        mImgShowAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<String> data = (List<String>) adapter.getData();
            Utils.showBigImg(data, position);
        });
    }

    private void initRv() {
        mMeetingUserAdapter = new MeetingUserAdapter(R.layout.item_choose_bedroom_qd);
        mViewDataBind.rvUser.setLayoutManager(new GridLayoutManager(this, 3));
        mViewDataBind.rvUser.setAdapter(mMeetingUserAdapter);
        mViewModel.getMeetingUserModel().observe(this, meetingUserModel -> {
            mViewDataBind.refresh.setRefreshing(false);
            dismissLoading();
            if (meetingUserModel != null) {
                mMeetingUserAdapter.setList(meetingUserModel.getData());
            }
        });

//        mMeetingUserAdapter.setOnItemClickListener((adapter, view, position) -> {
//
//            MeetingUserModel.DataBean dataBean = (MeetingUserModel.DataBean) adapter.getData().get(position);
//            Intent intent = new Intent(this, MeetingSignInFaceActivity.class);
//            intent.putExtra("name", dataBean.getVcJoinUserName());
//            intent.putExtra("vcMeetId", dataBean.getVcMeetId());
//            intent.putExtra("vcJoinUserId", dataBean.getVcJoinUserId());
//            intent.putExtra("intType", signType);
//            ActivityUtils.startActivity(intent);
//        });
    }

    @Override
    protected int initTitle() {
        return R.string.meeting_details;
    }
}