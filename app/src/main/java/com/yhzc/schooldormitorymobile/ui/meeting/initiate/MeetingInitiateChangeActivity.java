package com.yhzc.schooldormitorymobile.ui.meeting.initiate;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.yhzc.schooldormitorymobile.databinding.ActivityInitiateMeetingBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.ImageAdapter;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.ui.meeting.MeetingViewModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.SelectUserAdapter;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.SelectUserModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UpLoadBean;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UserModel;
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
 * @创建日期: 2021/7/13 15:15
 * @描述: 会议发布
 */
public class MeetingInitiateChangeActivity extends BaseActivity<MeetingViewModel, ActivityInitiateMeetingBinding> {

    private final List<SelectUserModel.DataBean.ListBean> options1Items = new ArrayList<>();
    private final ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private final ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private final List<String> selectUserId = new ArrayList<>();
    private SelectUserAdapter mSelectUserAdapter;
    private String mChargeUserId;
    private String meetingId;

    private ImageAdapter mImageAdapter;

    private RxPermissions mRxPermissions;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_initiate_meeting;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        ZoomMediaLoader.getInstance().init(new TestImageLoader());
        mRxPermissions = new RxPermissions(this);

        showLoading("正在加载");
        mViewModel.initSelectUser();
        meetingId = getIntent().getStringExtra("id");
        mViewModel.initMeetingDetails(meetingId);

        mViewModel.getMeetingDetailsModel().observe(this, meetingDetailsModel -> {
            dismissLoading();
            if (meetingDetailsModel != null) {
                mViewDataBind.etTitle.setText(meetingDetailsModel.getData().getVcTitle());
                mViewDataBind.tvCharge.setText(meetingDetailsModel.getData().getVcSignUserName());
                mViewDataBind.tvStartTime.setText(TimeUtils.millis2String(meetingDetailsModel.getData().getDtStartTime()));
                mViewDataBind.tvEndTime.setText(TimeUtils.millis2String(meetingDetailsModel.getData().getDtEndTime()));
                mViewDataBind.tvCheckInStartTime.setText(TimeUtils.millis2String(meetingDetailsModel.getData().getDtSignInStartTime()));
                mViewDataBind.tvCheckInEndTime.setText(TimeUtils.millis2String(meetingDetailsModel.getData().getDtSignInEndTime()));
                mViewDataBind.tvCheckOutStartTime.setText(TimeUtils.millis2String(meetingDetailsModel.getData().getDtSignOutStartTime()));
                mViewDataBind.tvCheckOutEndTime.setText(TimeUtils.millis2String(meetingDetailsModel.getData().getDtSignOutEndTime()));
                mChargeUserId = meetingDetailsModel.getData().getVcSignUser();
                if (meetingDetailsModel.getData().getJoinUserList() != null && meetingDetailsModel.getData().getJoinUserList().size() != 0) {
                    List<UserModel> userModels = mViewModel.getUserModels().getValue();
                    if (userModels == null) {
                        userModels = new ArrayList<>();
                    }

                    for (MeetingDetailsModel.DataBean.JoinUserListBean joinUserListBean : meetingDetailsModel.getData().getJoinUserList()) {
                        UserModel userModel = new UserModel(joinUserListBean.getJoinUserName(), joinUserListBean.getJoinUserId());
                        userModels.add(userModel);
                    }

                    mViewModel.setUserModels(userModels);
                }

                if (!StringUtils.isEmpty(meetingDetailsModel.getData().getVcPath())) {
                    String[] split = meetingDetailsModel.getData().getVcPath().split(",");
                    List<String> images = new ArrayList<>(Arrays.asList(split));
                    List<SelectImageModel> selectImageModels = mViewModel.getSelectImageModel().getValue();
                    if (selectImageModels == null) {
                        selectImageModels = new ArrayList<>();
                    }
                    for (String url : images) {
                        SelectImageModel selectImageModel = new SelectImageModel(url, "1");
                        selectImageModels.add(selectImageModel);
                    }
                    mViewModel.setSelectImageModel(selectImageModels);
                }
            }
        });

        initUserRv();
        mViewDataBind.tvAddUser.setOnClickListener(v -> initUser());
        mViewDataBind.tvCharge.setOnClickListener(v -> initChargeUser());
        mViewDataBind.tvStartTime.setOnClickListener(v -> showTimePickerView(mViewDataBind.tvStartTime));
        mViewDataBind.tvEndTime.setOnClickListener(v -> showTimePickerView(mViewDataBind.tvEndTime));
        mViewDataBind.tvCheckInStartTime.setOnClickListener(v -> showTimePickerView(mViewDataBind.tvCheckInStartTime));
        mViewDataBind.tvCheckInEndTime.setOnClickListener(v -> showTimePickerView(mViewDataBind.tvCheckInEndTime));
        mViewDataBind.tvCheckOutStartTime.setOnClickListener(v -> showTimePickerView(mViewDataBind.tvCheckOutStartTime));
        mViewDataBind.tvCheckOutEndTime.setOnClickListener(v -> showTimePickerView(mViewDataBind.tvCheckOutEndTime));
        mViewDataBind.tvSend.setOnClickListener(v -> checkData());
        initImgRv();
    }

    private void checkData() {
        if (StringUtils.isEmpty(mViewDataBind.etTitle.getText().toString().trim())) {
            ToastUtils.showShort("标题不能为空");
            return;
        }

        List<UserModel> userModels = mViewModel.getUserModels().getValue();
        if (userModels == null || userModels.size() == 0) {
            ToastUtils.showShort("请选择参会人员");
            return;
        }

        if (StringUtils.isEmpty(mChargeUserId)) {
            ToastUtils.showShort("请选择签到负责人");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.tvStartTime.getText().toString().trim())) {
            ToastUtils.showShort("请选择会议开始时间");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.tvEndTime.getText().toString().trim())) {
            ToastUtils.showShort("请选择会议结束时间");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.tvCheckInStartTime.getText().toString().trim())) {
            ToastUtils.showShort("请选择签到开始时间");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.tvCheckInEndTime.getText().toString().trim())) {
            ToastUtils.showShort("请选择签到结束时间");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.tvCheckOutStartTime.getText().toString().trim())) {
            ToastUtils.showShort("请选择签退开始时间");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.tvCheckOutEndTime.getText().toString().trim())) {
            ToastUtils.showShort("请选择签退结束时间");
            return;
        }

      updateImg();

    }

    private void sendMeeting(List<String> images) {
        Map<String, String> map = new HashMap<>();
        List<UserModel> userModels = mViewModel.getUserModels().getValue();
        StringBuilder sb = new StringBuilder();
        if (userModels != null && userModels.size() > 0) {
            for (int i = 0; i < userModels.size(); i++) {
                if (i < userModels.size() - 1) {
                    sb.append(userModels.get(i).getUserId()).append(",");
                } else {
                    sb.append(userModels.get(i).getUserId());
                }
            }
        }

        StringBuilder iamgeUrl = new StringBuilder();
        if (images != null && images.size() > 0) {
            for (int i = 0; i < images.size(); i++) {
                if (i < images.size() - 1) {
                    iamgeUrl.append(images.get(i)).append(",");
                } else {
                    iamgeUrl.append(images.get(i));
                }
            }
        }

        map.put("vcTitle", mViewDataBind.etTitle.getText().toString().trim());
        map.put("vcJoinUser", sb.toString());
        map.put("vcSignUser", mChargeUserId);
        map.put("vcPath", iamgeUrl.toString());
        map.put("dtStartTime", TimeUtils.string2Millis(mViewDataBind.tvStartTime.getText().toString().trim()) + "");
        map.put("dtEndTime", TimeUtils.string2Millis(mViewDataBind.tvEndTime.getText().toString().trim()) + "");
        map.put("dtSignInStartTime", TimeUtils.string2Millis(mViewDataBind.tvCheckInStartTime.getText().toString().trim()) + "");
        map.put("dtSignInEndTime", TimeUtils.string2Millis(mViewDataBind.tvCheckInEndTime.getText().toString().trim()) + "");
        map.put("dtSignOutStartTime", TimeUtils.string2Millis(mViewDataBind.tvCheckOutStartTime.getText().toString().trim()) + "");
        map.put("dtSignOutEndTime", TimeUtils.string2Millis(mViewDataBind.tvCheckOutEndTime.getText().toString().trim()) + "");

        OkGoUtils.put(ApiUrl.MEETINGPUT + meetingId, map, new BaseCallback() {
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
        return R.string.initiate_meeting;
    }

    private void initUserRv() {
        mSelectUserAdapter = new SelectUserAdapter(R.layout.item_notice_user, null);
        mViewDataBind.rvUser.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvUser.setAdapter(mSelectUserAdapter);
        mViewModel.getUserModels().observe(this, userModels -> {
            if (userModels != null) {
                mSelectUserAdapter.setList(userModels);
            }
        });

        mSelectUserAdapter.addChildClickViewIds(R.id.tv_delete);
        mSelectUserAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<UserModel> data = adapter.getData();
            if (view.getId() == R.id.tv_delete) {
                data.remove(position);
                mViewModel.setUserModels(data);
            }
        });
    }


    private void initUser() {
        options1Items.clear();
        options2Items.clear();
        options3Items.clear();

        SelectUserModel selectUserModel = mViewModel.getSelectUserModel().getValue();
        if (selectUserModel == null || selectUserModel.getCode() != ApiUrl.SUCCESS) {
            ToastUtils.showShort("用户信息为空，请稍后再试");
            return;
        }

        options1Items.addAll(selectUserModel.getData().getList());

        if (selectUserModel.getData().getList() != null && selectUserModel.getData().getList().size() != 0) {
            for (int i = 0; i < selectUserModel.getData().getList().size(); i++) {
                ArrayList<String> cityList = new ArrayList<>();
                ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
                if (selectUserModel.getData().getList().get(i).getChildren() != null && selectUserModel.getData().getList().get(i).getChildren().size() != 0) {
                    for (int j = 0; j < selectUserModel.getData().getList().get(i).getChildren().size(); j++) {//遍历该省份的所有城市
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

    private void initChargeUser() {
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
                    for (int j = 0; j < selectUserModel.getData().getList().get(i).getChildren().size(); j++) {//遍历该省份的所有城市
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

            showChargeUser(selectUserModel);
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

            List<UserModel> userModels = mViewModel.getUserModels().getValue();
            if (userModels == null) {
                userModels = new ArrayList<>();
            }
            UserModel userModel = new UserModel(opt3tx, id);
            userModels.add(userModel);

            mViewModel.setUserModels(userModels);

            String tx = opt1tx + "**" + opt2tx + "**" + opt3tx + "**" + id;
        })
                .setTitleText("选择参会人员")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void showChargeUser(SelectUserModel selectUserModel) {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {

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


            mViewDataBind.tvCharge.setText(opt3tx);

            mChargeUserId = id;

        })
                .setTitleText("选择签到负责人")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void showTimePickerView(TextView textView) {
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            String time = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm:ss"));
            textView.setText(time);
        }).setType(new boolean[]{true, true, true, true, true, true})//分别对应年月日时分秒，默认全部显示
                .setTitleText("选择日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(ColorUtils.getColor(R.color.color333333))//标题文字颜色
                .setSubmitColor(ColorUtils.getColor(R.color.color549BFF))//确定按钮文字颜色
                .setCancelColor(ColorUtils.getColor(R.color.color333333))//取消按钮文字颜色
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();

        timePickerView.show();
    }

    private void initImgRv() {
        mImageAdapter = new ImageAdapter(R.layout.item_hf_img);
        mViewDataBind.rvImg.setLayoutManager(new GridLayoutManager(this, 3));
        mViewDataBind.rvImg.setAdapter(mImageAdapter);

        mViewDataBind.tvSelect.setOnClickListener(v -> mRxPermissions.request(NEEDED_PERMISSIONS)
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
            sendMeeting(null);
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
                sendMeeting(upImages);
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
                            sendMeeting(images);
                        } else {
                            sendMeeting(upImages);
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
}