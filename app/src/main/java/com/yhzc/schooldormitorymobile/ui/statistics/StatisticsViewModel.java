package com.yhzc.schooldormitorymobile.ui.statistics;

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

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/3 15:13
 * @描述:
 */
public class StatisticsViewModel extends AndroidViewModel {
    private MutableLiveData<WeekStatisticsModel> mWeekStatisticsModel;

    public StatisticsViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<WeekStatisticsModel> getWeekStatisticsModel() {
        if (mWeekStatisticsModel == null) {
            mWeekStatisticsModel = new MutableLiveData<>();
        }
        return mWeekStatisticsModel;
    }

    public void setWeekStatisticsModel(WeekStatisticsModel data) {
        getWeekStatisticsModel().setValue(data);
    }

    public void initData() {
        OkGoUtils.get(ApiUrl.ALLWEEKSTATISTICS, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setWeekStatisticsModel(GsonUtils.fromJson(callback, WeekStatisticsModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setWeekStatisticsModel(null);
            }
        });
    }
}
