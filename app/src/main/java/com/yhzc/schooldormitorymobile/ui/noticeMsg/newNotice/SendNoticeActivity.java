package com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice;

import android.Manifest;
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
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.widemouth.library.toolitem.WMToolAlignment;
import com.widemouth.library.toolitem.WMToolBackgroundColor;
import com.widemouth.library.toolitem.WMToolBold;
import com.widemouth.library.toolitem.WMToolItalic;
import com.widemouth.library.toolitem.WMToolListBullet;
import com.widemouth.library.toolitem.WMToolListClickToSwitch;
import com.widemouth.library.toolitem.WMToolListNumber;
import com.widemouth.library.toolitem.WMToolQuote;
import com.widemouth.library.toolitem.WMToolSplitLine;
import com.widemouth.library.toolitem.WMToolStrikethrough;
import com.widemouth.library.toolitem.WMToolTextColor;
import com.widemouth.library.toolitem.WMToolTextSize;
import com.widemouth.library.toolitem.WMToolUnderline;
import com.luck.basemodule.base.BaseActivity;
import com.luck.basemodule.utils.PictureUtils;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivitySendNoticeBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.NoticeViewModel;
import com.luck.basemodule.utils.OkGoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/25 16:22
 * @描述:
 */
public class SendNoticeActivity extends BaseActivity<NoticeViewModel, ActivitySendNoticeBinding> {

    private final List<SelectUserModel.DataBean.ListBean> options1Items = new ArrayList<>();
    private final ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private final ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private final List<String> selectUserId = new ArrayList<>();
    private final List<String> images = new ArrayList<>();
    private final List<String> videos = new ArrayList<>();
    private String userId;
    private SelectUserAdapter mSelectUserAdapter;
    private SelectImgAdapter mSelectImgAdapter;
    private RxPermissions mRxPermissions;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_notice;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewModel.initSelectUser(this);
        mRxPermissions = new RxPermissions(this);
        initWMTC();
        initUserRv();
        initImgRv();
        showRightTextAndOnClickListener("确定", v -> sendNotice());
        mViewDataBind.tvAddUser.setOnClickListener(v -> initUser());
        mViewDataBind.tvAddImg.setOnClickListener(v -> mRxPermissions.request(NEEDED_PERMISSIONS)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        List<File> files = mViewModel.getImages().getValue();
                        if (files == null) {
                            seletedImage();
                        } else {
                            if (files.size() < 9) {
                                seletedImage();
                            } else {
                                ToastUtils.showShort("最多选择9张");
                            }
                        }
                    } else {
                        ToastUtils.showShort("无法获取权限");
                    }
                }));

        mViewDataBind.tvAddVideo.setOnClickListener(v -> mRxPermissions.request(NEEDED_PERMISSIONS)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        seletedVideo();
                    } else {
                        ToastUtils.showShort("无法获取权限");
                    }
                }));

    }


    private void initImgRv() {
        mSelectImgAdapter = new SelectImgAdapter(R.layout.item_notice_select_img, null);
        mViewDataBind.rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        mViewDataBind.rvImage.setAdapter(mSelectImgAdapter);
        mViewModel.getImages().observe(this, files -> {
            if (files != null) {
                mSelectImgAdapter.setList(files);
            }
        });
        mSelectImgAdapter.addChildClickViewIds(R.id.iv_delete);
        mSelectImgAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<File> data = adapter.getData();
            if (view.getId() == R.id.iv_delete) {
                data.remove(position);
                mViewModel.setImages(data);
            }
        });
    }

    private void seletedImage() {
        PictureUtils.createImage(this, 9, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                List<File> imageFileList = PictureUtils.getImageFileList(result);
                List<File> files = mViewModel.getImages().getValue();
                if (files == null) {
                    files = new ArrayList<>(imageFileList);
                    mViewModel.setImages(files);
                } else {
                    if (files.size() + imageFileList.size() > 9) {
                        ToastUtils.showShort("最多9张");
                    } else {
                        files.addAll(imageFileList);
                        mViewModel.setImages(files);
                    }
                }
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort("取消选择");
            }
        });
    }

    private void seletedVideo() {
        PictureUtils.createVideo(this, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                if (result != null && result.size() != 0) {
                    mViewModel.setVideoPath(result.get(0).getPath());
                    mViewDataBind.jsVideo.setVisibility(View.VISIBLE);
                    mViewDataBind.jsVideo.setUp(result.get(0).getPath(), "");
                }
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort("取消选择");
            }
        });
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
                        city_AreaList.add("全部");
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
            String opt1tx = options1Items.size() > 0 ?
                    options1Items.get(options1).getPickerViewText() : "";
            String opt2tx = options2Items.size() > 0
                    && options2Items.get(options1).size() > 0 ?
                    options2Items.get(options1).get(options2) : "";
            String opt3tx = options2Items.size() > 0
                    && options3Items.get(options1).size() > 0
                    && options3Items.get(options1).get(options2).size() > 0 ?
                    options3Items.get(options1).get(options2).get(options3) : "";
            if (StringUtils.equals("全部", opt3tx)) {
                List<UserModel> userModels = mViewModel.getUserModels().getValue();
                if (userModels == null) {
                    userModels = new ArrayList<>();
                }
                if (selectUserModel.getData().getList().get(options1).getChildren() != null && selectUserModel.getData().getList().get(options1).getChildren().size() != 0) {
                    if (selectUserModel.getData().getList().get(options1).getChildren().get(options2).getChildren() != null && selectUserModel.getData().getList().get(options1).getChildren().get(options2).getChildren().size() != 0) {
                        for (SelectUserModel.DataBean.ListBean.ChildrenBean.ChildrensBean childrensBean : selectUserModel.getData().getList().get(options1).getChildren().get(options2).getChildren()) {
                            UserModel userModel = new UserModel(childrensBean.getFullName(), childrensBean.getId());
                            userModels.add(userModel);
                        }
                    } else {
                        UserModel userModel = new UserModel(opt3tx, selectUserModel.getData().getList().get(options1).getChildren().get(options2).getId());
                        userModels.add(userModel);
                    }
                } else {
                    UserModel userModel = new UserModel(opt3tx, selectUserModel.getData().getList().get(options1).getChildren().get(options2).getId());
                    userModels.add(userModel);
                }
                mViewModel.setUserModels(userModels);
            } else {
                String id;
                if (selectUserModel.getData().getList().get(options1).getChildren() != null && selectUserModel.getData().getList().get(options1).getChildren().size() != 0) {
                    if (selectUserModel.getData().getList().get(options1).getChildren().get(options2).getChildren() != null && selectUserModel.getData().getList().get(options1).getChildren().get(options2).getChildren().size() != 0) {
                        id = selectUserModel.getData().getList().get(options1).getChildren().get(options2).getChildren().get(options3 - 1).getId();
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
            }
        })
                .setTitleText("选择用户")
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(16)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }

    private void initWMTC() {
        mViewDataBind.wmtc.addToolItem(new WMToolTextColor());
        mViewDataBind.wmtc.addToolItem(new WMToolBackgroundColor());
        mViewDataBind.wmtc.addToolItem(new WMToolTextSize());
        mViewDataBind.wmtc.addToolItem(new WMToolBold());
        mViewDataBind.wmtc.addToolItem(new WMToolItalic());
        mViewDataBind.wmtc.addToolItem(new WMToolUnderline());
        mViewDataBind.wmtc.addToolItem(new WMToolStrikethrough());
        mViewDataBind.wmtc.addToolItem(new WMToolListNumber());
        mViewDataBind.wmtc.addToolItem(new WMToolListBullet());
        mViewDataBind.wmtc.addToolItem(new WMToolAlignment());
        mViewDataBind.wmtc.addToolItem(new WMToolQuote());
        mViewDataBind.wmtc.addToolItem(new WMToolListClickToSwitch());
        mViewDataBind.wmtc.addToolItem(new WMToolSplitLine());
        mViewDataBind.wmet.setupWithToolContainer(mViewDataBind.wmtc);
    }


    private void sendNotice() {
        if (StringUtils.isEmpty(mViewDataBind.etBt.getText().toString().trim())) {
            ToastUtils.showShort("标题不能为空");
            return;
        }

        List<UserModel> userModels = mViewModel.getUserModels().getValue();
        if (userModels == null || userModels.size() == 0) {
            ToastUtils.showShort("请选择推送用户");
            return;
        }

        if (StringUtils.isEmpty(mViewDataBind.wmet.getHtml())) {
            ToastUtils.showShort("推送内容不能为空");
            return;
        }
        userId = list2String(userModels);
        upLoadImage();
    }

    public static String list2String(List<UserModel> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i).getUserId()).append(",");
                } else {
                    sb.append(list.get(i).getUserId());
                }
            }
        }
        return sb.toString();
    }

    private void upLoadImage() {
        showLoading("发布通知");
        images.clear();
        List<File> files = mViewModel.getImages().getValue();
        if (files != null && files.size() != 0) {
            updateLoadingMsg("正在上传图片");

            OkGoUtils.upFiles(ApiUrl.UPLOADIMG, files, new BaseCallback() {
                @Override
                protected void onSuccess(String callback) {
                    UpLoadBean upLoadBean = GsonUtils.fromJson(callback, UpLoadBean.class);
                    if (upLoadBean.getCode() == ApiUrl.SUCCESS) {
                        if (upLoadBean.getData() != null && upLoadBean.getData().size() != 0) {
                            for (UpLoadBean.DataBean dataBean : upLoadBean.getData()) {
                                images.add(dataBean.getUrl());
                            }
                            upLoadVideo();
                        } else {
                            dismissLoading();
                            ToastUtils.showShort("图片地址不正确");
                        }
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
        } else {
            upLoadVideo();
        }
    }

    private void upLoadVideo() {
        videos.clear();
        String value = mViewModel.getVideoPath().getValue();
        if (value != null) {
            updateLoadingMsg("正在上传视频");
            File videoFile = PictureUtils.url2VideoFile(value);
            List<File> files = new ArrayList<>();
            files.add(videoFile);
            OkGoUtils.upFiles(ApiUrl.UPLOADIMG, files, new BaseCallback() {
                @Override
                protected void onSuccess(String callback) {
                    UpLoadBean upLoadBean = GsonUtils.fromJson(callback, UpLoadBean.class);
                    if (upLoadBean.getCode() == ApiUrl.SUCCESS) {
                        if (upLoadBean.getData() != null && upLoadBean.getData().size() != 0) {
                            videos.add(upLoadBean.getData().get(0).getUrl());
                            upNotice();
                        } else {
                            dismissLoading();
                            ToastUtils.showShort("视频地址不正确");
                        }
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
                    updateLoadingMsg("正在上传视频：" + p + "%");
                }
            });
        } else {
            upNotice();
        }
    }

    private void upNotice() {
        updateLoadingMsg("正在发布");
        UpNoticeModel upNoticeModel = new UpNoticeModel();
        upNoticeModel.setVcTitle(mViewDataBind.etBt.getText().toString().trim());
        upNoticeModel.setVcSendUser(userId);
        upNoticeModel.setIntType(0);
        upNoticeModel.setIntStatus(mViewDataBind.rbFb.isChecked() ? 1 : 0);
        upNoticeModel.setIsReply(mViewDataBind.rbYes.isChecked() ? 1 : 0);
        upNoticeModel.setVcImg(images);
        upNoticeModel.setVcVideo(videos);
        upNoticeModel.setVcContent(mViewDataBind.wmet.getHtml());
        upNoticeModel.setTextMark("");

        OkGoUtils.postJson(ApiUrl.UPDATENOTICE, GsonUtils.toJson(upNoticeModel), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                if (baseBean.getCode() == ApiUrl.SUCCESS) {
                    dismissLoading();
                    ToastUtils.showShort(baseBean.getMsg());
                    finish();
                }
            }

            @Override
            protected void onError(String error) {
                dismissLoading();
                ToastUtils.showShort(error);
            }
        });
    }


    @Override
    protected int initTitle() {
        return R.string.fbtz;
    }
}