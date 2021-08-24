package com.yhzc.schooldormitorymobile.ui.discipline.bzr.details;

import android.Manifest;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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
import com.yhzc.schooldormitorymobile.databinding.ActivityDisciplineDetailsBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.discipline.DisciplineViewModel;
import com.yhzc.schooldormitorymobile.ui.discipline.details.DisciplineDetailsModel;
import com.yhzc.schooldormitorymobile.ui.discipline.details.DisciplineLevelModel;
import com.yhzc.schooldormitorymobile.ui.discipline.details.ImageModel;
import com.yhzc.schooldormitorymobile.ui.discipline.details.ImageWjAdapter;
import com.yhzc.schooldormitorymobile.ui.discipline.details.SelectStudentAdapter;
import com.yhzc.schooldormitorymobile.ui.discipline.details.SelectStudentModel;
import com.yhzc.schooldormitorymobile.ui.discipline.details.StudentListModel;
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
 * @创建日期: 2021/7/30 15:09
 * @描述: 违纪详情-发布-修改
 */
public class CkDisciplineDetailsActivity extends BaseActivity<DisciplineViewModel, ActivityDisciplineDetailsBinding> {

    private final List<StudentListModel.DataBean> options1Items = new ArrayList<>();
    private final ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private final ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    /**
     * 违纪类型：1-详情，2-发布
     */
    private String wjType;
    private String id;
    private String levelId;
    private boolean canChange = false;

    private ImageWjAdapter mImageAdapter;
    private SelectStudentAdapter mSelectUserAdapter;
    private RxPermissions mRxPermissions;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_discipline_details;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        ZoomMediaLoader.getInstance().init(new TestImageLoader());
        mRxPermissions = new RxPermissions(this);
        wjType = getIntent().getStringExtra("wjType");
        mViewModel.initStudent();
        mViewModel.initLevel();
        initImgRv();
        initUserRv();
        if (StringUtils.equals("1", wjType)) {
            showLoading("正在加载");
            setTabTitle("违纪详情");
            id = getIntent().getStringExtra("id");
            mViewModel.initDetails(id);
            mViewDataBind.clBottom.setVisibility(View.GONE);
            canChange = false;
            setData();
        } else {
            setTabTitle("违纪发布");
            mViewDataBind.clBottom.setVisibility(View.GONE);
            canChange = false;
            showRightTextAndOnClickListener("发布", v -> checkData());
        }

        mViewDataBind.tvBl.setOnClickListener(v -> {
            showRightTextAndOnClickListener("保存", v1 -> checkData());
            canChange = false;
            canChange();
        });
        canChange();

        mViewDataBind.tvAddUser.setOnClickListener(v -> {
            if (canChange) {
                initSelectStudent();
            }
        });
        mViewDataBind.tvTime.setOnClickListener(v -> {
            if (canChange) {
                hideInput();
                showTimePickerView();
            }
        });

        mViewDataBind.tvLevel.setOnClickListener(v -> {
            if (canChange) {
                hideInput();
                showLevelPickerView();
            }
        });

        mViewDataBind.tvWp.setOnClickListener(v -> showDeleteDialog());
    }

    private void setData() {
        mViewModel.getDisciplineDetailsModel().observe(this, disciplineDetailsModel -> {
            dismissLoading();
            if (disciplineDetailsModel != null) {
                mViewDataBind.etBt.setText(disciplineDetailsModel.getData().getVcTitle());
                mViewDataBind.etLy.setText(disciplineDetailsModel.getData().getTextContent());
                mViewDataBind.tvTime.setText(disciplineDetailsModel.getData().getDtTime());
                mViewDataBind.tvLevel.setText(disciplineDetailsModel.getData().getVcLevel());
                levelId = String.valueOf(disciplineDetailsModel.getData().getIntLevel());
                if (disciplineDetailsModel.getData().getJsonPic() != null && disciplineDetailsModel.getData().getJsonPic().size() != 0) {
                    List<ImageModel> imageModels = new ArrayList<>();
                    for (String url : disciplineDetailsModel.getData().getJsonPic()) {
                        ImageModel imageModel = new ImageModel(url, 1, canChange);
                        imageModels.add(imageModel);
                    }
                    mViewModel.setImages(imageModels);
                }

                if (disciplineDetailsModel.getData().getJsonStudent() != null && disciplineDetailsModel.getData().getJsonStudent().size() != 0) {
                    List<SelectStudentModel> studentModelList = new ArrayList<>();
                    for (DisciplineDetailsModel.DataBean.JsonStudentBean jsonStudentBean : disciplineDetailsModel.getData().getJsonStudent()) {
                        SelectStudentModel selectStudentModel = new SelectStudentModel(jsonStudentBean.getName(), jsonStudentBean.getId(), canChange);
                        studentModelList.add(selectStudentModel);
                    }
                    mViewModel.setSelectStudents(studentModelList);
                }
            }


        });
    }


    private void canChange() {
        if (canChange) {
            mViewDataBind.tvAddImg.setVisibility(View.VISIBLE);
            mViewDataBind.tvAddUser.setVisibility(View.VISIBLE);
            mViewDataBind.etBt.setFocusable(true);
            mViewDataBind.etBt.setFocusableInTouchMode(true);
            mViewDataBind.etLy.setFocusable(true);
            mViewDataBind.etLy.setFocusableInTouchMode(true);
            List<SelectStudentModel> selectStudentModels = mViewModel.getSelectStudents().getValue();
            if (selectStudentModels != null && selectStudentModels.size() != 0) {
                for (int i = 0; i < selectStudentModels.size(); i++) {
                    selectStudentModels.get(i).setCanChange(true);
                }
                mViewModel.setSelectStudents(selectStudentModels);
            }

            List<ImageModel> imageModels = mViewModel.getImages().getValue();
            if (imageModels != null && imageModels.size() != 0) {
                for (int i = 0; i < imageModels.size(); i++) {
                    imageModels.get(i).setCanChange(true);
                }
                mViewModel.getImages().setValue(imageModels);
            }

        } else {
            mViewDataBind.tvAddImg.setVisibility(View.INVISIBLE);
            mViewDataBind.tvAddUser.setVisibility(View.INVISIBLE);
            mViewDataBind.etBt.setFocusable(false);
            mViewDataBind.etBt.setFocusableInTouchMode(false);
            mViewDataBind.etLy.setFocusable(false);
            mViewDataBind.etLy.setFocusableInTouchMode(false);
        }
    }

    private void initUserRv() {
        mSelectUserAdapter = new SelectStudentAdapter(R.layout.item_notice_user);
        mViewDataBind.rvUser.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvUser.setAdapter(mSelectUserAdapter);
        mViewModel.getSelectStudents().observe(this, userModels -> {
            if (userModels != null) {
                mSelectUserAdapter.setList(userModels);
            }
        });

        mSelectUserAdapter.addChildClickViewIds(R.id.tv_delete);
        mSelectUserAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<SelectStudentModel> data = adapter.getData();
            if (view.getId() == R.id.tv_delete) {
                if (canChange) {
                    data.remove(position);
                    mViewModel.setSelectStudents(data);
                }
            }
        });
    }

    private void initSelectStudent() {
        StudentListModel studentListModel = mViewModel.getStudentListModel().getValue();
        if (studentListModel == null) {
            ToastUtils.showShort("学生信息未获取完成，请稍后再试");
        } else {
            initUser(studentListModel);
        }
    }

    private void initUser(StudentListModel studentListModel) {
        options1Items.clear();
        options2Items.clear();
        options3Items.clear();

        options1Items.addAll(studentListModel.getData());

        if (studentListModel.getData() != null && studentListModel.getData().size() != 0) {
            for (int i = 0; i < studentListModel.getData().size(); i++) {
                ArrayList<String> cityList = new ArrayList<>();
                ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
                if (studentListModel.getData().get(i).getChild() != null && studentListModel.getData().get(i).getChild().size() != 0) {
                    for (int j = 0; j < studentListModel.getData().get(i).getChild().size(); j++) {//遍历该省份的所有城市
                        String cityName = studentListModel.getData().get(i).getChild().get(j).getName();
                        cityList.add(cityName);
                        ArrayList<String> city_AreaList = new ArrayList<>();
                        if (studentListModel.getData().get(i).getChild().get(j).getChild() != null && studentListModel.getData().get(i).getChild().get(j).getChild().size() != 0) {
                            for (int k = 0; k < studentListModel.getData().get(i).getChild().get(j).getChild().size(); k++) {
                                city_AreaList.add(studentListModel.getData().get(i).getChild().get(j).getChild().get(k).getName());
                            }
                        } else {
                            city_AreaList.add(cityName);
                        }
                        province_AreaList.add(city_AreaList);
                    }
                } else {
                    cityList.add(studentListModel.getData().get(i).getName());
                    ArrayList<String> city_AreaList = new ArrayList<>();
                    city_AreaList.add(studentListModel.getData().get(i).getName());
                    province_AreaList.add(city_AreaList);
                }
                options2Items.add(cityList);
                options3Items.add(province_AreaList);
            }


            showPickerView(studentListModel);
        }


    }

    private void showTimePickerView() {
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            String time = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm:ss"));
            mViewDataBind.tvTime.setText(time);
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

    private void showPickerView(StudentListModel studentListModel) {

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

            if (studentListModel.getData().get(options1).getChild() != null && studentListModel.getData().get(options1).getChild().size() != 0) {
                if (studentListModel.getData().get(options1).getChild().get(options2).getChild() != null && studentListModel.getData().get(options1).getChild().get(options2).getChild().size() != 0) {
                    id = studentListModel.getData().get(options1).getChild().get(options2).getChild().get(options3).getId();
                } else {
                    id = studentListModel.getData().get(options1).getChild().get(options2).getId();
                }
            } else {
                id = studentListModel.getData().get(options1).getId();
            }

            List<SelectStudentModel> selectStudentModels = mViewModel.getSelectStudents().getValue();
            if (selectStudentModels == null) {
                selectStudentModels = new ArrayList<>();
            }
            SelectStudentModel selectStudentModel = new SelectStudentModel(opt3tx, id, canChange);
            selectStudentModels.add(selectStudentModel);
            mViewModel.setSelectStudents(selectStudentModels);
        })
                .setTitleText("选择违纪学生")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        List<StudentListModel.DataBean> data = studentListModel.getData();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void showLevelPickerView() {
        DisciplineLevelModel disciplineLevelModel = mViewModel.getDisciplineLevelModel().getValue();
        if (disciplineLevelModel == null || disciplineLevelModel.getData().size() == 0) {
            ToastUtils.showShort("未获取到违纪等级");
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            DisciplineLevelModel.DataBean dataBean = disciplineLevelModel.getData().get(options1);
            mViewDataBind.tvLevel.setText(dataBean.getName());
            levelId = String.valueOf(dataBean.getId());
        })
                .setTitleText("选择违纪等级")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(disciplineLevelModel.getData());//三级选择器
        pvOptions.show();
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

    private void checkData() {
        if (StringUtils.isEmpty(mViewDataBind.etBt.getText().toString().trim())) {
            ToastUtils.showShort("标题不能为空");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.etLy.getText().toString().trim())) {
            ToastUtils.showShort("内容不能为空");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.tvTime.getText().toString().trim())) {
            ToastUtils.showShort("时间不能为空");
            return;
        }
        if (StringUtils.isEmpty(levelId)) {
            ToastUtils.showShort("请选择违纪等级");
            return;
        }

        List<SelectStudentModel> selectStudentModels = mViewModel.getSelectStudents().getValue();
        if (selectStudentModels == null || selectStudentModels.size() == 0) {
            ToastUtils.showShort("请选择违纪学生");
            return;
        }

        List<String> ids = new ArrayList<>();
        for (SelectStudentModel selectStudentModel : selectStudentModels) {
            ids.add(selectStudentModel.getId());
        }

        updateImg(ids);
    }

    private void updateImg(List<String> ids) {
        List<ImageModel> ImageModels = mViewModel.getImages().getValue();
        if (ImageModels == null || ImageModels.size() == 0) {
            sendDetails(null, ids);
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
                sendDetails(upImages, ids);
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
                            sendDetails(images, ids);
                        } else {
                            sendDetails(upImages, ids);
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

    private void sendDetails(List<String> images, List<String> ids) {
        Map<String, Object> map = new HashMap<>();
        map.put("vcTitle", mViewDataBind.etBt.getText().toString());
        map.put("textContent", mViewDataBind.etLy.getText().toString());
        map.put("dtTime", mViewDataBind.tvTime.getText().toString());
        map.put("jsonPic", images);
        map.put("jsonStudentIds", ids);
        map.put("intLevel", levelId);
        if (StringUtils.equals("1", wjType)) {
            put(map);
        } else {
            send(map);
        }

    }

    private void send(Map map) {
        OkGoUtils.post(ApiUrl.WJADD, map, new BaseCallback() {
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

    protected void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(StringUtils.getString(R.string.qrsc));
        builder.setTitle("提示");
        builder.setPositiveButton("确认", (dialog, which) -> {
            delete();
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void put(Map map) {
        OkGoUtils.put(ApiUrl.WJDETAILS + id, map, new BaseCallback() {
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

    private void delete() {
        OkGoUtils.delete(ApiUrl.WJDETAILS + id, new HashMap(), new BaseCallback() {
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