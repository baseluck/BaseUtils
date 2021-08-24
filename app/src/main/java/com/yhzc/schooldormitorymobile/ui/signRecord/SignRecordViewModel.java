package com.yhzc.schooldormitorymobile.ui.signRecord;

import android.app.Application;
import android.content.Context;

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
 * @创建日期: 2021/4/15 15:29
 * @author: ProcyonLotor
 */
public class SignRecordViewModel extends AndroidViewModel {
    private MutableLiveData<SignListBean> mSignListBean;
    private MutableLiveData<CheckStatisticsModel> mCheckStatisticsModel;

    public SignRecordViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<SignListBean> getSignListBean() {
        if (mSignListBean == null) {
            mSignListBean = new MutableLiveData<>();
        }
        return mSignListBean;
    }

    public void setSignListBean(SignListBean SignListBean) {
        getSignListBean().setValue(SignListBean);
    }

    public MutableLiveData<CheckStatisticsModel> getCheckStatisticsModel() {
        if (mCheckStatisticsModel == null) {
            mCheckStatisticsModel = new MutableLiveData<>();
        }
        return mCheckStatisticsModel;
    }

    public void setCheckStatisticsModel(CheckStatisticsModel data) {
        getCheckStatisticsModel().setValue(data);
    }

    public void initList(Context context, String date) {
        Map<String, String> map = new HashMap<>();
        map.put("date", date);
        OkGoUtils.get(ApiUrl.SIGNLIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                SignListBean signListBean = GsonUtils.fromJson(callback, SignListBean.class);
                if (signListBean.getCode() == ApiUrl.SUCCESS) {
                    setSignListBean(signListBean);
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    public void initTjUser(String date) {
        Map<String, String> map = new HashMap<>();
        map.put("day", date);
        OkGoUtils.get(ApiUrl.PERSONSTATISTICS, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setCheckStatisticsModel(GsonUtils.fromJson(callback, CheckStatisticsModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setCheckStatisticsModel(null);
            }
        });
    }

}
