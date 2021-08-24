package com.yhzc.schooldormitorymobile.ui.checkhealth.details;

import android.Manifest;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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
import com.yhzc.schooldormitorymobile.databinding.ActivityCheckHealthDetailsBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.checkhealth.CheckHealthViewModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.ImageAdapter;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UpLoadBean;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;
import com.luck.basemodule.utils.TestImageLoader;
import com.luck.basemodule.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/28 11:58
 * @描述: 绩效打分详情
 */
public class CheckHealthDetailsActivity extends BaseActivity<CheckHealthViewModel, ActivityCheckHealthDetailsBinding> {
    private FirstAdapter mFirstAdapter;
    private ThirdAdapter mThirdAdapter;
    private String plan;
    private String title;
    private int firstPosition = 0;
    private boolean showImg = false;
    private ImageAdapter mImageAdapter;
    private RxPermissions mRxPermissions;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_health_details;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        ZoomMediaLoader.getInstance().init(new TestImageLoader());
        mRxPermissions = new RxPermissions(this);
        String status = getIntent().getStringExtra("status");
        if (StringUtils.equals("2", status)) {
            mViewDataBind.tvSend.setVisibility(View.VISIBLE);
        } else {
            mViewDataBind.tvSend.setVisibility(View.INVISIBLE);
        }
        plan = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        mViewDataBind.tvTitle.setText(title);
        mViewModel.initHealthDetails(plan);
        initRv();
        initImgRv();
        initShowImg();
        mViewDataBind.tvSend.setOnClickListener(v -> updateImg());
    }

    private void initShowImg() {
        mViewDataBind.ivSw.setOnClickListener(v -> {
            showImg = !showImg;
            showImg();
        });
    }

    private void showImg() {
        if (showImg) {
            mViewDataBind.ivSw.setImageResource(R.drawable.ic_up);
            mViewDataBind.clShowImg.setVisibility(View.VISIBLE);
        } else {
            mViewDataBind.clShowImg.setVisibility(View.GONE);
            mViewDataBind.ivSw.setImageResource(R.drawable.ic_down);
        }
    }

    private void initRv() {
        mFirstAdapter = new FirstAdapter(R.layout.item_khxq_top);
        mThirdAdapter = new ThirdAdapter(R.layout.item_health_details);
        mViewDataBind.rvTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mViewDataBind.rvContent.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvTop.setAdapter(mFirstAdapter);
        mViewDataBind.rvContent.setAdapter(mThirdAdapter);
        mViewModel.getHealthDetailsModel().observe(this, healthDetailsModel -> {
            dismissLoading();
            if (healthDetailsModel != null) {
                mFirstAdapter.setList(healthDetailsModel.getData().getList());
                if (healthDetailsModel.getData().getList() != null) {
                    for (HealthDetailsModel.DataBean.ListBean listBean : healthDetailsModel.getData().getList()) {
                        if (listBean.isSelect()) {
                            mThirdAdapter.setList(listBean.getChild());
                        }
                    }
                }
                List<SelectImageModel> selectImageModels = mViewModel.getSelectImageModel().getValue();
                if (selectImageModels == null) {
                    selectImageModels = new ArrayList<>();
                    for (String url : healthDetailsModel.getData().getJsonPic()) {
                        SelectImageModel selectImageModel = new SelectImageModel(url, "1");
                        selectImageModels.add(selectImageModel);
                    }
                    mViewModel.setSelectImageModel(selectImageModels);
                }
            }
        });
        mFirstAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<HealthDetailsModel.DataBean.ListBean> data = (List<HealthDetailsModel.DataBean.ListBean>) adapter.getData();
            if (!data.get(position).isSelect()) {
                HealthDetailsModel healthDetailsModel = mViewModel.getHealthDetailsModel().getValue();
                if (healthDetailsModel != null) {
                    for (int i = 0; i < healthDetailsModel.getData().getList().size(); i++) {
                        healthDetailsModel.getData().getList().get(i).setSelect(false);
                    }
                    healthDetailsModel.getData().getList().get(position).setSelect(true);
                    mViewModel.setHealthDetailsModel(healthDetailsModel);
                    firstPosition = position;
                }
            }
        });
        mThirdAdapter.setMyWatcher((num, position, id) -> {
            HealthDetailsModel healthDetailsModel = mViewModel.getHealthDetailsModel().getValue();
            if (healthDetailsModel != null) {
                healthDetailsModel.getData().getList().get(firstPosition).getChild().get(position).setIntScoreDef(Integer.parseInt(num.toString()));
            }
        });
        mThirdAdapter.addChildClickViewIds(R.id.iv_add, R.id.iv_minus, R.id.et_num);
        mThirdAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            HealthDetailsModel healthDetailsModel = mViewModel.getHealthDetailsModel().getValue();
            if (healthDetailsModel != null) {
                if (view.getId() == R.id.iv_add) {
                    int score = healthDetailsModel.getData().getList().get(firstPosition).getChild().get(position).getIntScoreDef();
                    int scoreMax = healthDetailsModel.getData().getList().get(firstPosition).getChild().get(position).getIntScoreMax();
                    if (score < scoreMax) {
                        score++;
                        healthDetailsModel.getData().getList().get(firstPosition).getChild().get(position).setIntScoreDef(score);
                        mViewModel.setHealthDetailsModel(healthDetailsModel);
                    } else {
                        ToastUtils.showShort("最大值为：" + scoreMax);
                    }
                } else if (view.getId() == R.id.iv_minus) {
                    int score = healthDetailsModel.getData().getList().get(firstPosition).getChild().get(position).getIntScoreDef();
                    int scoreMin = healthDetailsModel.getData().getList().get(firstPosition).getChild().get(position).getIntScoreMin();
                    if (score > scoreMin) {
                        score--;
                        healthDetailsModel.getData().getList().get(firstPosition).getChild().get(position).setIntScoreDef(score);
                        mViewModel.setHealthDetailsModel(healthDetailsModel);
                    } else {
                        ToastUtils.showShort("最小值为：" + scoreMin);
                    }
                } else if (view.getId() == R.id.et_num) {
                    int scoreMax = healthDetailsModel.getData().getList().get(firstPosition).getChild().get(position).getIntScoreMax();
                    int scoreMin = healthDetailsModel.getData().getList().get(firstPosition).getChild().get(position).getIntScoreMin();
                    List<Integer> data = new ArrayList<>();
                    for (int i = scoreMin; i <= scoreMax; i++) {
                        data.add(i);
                    }
                    showScoreList(data, position, healthDetailsModel);
                }
            }
        });
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

    private void updateImg() {
        List<SelectImageModel> selectImageModels = mViewModel.getSelectImageModel().getValue();
        if (selectImageModels == null || selectImageModels.size() == 0) {
            sendDetails(null);
        } else {
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
                sendDetails(upImages);
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
                            sendDetails(images);
                        } else {
                            sendDetails(upImages);
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
    }

    private void sendDetails(List<String> images) {
        HealthDetailsModel healthDetailsModel = mViewModel.getHealthDetailsModel().getValue();
        if (healthDetailsModel != null) {
            UpdateBean updateBean = new UpdateBean();
            updateBean.setId(plan);
            updateBean.setJsonPic(images);
            List<UpdateBean.ListBean> listBeans = new ArrayList<>();
            for (HealthDetailsModel.DataBean.ListBean listBean : healthDetailsModel.getData().getList()) {
                for (HealthDetailsModel.DataBean.ListBean.ChildBean childBean : listBean.getChild()) {
                    UpdateBean.ListBean upList = new UpdateBean.ListBean();
                    upList.setId(childBean.getVcId());
                    upList.setValue(String.valueOf(childBean.getIntScoreDef()));
                    listBeans.add(upList);
                }
            }
            updateBean.setList(listBeans);
            OkGoUtils.postJson(ApiUrl.CHECKHEALTHSUBMIT, GsonUtils.toJson(updateBean), new BaseCallback() {
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

    private void showScoreList(List<Integer> data, int position, HealthDetailsModel healthDetailsModel) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            int score = data.get(options1);
            healthDetailsModel.getData().getList().get(firstPosition).getChild().get(position).setIntScoreDef(score);
            mViewModel.setHealthDetailsModel(healthDetailsModel);
        })
                .setTitleText("选择分值")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(data);//三级选择器
        pvOptions.show();
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(StringUtils.getString(R.string.tjhsjwfxg));
        builder.setTitle("提示");
        builder.setPositiveButton("确认", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }


    @Override
    protected int initTitle() {
        return R.string.wsjc;
    }
}