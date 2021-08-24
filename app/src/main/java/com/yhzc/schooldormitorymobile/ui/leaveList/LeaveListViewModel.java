package com.yhzc.schooldormitorymobile.ui.leaveList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述:
 * @创建日期: 2021/4/16 14:49
 * @author: ProcyonLotor
 */
public class LeaveListViewModel extends AndroidViewModel {
    private MutableLiveData<LeaveListModel> mLeaveListModel;


    public LeaveListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<LeaveListModel> getLeaveListModel() {
        if (mLeaveListModel == null) {
            mLeaveListModel = new MutableLiveData<>();
        }
        return mLeaveListModel;
    }

    public void setLeaveListModel(LeaveListModel LeaveListModel) {
        getLeaveListModel().setValue(LeaveListModel);
    }

    public void initLeaveList(String status, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("intStaus", status);

        OkGoUtils.get(ApiUrl.LEAVEMYLIST + type, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                LeaveListModel leaveListModel = GsonUtils.fromJson(callback, LeaveListModel.class);
                setLeaveListModel(leaveListModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }
}
