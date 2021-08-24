package com.yhzc.schooldormitorymobile.ui.askLeaveDetails;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.SelectUserModel;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 19:31
 * @描述:
 */
public class LeaveDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<LeaveDetailsModel> mLeaveDetailsModel;
    private MutableLiveData<SelectUserModel> mSelectUserModel;

    private MutableLiveData<List<SelectImageModel>> mSelectImageModel;

    public MutableLiveData<List<SelectImageModel>> getSelectImageModel() {
        if (mSelectImageModel == null) {
            mSelectImageModel = new MutableLiveData<>();
        }
        return mSelectImageModel;
    }

    public void setSelectImageModel(List<SelectImageModel> selectImageModels) {
        getSelectImageModel().setValue(selectImageModels);
    }

    public LeaveDetailsViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<LeaveDetailsModel> getLeaveDetailsModel() {
        if (mLeaveDetailsModel == null) {
            mLeaveDetailsModel = new MutableLiveData<>();
        }
        return mLeaveDetailsModel;
    }

    public void setLeaveDetailsModel(LeaveDetailsModel LeaveDetailsModel) {
        getLeaveDetailsModel().setValue(LeaveDetailsModel);
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


    public void initLeaveDetails(String leaveId, String workType) {
        OkGoUtils.get(ApiUrl.LEAVEDETAILS + leaveId + "/" + workType, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                LeaveDetailsModel leaveDetailsModel = GsonUtils.fromJson(callback, LeaveDetailsModel.class);
                setLeaveDetailsModel(leaveDetailsModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    public void initSelectUser(Context context) {
        OkGoUtils.get(ApiUrl.SELECTUSER, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                SelectUserModel selectUserModel = GsonUtils.fromJson(callback, SelectUserModel.class);
                setSelectUserModel(selectUserModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });


    }
}
