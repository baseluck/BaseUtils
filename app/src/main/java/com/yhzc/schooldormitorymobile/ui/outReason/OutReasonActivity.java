package com.yhzc.schooldormitorymobile.ui.outReason;

import android.os.Bundle;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityOutReasonBinding;
import com.yhzc.schooldormitorymobile.utils.Cache;


/**
 * @描述: 外出事由提交
 * @创建日期: 2021/4/14 11:20
 * @author: ProcyonLotor
 */
public class OutReasonActivity extends BaseActivity<OutReasonViewModel, ActivityOutReasonBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_out_reason;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBind.etSignLy.setText(Cache.getWqly());
        mViewDataBind.tvWcsy.setOnClickListener(v -> {
            if (StringUtils.isEmpty(mViewDataBind.etSignLy.getText().toString().trim())) {
                ToastUtils.showShort("理由不能未空");
                return;
            }
            Cache.saveWqly(mViewDataBind.etSignLy.getText().toString().trim());
            finish();
        });
    }


    @Override
    protected int initTitle() {
        return R.string.sign_wcsy;
    }
}