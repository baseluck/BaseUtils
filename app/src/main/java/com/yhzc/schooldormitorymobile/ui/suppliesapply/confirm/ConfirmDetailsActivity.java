package com.yhzc.schooldormitorymobile.ui.suppliesapply.confirm;

import android.Manifest;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.lzy.okgo.model.Progress;
import com.previewlibrary.ZoomMediaLoader;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.luck.basemodule.base.BaseActivity;
import com.luck.basemodule.utils.PictureUtils;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityConfirmDetailsBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UpLoadBean;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.SuppliesApplyViewModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.returnsdetails.ReturnsWzAdapter;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;
import com.luck.basemodule.utils.TestImageLoader;
import com.luck.basemodule.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/28 14:59
 * @描述: 退库详情
 */
public class ConfirmDetailsActivity extends BaseActivity<SuppliesApplyViewModel, ActivityConfirmDetailsBinding> {
    private ReturnsWzAdapter mWzAdapter;
    private WzImageAdapter mImageAdapter;
    private RxPermissions mRxPermissions;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_details;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        ZoomMediaLoader.getInstance().init(new TestImageLoader());
        mRxPermissions = new RxPermissions(this);
        id = getIntent().getStringExtra("id");
        String title = getIntent().getStringExtra("title");
        setTabTitle(title);
        mViewModel.initReturnsDetails(id);
        initRv();
        initImgRv();
        mViewModel.getReturnDetailsModel().observe(this, returnDetailsModel -> {
            if (returnDetailsModel != null) {
                mViewDataBind.tvTitle.setText(returnDetailsModel.getData().getVcTitle());
                mViewDataBind.tvBz.setText(returnDetailsModel.getData().getTextMark());
                mWzAdapter.setList(returnDetailsModel.getData().getDetails());
            }
        });
        mViewDataBind.tvBl.setOnClickListener(v -> checkData());
    }

    private void initRv() {
        mWzAdapter = new ReturnsWzAdapter(R.layout.item_approve_wz);
        mViewDataBind.rvWz.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvWz.setAdapter(mWzAdapter);
    }

    private void initImgRv() {
        mImageAdapter = new WzImageAdapter(R.layout.item_hf_img);
        mViewDataBind.rvImg.setLayoutManager(new GridLayoutManager(this, 3));
        mViewDataBind.rvImg.setAdapter(mImageAdapter);
        mViewDataBind.tvAddImg.setOnClickListener(v -> mRxPermissions.request(NEEDED_PERMISSIONS)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        List<SelectImageModel> selectImageModels = mViewModel.getSelectImageModel().getValue();
                        if (selectImageModels == null) {
                            seletedImage();
                        } else {
                            if (selectImageModels.size() < 9) {
                                seletedImage();
                            } else {
                                ToastUtils.showShort("最多选择9张");
                            }
                        }
                    } else {
                        ToastUtils.showShort("权限获取失败，请检查设置");
                    }
                }));
        mViewModel.getSelectImageModel().observe(this, selectImageModels -> mImageAdapter.setList(selectImageModels));
        mImageAdapter.addChildClickViewIds(R.id.iv_delete, R.id.iv_img);
        mImageAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<SelectImageModel> data = adapter.getData();
            if (view.getId() == R.id.iv_delete) {
                data.remove(position);
                mViewModel.setSelectImageModel(data);
            } else if (view.getId() == R.id.iv_img) {
                List<String> images = new ArrayList<>();
                for (SelectImageModel selectImageModel : data) {
                    if (StringUtils.equals("0", selectImageModel.getImgType())) {
                        images.add(selectImageModel.getImgUrl());
                    } else {
                        images.add(Cache.getHttp() + selectImageModel.getImgUrl());
                    }
                }
                Utils.showBigImg(images, position);
            }
        });
    }

    private void seletedImage() {
        PictureUtils.createImage(this, 9, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                List<String> imageFileList = PictureUtils.getImagePath(result);
                List<SelectImageModel> selectImageModels = mViewModel.getSelectImageModel().getValue();
                if (selectImageModels == null) {
                    selectImageModels = new ArrayList<>();
                }
                if (selectImageModels.size() + imageFileList.size() > 9) {
                    ToastUtils.showShort("最多9张");
                    return;
                }
                for (String url : imageFileList) {
                    SelectImageModel selectImageModel = new SelectImageModel(url, "0");
                    selectImageModels.add(selectImageModel);
                }
                mViewModel.setSelectImageModel(selectImageModels);
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort("取消选择");
            }
        });
    }

    private void checkData() {
        List<SelectImageModel> selectImageModels = mViewModel.getSelectImageModel().getValue();
        if (selectImageModels == null || selectImageModels.size() == 0) {
            ToastUtils.showShort("请上传照片");
            return;
        }
        updateImg(selectImageModels);
    }


    private void updateImg(List<SelectImageModel> selectImageModels) {
        List<String> images = new ArrayList<>();
        List<String> upImages = new ArrayList<>();
        for (SelectImageModel selectImageModel : selectImageModels) {
            if (StringUtils.equals("0", selectImageModel.getImgType())) {
                images.add(selectImageModel.getImgUrl());
            } else {
                upImages.add(selectImageModel.getImgUrl());
            }
        }
        if (images.size() == 0) {
            send(upImages);
        } else {
            List<File> imageFiles = PictureUtils.getImageFiles(images);
            OkGoUtils.upFiles(ApiUrl.UPLOADIMG, imageFiles, new BaseCallback() {
                @Override
                protected void onSuccess(String callback) {
                    UpLoadBean upLoadBean = GsonUtils.fromJson(callback, UpLoadBean.class);
                    if (upLoadBean.getData() != null && upLoadBean.getData().size() != 0) {
                        List<String> images = new ArrayList<>();
                        for (UpLoadBean.DataBean dataBean : upLoadBean.getData()) {
                            images.add(dataBean.getUrl());
                        }
                        images.addAll(upImages);
                        send(images);
                    } else {
                        send(upImages);
                    }
                }

                @Override
                protected void onError(String error) {
                    dismissLoading();
                    ToastUtils.showShort(error);
                }

                @Override
                public void uploadProgress(Progress progress) {
                    super.uploadProgress(progress);
                    int p = (int) (progress.fraction * 100);
                    updateLoadingMsg("正在上传图片：" + p + "%");
                }
            });
        }
    }

    private void send(List<String> images) {
        Map<String, Object> map = new HashMap<>();
        map.put("vcId", id);
        StringBuilder imageBuilder = new StringBuilder();
        for (int i = 0; i < images.size(); i++) {
            if (i < images.size() - 1) {
                imageBuilder.append(images.get(i)).append(",");
            } else {
                imageBuilder.append(images.get(i));
            }
        }
        map.put("vcFiles", imageBuilder.toString());
        OkGoUtils.post(ApiUrl.RKCKQR, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                ToastUtils.showShort(baseBean.getMsg());
                finish();
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }

    @Override
    protected int initTitle() {
        return 0;
    }
}