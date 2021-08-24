package com.yhzc.schooldormitorymobile.ui.signIn;

import androidx.lifecycle.MutableLiveData;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.util.TimeUtils;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @描述:
 * @创建日期: 2021/4/15 12:52
 * @author: ProcyonLotor
 */
public class SignInFactory {
    private MutableLiveData<AMapLocation> mAMapLocation;
    private MutableLiveData<String> mTime;
    private Timer timer;
    private static SignInFactory mSignInFactory;
    public SignInFactory() {
    }
    public static void executeFixedRate() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                    }
                },
                0,
                1000,
                TimeUnit.MILLISECONDS);
    }

    public static SignInFactory getInstance() {
        if (mSignInFactory == null) {
            mSignInFactory = new SignInFactory();
        }
        return mSignInFactory;
    }


    public MutableLiveData<AMapLocation> getAMapLocation() {
        if (mAMapLocation == null) {
            mAMapLocation = new MutableLiveData<>();
        }
        return mAMapLocation;
    }

    public void setAMapLocation(AMapLocation aMapLocation) {
        getAMapLocation().setValue(aMapLocation);
    }

    public MutableLiveData<String> getTime() {
        if (mTime == null) {
            mTime = new MutableLiveData<>();
            mTime.setValue(TimeUtils.getNowString());
        }
        return mTime;
    }

    public void setTime(String time) {
        getTime().setValue(time);
    }
}
