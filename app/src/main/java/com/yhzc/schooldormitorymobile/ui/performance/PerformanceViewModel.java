package com.yhzc.schooldormitorymobile.ui.performance;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.performance.rank.PerformanceRankModel;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 17:51
 * @描述:
 */
public class PerformanceViewModel extends AndroidViewModel {

    private MutableLiveData<PerforManceModel> mPerforManceModel;
    private MutableLiveData<PerformanceRankModel> mPerformanceRankModel;


    public PerformanceViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<PerforManceModel> getPerforManceModel() {
        if (mPerforManceModel == null) {
            mPerforManceModel = new MutableLiveData<>();
        }
        return mPerforManceModel;
    }

    public void setPerforManceModel(PerforManceModel PerforManceModel) {
        getPerforManceModel().setValue(PerforManceModel);
    }

    public MutableLiveData<PerformanceRankModel> getPerformanceRankModel() {
        if (mPerformanceRankModel == null) {
            mPerformanceRankModel = new MutableLiveData<>();
        }
        return mPerformanceRankModel;
    }

    public void setPerformanceRankModel(PerformanceRankModel data) {
        getPerformanceRankModel().setValue(data);
    }

    public void initData() {
        OkGoUtils.get(ApiUrl.MYPLANLIST, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                PerforManceModel perforManceModel = GsonUtils.fromJson(callback, PerforManceModel.class);
                setPerforManceModel(perforManceModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    public void initDataAll() {
        OkGoUtils.get(ApiUrl.ALLPLANLIST, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                PerforManceModel perforManceModel = GsonUtils.fromJson(callback, PerforManceModel.class);
                setPerforManceModel(perforManceModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    public void initRankDetails(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("planId", id);

        OkGoUtils.get(ApiUrl.PERRANKDETAILS, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setPerformanceRankModel(GsonUtils.fromJson(callback, PerformanceRankModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setPerformanceRankModel(null);
            }
        });
    }
}
