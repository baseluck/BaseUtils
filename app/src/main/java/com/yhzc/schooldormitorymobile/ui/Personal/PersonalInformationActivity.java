package com.yhzc.schooldormitorymobile.ui.Personal;

import android.os.Bundle;

import com.blankj.utilcode.util.StringUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityPersonalInformationBinding;
import com.yhzc.schooldormitorymobile.utils.Cache;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/21 13:14
 * @描述: 个人信息
 */
public class PersonalInformationActivity extends BaseActivity<PersonalViewModel, ActivityPersonalInformationBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBind.tvName.setText(isEmptyString(Cache.getLogin().getData().getVcRealName()));
        mViewDataBind.tvSex.setText(isEmptyString(Cache.getLogin().getData().getVcSex()));
        mViewDataBind.tvEmail.setText(isEmptyString(Cache.getLogin().getData().getVcEmail()));
        mViewDataBind.tvLxdh.setText(isEmptyString(Cache.getLogin().getData().getVcPhone()));
        mViewDataBind.tvBm.setText(isEmptyString(Cache.getLogin().getData().getVcDept()));
        mViewDataBind.tvZw.setText(isEmptyString(Cache.getLogin().getData().getVcPostion()));
        mViewDataBind.tvKm.setText(isEmptyString(Cache.getLogin().getData().getVcZjkm()));
    }

    private String isEmptyString(String string) {
        return StringUtils.isEmpty(string) ? "无" : string;
    }

    @Override
    protected int initTitle() {
        return R.string.grxx;
    }
}