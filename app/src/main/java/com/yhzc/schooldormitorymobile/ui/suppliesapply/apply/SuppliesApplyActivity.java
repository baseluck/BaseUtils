package com.yhzc.schooldormitorymobile.ui.suppliesapply.apply;

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
import com.yhzc.schooldormitorymobile.databinding.ActivitySuppliesApplyBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.SuppliesApplyViewModel;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/20 10:14
 * @描述: 物资申请
 */
public class SuppliesApplyActivity extends BaseActivity<SuppliesApplyViewModel, ActivitySuppliesApplyBinding> {
    private WzslAdapter mWzslAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_supplies_apply;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewModel.initWzKc();
        initRv();
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
                    if (intNum < wzslModels.get(position).getKcNum()) {
                        intNum++;
                        wzslModels.get(position).setIntNum(intNum);
                    } else {
                        ToastUtils.showShort("库存剩余：" + wzslModels.get(position).getKcNum());
                    }
                } else if (view.getId() == R.id.iv_minus) {
                    if (intNum > 1) {
                        intNum--;
                        wzslModels.get(position).setIntNum(intNum);
                    }
                } else if (view.getId() == R.id.tv_num) {
                    List<String> data = new ArrayList<>();
                    for (int i = 1; i <= wzslModels.get(position).getKcNum(); i++) {
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
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(data);
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
        map.put("workType", "receiveApplyLeaveProcess");
        Map<String, Object> secondMap = new HashMap<>();
        secondMap.put("textContent", mViewDataBind.etSqyy.getText().toString().trim());
        secondMap.put("details", wzslModels);
        map.put("receiveCrForm", secondMap);
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