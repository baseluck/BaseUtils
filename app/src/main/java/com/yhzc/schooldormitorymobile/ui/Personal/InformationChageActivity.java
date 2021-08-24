package com.yhzc.schooldormitorymobile.ui.Personal;

import android.os.Bundle;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityInformationChageBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.utils.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/21 11:03
 * @描述: 修改个人信息
 */
public class InformationChageActivity extends BaseActivity<PersonalViewModel, ActivityInformationChageBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_chage;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBind.tvSend.setOnClickListener(v -> upUser());
    }

    private void upUser() {
        String phone = mViewDataBind.etPhone.getText().toString().trim();
        String email = mViewDataBind.etEmail.getText().toString().trim();
        Map<String, String> map = new HashMap<>(4);
        map.put("vcPhone", phone);
        map.put("vcEmail", email);
        OkGo.<String>post(ApiUrl.UPUSER)
                .headers("Authorization", Cache.getToken())
                .params(map)
                .execute(new BaseCallback() {
                    @Override
                    protected void onSuccess(String callback) {
                        BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                        if (baseBean.getCode() == ApiUrl.SUCCESS) {
                            ToastUtils.showShort("修改成功");
                            finish();
                        }
                    }

                    @Override
                    protected void onError(String error) {
                        ToastUtils.showShort(error);
                    }
                });
    }

    @Override
    protected int initTitle() {
        return R.string.xglxfs;
    }
}