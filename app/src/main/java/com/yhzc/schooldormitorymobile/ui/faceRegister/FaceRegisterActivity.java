package com.yhzc.schooldormitorymobile.ui.faceRegister;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.luck.basemodule.base.BaseActivity;
import com.luck.basemodule.utils.PictureUtils;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityFaceRegisterBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.home.fragment.mine.UpImageBean;
import com.yhzc.schooldormitorymobile.ui.login.LoginModel;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/31 16:30
 * @描述: 人脸注册
 */
public class FaceRegisterActivity extends BaseActivity<FaceRegisterViewModel, ActivityFaceRegisterBinding> {

    private RxPermissions mRxPermissions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_face_register;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        mRxPermissions = new RxPermissions(this);

        if (!StringUtils.isEmpty(Cache.getLogin().getData().getVcFacePic().trim()) && !StringUtils.isEmpty(Cache.getLogin().getData().getVcFaceToken().trim())) {
            Glide.with(this)
                    .load(Cache.getHttp() + Cache.getLogin().getData().getVcFacePic())
                    .into(mViewDataBind.ivFace);
            mViewDataBind.tvRegister.setVisibility(View.GONE);
        } else {
            mViewDataBind.tvRegister.setVisibility(View.VISIBLE);

        }

        mViewDataBind.tvRegister.setOnClickListener(v -> mRxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        seletedBeforeImg();
                    } else {
                        ToastUtils.showShort("需要相应权限");
                    }
                }));
    }

    @Override
    protected int initTitle() {
        return R.string.zcrlrz;
    }

    private void seletedBeforeImg() {
        PictureUtils.create(this, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                File imageFile = PictureUtils.getImageFile(result);
                upImg(imageFile);
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort("取消选择");
            }
        });
    }

    private void upImg(File file) {
        Map<String, String> map = new HashMap<>(2);
        map.put("vcType", "face");

        OkGoUtils.upFileMap(ApiUrl.UPFACEPIC, file, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                UpImageBean upImageBean = GsonUtils.fromJson(callback, UpImageBean.class);
                if (upImageBean.getCode() == ApiUrl.SUCCESS) {
                    Glide.with(FaceRegisterActivity.this)
                            .load(Cache.getHttp() + upImageBean.getData().getUrl())
                            .into(mViewDataBind.ivFace);
                    getFaceToken();
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }

    private void getFaceToken() {
        OkGoUtils.post(ApiUrl.FACETOKEN, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                if (baseBean.getCode() == ApiUrl.SUCCESS) {
                    if (!StringUtils.isEmpty(baseBean.getMsg().trim())) {
                        ToastUtils.showShort(baseBean.getMsg());
                        LoginModel loginModel = Cache.getLogin();
                        loginModel.getData().setVcFaceToken(baseBean.getMsg());
                        Cache.saveLogin(GsonUtils.toJson(loginModel));
                        mViewDataBind.tvRegister.setVisibility(View.GONE);
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