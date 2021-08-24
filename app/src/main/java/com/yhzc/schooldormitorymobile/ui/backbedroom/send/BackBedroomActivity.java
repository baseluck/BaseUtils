package com.yhzc.schooldormitorymobile.ui.backbedroom.send;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
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
import com.yhzc.schooldormitorymobile.databinding.ActivityBackBedroomBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.luck.basemodule.spinner.SpinnerDialog;
import com.yhzc.schooldormitorymobile.ui.askLeave.LeaveStudentModel;
import com.yhzc.schooldormitorymobile.ui.backbedroom.BackBedroomViewModel;
import com.yhzc.schooldormitorymobile.ui.backbedroom.list.BackBedroomListActivity;
import com.yhzc.schooldormitorymobile.ui.discipline.details.ImageModel;
import com.yhzc.schooldormitorymobile.ui.discipline.details.ImageWjAdapter;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UpLoadBean;
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
 * @创建日期: 2021/4/27 15:45
 * @描述: 回寝登记
 */
public class BackBedroomActivity extends BaseActivity<BackBedroomViewModel, ActivityBackBedroomBinding> {

    private ImageWjAdapter mImageAdapter;
    private RxPermissions mRxPermissions;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };

    private final boolean canChange = true;
    private String classId, studentId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_back_bedroom;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNfc();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        ZoomMediaLoader.getInstance().init(new TestImageLoader());
        mRxPermissions = new RxPermissions(this);
        initImgRv();
        showRightImgAndOnClickListener(0, v -> ActivityUtils.startActivity(BackBedroomListActivity.class));

        mViewDataBind.tvClass.setOnClickListener(v -> initClass());
        mViewDataBind.tvXs.setOnClickListener(v -> {
            if (StringUtils.isEmpty(classId)) {
                ToastUtils.showShort("请先选择班级");
            } else {
                initStudent();
            }
        });

        mViewDataBind.tvSend.setOnClickListener(v -> checkData());

        mViewModel.getStudentModel().observe(this, studentModel -> {
            if (studentModel != null) {
                mViewDataBind.tvClass.setText(studentModel.getData().getClassName());
                mViewDataBind.tvXs.setText(studentModel.getData().getVcRealName());
                mViewDataBind.etRoomNum.setText(studentModel.getData().getVcDromNum());
                classId = studentModel.getData().getVcClassId();
                studentId = studentModel.getData().getVcId();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String s = processIntent(intent);
        if (!StringUtils.isEmpty(s)) {
            mViewModel.getStudent(s);
        }
    }

    private String processIntent(Intent intent) {
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        return ByteArrayToHexString(tagFromIntent.getId());
    }

    private String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F"};
        StringBuilder out = new StringBuilder();


        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out.append(hex[i]);
            i = in & 0x0f;
            out.append(hex[i]);
        }
        return out.toString();
    }

    private void initNfc() {
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        if (null == adapter) {
            Toast.makeText(this, "不支持NFC功能", Toast.LENGTH_SHORT).show();
        } else if (!adapter.isEnabled()) {
            Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
            startActivity(intent);
        }

        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        String[][] mTechLists = new String[][]{new String[]{NfcB.class.getName()}, new String[]{NfcA.class.getName()}};

        if (adapter != null && !adapter.isEnabled()) {
            Toast.makeText(this, "NFC尚未开启", Toast.LENGTH_SHORT).show();
        }
        if (adapter != null) {
            adapter.enableForegroundDispatch(this, pi, new IntentFilter[]{tagDetected}, mTechLists);
        }
    }

    private void checkData() {
        if (StringUtils.isEmpty(classId)) {
            ToastUtils.showShort("请选择班级");
            return;
        }

        if (StringUtils.isEmpty(studentId)) {
            ToastUtils.showShort("请选择学生");
            return;
        }

        if (StringUtils.isEmpty(mViewDataBind.etRoomNum.getText().toString().trim())) {
            ToastUtils.showShort("请输入寝室号");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.etSignLy.getText().toString().trim())) {
            ToastUtils.showShort("请输入回寝理由");
            return;
        }

        sendDetails();
    }

    private void initImgRv() {
        mImageAdapter = new ImageWjAdapter(R.layout.item_hf_img);
        mViewDataBind.rvImg.setLayoutManager(new GridLayoutManager(this, 4));
        mViewDataBind.rvImg.setAdapter(mImageAdapter);
        mViewDataBind.tvAddImg.setOnClickListener(v -> {
                    if (canChange) {
                        mRxPermissions.request(NEEDED_PERMISSIONS)
                                .subscribe(aBoolean -> {
                                    if (aBoolean) {
                                        List<ImageModel> ImageModels = mViewModel.getImages().getValue();
                                        if (ImageModels == null) {
                                            seletedImage();
                                        } else {
                                            if (ImageModels.size() < 9) {
                                                seletedImage();
                                            } else {
                                                ToastUtils.showShort("最多选择9张");
                                            }
                                        }
                                    } else {
                                        ToastUtils.showShort("权限获取失败，请检查设置");
                                    }
                                });
                    }
                }
        );

        mViewModel.getImages().observe(this, ImageModels -> mImageAdapter.setList(ImageModels));

        mImageAdapter.addChildClickViewIds(R.id.iv_delete, R.id.iv_img);
        mImageAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<ImageModel> data = adapter.getData();
            if (view.getId() == R.id.iv_delete) {
                if (data.get(position).isCanChange()) {
                    data.remove(position);
                    mViewModel.setImages(data);
                }
            } else if (view.getId() == R.id.iv_img) {
                List<String> images = new ArrayList<>();
                for (ImageModel imageModel : data) {
                    if (imageModel.getType() == 0) {
                        images.add(imageModel.getPath());
                    } else {
                        images.add(Cache.getHttp() + imageModel.getPath());
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
                List<ImageModel> ImageModels = mViewModel.getImages().getValue();
                if (ImageModels == null) {
                    ImageModels = new ArrayList<>();
                }
                if (ImageModels.size() + imageFileList.size() > 9) {
                    ToastUtils.showShort("最多9张");
                    return;
                }
                for (String url : imageFileList) {
                    ImageModel ImageModel = new ImageModel(url, 0, canChange);
                    ImageModels.add(ImageModel);
                }
                mViewModel.setImages(ImageModels);

            }

            @Override
            public void onCancel() {
                ToastUtils.showShort("取消选择");
            }
        });
    }


    private void initClass() {
        showLoading("加载班级列表");

        OkGoUtils.get(ApiUrl.LEAVECLASSLIST, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dismissLoading();
                LeaveStudentModel leaveStudentModel = GsonUtils.fromJson(callback, LeaveStudentModel.class);
                if (leaveStudentModel.getCode() == ApiUrl.SUCCESS) {
                    if (leaveStudentModel.getData() != null && leaveStudentModel.getData().size() != 0) {
                        showLeaveClass2(leaveStudentModel.getData());
                    } else {
                        ToastUtils.showShort("班级列表为空");
                    }
                }
            }

            @Override
            protected void onError(String error) {
                dismissLoading();
                ToastUtils.showShort(error);
            }
        });

    }

    private void showLeaveClass2(List<LeaveStudentModel.DataBean> dataBeans) {
        ArrayList<String> items = new ArrayList<>();
        for (LeaveStudentModel.DataBean dataBean : dataBeans) {
            items.add(dataBean.getVcName());
        }
        SpinnerDialog spinnerDialog = new SpinnerDialog(this, items, "选择班级");

        spinnerDialog.setCancellable(true);
        spinnerDialog.setShowKeyboard(false);

        spinnerDialog.bindOnSpinerListener((item, position1) -> {
            classId = dataBeans.get(position1).getVcId();
            String vcName = dataBeans.get(position1).getVcName();
            mViewDataBind.tvClass.setText(vcName);
        });

        spinnerDialog.showSpinerDialog();
    }


    private void initStudent() {
        showLoading("加载学生");

        Map<String, String> map = new HashMap<>();
        map.put("classId", classId);

        OkGoUtils.get(ApiUrl.LEAVESTUDENTLIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dismissLoading();
                LeaveStudentModel leaveStudentModel = GsonUtils.fromJson(callback, LeaveStudentModel.class);
                if (leaveStudentModel.getCode() == ApiUrl.SUCCESS) {
                    if (leaveStudentModel.getData() != null && leaveStudentModel.getData().size() != 0) {
                        showLeaveStudent2(leaveStudentModel.getData());
                    } else {
                        ToastUtils.showShort("学生列表为空");
                    }
                }
            }

            @Override
            protected void onError(String error) {
                dismissLoading();
                ToastUtils.showShort(error);
            }
        });

    }

    private void getRoomNum(String studentId) {
        OkGoUtils.get(ApiUrl.STUDENTINFO2 + studentId, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                StudentModel studentModel = GsonUtils.fromJson(callback, StudentModel.class);
                mViewDataBind.etRoomNum.setText(studentModel.getData().getVcDromNum());
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    private void showLeaveStudent2(List<LeaveStudentModel.DataBean> dataBeans) {
        ArrayList<String> items = new ArrayList<>();
        for (LeaveStudentModel.DataBean dataBean : dataBeans) {
            items.add(dataBean.getVcName());
        }
        SpinnerDialog spinnerDialog = new SpinnerDialog(this, items, "选择学生");
        spinnerDialog.setCancellable(true);
        spinnerDialog.setShowKeyboard(false);
        spinnerDialog.bindOnSpinerListener((item, position1) -> {

            studentId = dataBeans.get(position1).getVcId();
            String vcName = dataBeans.get(position1).getVcName();
            mViewDataBind.tvXs.setText(vcName);
            getRoomNum(studentId);
        });

        spinnerDialog.showSpinerDialog();
    }


    private void updateImg() {
        List<ImageModel> ImageModels = mViewModel.getImages().getValue();
        if (ImageModels == null || ImageModels.size() == 0) {
            sendDetails(null);
        } else {
            List<String> images = new ArrayList<>();
            List<String> upImages = new ArrayList<>();
            for (ImageModel imageModel : ImageModels) {
                if (imageModel.getType() == 0) {
                    images.add(imageModel.getPath());
                } else {
                    upImages.add(imageModel.getPath());
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

    }

    private void sendDetails() {
        Map<String, String> map = new HashMap<>();
        map.put("vcClassId", classId);
        map.put("vcStudentId", studentId);
        map.put("vcDromNum", mViewDataBind.etRoomNum.getText().toString().trim());
        map.put("textContent", mViewDataBind.etSignLy.getText().toString().trim());

        OkGoUtils.post(ApiUrl.BACKBEDROOMSEND, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                ToastUtils.showShort(baseBean.getMsg());
                ActivityUtils.startActivity(BackBedroomListActivity.class);
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
        return R.string.back_bedroom_dj;
    }
}