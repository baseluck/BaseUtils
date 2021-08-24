package com.yhzc.schooldormitorymobile.ui.suppliesapply.confirm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.yhzc.schooldormitorymobile.databinding.ActivityWzApplyConfirmBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.askLeaveDetails.ApproveDetailsAdapter;
import com.yhzc.schooldormitorymobile.ui.askLeaveDetails.ApproveWzAdapter;
import com.yhzc.schooldormitorymobile.ui.askLeaveDetails.ImgShowAdapter;
import com.yhzc.schooldormitorymobile.ui.askLeaveDetails.LeaveDetailsItemAdapter;
import com.yhzc.schooldormitorymobile.ui.askLeaveDetails.LeaveDetailsViewModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.ImageAdapter;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.ui.face.FaceApproveActivity;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.SelectUserModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UpLoadBean;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;
import com.luck.basemodule.utils.TestImageLoader;
import com.luck.basemodule.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/14 9:56
 * @描述: 物资出库确认
 */
public class WzApplyConfirmActivity extends BaseActivity<LeaveDetailsViewModel, ActivityWzApplyConfirmBinding> {

    private String vcTaskId;
    private String mainId;
    private String workType;
    private String blQr;
    private final List<SelectUserModel.DataBean.ListBean> options1Items = new ArrayList<>();
    private final ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private final ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ImgShowAdapter mImgShowAdapter;
    private ActivityResultLauncher<Intent> mActivityResultLauncher;
    private String splx, sply;
    private Dialog mDialog;
    private ApproveWzAdapter mApproveWzAdapter;

    private ImageAdapter mImageAdapter;
    private RxPermissions mRxPermissions;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wz_apply_confirm;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        ZoomMediaLoader.getInstance().init(new TestImageLoader());
        mRxPermissions = new RxPermissions(this);
        vcTaskId = getIntent().getStringExtra("vcTaskId");
        mainId = getIntent().getStringExtra("mainId");
        blQr = getIntent().getStringExtra("blQr");
        workType = getIntent().getStringExtra("workType");
        if (StringUtils.equals("1", blQr)) {
            mViewDataBind.tvBl.setVisibility(View.GONE);
        } else {
            mViewDataBind.tvBl.setVisibility(View.VISIBLE);
        }

        if (StringUtils.equals("receiveApplyLeaveProcess", workType)) {
            mViewDataBind.clWz.setVisibility(View.VISIBLE);
            mViewDataBind.clFj.setVisibility(View.GONE);
        } else {
            mViewDataBind.clWz.setVisibility(View.GONE);
            mViewDataBind.clFj.setVisibility(View.VISIBLE);

        }

        mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            int resultCode = result.getResultCode();
            if (resultCode == RESULT_OK) {
                if (result.getData() != null) {
                    String type = result.getData().getStringExtra("type");
                    approve(type, sply, mDialog);

                }
            }
        });
        initWzRv();
        mViewModel.initSelectUser(this);
        mViewModel.initLeaveDetails(mainId, workType);
        mViewDataBind.tvBl.setVisibility(View.GONE);
        mViewDataBind.tvWp.setVisibility(View.GONE);
        mViewDataBind.vZw.setVisibility(View.GONE);
        initImgRv();
        initRv();

    }

    private void initWzRv() {
        mApproveWzAdapter = new ApproveWzAdapter(R.layout.item_approve_wz);
        mViewDataBind.rvWz.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvWz.setAdapter(mApproveWzAdapter);
    }

    private void initFjImgRv() {
        mImgShowAdapter = new ImgShowAdapter(R.layout.item_notice_select_img, null);
        mViewDataBind.rvFj.setLayoutManager(new GridLayoutManager(this, 4));
        mViewDataBind.rvFj.setAdapter(mImgShowAdapter);
        mImgShowAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<String> data = (List<String>) adapter.getData();
            Utils.showBigImg(data, position);
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
        map.put("vcId", vcTaskId);
        StringBuilder imageBuilder = new StringBuilder();
        for (int i = 0; i < images.size(); i++) {
            if (i < images.size() - 1) {
                imageBuilder.append(images.get(i)).append(",");
            } else {
                imageBuilder.append(images.get(i));

            }
        }
        map.put("vcFiles ", imageBuilder.toString());

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


    private void initRv() {
        mViewDataBind.rvAskLeave.setLayoutManager(new LinearLayoutManager(this));
        LeaveDetailsItemAdapter mLeaveItemAdapter = new LeaveDetailsItemAdapter(R.layout.item_leave_item, null);
        mViewDataBind.rvAskLeave.setAdapter(mLeaveItemAdapter);

        ApproveDetailsAdapter mApproveAdapter = new ApproveDetailsAdapter(R.layout.item_leave_approve, null);
        mViewDataBind.rvShlc.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvShlc.setAdapter(mApproveAdapter);

        mViewModel.getLeaveDetailsModel().observe(this, leaveDetailsModel -> {
            dismissLoading();
            if (leaveDetailsModel != null) {
                mLeaveItemAdapter.setList(leaveDetailsModel.getData().getContentList());
                mApproveAdapter.setList(leaveDetailsModel.getData().getApproveList());
                mApproveWzAdapter.setList(leaveDetailsModel.getData().getDetailList());
                mViewDataBind.tvLy.setText(leaveDetailsModel.getData().getTextContent());
                if (!StringUtils.isEmpty(leaveDetailsModel.getData().getVcAttachmentPath())) {
                    List<String> list = Arrays.asList(leaveDetailsModel.getData().getVcAttachmentPath().split(","));
                    mImgShowAdapter.setList(list);
                }
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.xq;
    }

    private void showBl() {
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_leave_bl, null);
        ImageView ivClose = dialogView.findViewById(R.id.iv_close);
        EditText etLy = dialogView.findViewById(R.id.et_ly);
        TextView tvYes = dialogView.findViewById(R.id.tv_yes);
        TextView tvNo = dialogView.findViewById(R.id.tv_no);
        final Dialog dialog = new Dialog(this, R.style.ShareDialog);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        ivClose.setOnClickListener(v -> dialog.dismiss());
        tvYes.setOnClickListener(v -> {
            if (StringUtils.isEmpty(etLy.getText().toString().trim())) {
                ToastUtils.showShort("理由不能为空");
                return;
            }
            sply = etLy.getText().toString().trim();
//            splx = "1";
            mDialog = dialog;
//            startFace("1");

            approve("1", etLy.getText().toString().trim(), dialog);
        });
        tvNo.setOnClickListener(v -> {
            if (StringUtils.isEmpty(etLy.getText().toString().trim())) {
                ToastUtils.showShort("理由不能为空");
                return;
            }
            sply = etLy.getText().toString().trim();
            mDialog = dialog;
            startFace("2");
        });
    }

    private void approve(String status, String ly, Dialog dialog) {
        Map<String, String> map = new HashMap<>();
        map.put("vcTaskId", vcTaskId);
        map.put("intStatus", status);
        map.put("vcRejetReason", ly);
        OkGoUtils.post(ApiUrl.LEAVEAPPROVE, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dialog.dismiss();
                ToastUtils.showShort("审批成功");
                finish();
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }


    private void startFace(String type) {
        Intent intent = new Intent(this, FaceApproveActivity.class);
        intent.putExtra("type", type);
        mActivityResultLauncher.launch(intent);
    }

    private void initUser() {
        options1Items.clear();
        options2Items.clear();
        options3Items.clear();

        SelectUserModel selectUserModel = mViewModel.getSelectUserModel().getValue();
        if (selectUserModel == null || selectUserModel.getCode() != ApiUrl.SUCCESS) {
            ToastUtils.showShort("无法获取用户信息");
            return;
        }

        options1Items.addAll(selectUserModel.getData().getList());

        if (selectUserModel.getData().getList() != null && selectUserModel.getData().getList().size() != 0) {
            for (int i = 0; i < selectUserModel.getData().getList().size(); i++) {
                ArrayList<String> cityList = new ArrayList<>();
                ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
                if (selectUserModel.getData().getList().get(i).getChildren() != null && selectUserModel.getData().getList().get(i).getChildren().size() != 0) {
                    for (int j = 0; j < selectUserModel.getData().getList().get(i).getChildren().size(); j++) {
                        String cityName = selectUserModel.getData().getList().get(i).getChildren().get(j).getFullName();
                        cityList.add(cityName);
                        ArrayList<String> city_AreaList = new ArrayList<>();
                        if (selectUserModel.getData().getList().get(i).getChildren().get(j).getChildren() != null && selectUserModel.getData().getList().get(i).getChildren().get(j).getChildren().size() != 0) {
                            for (int k = 0; k < selectUserModel.getData().getList().get(i).getChildren().get(j).getChildren().size(); k++) {
                                city_AreaList.add(selectUserModel.getData().getList().get(i).getChildren().get(j).getChildren().get(k).getFullName());
                            }
                        } else {
                            city_AreaList.add(cityName);
                        }
                        province_AreaList.add(city_AreaList);
                    }
                } else {
                    cityList.add(selectUserModel.getData().getList().get(i).getFullName());
                    ArrayList<String> city_AreaList = new ArrayList<>();
                    city_AreaList.add(selectUserModel.getData().getList().get(i).getFullName());
                    province_AreaList.add(city_AreaList);
                }
                options2Items.add(cityList);
                options3Items.add(province_AreaList);
            }


            showPickerView(selectUserModel);
        }


    }


    private void showPickerView(SelectUserModel selectUserModel) {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String opt1tx = options1Items.size() > 0 ?
                    options1Items.get(options1).getPickerViewText() : "";

            String opt2tx = options2Items.size() > 0
                    && options2Items.get(options1).size() > 0 ?
                    options2Items.get(options1).get(options2) : "";

            String opt3tx = options2Items.size() > 0
                    && options3Items.get(options1).size() > 0
                    && options3Items.get(options1).get(options2).size() > 0 ?
                    options3Items.get(options1).get(options2).get(options3) : "";
            String id;

            if (selectUserModel.getData().getList().get(options1).getChildren() != null && selectUserModel.getData().getList().get(options1).getChildren().size() != 0) {
                if (selectUserModel.getData().getList().get(options1).getChildren().get(options2).getChildren() != null && selectUserModel.getData().getList().get(options1).getChildren().get(options2).getChildren().size() != 0) {
                    id = selectUserModel.getData().getList().get(options1).getChildren().get(options2).getChildren().get(options3).getId();
                } else {
                    id = selectUserModel.getData().getList().get(options1).getChildren().get(options2).getId();
                }
            } else {
                id = selectUserModel.getData().getList().get(options1).getId();
            }

            assigned(id);


        })
                .setTitleText("选择委派用户")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void assigned(String vcAssignUserId) {
        Map<String, String> map = new HashMap<>();
        map.put("vcTaskId", vcTaskId);
        map.put("vcAssignUserId", vcAssignUserId);
        OkGoUtils.post(ApiUrl.LEAVEASSIGNED, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                ToastUtils.showShort("委派成功");
                finish();
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

}