package com.yhzc.schooldormitorymobile.ui.outsider;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.lzy.okgo.model.Progress;
import com.readTwoGeneralCard.ActiveCallBack;
import com.readTwoGeneralCard.OTGReadCardAPI;
import com.readTwoGeneralCard.Serverinfo;
import com.readTwoGeneralCard.TwoCardInfo;
import com.readTwoGeneralCard.eCardType;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.luck.basemodule.base.BaseActivity;
import com.luck.basemodule.utils.PictureUtils;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityOutsiderBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.ImageAdapter;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UpLoadBean;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;
import com.luck.basemodule.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/28 10:32
 * @描述: 外来人员登记
 */
public class OutsiderActivity extends BaseActivity<OutsiderViewModel, ActivityOutsiderBinding> implements ActiveCallBack {

    private OTGReadCardAPI ReadCardAPI;
    private final boolean bNFC = false;
    private final boolean bTestServer = false;
    private RxPermissions mRxPermissions;
    private String name, idCardNo;

    private ImageAdapter mImageAdapter;

    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_outsider;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNFC();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mRxPermissions = new RxPermissions(this);
        mViewDataBind.tvName.setText(String.format(StringUtils.getString(R.string.id_name), ""));
        mViewDataBind.tvSex.setText(String.format(StringUtils.getString(R.string.id_sex), ""));
        mViewDataBind.tvBirth.setText(String.format(StringUtils.getString(R.string.id_birth), ""));
        mViewDataBind.tvIdcard.setText(String.format(StringUtils.getString(R.string.id_idcard), ""));
        mViewDataBind.tvAddress.setText(String.format(StringUtils.getString(R.string.id_address), ""));
        initImgRv();

        mViewDataBind.tvSend.setOnClickListener(v -> checkParameters());

        mViewDataBind.etLeaveTime.setOnClickListener(v -> showTimePickerView());

        showRightImgAndOnClickListener(0, v -> ActivityUtils.startActivity(OutsiderListActivity.class));

    }

    private void initImgRv() {
        mImageAdapter = new ImageAdapter(R.layout.item_hf_img);
        mViewDataBind.rvImg.setLayoutManager(new GridLayoutManager(this, 3));
        mViewDataBind.rvImg.setAdapter(mImageAdapter);

        mViewDataBind.tvImgSelect.setOnClickListener(v -> mRxPermissions.request(NEEDED_PERMISSIONS)
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
            sendOutSider(null);
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
                sendOutSider(upImages);
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
                            sendOutSider(images);
                        } else {
                            sendOutSider(upImages);
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


    private void initPic() {

        mViewModel.getVisitorPic().observe(this, file ->
                Glide.with(this)
                        .load(file)
                        .into(mViewDataBind.ivVisitor));

        mViewDataBind.ivVisitor.setOnClickListener(v -> mRxPermissions.request(Manifest.permission.CAMERA,
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

    private void seletedBeforeImg() {
        PictureUtils.createTakePic(this, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                File imageFile = PictureUtils.getImageFile(result);
                mViewModel.setVisitorPic(imageFile);
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort("取消选择");
            }
        });
    }

    private void initNFC() {
        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        if (mAdapter == null) {
            ToastUtils.showShort("未找到NFC设备");
            return;
        }

        ReadCardAPI = new OTGReadCardAPI(getApplicationContext(), this, false);
        ArrayList<Serverinfo> twoCardServerlist = new ArrayList<>();
        twoCardServerlist.add(new Serverinfo("id.yzfuture.cn", 8848));
        ReadCardAPI.setServerInfo(twoCardServerlist, null, false);

        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        String[][] mTechLists = new String[][]{new String[]{NfcB.class.getName()}, new String[]{NfcA.class.getName()}};

        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "NFC尚未开启", Toast.LENGTH_SHORT).show();
        }
        mAdapter.enableForegroundDispatch(this, pi, new IntentFilter[]{tagDetected}, mTechLists);
    }

    @Override
    protected int initTitle() {
        return R.string.outsider;
    }

    @Override
    public void readProgress(int i, String s) {
        mViewDataBind.pbReadIdcard.setProgress(i);
    }

    @Override
    public void setUserInfo(String s) {

    }

    @Override
    public void DeviceOpenFailed(boolean b, boolean b1) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mViewDataBind.pbReadIdcard.setProgress(0);
        startReadIdcard(intent);
    }

    private void startReadIdcard(Intent intent) {
        int tt;
        tt = ReadCardAPI.NfcReadCard(Cache.NFC_KEY, null, intent, eCardType.eTwoGeneralCard, Cache.getLogin().getData().getVcRealName(), false);
        if (tt == 90) {
            setIdcardInfo();
        }
    }

    private void setIdcardInfo() {

        TwoCardInfo twoCardInfo = ReadCardAPI.GetTwoCardInfo();
        name = twoCardInfo.szTwoIdName;
        idCardNo = twoCardInfo.szTwoIdNo;
        mViewDataBind.tvName.setText(String.format(StringUtils.getString(R.string.id_name), twoCardInfo.szTwoIdName));
        mViewDataBind.tvSex.setText(String.format(StringUtils.getString(R.string.id_sex), twoCardInfo.szTwoIdSex));
        mViewDataBind.tvBirth.setText(String.format(StringUtils.getString(R.string.id_birth), twoCardInfo.szTwoIdBirthday));
        String idcard = twoCardInfo.szTwoIdNo.substring(0, 14) + "****";
        mViewDataBind.tvIdcard.setText(String.format(StringUtils.getString(R.string.id_idcard), idcard));
        mViewDataBind.tvAddress.setText(String.format(StringUtils.getString(R.string.id_address), twoCardInfo.szTwoIdAddress));
        Glide.with(this)
                .load(twoCardInfo.arrTwoIdPhoto)
                .into(mViewDataBind.ivHead);
    }

    private void checkParameters() {

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(idCardNo)) {
            ToastUtils.showShort("未获取到外来人员身份信息");
            return;
        }
        updateImg();

    }

    private void sendOutSider(List<String> imgUrl) {
        Map<String, Object> map = new HashMap<>();
        map.put("vcFkUser", name);
        map.put("vcPhone", mViewDataBind.etPhone.getText().toString().trim());
        map.put("vcIdNumber", idCardNo);
        map.put("intFkNum ", mViewDataBind.etCarNo.getText().toString().trim());
        map.put("vcContactUser", mViewDataBind.etBfUser.getText().toString().trim());
        if (imgUrl != null) {
            map.put("vcImg", imgUrl);
        }
        map.put("vcReason", mViewDataBind.etSignLy.getText().toString().trim());
        map.put("textMark", "");

        OkGoUtils.post(ApiUrl.OUTSIDER, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dismissLoading();
                ToastUtils.showShort("提交成功");
                ActivityUtils.startActivity(OutsiderListActivity.class);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }

    private void showTimePickerView() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 1, 1);
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            String time = TimeUtils.date2String(date);
            mViewDataBind.etLeaveTime.setText(time);
        }).setType(new boolean[]{true, true, true, true, true, true})//分别对应年月日时分秒，默认全部显示
                .setTitleText("选择离校时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(ColorUtils.getColor(R.color.color333333))//标题文字颜色
                .setSubmitColor(ColorUtils.getColor(R.color.color549BFF))//确定按钮文字颜色
                .setCancelColor(ColorUtils.getColor(R.color.color333333))//取消按钮文字颜色
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();

        timePickerView.show();
    }
}