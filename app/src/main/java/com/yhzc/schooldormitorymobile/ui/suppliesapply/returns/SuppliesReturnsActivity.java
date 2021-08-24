package com.yhzc.schooldormitorymobile.ui.suppliesapply.returns;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
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
import com.yhzc.schooldormitorymobile.databinding.ActivitySuppliesReturnsBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.ImageAdapter;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UpLoadBean;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.SuppliesApplyViewModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.apply.WzListModel;
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
 * @创建日期: 2021/7/20 10:14
 * @描述: 物资退库
 */
public class SuppliesReturnsActivity extends BaseActivity<SuppliesApplyViewModel, ActivitySuppliesReturnsBinding> {
    private WzReturnsAdapter mWzslAdapter;
    private ImageAdapter mImageAdapter;
    private RxPermissions mRxPermissions;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_supplies_returns;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        ZoomMediaLoader.getInstance().init(new TestImageLoader());
        mRxPermissions = new RxPermissions(this);
        mViewModel.initWzKc();
        initRv();
        initImgRv();
        mViewDataBind.tvSelect.setOnClickListener(v -> showWzList());
        showRightTextAndOnClickListener("提交", v -> checkData());
    }

    private void initRv() {
        mWzslAdapter = new WzReturnsAdapter(R.layout.item_supplies_apply);
        mViewDataBind.rvWz.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvWz.setAdapter(mWzslAdapter);
        mViewModel.getWzReturnsModel().observe(this, wzslModels -> {
            if (wzslModels != null) {
                mWzslAdapter.setList(wzslModels);
            }
        });
        mWzslAdapter.addChildClickViewIds(R.id.iv_add, R.id.iv_minus, R.id.tv_num, R.id.tv_delete);
        mWzslAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<WzReturnsModel> wzslModels = mViewModel.getWzReturnsModel().getValue();
            if (wzslModels != null) {
                int intNum = wzslModels.get(position).getIntNum();
                if (view.getId() == R.id.iv_add) {
                    if (intNum < wzslModels.get(position).getKcNum()) {
                        intNum++;
                        wzslModels.get(position).setIntNum(intNum);
                    } else {
                        ToastUtils.showShort("库存剩余：" + wzslModels.get(position).getKcNum());
                    }
                } else if (view.getId() == R.id.iv_minus) {
                    if (intNum > 1) {
                        intNum--;
                        wzslModels.get(position).setIntNum(intNum);
                    }
                } else if (view.getId() == R.id.tv_num) {
                    List<String> data = new ArrayList<>();
                    for (int i = 1; i <= wzslModels.get(position).getKcNum(); i++) {
                        data.add(String.valueOf(i));
                    }
                    showScoreList(data, position, wzslModels);
                } else if (view.getId() == R.id.tv_delete) {
                    wzslModels.remove(position);
                }
                mViewModel.setWzReturnsModel(wzslModels);
            }
        });
    }

    private void showScoreList(List<String> data, int position, List<WzReturnsModel> wzslModels) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            int score = Integer.parseInt(data.get(options1));
            wzslModels.get(position).setIntNum(score);
            mViewModel.setWzReturnsModel(wzslModels);
        })
                .setTitleText("选择数量")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(data);//三级选择器
        pvOptions.show();
    }

    @Override
    protected int initTitle() {
        return R.string.wz_returns;
    }

    private void initImgRv() {
        mImageAdapter = new ImageAdapter(R.layout.item_hf_img);
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
        if (StringUtils.isEmpty(mViewDataBind.etTitle.getText().toString().trim())) {
            ToastUtils.showShort("标题不能为空");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.etSqyy.getText().toString().trim())) {
            ToastUtils.showShort("申请原因不能为空");
            return;
        }

        List<WzReturnsModel> wzslModels = mViewModel.getWzReturnsModel().getValue();
        if (wzslModels == null || wzslModels.size() == 0) {
            ToastUtils.showShort("请选择物资");
            return;
        }
        List<SelectImageModel> selectImageModels = mViewModel.getSelectImageModel().getValue();
        if (selectImageModels == null || selectImageModels.size() == 0) {
            ToastUtils.showShort("请上传照片");
            return;
        }
        updateImg(selectImageModels, wzslModels);
    }

    private void updateImg(List<SelectImageModel> selectImageModels, List<WzReturnsModel> wzslModels) {
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
            send(upImages, wzslModels);
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
                        send(images, wzslModels);
                    } else {
                        send(upImages, wzslModels);
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

    private void showWzList() {
        WzListModel wzListModel = mViewModel.getWzListModel().getValue();
        if (wzListModel == null) {
            ToastUtils.showShort("物资列表正在初始化，稍后再试");
            mViewModel.initWzKc();
        } else {
            if (wzListModel.getData() != null && wzListModel.getData().size() != 0) {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
                    List<WzReturnsModel> wzslModels = mViewModel.getWzReturnsModel().getValue();
                    if (wzslModels == null) {
                        wzslModels = new ArrayList<>();
                    }
                    boolean has = false;
                    for (WzReturnsModel wzslModel : wzslModels) {
                        if (StringUtils.equals(wzslModel.getVcMaterialId(), wzListModel.getData().get(options1).getVcMaterialId())) {
                            has = true;
                        }
                    }
                    if (!has) {
                        WzListModel.DataBean dataBean = wzListModel.getData().get(options1);
                        WzReturnsModel wzslModel = new WzReturnsModel();
                        wzslModel.setIntNum(1);
                        wzslModel.setKcNum(dataBean.getIntNum());
                        wzslModel.setVcMaterialId(dataBean.getVcMaterialId());
                        wzslModel.setVcMaterialCode(dataBean.getVcCode());
                        wzslModel.setVcMaterialName(dataBean.getVcName());
                        wzslModel.setVcSpecification(dataBean.getVcSpecification());
                        wzslModel.setVcUnit(dataBean.getVcUnit());
                        wzslModels.add(wzslModel);
                        mViewModel.setWzReturnsModel(wzslModels);
                    }
                })
                        .setTitleText("选择请假类型")
                        .setTextColorCenter(Color.BLACK)
                        .setContentTextSize(20)
                        .build();
                pvOptions.setPicker(wzListModel.getData());
                pvOptions.show();
            } else {
                ToastUtils.showShort("物资列表为空");
            }
        }
    }

    private void send(List<String> images, List<WzReturnsModel> wzslModels) {
        Map<String, Object> map = new HashMap<>();
        map.put("vcTitle", mViewDataBind.etTitle.getText().toString().trim());
        map.put("textMark", mViewDataBind.etSqyy.getText().toString().trim());
        map.put("details", wzslModels);
        StringBuilder imageBuilder = new StringBuilder();
        for (int i = 0; i < images.size(); i++) {
            if (i < images.size() - 1) {
                imageBuilder.append(images.get(i)).append(",");
            } else {
                imageBuilder.append(images.get(i));
            }
        }
        map.put("vcFiles ", imageBuilder.toString());
        OkGoUtils.post(ApiUrl.WZKCTH, map, new BaseCallback() {
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
}