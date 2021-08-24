package com.yhzc.schooldormitorymobile.ui.suppliesapply.putapply;

import android.graphics.Color;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityPutApplyBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.SelectUserModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.SuppliesApplyViewModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.apply.WzListModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.apply.WzslAdapter;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.apply.WzslModel;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/20 10:14
 * @描述: 入库申请
 */
public class PutApplyActivity extends BaseActivity<SuppliesApplyViewModel, ActivityPutApplyBinding> {

    private WzslAdapter mWzslAdapter;
    private String userId;
    private final List<SelectUserModel.DataBean.ListBean> options1Items = new ArrayList<>();
    private final ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private final ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_put_apply;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewModel.initWzKc();
        mViewModel.initSelectUser();
        initRv();
        setTabTitle("入库申请");
        mViewDataBind.tvSelect.setOnClickListener(v -> showWzList());
        showRightTextAndOnClickListener("提交", v -> send());
    }

    private void initRv() {
        mWzslAdapter = new WzslAdapter(R.layout.item_supplies_apply);
        mViewDataBind.rvWz.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvWz.setAdapter(mWzslAdapter);
        mViewModel.getWzslModel().observe(this, wzslModels -> {
            if (wzslModels != null) {
                mWzslAdapter.setList(wzslModels);
            }
        });

        mWzslAdapter.addChildClickViewIds(R.id.iv_add, R.id.iv_minus, R.id.tv_num, R.id.tv_delete);
        mWzslAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<WzslModel> wzslModels = mViewModel.getWzslModel().getValue();
            if (wzslModels != null) {
                int intNum = wzslModels.get(position).getIntNum();
                if (view.getId() == R.id.iv_add) {
                    intNum++;
                    wzslModels.get(position).setIntNum(intNum);
                } else if (view.getId() == R.id.iv_minus) {
                    if (intNum > 1) {
                        intNum--;
                        wzslModels.get(position).setIntNum(intNum);
                    }
                } else if (view.getId() == R.id.tv_num) {
                    List<String> data = new ArrayList<>();
                    for (int i = 1; i <= 10000; i++) {
                        data.add(String.valueOf(i));
                    }
                    showScoreList(data, position, wzslModels);
                } else if (view.getId() == R.id.tv_delete) {
                    wzslModels.remove(position);
                }
                mViewModel.setWzslModel(wzslModels);
            }
        });
    }

    private void showScoreList(List<String> data, int position, List<WzslModel> wzslModels) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            int score = Integer.parseInt(data.get(options1));
            wzslModels.get(position).setIntNum(score);
            mViewModel.setWzslModel(wzslModels);
        })
                .setTitleText("选择数量")
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(data);
        pvOptions.show();
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

            showChargeUser(selectUserModel);
        }


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


            mViewDataBind.tvUser.setText(opt3tx);

            userId = id;

        })
                .setTitleText("选择签到负责人")
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }


    @Override
    protected int initTitle() {
        return R.string.wz_apply;
    }

    private void showWzList() {
        WzListModel wzListModel = mViewModel.getWzListModel().getValue();
        if (wzListModel == null) {
            ToastUtils.showShort("物资列表正在初始化，稍后再试");
            mViewModel.initWzKc();

        } else {
            if (wzListModel.getData() != null && wzListModel.getData().size() != 0) {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
                    List<WzslModel> wzslModels = mViewModel.getWzslModel().getValue();
                    if (wzslModels == null) {
                        wzslModels = new ArrayList<>();
                    }

                    boolean has = false;

                    for (WzslModel wzslModel : wzslModels) {
                        if (StringUtils.equals(wzslModel.getVcMaterialId(), wzListModel.getData().get(options1).getVcMaterialId())) {
                            has = true;
                        }

                    }

                    if (!has) {
                        WzListModel.DataBean dataBean = wzListModel.getData().get(options1);
                        WzslModel wzslModel = new WzslModel();
                        wzslModel.setIntNum(1);
                        wzslModel.setKcNum(dataBean.getIntNum());
                        wzslModel.setVcMaterialId(dataBean.getVcMaterialId());
                        wzslModel.setVcMaterialCode(dataBean.getVcCode());
                        wzslModel.setVcMaterialName(dataBean.getVcName());
                        wzslModel.setVcSpecification(dataBean.getVcSpecification());
                        wzslModel.setVcUnit(dataBean.getVcUnit());
                        wzslModels.add(wzslModel);
                        mViewModel.setWzslModel(wzslModels);
                    }


                })
                        .setTitleText("选择请假类型")
                        .setTextColorCenter(Color.BLACK)
                        .setContentTextSize(20)
                        .build();
                pvOptions.setPicker(wzListModel.getData());
                pvOptions.show();
            } else {
                ToastUtils.showShort("物资列表为空");
            }
        }


    }

    private void send() {
        if (StringUtils.isEmpty(mViewDataBind.etSqyy.getText().toString().trim())) {
            ToastUtils.showShort("申请原因不能为空");
            return;
        }

        List<WzslModel> wzslModels = mViewModel.getWzslModel().getValue();
        if (wzslModels == null || wzslModels.size() == 0) {
            ToastUtils.showShort("请选择物资");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("workType", "enterStockProcess");
        Map<String, Object> secondMap = new HashMap<>();
        secondMap.put("textMark", mViewDataBind.etSqyy.getText().toString().trim());
        if (!StringUtils.isEmpty(userId)) {
            secondMap.put("vcUserId", userId);
        }
        secondMap.put("details", wzslModels);
        map.put("stockRecordCrForm", secondMap);

        OkGoUtils.post(ApiUrl.LEAVESEND, map, new BaseCallback() {
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
}