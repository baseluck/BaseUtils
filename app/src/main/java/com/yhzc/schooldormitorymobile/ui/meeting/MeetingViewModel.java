package com.yhzc.schooldormitorymobile.ui.meeting;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.ui.meeting.initiate.MeetingDetailsModel;
import com.yhzc.schooldormitorymobile.ui.meeting.list.MeetingListModel;
import com.yhzc.schooldormitorymobile.ui.meeting.signin.MeetingUserModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.SelectUserModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UserModel;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/12 17:48
 * @描述:
 */
public class MeetingViewModel extends AndroidViewModel {

    private MutableLiveData<MeetingListModel> mMeetingListModel;
    private MutableLiveData<SelectUserModel> mSelectUserModel;
    private MutableLiveData<List<UserModel>> mUserModels;
    private MutableLiveData<MeetingUserModel> mMeetingUserModel;
    private MutableLiveData<MeetingDetailsModel> mMeetingDetailsModel;
    private MutableLiveData<List<SelectImageModel>> mSelectImageModel;


    public MeetingViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<MeetingListModel> getMeetingListModel() {
        if (mMeetingListModel == null) {
            mMeetingListModel = new MutableLiveData<>();
        }
        return mMeetingListModel;
    }

    public MutableLiveData<List<UserModel>> getUserModels() {
        if (mUserModels == null) {
            mUserModels = new MutableLiveData<>();
        }
        return mUserModels;
    }

    public void setUserModels(List<UserModel> userModels) {
        getUserModels().setValue(userModels);
    }

    public void setMeetingListModel(MeetingListModel MeetingListModel) {
        getMeetingListModel().setValue(MeetingListModel);
    }

    public MutableLiveData<SelectUserModel> getSelectUserModel() {
        if (mSelectUserModel == null) {
            mSelectUserModel = new MutableLiveData<>();
        }
        return mSelectUserModel;
    }

    public void setSelectUserModel(SelectUserModel SelectUserModel) {
        getSelectUserModel().setValue(SelectUserModel);
    }

    public MutableLiveData<MeetingUserModel> getMeetingUserModel() {
        if (mMeetingUserModel == null) {
            mMeetingUserModel = new MutableLiveData<>();
        }
        return mMeetingUserModel;
    }

    public void setMeetingUserModel(MeetingUserModel MeetingUserModel) {
        getMeetingUserModel().setValue(MeetingUserModel);
    }

    public MutableLiveData<MeetingDetailsModel> getMeetingDetailsModel() {
        if (mMeetingDetailsModel == null) {
            mMeetingDetailsModel = new MutableLiveData<>();
        }
        return mMeetingDetailsModel;
    }

    public void setMeetingDetailsModel(MeetingDetailsModel MeetingDetailsModel) {
        getMeetingDetailsModel().setValue(MeetingDetailsModel);
    }

    public MutableLiveData<List<SelectImageModel>> getSelectImageModel() {
        if (mSelectImageModel == null) {
            mSelectImageModel = new MutableLiveData<>();
        }
        return mSelectImageModel;
    }

    public void setSelectImageModel(List<SelectImageModel> selectImageModels) {
        getSelectImageModel().setValue(selectImageModels);
    }

    public void initMeetingList(int currentPage, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("currentPage", currentPage + "");
        map.put("type", type);
        OkGoUtils.get(ApiUrl.MEETINGLIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                MeetingListModel meetingListModel = GsonUtils.fromJson(callback, MeetingListModel.class);
                if (meetingListModel != null) {
                    if (meetingListModel.getData().getList() != null && meetingListModel.getData().getList().size() != 0) {
                        for (int i = 0; i < meetingListModel.getData().getList().size(); i++) {
                            meetingListModel.getData().getList().get(i).setCheckType(type);
                        }
                    }
                }

                setMeetingListModel(meetingListModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setMeetingListModel(null);
            }
        });

    }

    public void initSelectUser() {
        OkGoUtils.get(ApiUrl.SELECTUSER, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                SelectUserModel selectUserModel = GsonUtils.fromJson(callback, SelectUserModel.class);
                setSelectUserModel(selectUserModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setSelectUserModel(null);
            }
        });

    }

    public void initMeetingSignInUser(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("mid", id);

        OkGoUtils.get(ApiUrl.MEETINGUSERLIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setMeetingUserModel(GsonUtils.fromJson(callback, MeetingUserModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setMeetingUserModel(null);
            }
        });

    }

    public void initMeetingDetails(String id) {
        OkGoUtils.get(ApiUrl.MEETINGDETAILS + id, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setMeetingDetailsModel(GsonUtils.fromJson(callback, MeetingDetailsModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setMeetingDetailsModel(null);
            }
        });
    }

}
