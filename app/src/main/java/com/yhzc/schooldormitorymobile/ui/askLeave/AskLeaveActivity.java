package com.yhzc.schooldormitorymobile.ui.askLeave;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ActivityUtils;
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
import com.luck.basemodule.spinner.SpinnerDialog;
import com.luck.basemodule.utils.PictureUtils;
import com.luck.basemodule.utils.TestImageLoader;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityAskLeaveBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.leaveList.mine.MineLeaveListActivity;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.SelectImgAdapter;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UpLoadBean;
import com.luck.basemodule.utils.OkGoUtils;
import com.luck.basemodule.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @描述: 请假
 * @创建日期: 2021/4/16 13:22
 * @author: ProcyonLotor
 */

public class AskLeaveActivity extends BaseActivity<AskLeaveViewModel, ActivityAskLeaveBinding> {

    private LeaveItemAdapter mLeaveItemAdapter;
    private ApproveAdapter mApproveAdapter;
    private String startTime, endTime, classId, studentId, leaveTypeId;
    private String workType = "leaveProcess";
    private final List<String> images = new ArrayList<>();
    private SelectImgAdapter mSelectImgAdapter;
    private RxPermissions mRxPermissions;

    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.initLeaveApprove("");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ask_leave;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        ZoomMediaLoader.getInstance().init(new TestImageLoader());
        mRxPermissions = new RxPermissions(this);
        initStudentItem();
        initItem();
        initRv();
        initImgRv();
        mViewDataBind.tvWcsy.setOnClickListener(v -> {
            if (StringUtils.equals(workType, "leaveProcess")) {
                sendStudentLeave();
            } else if (StringUtils.equals(workType, "teacherLeaveProcess")) {
                sendTeacherLeave();
            } else if (StringUtils.equals(workType, "commLeaveProcess")) {
                sendCommonLeave();
            } else {
                sendLogisticsLeave();
            }
        });

        mViewDataBind.ivAdd.setOnClickListener(v -> mRxPermissions.request(NEEDED_PERMISSIONS)
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

    private void upLoadImage(int type) {
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
                            if (type == 1) {
                                sendStudent();
                            } else if (type == 4) {
                                sendCommon();
                            } else if (type == 2) {
                                sendTeacher();
                            } else {
                                sendLogistics();
                            }
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
            if (type == 1) {
                sendStudent();
            } else if (type == 2) {
                sendTeacher();
            } else if (type == 4) {
                sendCommon();
            } else {
                sendLogistics();
            }
        }
    }

    private void sendStudentLeave() {
        if (StringUtils.isEmpty(leaveTypeId)) {
            ToastUtils.showShort("请选择请假类型");
            return;
        }
        if (StringUtils.isEmpty(classId)) {
            ToastUtils.showShort("请选择班级");
            return;
        }
        if (StringUtils.isEmpty(studentId)) {
            ToastUtils.showShort("请选择学生");
            return;
        }
        if (StringUtils.isEmpty(startTime)) {
            ToastUtils.showShort("请选择开始时间");
            return;
        }
        if (StringUtils.isEmpty(endTime)) {
            ToastUtils.showShort("请选择结束时间");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.etSignLy.getText().toString().trim())) {
            ToastUtils.showShort("请输入请假理由");
            return;
        }
        upLoadImage(1);
    }

    private void sendStudent() {
        updateLoadingMsg("正在发布");
        Map<String, String> map = new HashMap<>();
        map.put("workType", "leaveProcess");
        map.put("vcGradeClassId", classId);
        map.put("vcStudentId", studentId);
        map.put("vcType", leaveTypeId);
        map.put("textContent", mViewDataBind.etSignLy.getText().toString().trim());
        map.put("dtStartTime", startTime);
        map.put("dtEndTime", endTime);
        if (images.size() != 0) {
            StringBuilder str2 = new StringBuilder();
            for (Iterator<String> iterator = images.iterator(); iterator.hasNext(); ) {
                String string = iterator.next();
                str2.append(string);
                if (iterator.hasNext()) {
                    str2.append(",");
                }
            }
            map.put("vcAttachmentPath", str2.toString().trim());
        }
        OkGoUtils.post(ApiUrl.LEAVESEND, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dismissLoading();
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                if (baseBean.getCode() == ApiUrl.SUCCESS) {
                    ToastUtils.showShort("提交成功");
                    ActivityUtils.startActivity(MineLeaveListActivity.class);
                }
            }

            @Override
            protected void onError(String error) {
                dismissLoading();
                ToastUtils.showShort(error);
            }
        });

    }

    private void sendTeacherLeave() {
        if (StringUtils.isEmpty(leaveTypeId)) {
            ToastUtils.showShort("请选择请假类型");
            return;
        }
        if (StringUtils.isEmpty(startTime)) {
            ToastUtils.showShort("请选择开始时间");
            return;
        }
        if (StringUtils.isEmpty(endTime)) {
            ToastUtils.showShort("请选择结束时间");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.etSignLy.getText().toString().trim())) {
            ToastUtils.showShort("请输入请假理由");
            return;
        }
        upLoadImage(2);
    }

    private void sendCommonLeave() {
        if (StringUtils.isEmpty(leaveTypeId)) {
            ToastUtils.showShort("请选择请假类型");
            return;
        }
        if (StringUtils.isEmpty(startTime)) {
            ToastUtils.showShort("请选择开始时间");
            return;
        }
        if (StringUtils.isEmpty(endTime)) {
            ToastUtils.showShort("请选择结束时间");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.etSignLy.getText().toString().trim())) {
            ToastUtils.showShort("请输入请假理由");
            return;
        }
        upLoadImage(4);
    }

    private void sendLogisticsLeave() {
        if (StringUtils.isEmpty(leaveTypeId)) {
            ToastUtils.showShort("请选择请假类型");
            return;
        }
        if (StringUtils.isEmpty(startTime)) {
            ToastUtils.showShort("请选择开始时间");
            return;
        }
        if (StringUtils.isEmpty(endTime)) {
            ToastUtils.showShort("请选择结束时间");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.etSignLy.getText().toString().trim())) {
            ToastUtils.showShort("请输入请假理由");
            return;
        }
        upLoadImage(3);
    }

    private void sendTeacher() {

        updateLoadingMsg("正在发布");

        Map<String, String> map = new HashMap<>();
        map.put("workType", "teacherLeaveProcess");
        map.put("vcType", leaveTypeId);
        map.put("textContent", mViewDataBind.etSignLy.getText().toString().trim());
        map.put("dtStartTime", startTime);
        map.put("dtEndTime", endTime);
        if (images.size() != 0) {
            StringBuilder str2 = new StringBuilder();
            for (Iterator<String> iterator = images.iterator(); iterator.hasNext(); ) {
                String string = iterator.next();
                str2.append(string);
                if (iterator.hasNext()) {
                    str2.append(",");
                }
            }
            map.put("vcAttachmentPath", str2.toString().trim());
        }

        OkGoUtils.post(ApiUrl.LEAVESEND, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dismissLoading();
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                if (baseBean.getCode() == ApiUrl.SUCCESS) {
                    ToastUtils.showShort("提交成功");
                    ActivityUtils.startActivity(MineLeaveListActivity.class);
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                dismissLoading();
            }
        });

    }

    private void sendCommon() {
        updateLoadingMsg("正在发布");
        Map<String, Object> map = new HashMap<>();
        map.put("workType", "commLeaveProcess");
        Map<String, String> mapContent = new HashMap<>();

        mapContent.put("vcType", leaveTypeId);
        mapContent.put("textContent", mViewDataBind.etSignLy.getText().toString().trim());
        mapContent.put("dtStartTime", startTime);
        mapContent.put("dtEndTime", endTime);
        if (images.size() != 0) {
            StringBuilder str2 = new StringBuilder();
            for (Iterator<String> iterator = images.iterator(); iterator.hasNext(); ) {
                String string = iterator.next();
                str2.append(string);
                if (iterator.hasNext()) {
                    str2.append(",");
                }
            }
            mapContent.put("vcAttachmentPath", str2.toString().trim());
        }
        map.put("vacationCrForm",mapContent);
        OkGoUtils.post(ApiUrl.LEAVESEND, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dismissLoading();
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                if (baseBean.getCode() == ApiUrl.SUCCESS) {
                    ToastUtils.showShort("提交成功");
                    ActivityUtils.startActivity(MineLeaveListActivity.class);
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                dismissLoading();
            }
        });

    }

    private void sendLogistics() {
        updateLoadingMsg("正在发布");
        Map<String, String> map = new HashMap<>();
        map.put("workType", "logisticsLeaveProcess");
        map.put("vcType", leaveTypeId);
        map.put("textContent", mViewDataBind.etSignLy.getText().toString().trim());
        map.put("dtStartTime", startTime);
        map.put("dtEndTime", endTime);
        if (images.size() != 0) {
            StringBuilder str2 = new StringBuilder();
            for (Iterator<String> iterator = images.iterator(); iterator.hasNext(); ) {
                String string = iterator.next();
                str2.append(string);
                if (iterator.hasNext()) {
                    str2.append(",");
                }
            }
            map.put("vcAttachmentPath", str2.toString().trim());
        }

        OkGoUtils.post(ApiUrl.LEAVESEND, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dismissLoading();
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                if (baseBean.getCode() == ApiUrl.SUCCESS) {
                    ToastUtils.showShort("提交成功");
                    ActivityUtils.startActivity(MineLeaveListActivity.class);
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }

    private void initRv() {
        mApproveAdapter = new ApproveAdapter(R.layout.item_leave_approve, null);
        mViewDataBind.rvShlc.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvShlc.setAdapter(mApproveAdapter);
        mViewModel.getLeaveApproveModel().observe(this, leaveApproveModel -> {
            if (leaveApproveModel != null) {
                if (leaveApproveModel.getCode() == ApiUrl.SUCCESS) {
                    mApproveAdapter.setList(leaveApproveModel.getData());
                }
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.leave_sq;
    }

    private void initStudentItem() {
        startTime = "";
        endTime = "";
        classId = "";
        studentId = "";
        leaveTypeId = "";
        workType = "leaveProcess";
        List<LeaveItemModel> leaveItemModels = new ArrayList<>();
        leaveItemModels.add(new LeaveItemModel("请假类型", "", "学生请假"));
        leaveItemModels.add(new LeaveItemModel("假期类型", "", "请选择"));
        leaveItemModels.add(new LeaveItemModel("选择班级", "", "请选择"));
        leaveItemModels.add(new LeaveItemModel("选择学生", "", "请选择"));
        leaveItemModels.add(new LeaveItemModel("开始时间", "", "请选择"));
        leaveItemModels.add(new LeaveItemModel("结束时间", "", "请选择"));
        mViewModel.setLeaveItemModels(leaveItemModels);
    }

    private void initTeacherItem() {
        startTime = "";
        endTime = "";
        classId = "";
        studentId = "";
        leaveTypeId = "";
        workType = "teacherLeaveProcess";
        List<LeaveItemModel> leaveItemModels = new ArrayList<>();
        leaveItemModels.add(new LeaveItemModel("请假类型", "", "老师请假"));
        leaveItemModels.add(new LeaveItemModel("假期类型", "", "请选择"));
        leaveItemModels.add(new LeaveItemModel("开始时间", "", "请选择"));
        leaveItemModels.add(new LeaveItemModel("结束时间", "", "请选择"));
        mViewModel.setLeaveItemModels(leaveItemModels);
    }

    private void initCommonItem() {
        startTime = "";
        endTime = "";
        classId = "";
        studentId = "";
        leaveTypeId = "";
        workType = "commLeaveProcess";
        List<LeaveItemModel> leaveItemModels = new ArrayList<>();
        leaveItemModels.add(new LeaveItemModel("请假类型", "", "通用请假"));
        leaveItemModels.add(new LeaveItemModel("假期类型", "", "请选择"));
        leaveItemModels.add(new LeaveItemModel("开始时间", "", "请选择"));
        leaveItemModels.add(new LeaveItemModel("结束时间", "", "请选择"));
        mViewModel.setLeaveItemModels(leaveItemModels);
    }

    private void initLogisticsItem() {
        startTime = "";
        endTime = "";
        classId = "";
        studentId = "";
        leaveTypeId = "";
        workType = "logisticsLeaveProcess";
        List<LeaveItemModel> leaveItemModels = new ArrayList<>();
        leaveItemModels.add(new LeaveItemModel("请假类型", "", "后勤请假"));
        leaveItemModels.add(new LeaveItemModel("假期类型", "", "请选择"));
        leaveItemModels.add(new LeaveItemModel("开始时间", "", "请选择"));
        leaveItemModels.add(new LeaveItemModel("结束时间", "", "请选择"));
        mViewModel.setLeaveItemModels(leaveItemModels);
    }


    private void initItem() {
        mViewDataBind.rvAskLeave.setLayoutManager(new LinearLayoutManager(this));
        mLeaveItemAdapter = new LeaveItemAdapter(R.layout.item_leave_item, null);
        mViewDataBind.rvAskLeave.setAdapter(mLeaveItemAdapter);
        mViewModel.getLeaveItemModels().observe(this, leaveItemModels1 -> mLeaveItemAdapter.setList(leaveItemModels1));
        mLeaveItemAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<LeaveItemModel> datas = (List<LeaveItemModel>) adapter.getData();
            String title = datas.get(position).getTitle();
            switch (title) {
                case "请假类型":
                    showLeaveTypeSelect(title, position);
                    break;
                case "假期类型":
                    initLeaveType(title, position);
                    break;
                case "选择班级":
                    initClass(title, position);
                    break;
                case "选择学生":
                    if (StringUtils.isEmpty(classId)) {
                        ToastUtils.showShort("请先选择班级");
                    } else {
                        initStudent(title, position);
                    }
                    break;
                case "开始时间":
                case "结束时间":
                    showTimePickerView(position, title);
                    break;
            }

        });
        showRightImgAndOnClickListener(0, v -> ActivityUtils.startActivity(MineLeaveListActivity.class));
    }

    private void showTimePickerView(int position, String title) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 1, 1);
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            String time = TimeUtils.date2String(date);
            if (StringUtils.equals(title, "开始时间")) {
                startTime = time;
            } else if (StringUtils.equals(title, "结束时间")) {
                endTime = time;
            }
            LeaveItemModel leaveItemModel = new LeaveItemModel(title, "", time);
            mLeaveItemAdapter.setData(position, leaveItemModel);
        }).setType(new boolean[]{true, true, true, true, true, true})//分别对应年月日时分秒，默认全部显示
                .setTitleText("选择" + title)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(ColorUtils.getColor(R.color.color333333))//标题文字颜色
                .setSubmitColor(ColorUtils.getColor(R.color.color549BFF))//确定按钮文字颜色
                .setCancelColor(ColorUtils.getColor(R.color.color333333))//取消按钮文字颜色
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();

        timePickerView.show();
    }

    private void initLeaveType(String title, int position) {
        showLoading("加载假期类型");
        OkGoUtils.get(ApiUrl.LEAVETYPE, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dismissLoading();
                LeaveTypeModel leaveTypeModel = GsonUtils.fromJson(callback, LeaveTypeModel.class);
                if (leaveTypeModel.getCode() == ApiUrl.SUCCESS) {
                    if (leaveTypeModel.getData().getList() != null && leaveTypeModel.getData().getList().size() != 0) {
                        showLeaveType(leaveTypeModel.getData().getList(), title, position);
                    } else {
                        ToastUtils.showShort("假期类型为空");
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

    private void showLeaveType(List<LeaveTypeModel.DataBean.ListBean> listBeans, String title, int position) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            leaveTypeId = listBeans.get(options1).getId();
            String fullName = listBeans.get(options1).getFullName();
            LeaveItemModel leaveItemModel = new LeaveItemModel(title, "", fullName);
            mLeaveItemAdapter.setData(position, leaveItemModel);
        })
                .setTitleText("选择假期类型")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(listBeans);//三级选择器
        pvOptions.show();
    }

    private void showLeaveTypeSelect(String title, int position) {
        List<String> leaveType = new ArrayList<>();
        leaveType.add("学生请假");
        leaveType.add("老师请假");
        leaveType.add("后勤请假");
        leaveType.add("通用请假");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String fullName = leaveType.get(options1);
            if (StringUtils.equals(fullName, "学生请假")) {
                initStudentItem();
            } else if (StringUtils.equals(fullName, "老师请假")) {
                initTeacherItem();
            } else if (StringUtils.equals(fullName, "通用请假")) {
                initCommonItem();
            } else {
                initLogisticsItem();
            }
        })
                .setTitleText("选择请假类型")
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(leaveType);
        pvOptions.show();
    }

    private void initClass(String title, int position) {
        showLoading("加载班级列表");
        OkGoUtils.get(ApiUrl.LEAVECLASSLIST, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dismissLoading();
                LeaveStudentModel leaveStudentModel = GsonUtils.fromJson(callback, LeaveStudentModel.class);
                if (leaveStudentModel.getCode() == ApiUrl.SUCCESS) {
                    if (leaveStudentModel.getData() != null && leaveStudentModel.getData().size() != 0) {
                        showLeaveClass2(leaveStudentModel.getData(), title, position);
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

    private void showLeaveClass2(List<LeaveStudentModel.DataBean> dataBeans, String title, int position) {
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
            LeaveItemModel leaveItemModel = new LeaveItemModel(title, "", vcName);
            mLeaveItemAdapter.setData(position, leaveItemModel);
            mViewModel.initLeaveApprove(classId);
        });
        spinnerDialog.showSpinerDialog();
    }

    private void showLeaveClass(List<LeaveStudentModel.DataBean> dataBeans, String title, int position) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            classId = dataBeans.get(options1).getVcId();
            String vcName = dataBeans.get(options1).getVcName();
            LeaveItemModel leaveItemModel = new LeaveItemModel(title, "", vcName);
            mLeaveItemAdapter.setData(position, leaveItemModel);
        })
                .setTitleText("选择班级")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(dataBeans);//三级选择器
        pvOptions.show();
    }

    private void initStudent(String title, int position) {
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
                        showLeaveStudent2(leaveStudentModel.getData(), title, position);
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

    private void showLeaveStudent2(List<LeaveStudentModel.DataBean> dataBeans, String title, int position) {
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
            LeaveItemModel leaveItemModel = new LeaveItemModel(title, "", vcName);
            mLeaveItemAdapter.setData(position, leaveItemModel);
        });
        spinnerDialog.showSpinerDialog();
    }

    private void showLeaveStudent(List<LeaveStudentModel.DataBean> dataBeans, String title, int position) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            studentId = dataBeans.get(options1).getVcId();
            String vcName = dataBeans.get(options1).getVcName();
            LeaveItemModel leaveItemModel = new LeaveItemModel(title, "", vcName);
            mLeaveItemAdapter.setData(position, leaveItemModel);
        })
                .setTitleText("选择学生")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(dataBeans);//三级选择器
        pvOptions.show();
    }

    private void initImgRv() {
        mSelectImgAdapter = new SelectImgAdapter(R.layout.item_notice_select_img, null);
        mViewDataBind.rvFj.setLayoutManager(new GridLayoutManager(this, 4));
        mViewDataBind.rvFj.setAdapter(mSelectImgAdapter);
        mViewModel.getImages().observe(this, files -> {
            if (files != null) {
                mSelectImgAdapter.setList(files);
            }
        });

        mSelectImgAdapter.addChildClickViewIds(R.id.iv_delete, R.id.iv_img);
        mSelectImgAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<File> data = adapter.getData();
            if (view.getId() == R.id.iv_delete) {
                data.remove(position);
                mViewModel.setImages(data);
            } else if (view.getId() == R.id.iv_img) {
                Utils.showBigImgFile(data, position);
            }
        });
    }

}