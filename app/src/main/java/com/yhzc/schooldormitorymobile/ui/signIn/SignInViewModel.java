package com.yhzc.schooldormitorymobile.ui.signIn;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.HashMap;

/**
 * @描述: 签到model
 * @创建日期: 2021/4/13 14:11
 * @author: ProcyonLotor
 */
public class SignInViewModel extends AndroidViewModel {
    private MutableLiveData<SignInModel> mSignInModel;
    private final SignInFactory mSignInFactory;

    public SignInViewModel(@NonNull Application application) {
        super(application);
        mSignInFactory = SignInFactory.getInstance();
    }

    public MutableLiveData<AMapLocation> getAMapLocation() {
        return mSignInFactory.getAMapLocation();
    }

    public void setAMapLocation(AMapLocation aMapLocation) {
        mSignInFactory.setAMapLocation(aMapLocation);
    }

    public MutableLiveData<String> getTime() {
        return mSignInFactory.getTime();
    }

    public MutableLiveData<SignInModel> getSignInModel() {
        if (mSignInModel == null) {
            mSignInModel = new MutableLiveData<>();
        }
        return mSignInModel;
    }

    public void setSignInModel(SignInModel SignInModel) {
        getSignInModel().setValue(SignInModel);
    }

    public void initSignList(Context context) {
        OkGoUtils.get(ApiUrl.SIGNLOCATION, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                SignInModel signInModel = GsonUtils.fromJson(callback, SignInModel.class);
                if (signInModel.getCode() == ApiUrl.SUCCESS) {
                    setSignInModel(signInModel);
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }
}
