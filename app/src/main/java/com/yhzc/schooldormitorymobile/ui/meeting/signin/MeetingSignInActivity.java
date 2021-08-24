package com.yhzc.schooldormitorymobile.ui.meeting.signin;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivitySignInMeetingBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.meeting.MeetingViewModel;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/13 15:41
 * @描述: 会议签到
 */
public class MeetingSignInActivity extends BaseActivity<MeetingViewModel, ActivitySignInMeetingBinding> {

    private String id;
    private MeetingUserAdapter mMeetingUserAdapter;
    private String signType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_in_meeting;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(id)) {
            mViewModel.initMeetingSignInUser(id);
        }
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        id = getIntent().getStringExtra("id");
        signType = getIntent().getStringExtra("signType");
        mViewModel.initMeetingSignInUser(id);
        showRightTextAndOnClickListener("签到", v -> {
            MeetingUserModel meetingUserModel = mViewModel.getMeetingUserModel().getValue();
            if (meetingUserModel != null && meetingUserModel.getData() != null && meetingUserModel.getData().size() != 0) {
                Intent intent = new Intent(this, MeetingSignInNoUserFaceActivity.class);
                intent.putExtra("vcMeetId", meetingUserModel.getData().get(0).getVcMeetId());
                intent.putExtra("intType", signType);
                ActivityUtils.startActivity(intent);
            }
        });
        initRv();
    }

    private void initRv() {
        mMeetingUserAdapter = new MeetingUserAdapter(R.layout.item_choose_bedroom_qd);
        mViewDataBind.rvUser.setLayoutManager(new GridLayoutManager(this, 3));
        mViewDataBind.rvUser.setAdapter(mMeetingUserAdapter);
        mViewModel.getMeetingUserModel().observe(this, meetingUserModel -> {
            dismissLoading();
            if (meetingUserModel != null) {
                mMeetingUserAdapter.setList(meetingUserModel.getData());
            }
        });
        mMeetingUserAdapter.setOnItemClickListener((adapter, view, position) -> {
            MeetingUserModel.DataBean dataBean = (MeetingUserModel.DataBean) adapter.getData().get(position);
            Intent intent=new Intent(this,MeetingSignInFaceActivity.class);
            intent.putExtra("name",dataBean.getVcJoinUserName());
            intent.putExtra("vcMeetId",dataBean.getVcMeetId());
            intent.putExtra("vcJoinUserId",dataBean.getVcJoinUserId());
            intent.putExtra("intType",signType);
            ActivityUtils.startActivity(intent);
        });

    }

    private void signIn(String picUrl, String vcMeetId, String vcJoinUserId) {
        Map<String, String> map = new HashMap<>();
        map.put("vcMeetId", vcMeetId);
        map.put("vcJoinUserId", vcJoinUserId);
        map.put("vcFaceImg", picUrl);
        map.put("intType", signType);

        OkGoUtils.post(ApiUrl.MEETINGSIGN, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                if (baseBean.getCode() == ApiUrl.SUCCESS) {
                    ToastUtils.showShort("签到成功");
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                finish();
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.meeting_user_list;
    }
}