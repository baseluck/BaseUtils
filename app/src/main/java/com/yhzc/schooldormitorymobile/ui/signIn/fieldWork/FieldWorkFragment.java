package com.yhzc.schooldormitorymobile.ui.signIn.fieldWork;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.luck.basemodule.base.BaseFragment;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.FragmentFieldWordBinding;
import com.yhzc.schooldormitorymobile.ui.outReason.OutReasonActivity;
import com.yhzc.schooldormitorymobile.ui.signIn.SignInViewModel;
import com.yhzc.schooldormitorymobile.utils.Cache;

/**
 * @描述:
 * @创建日期: 2021/4/13 15:17
 * @author: ProcyonLotor
 */
public class FieldWorkFragment extends BaseFragment<SignInViewModel, FragmentFieldWordBinding> {
    private RxPermissions mRxPermissions;
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private String signLy;

    public static FieldWorkFragment newInstance(Bundle bundle) {
        FieldWorkFragment fragment = new FieldWorkFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_field_word;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mRxPermissions = new RxPermissions(requireActivity());
        mViewDataBind.tvLocation.setText("正在定位");
        mViewModel.getAMapLocation().observe(this, aMapLocation -> mViewDataBind.tvLocation.setText(aMapLocation.getAddress()));
        mViewDataBind.tvLocation.setText("打开地图");
        mViewDataBind.tvWcsy.setOnClickListener(v -> ActivityUtils.startActivity(OutReasonActivity.class));
        mViewDataBind.slSignIn.setOnClickListener(v -> {
            AMapLocation aMapLocation = mViewModel.getAMapLocation().getValue();
            if (aMapLocation == null || StringUtils.isEmpty(aMapLocation.getAddress())) {
                ToastUtils.showShort("没有定位信息");
                return;
            }

            if (StringUtils.isEmpty(Cache.getWqly())) {
                ToastUtils.showShort("打卡理由不能为空");
                return;
            }
            Intent intent = new Intent(requireContext(), FaceWqRecognizeActivity.class);
            intent.putExtra("address", aMapLocation.getAddress());
            intent.putExtra("wqly", Cache.getWqly());
            ActivityUtils.startActivity(intent);
        });
    }
}
