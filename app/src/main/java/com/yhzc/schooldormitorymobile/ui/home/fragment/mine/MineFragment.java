package com.yhzc.schooldormitorymobile.ui.home.fragment.mine;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.luck.basemodule.base.BaseFragment;
import com.luck.basemodule.utils.PictureUtils;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.FragmentMineBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.Personal.InformationChageActivity;
import com.yhzc.schooldormitorymobile.ui.Personal.PersonalInformationActivity;
import com.yhzc.schooldormitorymobile.ui.changePwd.ChangePwdActivity;
import com.yhzc.schooldormitorymobile.ui.faceRegister.FaceRegisterActivity;
import com.yhzc.schooldormitorymobile.ui.home.HomeViewModel;
import com.yhzc.schooldormitorymobile.ui.login.LoginActivity;
import com.yhzc.schooldormitorymobile.ui.login.LoginModel;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: procyonlotor
 * @创建日期: 2021/4/22 14:58
 * @描述: 个人中心
 */
public class MineFragment extends BaseFragment<HomeViewModel, FragmentMineBinding> {

    private RxPermissions mRxPermissions;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(Cache.getLogin().getData().getVcFacePic())) {
        } else {
            mViewDataBind.tvZcrl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
        }
        mRxPermissions = new RxPermissions(requireActivity());
        mViewDataBind.tvName.setText(Cache.getLogin().getData().getVcRealName());
        mViewDataBind.tvZw.setText(Cache.getLogin().getData().getVcPostion());


        Glide.with(requireActivity())
                .load(Cache.getHttp() + Cache.getLogin().getData().getVcHeadPic())
                .placeholder(R.mipmap.ic_head)
                .into(mViewDataBind.ivHead);

        mViewDataBind.ivHead.setOnClickListener(v -> mRxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        seletedBeforeImg(1);
                    } else {
                        ToastUtils.showShort("需要相应权限");
                    }
                }));

        mViewDataBind.tvZcrl.setOnClickListener(v -> ActivityUtils.startActivity(FaceRegisterActivity.class));

        initClick();
    }

    private void initClick() {
        mViewDataBind.tvGrxx.setOnClickListener(v -> ActivityUtils.startActivity(PersonalInformationActivity.class));
        mViewDataBind.tvXgmm.setOnClickListener(v -> ActivityUtils.startActivity(ChangePwdActivity.class));
        mViewDataBind.tvXgdh.setOnClickListener(v -> ActivityUtils.startActivity(InformationChageActivity.class));
        mViewDataBind.tvExit.setOnClickListener(v -> {
            Cache.saveLogin("");
            Cache.saveToken("");
            ActivityUtils.startActivity(LoginActivity.class);
            ActivityUtils.finishAllActivities();
        });

    }

    private void seletedBeforeImg(int status) {
        PictureUtils.create(requireActivity(), new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                File imageFile = PictureUtils.getImageFile(result);
                upImg(imageFile, status);
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort("取消选择");
            }
        });
    }

    private void upImg(File file, int status) {
        Map<String, String> map = new HashMap<>(2);
        if (status == 1) {
            map.put("vcType", "head");
        } else {
            map.put("vcType", "face");
        }

        OkGoUtils.upFileMap(ApiUrl.UPFACEPIC, file, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                UpImageBean upImageBean = GsonUtils.fromJson(callback, UpImageBean.class);
                if (upImageBean.getCode() == ApiUrl.SUCCESS) {
                    LoginModel loginModel = Cache.getLogin();
                    if (status == 1) {
                        loginModel.getData().setVcHeadPic(upImageBean.getData().getUrl());
                        Cache.saveLogin(GsonUtils.toJson(loginModel));
                        Glide.with(mViewDataBind.ivHead)
                                .load(file)
                                .into(mViewDataBind.ivHead);
                    } else {
                        loginModel.getData().setVcFacePic(upImageBean.getData().getUrl());
                        Cache.saveLogin(GsonUtils.toJson(loginModel));
                    }
                }

            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }
}