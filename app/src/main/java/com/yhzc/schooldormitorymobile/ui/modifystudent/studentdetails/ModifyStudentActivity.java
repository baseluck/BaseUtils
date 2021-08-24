package com.yhzc.schooldormitorymobile.ui.modifystudent.studentdetails;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.previewlibrary.ZoomMediaLoader;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.luck.basemodule.base.BaseActivity;
import com.luck.basemodule.utils.PictureUtils;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.bean.UploadModel;
import com.yhzc.schooldormitorymobile.databinding.ActivityModifyStudentBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.backbedroom.send.StudentModel;
import com.yhzc.schooldormitorymobile.ui.modifystudent.ModifyStudentViewModel;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;
import com.luck.basemodule.utils.TestImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/19 17:28
 * @描述: 学生信息
 */
public class ModifyStudentActivity extends BaseActivity<ModifyStudentViewModel, ActivityModifyStudentBinding> {

    private RxPermissions mRxPermissions;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private String id;
    private int type = 1;
    private String fId, sId, tId;
    private String pic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_student;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        ZoomMediaLoader.getInstance().init(new TestImageLoader());
        mViewModel.getJg("-1");
        mRxPermissions = new RxPermissions(this);
        id = getIntent().getStringExtra("id");
        mViewModel.getStudnet(id);
        mViewModel.getStudentModel().observe(this, studentModel -> {
            if (studentModel != null) {
                StudentModel.DataBean data = studentModel.getData();
                Glide.with(this)
                        .load(Cache.getHttp() + studentModel.getData().getVcIdPhoto())
                        .into(mViewDataBind.ivHead);
                mViewDataBind.etName.setText(data.getVcRealName());
                mViewDataBind.etXjh.setText(data.getVcSchoolNum());
                mViewDataBind.etSex.setText(data.getVcSex());
                mViewDataBind.etCsrq.setText(TimeUtils.millis2String(data.getDtBirthday(), TimeUtils.getSafeDateFormat("yyyy-MM-dd")));
                mViewDataBind.etJg.setText(data.getVcNativePlaceAll());
                mViewDataBind.etXzz.setText(data.getVcAddress());
                mViewDataBind.etMz.setText(data.getVcNation());
                mViewDataBind.etXslb.setText(data.getVcType());
                if (data.getIntYear()!=0) {
                    mViewDataBind.etRxnf.setText(String.valueOf(data.getIntYear()));
                }
                mViewDataBind.etRxsj.setText(TimeUtils.millis2String(data.getDtEnrol(), TimeUtils.getSafeDateFormat("yyyy-MM-dd")));
                mViewDataBind.etBysj.setText(TimeUtils.millis2String(data.getDtGraduate(), TimeUtils.getSafeDateFormat("yyyy-MM-dd")));
                mViewDataBind.etJzdh.setText(data.getVcParentPhone());
            }
        });

        mViewDataBind.etSex.setOnClickListener(v -> getSex());
        mViewDataBind.etCsrq.setOnClickListener(v -> showCsrq());
        mViewDataBind.etJg.setOnClickListener(v -> showAddress());
        mViewDataBind.etMz.setOnClickListener(v -> getMz());
        mViewDataBind.etXslb.setOnClickListener(v -> showLb());
        mViewDataBind.etRxnf.setOnClickListener(v -> showNf());
        mViewDataBind.etRxsj.setOnClickListener(v -> showRxsj());
        mViewDataBind.etBysj.setOnClickListener(v -> showBysj());

        showRightTextAndOnClickListener("提交", v -> update());

        mViewDataBind.xszp.setOnClickListener(v -> mRxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        seletedImage();
                    } else {
                        ToastUtils.showShort("需要相应权限");
                    }
                }));


    }

    private void update() {
        Map<String, String> map = new HashMap<>();
        if (!StringUtils.isEmpty(pic)) {
            map.put("vcIdPhoto", pic);
        }
        map.put("vcSchoolNum", mViewDataBind.etXjh.getText().toString().trim());
        map.put("vcRealName", mViewDataBind.etName.getText().toString().trim());
        map.put("vcSex", mViewDataBind.etSex.getText().toString().trim());
        if (!StringUtils.isEmpty(mViewDataBind.etCsrq.getText().toString().trim())) {
            map.put("dtBirthday", TimeUtils.string2Millis(mViewDataBind.etCsrq.getText().toString().trim(),TimeUtils.getSafeDateFormat("yyyy-MM-dd")) + "");
        }
        if (!StringUtils.isEmpty(fId)) {
            map.put("vcNativePlace", fId + "," + sId + "," + tId);
        }
        map.put("vcAddress", mViewDataBind.etXzz.getText().toString().trim());
        map.put("vcNation", mViewDataBind.etMz.getText().toString().trim());
        map.put("vcType", mViewDataBind.etXslb.getText().toString().trim());
        if (!StringUtils.isEmpty(mViewDataBind.etRxnf.getText().toString().trim())) {
            map.put("intYear", mViewDataBind.etRxnf.getText().toString().trim());
        }
        map.put("vcParentPhone", mViewDataBind.etJzdh.getText().toString().trim());
        if (!StringUtils.isEmpty(mViewDataBind.etRxsj.getText().toString().trim())) {
            map.put("dtEnrol", TimeUtils.string2Millis(mViewDataBind.etRxsj.getText().toString().trim(),TimeUtils.getSafeDateFormat("yyyy-MM-dd")) + "");
        }
        if (!StringUtils.isEmpty(mViewDataBind.etBysj.getText().toString().trim())) {
            map.put("dtGraduate", TimeUtils.string2Millis(mViewDataBind.etBysj.getText().toString().trim(),TimeUtils.getSafeDateFormat("yyyy-MM-dd")) + "");
        }

        OkGoUtils.put(ApiUrl.GXXS + id, map, new BaseCallback() {
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

    private void seletedImage() {
        PictureUtils.createImage(this, 1, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                File imageFile = PictureUtils.getImageFile(result);
                if (imageFile != null) {
                    uploadImg(imageFile);
                }
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort("取消选择");
            }
        });
    }


    private void uploadImg(File image) {
        OkGoUtils.upFile(ApiUrl.UPLOAD, image, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                UploadModel upLoadBean = GsonUtils.fromJson(callback, UploadModel.class);
                if (upLoadBean.getData() != null) {
                    pic = upLoadBean.getData().getPath();
                    Glide.with(ModifyStudentActivity.this)
                            .load(Cache.getHttp() + pic)
                            .into(mViewDataBind.ivHead);
                }
            }

            @Override
            protected void onError(String error) {
                dismissLoading();
                ToastUtils.showShort(error);
            }

        });
    }

    private void updateHead(String img) {
        Map<String, String> map = new HashMap<>();
        map.put("stuId", id);
        map.put("img", img);
        OkGoUtils.post(ApiUrl.UPLOADSP, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {

            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }

    private void getSex() {
        hideInput();
        OkGoUtils.get(ApiUrl.GETSEX, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                SexModel sexModel = GsonUtils.fromJson(callback, SexModel.class);
                showSex(sexModel.getData().getList());
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    private void getMz() {
        hideInput();
        OkGoUtils.get(ApiUrl.GETMZ, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                SexModel sexModel = GsonUtils.fromJson(callback, SexModel.class);
                showMz(sexModel.getData().getList());
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    private void showSex(List<SexModel.DataBean.ListBean> listBeans) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String fullName = listBeans.get(options1).getFullName();
            mViewDataBind.etSex.setText(fullName);
        })
                .setTitleText("选择性别")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(listBeans);//三级选择器
        pvOptions.show();
    }

    private void showMz(List<SexModel.DataBean.ListBean> listBeans) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String fullName = listBeans.get(options1).getFullName();
            mViewDataBind.etMz.setText(fullName);
        })
                .setTitleText("选择民族")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(listBeans);//三级选择器
        pvOptions.show();
    }


    private void showLb() {
        hideInput();
        List<String> lb = new ArrayList<>();
        lb.add("正常升学");
        lb.add("借读");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String fullName = lb.get(options1);
            mViewDataBind.etXslb.setText(fullName);
        })
                .setTitleText("选择类别")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(lb);//三级选择器
        pvOptions.show();
    }


    private void showAddress() {
        hideInput();
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(ActivityUtils.getTopActivity()).inflate(R.layout.dialog_address, null);
        TextView tvSf = dialogView.findViewById(R.id.tv_sf);
        TextView tvSq = dialogView.findViewById(R.id.tv_sq);
        ImageView ivClose = dialogView.findViewById(R.id.iv_close);
        TextView tvQu = dialogView.findViewById(R.id.tv_qu);
        RecyclerView rv = dialogView.findViewById(R.id.rv_sf);
        final Dialog dialog = new Dialog(ActivityUtils.getTopActivity(), R.style.ShareDialog);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(dialogView);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        Resources resources = ActivityUtils.getTopActivity().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        JgAdapter jgAdapter = new JgAdapter(R.layout.item_dialog_address);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(jgAdapter);
        mViewModel.getJgModel().observe(this, jgModel -> {
            if (jgModel != null) {
                jgAdapter.setList(jgModel.getData().getList());
            }
        });

        jgAdapter.setOnItemClickListener((adapter, view, position) -> {
            JgModel.DataBean.ListBean listBean = (JgModel.DataBean.ListBean) adapter.getData().get(position);
            if (type == 1) {
                fId = listBean.getId();
                sId = "";
                tId = "";
                tvSf.setText(listBean.getFullName());
                tvSq.setText("");
                tvQu.setText("");
                jgAdapter.setList(null);
                mViewModel.getJg(listBean.getId());
                type = 2;
            } else if (type == 2) {
                sId = listBean.getId();
                tId = "";
                tvSq.setText(listBean.getFullName());
                tvQu.setText("");
                jgAdapter.setList(null);
                mViewModel.getJg(listBean.getId());
                type = 3;
            } else if (type == 3) {
                tId = listBean.getId();
                tvQu.setText(listBean.getFullName());
                String jg = tvSf.getText().toString().trim() + tvSq.getText().toString().trim() + tvQu.getText().toString().trim();
                mViewDataBind.etJg.setText(jg);
                dialog.dismiss();
            }
        });

        tvSf.setOnClickListener(v -> {
            if (type != 1) {
                type = 1;
                mViewModel.getJg("-1");
            }
        });
        tvSq.setOnClickListener(v -> {
            if (type != 2 && !StringUtils.isEmpty(fId)) {
                type = 2;
                mViewModel.getJg(fId);
            }
        });
        ivClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showCsrq() {
        hideInput();
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            String time = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy-MM-dd"));
            mViewDataBind.etCsrq.setText(time);
        }).setType(new boolean[]{true, true, true, false, false, false})//分别对应年月日时分秒，默认全部显示
                .setTitleText("选择出生日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(ColorUtils.getColor(R.color.color333333))//标题文字颜色
                .setSubmitColor(ColorUtils.getColor(R.color.color549BFF))//确定按钮文字颜色
                .setCancelColor(ColorUtils.getColor(R.color.color333333))//取消按钮文字颜色
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();

        timePickerView.show();
    }

    private void showNf() {
        hideInput();
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            String time = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy"));
            mViewDataBind.etRxnf.setText(time);
        }).setType(new boolean[]{true, false, false, false, false, false})//分别对应年月日时分秒，默认全部显示
                .setTitleText("选择入学年份")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(ColorUtils.getColor(R.color.color333333))//标题文字颜色
                .setSubmitColor(ColorUtils.getColor(R.color.color549BFF))//确定按钮文字颜色
                .setCancelColor(ColorUtils.getColor(R.color.color333333))//取消按钮文字颜色
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();

        timePickerView.show();
    }

    private void showRxsj() {
        hideInput();
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            String time = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy-MM-dd"));
            mViewDataBind.etRxsj.setText(time);
        }).setType(new boolean[]{true, true, true, false, false, false})//分别对应年月日时分秒，默认全部显示
                .setTitleText("选择入学日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(ColorUtils.getColor(R.color.color333333))//标题文字颜色
                .setSubmitColor(ColorUtils.getColor(R.color.color549BFF))//确定按钮文字颜色
                .setCancelColor(ColorUtils.getColor(R.color.color333333))//取消按钮文字颜色
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();

        timePickerView.show();
    }

    private void showBysj() {
        hideInput();
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            String time = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy-MM-dd"));
            mViewDataBind.etBysj.setText(time);
        }).setType(new boolean[]{true, true, true, false, false, false})//分别对应年月日时分秒，默认全部显示
                .setTitleText("选择毕业日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(ColorUtils.getColor(R.color.color333333))//标题文字颜色
                .setSubmitColor(ColorUtils.getColor(R.color.color549BFF))//确定按钮文字颜色
                .setCancelColor(ColorUtils.getColor(R.color.color333333))//取消按钮文字颜色
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        timePickerView.show();
    }

    @Override
    protected int initTitle() {
        return R.string.xsxx;
    }
}