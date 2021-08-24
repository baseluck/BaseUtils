package com.yhzc.schooldormitorymobile.ui.askLeaveDetails;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.previewlibrary.ZoomMediaLoader;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityAskLeaveDetailsBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.SelectUserModel;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;
import com.luck.basemodule.utils.UrlImageLoader;
import com.luck.basemodule.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/14 9:56
 * @描述: 请假详情
 */
public class AskLeaveDetailsActivity extends BaseActivity<LeaveDetailsViewModel, ActivityAskLeaveDetailsBinding> {

    private String vcTaskId;
    private String workType;
    private final List<SelectUserModel.DataBean.ListBean> options1Items = new ArrayList<>();
    private final ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private final ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ImgShowAdapter mImgShowAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ask_leave_details;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        ZoomMediaLoader.getInstance().init(new UrlImageLoader(Cache.getHttp()));
        vcTaskId = getIntent().getStringExtra("vcTaskId");
        workType = getIntent().getStringExtra("workType");
        mViewDataBind.tvBl.setVisibility(View.GONE);
        mViewDataBind.tvWp.setVisibility(View.GONE);

        mViewModel.initSelectUser(this);
        mViewModel.initLeaveDetails(vcTaskId, workType);
        initImgRv();
        initRv();
    }

    private void initImgRv() {
        mImgShowAdapter = new ImgShowAdapter(R.layout.item_notice_select_img, null);
        mViewDataBind.rvFj.setLayoutManager(new GridLayoutManager(this, 4));
        mViewDataBind.rvFj.setAdapter(mImgShowAdapter);
        mImgShowAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<String> data= (List<String>) adapter.getData();
            Utils.showBigImg(data,position);
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
                mViewDataBind.tvLy.setText(leaveDetailsModel.getData().getTextContent());
                List<String> list = Arrays.asList(leaveDetailsModel.getData().getVcAttachmentPath().split(","));
                mImgShowAdapter.setList(list);
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.xq;
    }

    private void showBl(Context context) {
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_leave_bl, null);
        ImageView ivClose = dialogView.findViewById(R.id.iv_close);
        EditText etLy = dialogView.findViewById(R.id.et_ly);
        TextView tvYes = dialogView.findViewById(R.id.tv_yes);
        TextView tvNo = dialogView.findViewById(R.id.tv_no);
        final Dialog dialog = new Dialog(context, R.style.ShareDialog);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        Resources resources = context.getResources();
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
            approve("1", etLy.getText().toString().trim(), dialog);
        });
        tvNo.setOnClickListener(v -> {
            if (StringUtils.isEmpty(etLy.getText().toString().trim())) {
                ToastUtils.showShort("理由不能为空");
                return;
            }
            approve("2", etLy.getText().toString().trim(), dialog);
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