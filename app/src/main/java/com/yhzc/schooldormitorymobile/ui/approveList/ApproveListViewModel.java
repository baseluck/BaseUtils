package com.yhzc.schooldormitorymobile.ui.approveList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 17:03
 * @描述:
 */
public class ApproveListViewModel extends AndroidViewModel {
    private MutableLiveData<ApproveListModel> mApproveListModel;

    public ApproveListViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<ApproveListModel> getApproveListModel() {
        if (mApproveListModel == null) {
            mApproveListModel = new MutableLiveData<>();
        }
        return mApproveListModel;
    }

    public void setApproveListModel(ApproveListModel ApproveListModel) {
        getApproveListModel().setValue(ApproveListModel);
    }

    public void initApproveList(int currentPage) {
        Map<String, String> map = new HashMap<>();
        map.put("currentPage", currentPage + "");
        OkGoUtils.get(ApiUrl.APPROVELIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                ApproveListModel approveListModel = GsonUtils.fromJson(callback, ApproveListModel.class);
                setApproveListModel(approveListModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });


    }
}
