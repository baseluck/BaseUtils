package com.yhzc.schooldormitorymobile.ui.signIn;

import android.Manifest;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cy.tablayoutniubility.FragPageAdapterVp2NoScroll;
import com.cy.tablayoutniubility.TabAdapterNoScroll;
import com.cy.tablayoutniubility.TabMediatorVp2NoScroll;
import com.cy.tablayoutniubility.TabNoScrollViewHolder;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivitySignInBinding;
import com.yhzc.schooldormitorymobile.ui.signIn.fieldWork.FieldWorkFragment;
import com.yhzc.schooldormitorymobile.ui.signIn.goWork.GoWorkFragment;
import com.yhzc.schooldormitorymobile.ui.signRecord.SignRecordActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @描述: 打卡签到
 * @创建日期: 2021/4/13 14:12
 * @author: ProcyonLotor
 */
public class SignInActivity extends BaseActivity<SignInViewModel, ActivitySignInBinding> implements AMapLocationListener {

    private AMapLocationClient mLocationClient = null;
    private static final String BACK_LOCATION_PERMISSION = "android.permission.ACCESS_BACKGROUND_LOCATION";
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        RxPermissions rxPermissions = new RxPermissions(this);
        mViewDataBind.tablayout.getIndicatorView().getIndicator().setHeight_indicator(0);
        FragPageAdapterVp2NoScroll<String> fragmentPageAdapter = new FragPageAdapterVp2NoScroll<String>(this) {
            @Override
            public Fragment createFragment(String bean, int position) {
                if (position == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("content", "测试页面" + position);
                    return GoWorkFragment.newInstance(bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("content", "测试页面" + position);
                    return FieldWorkFragment.newInstance(bundle);
                }
            }

            @Override
            public void bindDataToTab(TabNoScrollViewHolder holder, int position, String bean, boolean isSelected) {
                TextView textView = holder.getView(R.id.tv);
                if (isSelected) {
                    textView.setTextColor(0xff0382E0);
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else {
                    textView.setTextColor(0xff83C9FD);
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
                textView.setText(bean);
            }

            @Override
            public int getTabLayoutID(int position, String bean) {
                return R.layout.item_tab_center;
            }
        };
        TabAdapterNoScroll<String> tabAdapter = new TabMediatorVp2NoScroll<String>(mViewDataBind.tablayout, mViewDataBind.viewPager).setAdapter(fragmentPageAdapter);
        List<String> list = new ArrayList<>();
        list.add("上班打卡");
        list.add("外勤打卡");
        fragmentPageAdapter.add(list);
        tabAdapter.add(list);
        rxPermissions.request(needPermissions)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (aBoolean) {
                            initLocation();
                        } else {
                            ToastUtils.showShort("无法获取定位权限");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        showRightImgAndOnClickListener(0, v -> ActivityUtils.startActivity(SignRecordActivity.class));
    }

    @Override
    protected int initTitle() {
        return R.string.sign_in;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = getDefaultOption();
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(this);
        mLocationClient.startLocation();
    }

    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mOption.setGpsFirst(true);
        mOption.setHttpTimeOut(30000);
        mOption.setInterval(2000);
        mOption.setNeedAddress(true);
        mOption.setOnceLocation(false);
        mOption.setOnceLocationLatest(false);
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        mOption.setSensorEnable(false);
        mOption.setWifiScan(true);
        mOption.setLocationCacheEnable(true);
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);
        return mOption;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode() == 0) {
            mViewModel.setAMapLocation(aMapLocation);
        }
    }
}