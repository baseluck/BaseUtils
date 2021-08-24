package com.yhzc.schooldormitorymobile.ui.backbedroom.checkin;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
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
import com.yhzc.schooldormitorymobile.databinding.ActivityCheckInBedroomBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.backbedroom.BackBedroomViewModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.ImageAdapter;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
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
 * @创建日期: 2021/8/2 9:35
 * @描述: 查询回寝记录
 */
public class CheckInBedroomActivity extends BaseActivity<BackBedroomViewModel, ActivityCheckInBedroomBinding> {

    private int backType = 0;
    private ImageAdapter mImageAdapter;

    private RxPermissions mRxPermissions;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_in_bedroom;
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

        mViewModel.getBackroomDetailsModel().observe(this, backroomDetailsModel -> {
            if (backroomDetailsModel != null) {
                mViewDataBind.tvName.setText(String.format(StringUtils.getString(R.string.backroom_teacher), backroomDetailsModel.getData().getVcTeacherId()));
                mViewDataBind.tvSex.setText(String.format(StringUtils.getString(R.string.backroom_student), backroomDetailsModel.getData().getVcStudentId()));
                mViewDataBind.tvBirth.setText(String.format(StringUtils.getString(R.string.backroom_class_id), backroomDetailsModel.getData().getVcClassId()));
                mViewDataBind.tvIdcard.setText(String.format(StringUtils.getString(R.string.backroom_num), backroomDetailsModel.getData().getVcDromNum()));
                mViewDataBind.tvAddress.setText(String.format(StringUtils.getString(R.string.backroom_bz), backroomDetailsModel.getData().getTextContent()));
            }
        });

        mViewDataBind.tvHqcq.setOnClickListener(v -> {
            List<String> list = new ArrayList<>();
            list.add("学生回寝");
            list.add("学生出寝");
            showPickerView(list);
        });

        mViewDataBind.tvXzsj.setOnClickListener(v -> {
            if (backType == 0) {
                ToastUtils.showShort("请先选择入寝出寝");
            } else {
                showTimePickerView();
            }
        });

        mViewDataBind.tvSend.setOnClickListener(v -> checkData());

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
        BackroomDetailsModel backroomDetailsModel = mViewModel.getBackroomDetailsModel().getValue();
        if (backroomDetailsModel == null) {
            ToastUtils.showShort("回寝信息未找到");
            return;
        }

        updateImg(backroomDetailsModel.getData().getVcId());
    }

    private void updateImg(String id) {
        List<SelectImageModel> selectImageModels = mViewModel.getSelectImageModel().getValue();
        if (selectImageModels == null || selectImageModels.size() == 0) {
            sendDetails(null, id);
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
                sendDetails(upImages, id);
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
                            sendDetails(images, id);
                        } else {
                            sendDetails(upImages, id);
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

    private void sendDetails(List<String> images, String id) {
        Map<String, String> map = new HashMap<>();
        if (backType == 1) {
            map.put("dtBackTime", mViewDataBind.tvXzsj.getText().toString().trim());
        } else if (backType == 2) {
            map.put("dtLeaveTime", mViewDataBind.tvXzsj.getText().toString().trim());
        } else {
            ToastUtils.showShort("请先选择入寝出寝");
        }

        if (images != null && images.size() != 0) {
            StringBuilder imageBuilder = new StringBuilder();
            for (int i = 0; i < images.size(); i++) {
                if (i < images.size() - 1) {
                    imageBuilder.append(images.get(i)).append(",");
                } else {
                    imageBuilder.append(images.get(i));

                }
            }

            map.put("vcAttachmentPath", imageBuilder.toString());
        }

        map.put("textMark", mViewDataBind.etBz.getText().toString());

        OkGoUtils.post(ApiUrl.BACKBEDROOMCHECK + id, map, new BaseCallback() {
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

    private void showTimePickerView() {
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            String time = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm:ss"));
            mViewDataBind.tvXzsj.setText(time);
        }).setType(new boolean[]{true, true, true, true, true, true})
                .setTitleText("选择日期")
                .setOutSideCancelable(true)
                .setTitleColor(ColorUtils.getColor(R.color.color333333))
                .setSubmitColor(ColorUtils.getColor(R.color.color549BFF))
                .setCancelColor(ColorUtils.getColor(R.color.color333333))
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        timePickerView.show();
    }

    private void showPickerView(List<String> list) {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            if (StringUtils.equals("学生回寝", list.get(options1))) {
                backType = 1;
            } else if (StringUtils.equals("学生出寝", list.get(options1))) {
                backType = 2;
            }

            mViewDataBind.tvHqcq.setText(list.get(options1));

        })
                .setTitleText("选择入寝出寝")
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(list);
        pvOptions.show();
    }

    @Override
    protected int initTitle() {
        return R.string.back_bedroom;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String s = processIntent(intent);
        if (!StringUtils.isEmpty(s)) {
            mViewModel.initBackData(s);
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
}