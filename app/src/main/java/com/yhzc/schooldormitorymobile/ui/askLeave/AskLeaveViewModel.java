package com.yhzc.schooldormitorymobile.ui.askLeave;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.luck.basemodule.utils.OkGoUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述:
 * @创建日期: 2021/4/16 13:39
 * @author: ProcyonLotor
 */
public class AskLeaveViewModel extends AndroidViewModel {

    private MutableLiveData<LeaveApproveModel> mLeaveApproveModel;
    private MutableLiveData<List<LeaveItemModel>> mLeaveItemModels;
    private MutableLiveData<List<File>> mImages;

    public AskLeaveViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<LeaveApproveModel> getLeaveApproveModel() {
        if (mLeaveApproveModel == null) {
            mLeaveApproveModel = new MutableLiveData<>();
        }
        return mLeaveApproveModel;
    }

    public void setLeaveApproveModel(LeaveApproveModel LeaveApproveModel) {
        getLeaveApproveModel().setValue(LeaveApproveModel);
    }

    public MutableLiveData<List<LeaveItemModel>> getLeaveItemModels() {
        if (mLeaveItemModels == null) {
            mLeaveItemModels = new MutableLiveData<>();
        }
        return mLeaveItemModels;
    }

    public void setLeaveItemModels(List<LeaveItemModel> leaveItemModels) {
        getLeaveItemModels().setValue(leaveItemModels);
    }

    public MutableLiveData<List<File>> getImages() {
        if (mImages == null) {
            mImages = new MutableLiveData<>();
        }
        return mImages;
    }

    public void setImages(List<File> images) {
        getImages().setValue(images);
    }

    public void initLeaveApprove(String classId) {
        Map<String, String> map = new HashMap<>();
        if (!StringUtils.isEmpty(classId)) {
            map.put("gradeClassId", classId);
        }
        OkGoUtils.get(ApiUrl.LEAVEAPPROVER, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                LeaveApproveModel leaveApproveModel = GsonUtils.fromJson(callback, LeaveApproveModel.class);
                setLeaveApproveModel(leaveApproveModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });


    }
}
