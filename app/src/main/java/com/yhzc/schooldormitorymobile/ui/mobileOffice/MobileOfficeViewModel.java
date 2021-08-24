package com.yhzc.schooldormitorymobile.ui.mobileOffice;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.home.HomeItemApp;
import com.yhzc.schooldormitorymobile.ui.home.NewNoticeModel;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.HomeOfficeModel;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 14:42
 * @描述:
 */
public class MobileOfficeViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mNewNotice;
    private MutableLiveData<List<MobileOfficeModel>> mMobileOfficeModels;
    private MutableLiveData<MobileModel> mMobileModel;
    private MutableLiveData<HomeOfficeModel> mHomeOfficeModel;


    public MobileOfficeViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getNewNotice() {
        if (mNewNotice == null) {
            mNewNotice = new MutableLiveData<>();
        }
        return mNewNotice;
    }

    public void setNewNotice(Boolean newNotice) {
        getNewNotice().setValue(newNotice);
    }

    public MutableLiveData<List<MobileOfficeModel>> getMobileOfficeModels() {
        if (mMobileOfficeModels == null) {
            mMobileOfficeModels = new MutableLiveData<>();
        }
        return mMobileOfficeModels;
    }

    public void setMobileOfficeModels(List<MobileOfficeModel> mobileOfficeModels) {
        getMobileOfficeModels().setValue(mobileOfficeModels);
    }

    public MutableLiveData<MobileModel> getMobileModel() {
        if (mMobileModel == null) {
            mMobileModel = new MutableLiveData<>();
        }
        return mMobileModel;
    }

    public void setMobileModel(MobileModel data) {
        getMobileModel().setValue(data);
    }

    public MutableLiveData<HomeOfficeModel> getHomeOfficeModel() {
        if (mHomeOfficeModel == null) {
            mHomeOfficeModel = new MutableLiveData<>();
        }
        return mHomeOfficeModel;
    }

    public void setHomeOfficeModel(HomeOfficeModel data) {
        getHomeOfficeModel().setValue(data);
    }

    public void initNewNotice(Context context) {
        OkGoUtils.get(ApiUrl.NEWNOTICE, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                NewNoticeModel newNoticeModel = GsonUtils.fromJson(callback, NewNoticeModel.class);
                if (newNoticeModel.getCode() == ApiUrl.SUCCESS) {
                    Cache.saveHasNewNotice(newNoticeModel.isData());
                    setNewNotice(newNoticeModel.isData());
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    public void initHomeOffice() {
        OkGoUtils.get(ApiUrl.HOMEOFFICEITEM, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setHomeOfficeModel(GsonUtils.fromJson(callback, HomeOfficeModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setHomeOfficeModel(null);
            }
        });
    }

    public void initData() {
        OkGoUtils.get(ApiUrl.MOBILEOFFICEITEM, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setMobileModel(GsonUtils.fromJson(callback, MobileModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setMobileModel(null);
            }
        });
    }

    public void getData(boolean showNew) {
        List<MobileOfficeModel> mobileOfficeModels = new ArrayList<>();
        MobileOfficeModel mobileOfficeModel1 = new MobileOfficeModel();
        mobileOfficeModel1.setTitle("日常管理");
        List<HomeItemApp> homeItemApps1 = new ArrayList<>();
        homeItemApps1.add(new HomeItemApp(R.mipmap.a, "回寝提交", false));
        homeItemApps1.add(new HomeItemApp(R.mipmap.a, "回寝登记", false));
        homeItemApps1.add(new HomeItemApp(R.mipmap.b, "签到打卡", false));
        homeItemApps1.add(new HomeItemApp(R.mipmap.c, "请假申请", false));
        homeItemApps1.add(new HomeItemApp(R.mipmap.d, "通知公告", showNew));
        homeItemApps1.add(new HomeItemApp(R.mipmap.ic_notice, "发布通知", false));
        homeItemApps1.add(new HomeItemApp(R.mipmap.f, "审批处理", false));
        homeItemApps1.add(new HomeItemApp(R.mipmap.ic_supplies, "物资申请", false));
        homeItemApps1.add(new HomeItemApp(R.mipmap.ic_supplies, "物资退库", false));
        homeItemApps1.add(new HomeItemApp(R.mipmap.ic_supplies, "随手记", false));
        homeItemApps1.add(new HomeItemApp(R.mipmap.ic_supplies, "消息中心", false));
        mobileOfficeModel1.setHomeItemApps(homeItemApps1);

        MobileOfficeModel mobileOfficeModel2 = new MobileOfficeModel();
        mobileOfficeModel2.setTitle("学校管理");
        List<HomeItemApp> homeItemApps2 = new ArrayList<>();
        homeItemApps2.add(new HomeItemApp(R.mipmap.h, "绩效打分", false));
        homeItemApps2.add(new HomeItemApp(R.mipmap.h, "绩效成绩", false));
        homeItemApps2.add(new HomeItemApp(R.mipmap.h, "学生违纪", false));
        homeItemApps2.add(new HomeItemApp(R.mipmap.ic_outsider, "外来人员", false));
        homeItemApps2.add(new HomeItemApp(R.mipmap.ic_in_school, "学生入校", false));
        homeItemApps2.add(new HomeItemApp(R.mipmap.ic_out_school, "学生出校", false));
        mobileOfficeModel2.setHomeItemApps(homeItemApps2);

        MobileOfficeModel mobileOfficeModel3 = new MobileOfficeModel();
        mobileOfficeModel3.setTitle("应用管理");
        List<HomeItemApp> homeItemApps3 = new ArrayList<>();
        homeItemApps3.add(new HomeItemApp(R.mipmap.ic_check_bed_mor, "早上查寝", false));
        homeItemApps3.add(new HomeItemApp(R.mipmap.ic_check_bed_mor, "中午查寝", false));
        homeItemApps3.add(new HomeItemApp(R.mipmap.ic_check_bed_night, "晚上查寝", false));
        homeItemApps3.add(new HomeItemApp(R.mipmap.ic_check_health_tj, "卫生突击检查", false));
        homeItemApps3.add(new HomeItemApp(R.mipmap.ic_check_health_rc, "卫生日常检查", false));
        mobileOfficeModel3.setHomeItemApps(homeItemApps3);
//
        MobileOfficeModel mobileOfficeModel4 = new MobileOfficeModel();
        mobileOfficeModel4.setTitle("会议管理");
        List<HomeItemApp> homeItemApps4 = new ArrayList<>();
        homeItemApps4.add(new HomeItemApp(R.mipmap.ic_meeting, "发布会议", false));
        homeItemApps4.add(new HomeItemApp(R.mipmap.ic_my_meeting, "我的会议", false));
        mobileOfficeModel4.setHomeItemApps(homeItemApps4);

        mobileOfficeModels.add(mobileOfficeModel1);
        mobileOfficeModels.add(mobileOfficeModel2);
        mobileOfficeModels.add(mobileOfficeModel3);
        mobileOfficeModels.add(mobileOfficeModel4);

        setMobileOfficeModels(mobileOfficeModels);
    }
}
